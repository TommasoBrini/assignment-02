package ex2.gui;

import ex2.core.component.DataEvent;
import ex2.core.component.searcher.SearcherType;
import ex2.core.listener.HistoryListener;
import ex2.utils.ComboBoxUtils;
import ex2.utils.PanelUtils;
import ex2.worker.concrete.WorkerStrategy;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class GUIAnalysis extends JFrame implements HistoryListener {
    enum YType {
        TIME,
        DEPTH
    }

    private static final String TITLE = "Analysis Area";
    private static final Dimension PREFERRED_SIZE = new Dimension(600, 500);
    private static final String CATEGORY_AXIS = "Category Axis";

    private final JComboBox<WorkerStrategy> workerStrategyJComboBox;
    private final JComboBox<SearcherType> searcherTypeJComboBox;
    private final JComboBox<YType> yJComboBox;
    private final List<DataEvent> history;
    private final JFreeChart chart;

    public GUIAnalysis() {
        super(TITLE);
        this.workerStrategyJComboBox = ComboBoxUtils.createComboBox(List.of(WorkerStrategy.values()));
        this.searcherTypeJComboBox = ComboBoxUtils.createComboBox(List.of(SearcherType.values()));
        this.yJComboBox = ComboBoxUtils.createComboBox(List.of(YType.values()));
        this.history = new ArrayList<>();

        this.chart = ChartFactory.createBarChart(
                TITLE,
                CATEGORY_AXIS,
                Objects.requireNonNull(this.yJComboBox.getSelectedItem()).toString(),
                new DefaultCategoryDataset(),           // I dati da utilizzare
                PlotOrientation.VERTICAL,
                true,              // Mostra la legenda
                true,
                false
        );

        final ChartPanel chartPanel = new ChartPanel(this.chart);
        chartPanel.setPreferredSize(PREFERRED_SIZE);

        this.setContentPane(PanelUtils.createPanelWithBorderLayout());
        final JPanel northPanel = PanelUtils.createPanelWithBorderLayout();

        final JPanel xPanel = PanelUtils.createPanelWithFlowLayout();
        xPanel.add(new JLabel("X   "));
        xPanel.add(new JLabel("Worker Strategy: "));
        xPanel.add(this.workerStrategyJComboBox);
        xPanel.add(new JLabel("Searcher Type: "));
        xPanel.add(this.searcherTypeJComboBox);

        final JPanel yPanel = PanelUtils.createPanelWithFlowLayout();
        yPanel.add(new JLabel("Y   "));
        yPanel.add(this.yJComboBox);

        northPanel.add(xPanel, BorderLayout.NORTH);
        northPanel.add(yPanel, BorderLayout.SOUTH);

        this.add(BorderLayout.NORTH, northPanel);
        this.add(BorderLayout.CENTER, chartPanel);

        this.buildListener();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private void buildListener() {
        this.yJComboBox.addItemListener(l -> {
            final YType valueY = (YType) l.getItem();
            this.rangeAxis().setLabel(valueY.toString());
        });

        this.workerStrategyJComboBox.addItemListener(l -> {
            final WorkerStrategy valueWorker = (WorkerStrategy) l.getItem();
        });

        this.searcherTypeJComboBox.addItemListener(l -> {
            final SearcherType valueSearcher = (SearcherType) l.getItem();
        });
    }

    private CategoryPlot categoryPlot() {
        return (CategoryPlot) this.chart.getPlot();
    }

    private CategoryAxis domainAxis() {
        return this.categoryPlot().getDomainAxis();
    }

    private ValueAxis rangeAxis() {
        return this.categoryPlot().getRangeAxis();
    }

    @Override
    public void append(final DataEvent event) {
        this.history.add(event);
        this.updateHistory();
    }

    private void updateHistory() {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        final AtomicInteger index = new AtomicInteger();
        this.history.forEach(dataEvent -> {
            final String key = "[" + index.getAndIncrement() + "]" + dataEvent.url();
            dataset.addValue(dataEvent.duration(), key, dataEvent.workerStrategy());
        });

        this.categoryPlot().setDataset(dataset);
        this.chart.fireChartChanged();
    }
}
