package bsu.rfe.java.group6.lab3.Churilo.varC3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;

public class MainFrame extends JFrame {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;

    private Icon imgAuthor;

    private Double[] coefficients;

    private JFileChooser fileChooser = null;

    private JMenuItem saveToTextMenuItem;
    private JMenuItem saveToGraphicsMenuItem;
    private JMenuItem saveToCSVMenuItem;
    private JMenuItem searchValueMenuItem;
    private JMenuItem showPalindromesMenuItem;
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

        imgAuthor = new ImageIcon("Pictures/Author.png");

        //Menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);
        JMenu tableMenu = new JMenu("Таблица");
        menuBar.add(tableMenu);
        JMenu helpMenu = new JMenu("Справка");
        menuBar.add(helpMenu);

        //File menu
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

        //Table menu
        Action searchValueAction = new AbstractAction("Найти значение многочлена") {
            public void actionPerformed(ActionEvent e) {
                String value = JOptionPane.showInputDialog(MainFrame.this, "Введите значение для поиска", "Поиск значения", JOptionPane.QUESTION_MESSAGE);
                renderer.setNeedle(value);
                getContentPane().repaint();
            }
        };
        searchValueMenuItem = tableMenu.add(searchValueAction);
        searchValueMenuItem.setEnabled(false);

        Action showPalindromesAction = new AbstractAction("Показать палиндромы") {
            public void actionPerformed(ActionEvent e) {
                renderer.setSelectPalindromes(true);
                getContentPane().repaint();
            }
        };
        showPalindromesMenuItem = tableMenu.add(showPalindromesAction);
        showPalindromesMenuItem.setEnabled(false);

        //Help menu
        Action aboutProgramAction = new AbstractAction("О программе") {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainFrame.this, "Автор программы студент второго курса 6 группы Чурило Игнат", "О программе", JOptionPane.INFORMATION_MESSAGE, imgAuthor);
            }
        };
        aboutProgramMenuItem = helpMenu.add(aboutProgramAction);

        //X range
        JLabel labelForFrom = new JLabel("X изменяется на интервале от:");
        textFieldFrom = new JTextField("0.0", 10);
        textFieldFrom.setMaximumSize(textFieldFrom.getPreferredSize());

        JLabel labelForTo = new JLabel("до:");
        textFieldTo = new JTextField("1.0", 10);
        textFieldTo.setMaximumSize(textFieldTo.getPreferredSize());

        JLabel labelForStep = new JLabel("с шагом:");
        textFieldStep = new JTextField("0.1", 10);
        textFieldStep.setMaximumSize(textFieldStep.getPreferredSize());

        Box hBoxRange = Box.createHorizontalBox();
        hBoxRange.setBorder(BorderFactory.createBevelBorder(1));
        hBoxRange.add(Box.createHorizontalGlue());
        hBoxRange.add(labelForFrom);
        hBoxRange.add(Box.createHorizontalStrut(10));
        hBoxRange.add(textFieldFrom);
        hBoxRange.add(Box.createHorizontalStrut(20));
        hBoxRange.add(labelForTo);
        hBoxRange.add(Box.createHorizontalStrut(10));
        hBoxRange.add(textFieldTo);
        hBoxRange.add(Box.createHorizontalStrut(20));
        hBoxRange.add(labelForStep);
        hBoxRange.add(Box.createHorizontalStrut(10));
        hBoxRange.add(textFieldStep);
        hBoxRange.add(Box.createHorizontalGlue());

        hBoxRange.setPreferredSize(new Dimension((new Double(hBoxRange.getMaximumSize().getWidth()).intValue()), new Double(hBoxRange.getMinimumSize().getHeight()).intValue()*2));
        getContentPane().add(hBoxRange, BorderLayout.NORTH);

        //Button "Вчислить"
        JButton buttonCalc = new JButton("Вычислить");
        buttonCalc.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    renderer.setNeedle(null);
                    renderer.setSelectPalindromes(false);

                    Double from = Double.parseDouble(textFieldFrom.getText());
                    Double to = Double.parseDouble(textFieldTo.getText());
                    Double step = Double.parseDouble(textFieldStep.getText());
                    data = new GornerTableModel(from, to, step, MainFrame.this.coefficients);

                    JTable table = new JTable(data);
                    table.setDefaultRenderer(Double.class, renderer);
                    table.setDefaultRenderer(Float.class, renderer);
                    table.setRowHeight(30);
                    hBoxResult.removeAll();
                    hBoxResult.add(new JScrollPane(table));
                    getContentPane().validate();

                    saveToTextMenuItem.setEnabled(true);
                    saveToGraphicsMenuItem.setEnabled(true);
                    saveToCSVMenuItem.setEnabled(true);

                    searchValueMenuItem.setEnabled(true);
                    showPalindromesMenuItem.setEnabled(true);
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(MainFrame.this, "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        //Button "Очистить"
        JButton buttonClear = new JButton("Очистить");
        buttonClear.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                renderer.setNeedle(null);
                renderer.setSelectPalindromes(false);

                textFieldFrom.setText("0.0");
                textFieldTo.setText("1.0");
                textFieldStep.setText("0.1");

                hBoxResult.removeAll();
                hBoxResult.add(new JPanel());
                getContentPane().repaint();

                saveToTextMenuItem.setEnabled(false);
                saveToGraphicsMenuItem.setEnabled(false);
                saveToCSVMenuItem.setEnabled(false);

                searchValueMenuItem.setEnabled(false);
                showPalindromesMenuItem.setEnabled(false);
            }
        });

        //Buttons
        Box hBoxButtons = Box.createHorizontalBox();
        hBoxButtons.setBorder(BorderFactory.createBevelBorder(1));
        hBoxButtons.add(Box.createHorizontalGlue());
        hBoxButtons.add(buttonCalc);
        hBoxButtons.add(Box.createHorizontalStrut(30));
        hBoxButtons.add(buttonClear);
        hBoxButtons.add(Box.createHorizontalGlue());
        hBoxButtons.setPreferredSize(new Dimension(new Double(hBoxButtons.getMaximumSize().getWidth()).intValue(), new Double(hBoxButtons.getMinimumSize().getHeight()).intValue()*2));
        getContentPane().add(hBoxButtons, BorderLayout.SOUTH);

        hBoxResult = Box.createHorizontalBox();
        hBoxResult.add(new JPanel());
        getContentPane().add(hBoxResult, BorderLayout.CENTER);
    }

    protected void saveToTextFile(File selectedFile){
        try{
            PrintStream out = new PrintStream(selectedFile);

            out.println("Результаты табулирования многочлена по схеме Горнера");

            out.print("Многочлен: ");
            for (int i=0; i<coefficients.length; i++) {
                out.print(coefficients[i] + "*X^" +
                        (coefficients.length-i-1));
                if (i!=coefficients.length-1)
                    out.print(" + ");
            }
            out.println("");

            out.println("Интервал от " + data.getFrom() + " до " + data.getTo() + " с шагом " + data.getStep());

            out.println("====================================================");
            for (int i = 0; i < data.getRowCount(); i++){
                out.println("Значение в точке " + data.getValueAt(i, 0) + " для Double равно " + data.getValueAt(i, 1) + ", для Float - " + data.getValueAt(i, 2) + ". Разница между значениями Double и Float равна " + data.getValueAt(i, 3));
            }

            out.close();
        } catch (FileNotFoundException e){ }
    }

    protected void saveToGraphicsFile(File selectedFile){
        try{
            DataOutputStream out = new DataOutputStream(new FileOutputStream(selectedFile));

            for (int i = 0; i < data.getRowCount(); i++) {
                out.writeDouble((Double)data.getValueAt(i,0));
                out.writeDouble((Double)data.getValueAt(i,1));
                out.writeFloat((Float)data.getValueAt(i, 2));
                out.writeDouble((Double)data.getValueAt(i, 3));
            }

            out.close();
        } catch (Exception e){

        }
    }

    protected void saveToCSVFile(File selectedFile){
        try(FileWriter writer = new FileWriter(selectedFile)){
            for (int i = 0; i < data.getRowCount(); i++){
                writer.append("" + data.getValueAt(i, 0) + ',' + data.getValueAt(i, 1) + ',' + data.getValueAt(i, 2) + ',' + data.getValueAt(i, 3));
                writer.append(System.lineSeparator());
            }
            writer.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        if (args.length == 0){
            System.out.println("Невозможно табулировать многочлен, для которого не задано ни одного коэффициента!");
            System.exit(-1);
        }

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
