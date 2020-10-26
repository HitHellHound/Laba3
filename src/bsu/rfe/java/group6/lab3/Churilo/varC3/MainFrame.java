package bsu.rfe.java.group6.lab3.Churilo.varC3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class MainFrame extends JFrame {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;

    private Double[] coefficients;

    private JFileChooser fileChooser = null;

    private JMenuItem saveToTextMenuItem;
    private JMenuItem saveToGraphicsMenuItem;
    private JMenuItem saveToCSVMenuItem;
    private JMenuItem searchValueMenuItem;
    private JMenuItem searchPalindromesMenuItem;
    private JMenuItem aboutProgramMenuItem;

    private JTextField textFieldFrom;
    private JTextField textFieldTo;
    private JTextField textFieldStep;

    private Box hBoxResult;

    private  GornerTableCellRenderer renderer = new GornerTableCellRenderer();

    private GornerTableModel data;

    public MainFrame (Double[] coefficients){
        super("Табулирование многочлена на отрезке по схеме Горнера");
        this.coefficients = coefficients;
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH) / 2, (kit.getScreenSize().height - HEIGHT) / 2);

        //Menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);
        JMenu tableMenu = new JMenu("Таблица");
        menuBar.add(tableMenu);
        JMenu helpMenu = new JMenu("Справка");
        menuBar.add(helpMenu);

        Action saveToTextAction = new AbstractAction("Сохранить в текстовый файл") {
            public void actionPerformed(ActionEvent e) {
                if (fileChooser == null){
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION){
                    saveToTextFile(fileChooser.getSelectedFile());
                }
            }
        };
        saveToTextMenuItem = fileMenu.add(saveToTextAction);
        saveToTextMenuItem.setEnabled(false);

        Action saveToGraphicsAction = new AbstractAction("Сохранить в данные для построения графика") {
            public void actionPerformed(ActionEvent e) {
                if (fileChooser == null){
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION){
                    saveToGraphicsFile(fileChooser.getSelectedFile());
                }
            }
        };
        saveToGraphicsMenuItem = fileMenu.add(saveToGraphicsAction);
        saveToGraphicsMenuItem.setEnabled(false);

        Action saveToCSVAction = new AbstractAction("Сохранить в CSV файл") {
            public void actionPerformed(ActionEvent e) {
                if (fileChooser == null){
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION){
                    saveToCSVFile(fileChooser.getSelectedFile());
                }
            }
        };
        saveToCSVMenuItem = fileMenu.add(saveToCSVAction);
        saveToCSVMenuItem.setEnabled(false);
    }

    public static void main(String args[]){
        Double[] coefficients = new Double[args.length];
        int i = 0;
        try{
            for (String arg:args)
                coefficients[i++] = Double.parseDouble(arg);
        }
        catch (NumberFormatException ex){
            System.out.println(("Ошибка преобразования строки '" + args[i] + "' в число типа Double"));
            System.exit(-2);
        }
        MainFrame frame = new MainFrame(coefficients);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    protected void saveToTextFile(File selectedFile){

    }

    protected void saveToGraphicsFile(File selectedFile){

    }

    protected void saveToCSVFile(File selectedFile){

    }
}
