package org.ailab.wimfra.util;

import java.util.*;

/**
 * User: Wu Zhanpeng
 * Date: 16-1-13 下午7:03
 * Desc: 几何代数计算工具
 */
public class GeometricUtil {
    /**
     * 计算两个向量的内积
     * @param a
     * @param b
     * @return
     */
    public static double calcInnerProduct(List<Double> a, List<Double> b) throws Exception{
        if (a.size() != b.size())
            throw new Exception("向量长度不一致，无法计算内积");
        double innerProduct = 0.0;
        for (int i = 0; i<a.size() && i<b.size(); i++) {
            innerProduct += a.get(i) * b.get(i);
        }
        return innerProduct;
    }

    /**
     * 计算向量的模
     * @param a
     * @return
     */
    public static double calcNormOfVector(List<Double> a) {
        double norm = 0.0;
        for (double d : a)
            norm += Math.pow(d, 2.0);
        norm = Math.sqrt(norm);
        return norm;
    }

    /**
     * 计算两个向量的余弦距离
     * @param a
     * @param b
     * @return
     * @throws Exception
     */
    public static double calcCosineDistance(List<Double> a, List<Double> b) throws Exception{
        double innerProduct = calcInnerProduct(a, b);
        if (innerProduct == 0.0)
            return innerProduct;
        double aNorm = calcNormOfVector(a);
        double bNorm = calcNormOfVector(b);
        double cosineDistance = innerProduct / (aNorm * bNorm);
        if (Double.isNaN(cosineDistance)) {
            System.out.printf("NAN");
            cosineDistance = 0.0;
        }
        return cosineDistance;
    }
}
