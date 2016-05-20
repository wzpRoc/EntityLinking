package org.ailab.tool.aida;

/**
 * Created with IntelliJ IDEA.
 * User: robby
 * Date: 15-12-28
 * Time: 下午5:02
 * To change this template use File | Settings | File Templates.
 */
public class MatrixMathMethod {
    /**
     * 1
     * 求解代数余子式 输入：原始矩阵+行+列 现实中真正的行和列数目
     */
    public static float[][] getDY(float[][] data, int h, int v) {
        int H = data.length;
        int V = data[0].length;
        float[][] newData = new float[H - 1][V - 1];

        for (int i = 0; i < newData.length; i++) {

            if (i < h - 1) {
                for (int j = 0; j < newData[i].length; j++) {
                    if (j < v - 1) {
                        newData[i][j] = data[i][j];
                    } else {
                        newData[i][j] = data[i][j + 1];
                    }
                }
            } else {
                for (int j = 0; j < newData[i].length; j++) {
                    if (j < v - 1) {
                        newData[i][j] = data[i + 1][j];
                    } else {
                        newData[i][j] = data[i + 1][j + 1];
                    }
                }

            }
        }
        // System.out.println("---------------------代数余子式测试.---------------------------------");
        // for(int i=0;i<newData.length;i++){
        // for(int j=0;j<newData[i].length;j++){
        // System.out.print("newData["+i+"]"+"["+j+"]="+newData[i][j]+"   ");
        // }
        //
        // System.out.println();
        // }

        return newData;
    }

    /**
     * 2
     * 求2阶行列式的数值
     * @param data
     * @return
     */
    public static float getHL2(float[][] data) {
        // data 必须是2*2 的数组
        float num1 = data[0][0] * data[1][1];
        float num2 = -data[0][1] * data[1][0];
        return num1 + num2;
    }

    /**
     * 求3阶行列式的数值
     *
     * @param data
     * @return
     */
    public static float getHL3(float[][] data) {
        float num1 = data[0][0] * getHL2(getDY(data, 1, 1));
        float num2 = -data[0][1] * getHL2(getDY(data, 1, 2));
        float num3 = data[0][2] * getHL2(getDY(data, 1, 3));
        // System.out.println("---->"+num1);
        // System.out.println("---->"+num2);
        // System.out.println("---->"+num3);
        System.out.println("3阶行列式的数值是：----->" + (num1 + num2 + num3));
        return num1 + num2 + num3;
    }

    /**
     * 求4阶行列式的数值
     *
     * @param data
     * @return
     */
    public static float getHL4(float[][] data) {
        float num1 = data[0][0] * getHL3(getDY(data, 1, 1));
        float num2 = -data[0][1] * getHL3(getDY(data, 1, 2));
        float num3 = data[0][2] * getHL3(getDY(data, 1, 3));
        float num4 = -data[0][3] * getHL3(getDY(data, 1, 4));
        // System.out.println("--------->"+num1);
        // System.out.println("--------->"+num2);
        // System.out.println("--------->"+num3);
        // System.out.println("--------->"+num4);
        // System.out.println("4阶行列式的数值------->"+(num1+num2+num3+num4));

        return num1 + num2 + num3 + num4;
    }

//    /**
//     * 求5阶行列式的数值
//     */
//    public static float getHL5(float[][] data) {
//
//        float num1 = data[0][0] * getHL4(getDY(data, 1, 1));
//        float num2 = -data[0][1] * getHL4(getDY(data, 1, 2));
//        float num3 = data[0][2] * getHL4(getDY(data, 1, 3));
//        float num4 = -data[0][3] * getHL4(getDY(data, 1, 4));
//        float num5 = data[0][4] * getHL4(getDY(data, 1, 5));
//
//        System.out.println("5 阶行列式的数值是：  ------->"
//                + (num1 + num2 + num3 + num4 + num5));
//        return num1 + num2 + num3 + num4 + num5;
//
//    }

    /**
     * 求解行列式的模----------->最终的总结归纳
     *
     * @param data
     * @return
     */
    public static float getHL(float[][] data) {

        // 终止条件
        if (data.length == 2) {
            return data[0][0] * data[1][1] - data[0][1] * data[1][0];
        }

        float total = 0;
        // 根据data 得到行列式的行数和列数
        int num = data.length;
        // 创建一个大小为num 的数组存放对应的展开行中元素求的的值
        float[] nums = new float[num];

        for (int i = 0; i < num; i++) {
            if (i % 2 == 0) {
                nums[i] = data[0][i] * getHL(getDY(data, 1, i + 1));
            } else {
                nums[i] = -data[0][i] * getHL(getDY(data, 1, i + 1));
            }
        }
        for (int i = 0; i < num; i++) {
            total += nums[i];
        }
        System.out.println("total=" + total);
        return total;
    }

    /**
     * 求解3阶矩阵的逆矩阵
     * @param data
     * @return
     */
    public static float[][] getN3(float[][] data) {
        // 先是求出整个行列式的数值|A|
        float A = getHL3(data);
        float[][] newData = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                float num;
                if ((i + j) % 2 == 0) {// i+j 是偶数 实际是(i+1)+(j+1)
                    num = getHL2(getDY(data, i + 1, j + 1));
                } else {
                    num = -getHL2(getDY(data, i + 1, j + 1));
                }
                System.out.println("num=" + num);
                newData[i][j] = num / A;
            }
        }

        // 再转制
        newData = getA_T(newData);

        // 打印
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("newData[" + i + "][" + j + "]= "
                        + newData[i][j] + "   ");
            }

            System.out.println();
        }

        return newData;
    }

    /**
     * 求解逆矩阵------>z最后的总结和归纳
     *
     * @param data
     * @return
     */
    public static float[][] getN(float[][] data) {
        if (preJudge(data)) {


            // 先是求出行列式的模|data|
            float A = getHL(data);
            // 创建一个等容量的逆矩阵
            float[][] newData = new float[data.length][data.length];

            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data.length; j++) {
                    float num;
                    if ((i + j) % 2 == 0) {
                        num = getHL(getDY(data, i + 1, j + 1));
                    } else {
                        num = -getHL(getDY(data, i + 1, j + 1));
                    }

                    newData[i][j] = num / A;
                }
            }

            // 转置 代数余子式转制
            newData = getA_T(newData);
            // 打印
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data.length; j++) {
                    System.out.print("newData[" + i + "][" + j + "]= "
                            + newData[i][j] + "   ");
                }

                System.out.println();
            }

            return newData;
        }
        return null;
    }

    public static boolean preJudge(float[][] data)
    {
        if(data.length != data[0].length)
        {
            System.out.println("该矩阵不是方阵");
            return false;
        }
        else if(getHL(data) == 0)
        {
            System.out.println("该矩阵的秩为0，不可求逆运算");
            return false;
        }
        return true;
    }

    /**
     * 取得转置矩阵
     * @param A
     * @return
     */
    public static float[][] getA_T(float[][] A) {
        int h = A.length;
        int v = A[0].length;
        // 创建和A行和列相反的转置矩阵
        float[][] A_T = new float[v][h];
        // 根据A取得转置矩阵A_T
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < h; j++) {
                A_T[j][i] = A[i][j];
            }
        }
        System.out.println("取得转置矩阵  ........");
        return A_T;
    }


}
