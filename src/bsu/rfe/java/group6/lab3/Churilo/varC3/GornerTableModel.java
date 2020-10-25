package bsu.rfe.java.group6.lab3.Churilo.varC3;

import javax.swing.table.AbstractTableModel;

public class GornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients){
        this.coefficients = coefficients;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    public int getColumnCount(){
        return 0;
    }

    public int getRowCount(){
        return 0;
    }

    public Object getValueAt(int row, int col){
        return null;
    }
}
