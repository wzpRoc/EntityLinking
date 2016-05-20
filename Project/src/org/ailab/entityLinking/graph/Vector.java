package org.ailab.entityLinking.graph;

import java.util.ArrayList;

/**
 * User: lutingming
 * Date: 16-1-5
 * Time: 上午12:01
 */
public class Vector extends ArrayList<Double> {
    public Vector(int initialCapacity) {
        super(initialCapacity);
    }

    public Vector() {
    }

    public Vector multiply(double d) {
        Vector result = new Vector(this.size());
        for(int i=0; i<size(); i++) {
            result.add(get(i)*d);
        }
        return result;
    }

    public void multiplySelf(double d) {
        for(int i=0; i<size(); i++) {
            set(i, get(i)*d);
        }
    }

    public double innerMultiply(Vector anotherVector) {
        double result = 0;
        for(int i=0; i<size(); i++) {
            result += get(i)*anotherVector.get(i);
        }
        return result;
    }

    public Vector plus(Vector anotherVector) {
        Vector result = new Vector(this.size());
        for(int i=0; i<size(); i++) {
            double temp = get(i) + anotherVector.get(i);
            result.set(i, temp);
        }
        return result;
    }

    public void plusWithSelf(Vector anotherVector) {
        for(int i=0; i<size(); i++) {
            double temp = get(i) + anotherVector.get(i);
            set(i, temp);
        }
    }

    public void setAll(double value, int count) {
        clear();
        for(int i=0; i<count; i++) {
            add(value);
        }
    }

    public Vector clone() {
        Vector clonedVector = new Vector(this.size());
        for(double d : this) {
            clonedVector.add(d);
        }
        return clonedVector;
    }
}
