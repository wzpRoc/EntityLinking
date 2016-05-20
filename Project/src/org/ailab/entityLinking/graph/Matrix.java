package org.ailab.entityLinking.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * User: lutingming
 * Date: 16-1-4
 * Time: 下午11:30
 */
public class Matrix {
    private final int DEFAULT_NORMALIZED_VALUE = 0;
    private int rowTotal;
    private int columnTotal;

    private List<Vector> data;

    public Matrix(int columnTotal, int rowTotal) {
        this.columnTotal = columnTotal;
        this.rowTotal = rowTotal;
        this.data = new ArrayList<Vector>(rowTotal);
        for(int i=0; i< rowTotal; i++) {
            data.add(new Vector(columnTotal));
        }
    }

    public Vector getRow(int rowIdx) {
        return data.get(rowIdx);
    }

    public double get(int rowIdx, int columnIdx) {
        return getRow(rowIdx).get(columnIdx);
    }

    public void multiply(double d) {
        for(Vector row : data) {
            for(int i=0; i<row.size(); i++) {
                row.set(i, row.get(i)*d);
            }
        }
    }

    public Vector multiply(Vector anotherVector) {
        Vector result = new Vector(rowTotal);
        for(int rowIdx=0; rowIdx< rowTotal; rowIdx++) {
            Vector row = data.get(rowIdx);
            double temp = row.innerMultiply(anotherVector);
            result.add(temp);
        }

        return result;
    }

    public void setRow(int rowIdx, double value) {
        getRow(rowIdx).setAll(value, columnTotal);
    }

    public void set(int rowIdx, int colIdx, double value) {
        getRow(rowIdx).set(colIdx, value);
    }

    /**
     * 按列归一化
     */
    public void normalizeByRow() {
        for(int colIdx=0; colIdx< columnTotal; colIdx++) {
            // sum by column
            double sum = 0;
            for(int rowIdx=0; rowIdx< rowTotal; rowIdx++) {
                sum+= get(rowIdx, colIdx);
            }
            // normalization
            for(int rowIdx=0; rowIdx< rowTotal; rowIdx++) {
                double normalizedValue;
                if(sum == 0) {
                    normalizedValue = DEFAULT_NORMALIZED_VALUE;
                } else {
                    normalizedValue = get(rowIdx, colIdx) / sum;
                }
                set(rowIdx, colIdx, normalizedValue);
            }
        }
    }
}
