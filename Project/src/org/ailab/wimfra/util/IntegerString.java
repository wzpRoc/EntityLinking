package org.ailab.wimfra.util;

/**
 * User: Lu Tingming
 * Date: 2012-9-9
 * Time: 18:21:22
 * Desc:
 */
public class IntegerString {
    public int i;
    public String s;

    public IntegerString(int i, String s) {
        this.i = i;
        this.s = s;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String toString() {
        return i + ", " + s;
    }
}
