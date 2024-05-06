package ex2.gui.area;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class AnalysisArea {
    // possibili X
    // 1 valore per il tempo
    // 2 valore per la depth

    // possibili Y
    // gruppo per searcher
    // gruppo per worker
    private static final Dimension PREFERRED_SIZE = new Dimension(500, 300);
    private static final String ASSE_X = "Depth";
    private static final String ASSE_Y = "Time";


    private final CategoryDataset dataset;
    private final JFreeChart chart;

    public AnalysisArea() {
        this.dataset = this.createDataset();

        this.chart = ChartFactory.createBarChart(
                "Sales Report",    // Titolo del grafico
                ASSE_X,           // Etichetta asse x
                ASSE_Y,           // Etichetta asse y
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


    private CategoryDataset createDataset( ) {
        final String fiat = "DATA 1";
        final String audi = "DATA 2";
        final String ford = "DATA 3";

        final String event = "event-loop";
        final String virtual = "virtual";
        final String react = "react";

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

        dataset.addValue( 3.0 , fiat , virtual );
        dataset.addValue( 5.0 , fiat , event );
        dataset.addValue( 5.0 , fiat , react );

        dataset.addValue( 6.0 , audi , virtual );
        dataset.addValue( 10.0 , audi , event );
        dataset.addValue( 4.0 , audi , react );

        dataset.addValue( 2.0 , ford , virtual );
        dataset.addValue( 3.0 , ford , event );
        dataset.addValue( 6.0 , ford , react );

        return dataset;
    }

}
