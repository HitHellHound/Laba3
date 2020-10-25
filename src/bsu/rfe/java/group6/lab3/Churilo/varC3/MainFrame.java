package bsu.rfe.java.group6.lab3.Churilo.varC3;

import javax.swing.*;
import java.awt.*;

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
}
