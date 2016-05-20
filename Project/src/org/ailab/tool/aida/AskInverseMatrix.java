package org.ailab.tool.aida;

/**
 * Created with IntelliJ IDEA.
 * User: robby
 * Date: 15-12-28
 * Time: 下午5:01
 * To change this template use File | Settings | File Templates.
 */
public class AskInverseMatrix {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // getHL3(data3);
        // getHL4(data4);
        // getHL5(data5);
        // getN3(data3);
        // getHL(data5);
//        MatrixMathMethod.getN(data3);
        MatrixMathMethod.getN(data3);
    }

    static float[][] data6 = { { 1, 2, 3, 4, 5, 6 },
            { 1, 2, 3, 4, 5, 6 },
            { 3, 4, 3, 2, 2, 1 },
            { 1, 2, 3, 4, 5, 6 },
            { 1, 2, 3, 4, 5, 6 },
            { 1, 2, 3, 4, 5, 6 },

    };

    static float[][] data5 = { { 1, 2, 3, 4, 5 },
            { 2, 3, 4, 5, 1 },
            { 3, 4, 5, 1, 2 },
            { 4, 5, 1, 2, 3 },
            { 5, 1, 2, 3, 4 },

    };

    static float[][] data4 = { { 1, 0, -1, 2 },
            { -2, 1, 3, 1 },
            { 0, 2, 0, -2 },
            { 1, 3, 4, -2 },

    };
    static float[][] data3 = { {1,2,-1 },
            {3,1,0 },
            {-1,-1,-2 },
    };

}
