package bsu.rfe.java.group6.lab3.Churilo.varC3;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class GornerTableCellRenderer implements TableCellRenderer {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private JCheckBox flag = new JCheckBox();

    private String needle = null;
    private Boolean selectPalindromes = false;

    private DecimalFormat formatter = (DecimalFormat)NumberFormat.getInstance();
    private DecimalFormat dot = (DecimalFormat)NumberFormat.getInstance();

    public GornerTableCellRenderer() {
        formatter.setMaximumFractionDigits(5);
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);

        dot.setMinimumFractionDigits(18);
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols dotted = dot.getDecimalFormatSymbols();
        dotted.setDecimalSeparator('.');
        dot.setDecimalFormatSymbols(dotted);

        flag.setSelected(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        String formattedDouble;
        if (col != 3)
            formattedDouble = formatter.format(value);
        else
            formattedDouble = dot.format(value);
        label.setText(formattedDouble);
        panel.setBackground(Color.WHITE);

        if (selectPalindromes) {
            boolean palindrome = true;
            int b = 0;
            int e = formattedDouble.length() - 1;
            while (b < e) {
                if (formattedDouble.charAt(b) == '.')
                    b++;
                if (formattedDouble.charAt(e) == '.')
                    e--;
                if (formattedDouble.charAt(b) != formattedDouble.charAt(e)) {
                    palindrome = false;
                    break;
                }
                b++;
                e--;
            }
            if (palindrome)
                panel.setBackground(Color.MAGENTA);
        }

        panel.removeAll();
        if ((col == 1 || col == 2) && needle != null && needle.equals(formattedDouble)){
            panel.add(flag);
            panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        }
        else {
            panel.add(label);
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        }
        return panel;
    }

    public void setNeedle(String needle){
        this.needle = needle;
    }

    public void setSelectPalindromes(Boolean selected){
        this.selectPalindromes = selected;
    }
}
