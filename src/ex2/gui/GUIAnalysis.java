package ex2.gui;

import ex2.core.component.DataEvent;
import ex2.core.listener.HistoryListener;
import ex2.utils.ComboBoxUtils;
import ex2.utils.PanelUtils;
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
    enum XType {
        WORKER,
        SEARCHER
    }
    enum YType {
        TIME,
        DEPTH
    }

    private static final String TITLE = "Analysis Area";
    private static final Dimension PREFERRED_SIZE = new Dimension(600, 500);
    private static final String CATEGORY_AXIS = "Category Axis";

    private final JComboBox<XType> xComboBox;
    private final JComboBox<YType> yComboBox;
    private final List<DataEvent> history;
    private final JFreeChart chart;

    public GUIAnalysis() {
        super(TITLE);
        this.xComboBox = ComboBoxUtils.createComboBox(List.of(XType.values()));
        this.yComboBox = ComboBoxUtils.createComboBox(List.of(YType.values()));
        this.history = new ArrayList<>();

        this.chart = ChartFactory.createBarChart(
                TITLE,
                CATEGORY_AXIS,
                Objects.requireNonNull(this.yComboBox.getSelectedItem()).toString(),
                new DefaultCategoryDataset(),           // I dati da utilizzare
                PlotOrientation.VERTICAL,
                true,              // Mostra la legenda
                true,
                false
        );

        final ChartPanel chartPanel = new ChartPanel(this.chart);
        chartPanel.setPreferredSize(PREFERRED_SIZE);

        this.setContentPane(PanelUtils.createPanelWithBorderLayout());
        final JPanel northPanel = PanelUtils.createPanelWithFlowLayout();

        northPanel.add(new JLabel("X: "));
        northPanel.add(this.xComboBox);
        northPanel.add(new JLabel("Y: "));
        northPanel.add(this.yComboBox);

        this.add(BorderLayout.NORTH, northPanel);
        this.add(BorderLayout.CENTER, chartPanel);

        this.buildListener();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
    }

    private void buildListener() {
        this.yComboBox.addItemListener(l -> {
            final YType yValue = (YType) l.getItem();
            this.rangeAxis().setLabel(yValue.toString());
            this.updateHistory();
        });

        this.xComboBox.addItemListener(l -> {
            final XType xValue = (XType) l.getItem();
            this.domainAxis().setLabel(xValue.toString());
            this.updateHistory();
        });
    }

    @Override
    public void append(final DataEvent event) {
        this.history.add(event);
        this.updateHistory();
    }

    public void changeVisible() {
        this.setVisible(!this.isVisible());
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

    private XType selectX() {
        return (XType) this.xComboBox.getSelectedItem();
    }

    private YType selectY() {
        return (YType) this.yComboBox.getSelectedItem();
    }

    private long valueY(final DataEvent event) {
        final YType yValue = this.selectY();
        return switch (yValue) {
            case TIME -> event.duration();
            case DEPTH -> event.maxDepth();
        };
    }

    private String valueX(final DataEvent event) {
        final XType xValue = this.selectX();
        return switch (xValue) {
            case WORKER -> event.workerStrategy().toString();
            case SEARCHER -> event.searcherType().toString();
        };
    }

    private void updateHistory() {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        final AtomicInteger index = new AtomicInteger();
        this.history.forEach(dataEvent -> {
            final String key = "[" + index.getAndIncrement() + "]" + dataEvent.url();
            dataset.addValue(this.valueY(dataEvent), key, this.valueX(dataEvent));
        });

        this.categoryPlot().setDataset(dataset);
        this.chart.fireChartChanged();
    }
}
