package org.ailab.wikipedia.crawler;

/**

 * Time: 2010-11-12 12:42:59
 * Desc: 下载消息
 */
public class DownloadMessage {
    public static final DownloadMessage ALREADY_EXISTS = new DownloadMessage(DownloadResult.alreadyExists);
    public static final DownloadMessage NOT_FOUND = new DownloadMessage(DownloadResult.notFound);
    public static final DownloadMessage NETWORK_ERROR = new DownloadMessage(DownloadResult.networkError);
    public static final DownloadMessage FORBIDDEN = new DownloadMessage(DownloadResult.forbidden);
    public static final DownloadMessage SUCCEEDED = new DownloadMessage(DownloadResult.succeeded);

    // 下载结果
    public DownloadResult result;


    /**
     * Constructor
     * @param result
     */
    public DownloadMessage(DownloadResult result) {
        this.result = result;
    }


    /**
     * 是否可以继续
     * @return
     */
    public boolean canProceed(){
        // 如果文件已存在，或者下载成功，那么可以继续
        return result == DownloadResult.alreadyExists || result == DownloadResult.succeeded;
    }


    /**
     * toString
     * @return
     */
    public String toString(){
        if(result == DownloadResult.alreadyExists){
            return "already exists";
        } else if (result == DownloadResult.networkError){
            return "network error";
        } else if (result == DownloadResult.forbidden){
            return "forbidden";
        } else if (result == DownloadResult.succeeded){
            return "succeeded";
        } else {
            return null;
        }
    }
}
