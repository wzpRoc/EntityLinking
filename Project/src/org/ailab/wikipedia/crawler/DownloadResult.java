package org.ailab.wikipedia.crawler;

/**

 * Time: 2010-11-12 13:01:16
 * Desc: 下载的结果
 */
public enum DownloadResult {
    // 文件已经存在
    alreadyExists,
    // 网络错误
    notFound,
    // 网络错误
    networkError,
    // 禁止访问
    forbidden,
    // 成功
    succeeded
}
