package ex2.gui.area;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class AnalysisArea {

    private static final Dimension PREFERRED_SIZE = new Dimension(500, 300);
    private final DefaultCategoryDataset dataset;
    private final JFreeChart chart;

    public AnalysisArea() {
        this.dataset = new DefaultCategoryDataset();
        this.dataset.addValue(10, "Sales", "January");
        this.dataset.addValue(20, "Sales", "February");
        this.dataset.addValue(30, "Sales", "March");

        this.chart = ChartFactory.createBarChart(
                "Sales Report",    // Titolo del grafico
                "Month",           // Etichetta asse x
                "Sales",           // Etichetta asse y
                this.dataset,           // I dati da utilizzare
                PlotOrientation.VERTICAL,
                true,              // Mostra la legenda
                true,
                false
        );

        final ChartPanel chartPanel = new ChartPanel(this.chart);
        chartPanel.setPreferredSize(PREFERRED_SIZE);

        final JFrame frame = new JFrame("Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

}
