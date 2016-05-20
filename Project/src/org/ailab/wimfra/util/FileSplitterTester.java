package org.ailab.wimfra.util;

import java.io.*;

/**
 * User: Lu Tingming
 * Date: 15-5-6
 * Time: 下午12:07
 * Desc:
 */
public class FileSplitterTester {
    public static void main(String[] args) {
        new FileSplitter("D:\\Research\\NLPCC\\2015\\Sample Data\\releaseRDF.dumps", 10000).split();
    }
}
