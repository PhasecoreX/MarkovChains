package com.pcxserver.markovChains;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainWindow extends JFrame implements ActionListener {

    /** Serial ID Shenanigans **/
    private static final long serialVersionUID = 1L;

    private JPanel            homePanel;

    private JButton           readButton;

    private JButton           writeButton;

    private JButton           createFicButton;

    private JTextField        ficNameField;

    private JTextArea         outputArea;

    private JButton           clearDbButton;

    private JFileChooser      fileChooser;

    private MarkovDatabase    db;

    public MainWindow() {
        initialize();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == writeButton) {
            outputArea.setText(db.writeSentence());
        }

        if (e.getSource() == readButton) {
            int returnVal = fileChooser.showOpenDialog(MainWindow.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    db.readfile(file.getAbsolutePath());
                } catch (Exception e1) {
                    outputArea.setText("Invalid Filename");
                    e1.printStackTrace();
                    return;
                }
                outputArea.setText("\"" + file.getName() + "\" added to database!\n");
                writeButton.setEnabled(true);
                clearDbButton.setEnabled(true);
                createFicButton.setEnabled(true);
                ficNameField.setEditable(true);
            }
        }

        if (e.getSource() == clearDbButton) {
            db = new MarkovDatabase();
            outputArea.setText("Database Cleared.");
            writeButton.setEnabled(false);
            clearDbButton.setEnabled(false);
            createFicButton.setEnabled(false);
            ficNameField.setEditable(false);
        }

        if (e.getSource() == createFicButton) {
            String name = ficNameField.getText();
            if (name.equals("")) {
                outputArea.setText("Please enter a filename.");
            } else {
                try {
                    db.writeParagraph(name);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                outputArea.setText("Fanfiction Created! Under name: \"" + name + ".txt\"");
                ficNameField.setText("");
            }
        }
    }

    private void initialize() {

        db = new MarkovDatabase();

        setTitle("Markov Match Maker");
        setBounds(100, 100, 626, 298);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setResizable(false);

        homePanel = new JPanel();
        homePanel.setBounds(0, 0, 626, 298);
        homePanel.setLayout(null);
        homePanel.setBorder(null);

        readButton = new JButton();
        readButton.setText("Read in Textfile");
        readButton.setEnabled(true);
        readButton.setBounds(8, 8, 200, 50);
        readButton.addActionListener(this);
        homePanel.add(readButton);

        writeButton = new JButton();
        writeButton.setText("Create Sentence");
        writeButton.setEnabled(false);
        writeButton.setBounds(8, 66, 200, 50);
        writeButton.addActionListener(this);
        homePanel.add(writeButton);

        clearDbButton = new JButton();
        clearDbButton.setText("Clear Database");
        clearDbButton.setEnabled(false);
        clearDbButton.setBounds(8, 124, 200, 50);
        clearDbButton.addActionListener(this);
        homePanel.add(clearDbButton);

        createFicButton = new JButton();
        createFicButton.setText("Create Fanfiction");
        createFicButton.setEnabled(false);
        createFicButton.setBounds(8, 216, 200, 50);
        createFicButton.addActionListener(this);
        homePanel.add(createFicButton);

        ficNameField = new JTextField();
        ficNameField.setEditable(false);
        ficNameField.setBounds(216, 216, 400, 50);
        ficNameField.setFont(new Font("Default", Font.PLAIN, 24));
        homePanel.add(ficNameField);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBounds(216, 8, 400, 200);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Default", Font.PLAIN, 16));
        homePanel.add(outputArea);

        fileChooser = new JFileChooser();

        this.add(homePanel);
    }
}
