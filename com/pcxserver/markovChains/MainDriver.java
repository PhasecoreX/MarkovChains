package com.pcxserver.markovChains;

import java.awt.EventQueue;

public class MainDriver {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    @SuppressWarnings("unused")
                    MainWindow window = new MainWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
