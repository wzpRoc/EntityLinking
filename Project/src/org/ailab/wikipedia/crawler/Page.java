package org.ailab.wikipedia.crawler;

/**
 * User: Lu Tingming
 * Time: 2010-11-11 20:53:54
 * Desc: 页面类
 */
public class Page {
    // URL
    public String url;
    // 本地路径
    public String path;
    // 内容
    public String content;


    /**
     * Constructor
     * @param url
     * @param path
     */
    public Page(String url, String path) {
        init(url, path);
    }


    /**
     * Initializer
     * @param url
     * @param path
     */
    public void init(String url, String path) {
        this.url = url;
        this.path = path;
    }

}
