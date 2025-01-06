package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            JsonNode cryptoData = CryptoAPI.getCryptoData();
            JsonNode dataNode = cryptoData.get("data");

            if (dataNode != null) {
                JFrame frame = new JFrame("Crypto Charts");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.setSize(1920, 1080);

                JPanel chartPanel = new JPanel(new GridLayout(2, 4));
                int count = 0;
                for (JsonNode crypto : dataNode) {
                    if (count < 8) {
                        chartPanel.add(CryptoChart.createChart(crypto));
                        count++;
                    } else {
                        break;
                    }
                }
                frame.add(chartPanel, BorderLayout.CENTER);
                frame.add(CryptoChart.createChangePanel(), BorderLayout.EAST);

                frame.setVisible(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}