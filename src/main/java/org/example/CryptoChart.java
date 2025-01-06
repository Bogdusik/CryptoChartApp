package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CryptoChart {
    private static final List<Double> priceHistory = new ArrayList<>();
    private static final List<Long> timeHistory = new ArrayList<>();

    public static JPanel createChart(JsonNode cryptoData) {
        String name = cryptoData.get("name").asText();
        JsonNode quote = cryptoData.get("quote");
        JsonNode usd = quote.get("USD");
        final double[] price = {usd.get("price").asDouble()};

        TimeSeries series = new TimeSeries(name + " Price");
        TimeSeriesCollection dataset = new TimeSeriesCollection(series);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                name + " Price Over Time",
                "Time",
                "Price (USD)",
                dataset,
                false,
                true,
                false
        );

        chart.setBackgroundPaint(new java.awt.Color(224, 110, 139, 255));
        chart.getPlot().setBackgroundPaint(new java.awt.Color(255, 255, 255));
        chart.getXYPlot().setDomainGridlinePaint(java.awt.Color.LIGHT_GRAY);
        chart.getXYPlot().setRangeGridlinePaint(java.awt.Color.LIGHT_GRAY);

        Timer timer = new Timer(1000, e -> {
            price[0] += new Random().nextDouble() * 2 - 1;
            long currentTime = System.currentTimeMillis();

            // Save to history
            priceHistory.add(price[0]);
            timeHistory.add(currentTime);

            // Update the chart
            series.addOrUpdate(new Second(), price[0]);
        });
        timer.start();

        return new ChartPanel(chart);
    }

    /**
     * Calculate price change for a given time interval.
     *
     * @param intervalMillis Time interval in milliseconds (e.g., 60_000 for 1 minute)
     * @return Price change over the interval
     */
    public static double getChangeForInterval(long intervalMillis) {
        long currentTime = System.currentTimeMillis();
        for (int i = timeHistory.size() - 1; i >= 0; i--) {
            if (currentTime - timeHistory.get(i) <= intervalMillis) {
                return priceHistory.get(priceHistory.size() - 1) - priceHistory.get(i);
            }
        }
        return 0;
    }

    /**
     * Generate a label to display price changes over various intervals.
     *
     * @return JPanel containing the labels
     */
    public static JPanel createChangePanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1));

        // Update labels dynamically
        Timer timer = new Timer(1000, e -> {
            panel.removeAll();
            panel.add(new JLabel("Change (Last Minute): " + getChangeForInterval(60_000) + " USD"));
            panel.add(new JLabel("Change (Last Hour): " + getChangeForInterval(60_000 * 60) + " USD"));
            panel.add(new JLabel("Change (Last Day): " + getChangeForInterval(60_000 * 60 * 24) + " USD"));
            panel.add(new JLabel("Change (Last Week): " + getChangeForInterval(60_000 * 60 * 24 * 7) + " USD"));
            panel.revalidate();
            panel.repaint();
        });
        timer.start();

        return panel;
    }
}