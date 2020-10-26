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

    public Double getFrom(){
        return from;
    }

    public Double getTo(){
        return to;
    }

    public Double getStep(){
        return step;
    }

    public int getColumnCount(){
        return 4;
    }

    public int getRowCount(){
        return new Double(Math.ceil((to - from) / step)).intValue() + 1;
    }

    public Object getValueAt(int row, int col){
        double d_x = from + step * row;
        Double d_result = 0.0;

        float f_x = from.floatValue() + step.floatValue() * row;
        Float f_result = 0.0f;

        double x = d_x;
        d_result += coefficients[coefficients.length - 1];
        for (int i = coefficients.length - 2; i >= 0; i--){
            d_result += coefficients[i] * x;
            x *= d_x;
        }

        float xf = f_x;
        f_result += coefficients[coefficients.length - 1].floatValue();
        for (int i = coefficients.length - 2; i >= 0; i--){
            f_result += coefficients[i].floatValue() * xf;
            xf *= f_x;
        }

        if (col == 0){
            return d_x;
        } else if (col == 1){
            return d_result;
        } else if (col == 2){
            return f_result;
        } else {
            return Math.abs(d_result - f_result);
        }
    }

    public String getColumnName(int col){
        switch (col){
            case 0:
                return "Значение X";
            case 1:
                return "Значение многочлена в Double";
            case 2:
                return "Значение многочлена в Float";
            default:
                return "Разница Double и Float";
        }
    }

    public Class<?> getColumnClass(int col){
        if (col == 2)
            return Float.class;
        return Double.class;
    }
}
