package org.ailab.wikipedia.crawler;

import org.ailab.wimfra.util.FileUtil;
import org.apache.log4j.Logger;

import org.ailab.wimfra.util.RandomUtil;
import org.ailab.wimfra.util.StringUtil;
import org.ailab.wimfra.util.time.TimeUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.ProxySelectorRoutePlanner;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import sun.misc.BASE64Encoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ProxySelector;
import java.util.Calendar;
import java.util.Properties;

/**
 * Date: 2010-11-12 12:42:59
 * Desc: 爬虫框架
 * 1. 具有下载功能
 * 2. 具有过程控制功能，根据状态决定继续、等待、退出
 */
public class Crawler {
    //////////////////////////////////////////////////////////////////////////////
    //                       以下为代理相关
    // 是否启用代理
    public static boolean PROXY_ENABLE = false;
    // 主机
    public static String PROXY_HOST;
    // 端口
    public static int PROXY_PORT;
    // 用户
    public static String PROXY_USER;
    // 密码
    public static String PROXY_PASSWORD;
    // 凭证
    public static String PROXY_CREDENTIALS;
    // NTCredentials1的用户名密码
    public static String PROXY_NTCredentials1_usernamePassword;
    // NTCredentials1的用户名
    public static String PROXY_NTCredentials2_username;
    // NTCredentials1的密码
    public static String PROXY_NTCredentials2_password;
    // NTCredentials1的域
    public static String PROXY_NTCredentials2_domain;
    // NTCredentials1的workstation
    public static String PROXY_NTCredentials2_workstation;
    //                       以上为代理相关
    //////////////////////////////////////////////////////////////////////////////


    public static boolean ENABLE_FILE = true;
    // 最多网络错
    private final int MAX_NETWORK_ERROR = 20;
    // 最多禁止访问
    private final int MAX_FORBIDDEN = 4;
    // 网络错误后休眠时长（秒）
    private final int SLEEP_SECONDS_AFTER_NETWORK_ERROR = 5;

    // 最长连接时间（分钟）
    public int maxConnectionMinutes = 45;
    // 下载休眠时间（秒）
    public int sleepSecondsAfterDownload = 2;
    // 连接休眠时间（分钟）
    public int sleepMinutesAfterConnection = 10;
    //    public int sleepMinutesAfterConnection = 1;
    // 禁止访问后休眠时长（分钟）
    public int sleepMinutesAfterForbiden = 5;
    // 网络错误重试次数
    public int nRetries = 2;

    // 日志记录器
    public Logger logger;

    // 客户端
    private DefaultHttpClient client;
    // 上下文
    private HttpContext context;
    // 连接开始时间
    private long startTimeOfConnection;

    // 下载器级别计数
    // 总任务数
    public int total_all;
    // 网络错误
    public int total_networkError;
    // 文件已存在
    public int total_exists;
    // 禁止访问
    public int total_forbidden;
    // 成功
    public int total_succeed;

    // 连接级别的计数
    // 总任务数
    public int cnt_all;
    // 网络错误
    public int cnt_networkError;
    // 文件已存在
    public int cnt_exists;
    // 禁止访问
    public int cnt_forbidden;
    // 成功
    public int cnt_succeed;


    public Crawler() {
        init();
    }

    /**
     * 得到下载任务数
     *
     * @return
     */
    public int getDownloadTaskCountOfCurrentConnection() {
        return cnt_all - cnt_exists;
    }


    /**
     * 得到下载任务的平均时长
     *
     * @return
     */
    public long getAverageDurationOfCurrentConnection() {
        // 时长
        long duration = System.currentTimeMillis() - startTimeOfConnection;
        // 任务数
        int tasks = getDownloadTaskCountOfCurrentConnection();

        // 计算平均时长
        if (tasks == 0) {
            return 0;
        } else {
            return duration / tasks;
        }
    }


    /**
     * 初始化
     */
    public void init(Logger logger) {
        if (logger == null) {
            this.logger = Logger.getLogger(this.getClass());
        } else {
            this.logger = logger;
        }
    }


    /**
     * 初始化
     */
    public void init() {
        init(null);
    }


    /**
     * 创建连接
     *
     * @throws Exception
     */
    public void createConnection() throws Exception {
        // 清空计数器
        cnt_all = 0;
        cnt_exists = 0;
        cnt_networkError = 0;
        cnt_forbidden = 0;
        cnt_succeed = 0;

        // 记录开始时间
        startTimeOfConnection = System.currentTimeMillis();

        // 创建上下文
        context = new BasicHttpContext();
        // 创建客户端
        client = new DefaultHttpClient();
        // 设置超时时长
        client.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60 * 1000);
        client.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 60 * 1000);
        client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1");

        // 设置代理
        logger.debug("PROXY_ENABLE=[" + PROXY_ENABLE + "]");
        if (PROXY_ENABLE) {
            logger.debug("PROXY_HOST=[" + PROXY_HOST + "]");
            logger.debug("PROXY_PORT=[" + PROXY_PORT + "]");
            if ("HuaweiCredentials".equalsIgnoreCase(PROXY_CREDENTIALS)) {
                // HuaweiCredentials
                // 1. 打印相关信息
                logger.debug("Using HuaweiCredentials");
                System.out.println("PROXY_USER=[" + PROXY_USER + "]");
                // System.out.println("PROXY_PASSWORD=[" + PROXY_PASSWORD + "]");
                // 2. 设置代理
                Properties prop = System.getProperties();
                prop.put("proxySet", "true");
                prop.put("http.proxyHost", PROXY_HOST);
                prop.put("http.proxyPort", String.valueOf(PROXY_PORT));
                // 3. 设置httpClient代理路由
                ProxySelectorRoutePlanner routePlanner = new ProxySelectorRoutePlanner(
                        client.getConnectionManager().getSchemeRegistry(),
                        ProxySelector.getDefault());
                client.setRoutePlanner(routePlanner);
            } else {
                HttpHost proxy = new HttpHost(PROXY_HOST, PROXY_PORT);
                client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

                // 设置授权范围
                // final AuthScope authScope = new AuthScope(PROXY_HOST, PROXY_PORT);
                // 设置授权范围为全部
                final AuthScope authScope = AuthScope.ANY;

                /*
                // 注册NTLM
                client.getAuthSchemes().register("ntlm",new NTLMSchemeFactory());
                List<String> authpref = new ArrayList<String>();
                authpref.add(AuthPolicy.NTLM);
                client.getParams().setParameter(AuthPNames.PROXY_AUTH_PREF, authpref);
                */

                // 按照授权设置分情况处理
                logger.debug("PROXY_CREDENTIALS=[" + PROXY_CREDENTIALS + "]");
                if ("NTCredentials1".equalsIgnoreCase(PROXY_CREDENTIALS)) {
                    // NTCredentials1
                    logger.debug("Using NTCredentials1");
                    logger.debug("PROXY_NTCredentials1_usernamePassword=[" + PROXY_NTCredentials1_usernamePassword + "]");
                    final NTCredentials ntCredentials1 = new NTCredentials(PROXY_NTCredentials1_usernamePassword);
                    client.getCredentialsProvider().setCredentials(authScope, ntCredentials1);
                } else if ("NTCredentials2".equalsIgnoreCase(PROXY_CREDENTIALS)) {
                    // NTCredentials2
                    logger.debug("Using NTCredentials2");
                    logger.debug("PROXY_NTCredentials2_username=[" + PROXY_NTCredentials2_username + "]");
                    logger.debug("PROXY_NTCredentials2_password=[" + PROXY_NTCredentials2_password + "]");
                    logger.debug("PROXY_NTCredentials2_workstation=[" + PROXY_NTCredentials2_workstation + "]");
                    logger.debug("PROXY_NTCredentials2_domain=[" + PROXY_NTCredentials2_domain + "]");
                    final NTCredentials ntCredentials2 = new NTCredentials(
                            PROXY_NTCredentials2_username,
                            PROXY_NTCredentials2_password,
                            PROXY_NTCredentials2_workstation,
                            PROXY_NTCredentials2_domain);
                    client.getCredentialsProvider().setCredentials(authScope, ntCredentials2);
                } else {
                    logger.debug("PROXY_USER=[" + PROXY_USER + "]");
                    if (!StringUtil.isEmpty(PROXY_USER)) {
                        logger.debug("Using simple username/password");
                        // logger.debug("PROXY_PASSWORD=[" + PROXY_PASSWORD + "]");
                        final UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials(PROXY_USER, PROXY_PASSWORD);
                        client.getCredentialsProvider().setCredentials(authScope, usernamePasswordCredentials);
                    } else {
                        logger.debug("Using none credential");
                    }
                }
            }
        }
    }


    /**
     * 关闭连接
     */
    public void closeConnection() {
        // 更新下载器级别计数器
        total_all += cnt_all;
        total_exists += cnt_exists;
        total_networkError += cnt_networkError;
        total_forbidden += cnt_forbidden;
        total_succeed += cnt_succeed;

        // 关闭客户端
        if (client != null) {
            client.getConnectionManager().shutdown();
        }
        client = null;
        context = null;

        // 清空连接级别计数器
        cnt_all = 0;
        cnt_exists = 0;
        cnt_networkError = 0;
        cnt_forbidden = 0;
        cnt_succeed = 0;
    }


    /**
     * 更新会话
     *
     * @return 返回是否成功
     * @throws Exception
     */
    public boolean updateSession() throws Exception {
        return true;
    }


    /**
     * 更新URL，比如置换验证码
     *
     * @param url
     * @return
     */
    public String updateUrl(String url) {
        return url;
    }

    public DownloadMessage download(String link, String path) throws Exception {
        return download(new Page(link, path));
    }

    /**
     * 下载页面
     *
     * @param page
     * @return
     * @throws Exception
     */
    public DownloadMessage download(Page page) throws Exception {
        // 总任务数
        cnt_all++;

        if (ENABLE_FILE && FileUtil.exists(page.path)) {
            // 文件已存在
            logger.debug("Already exists.");
            cnt_exists++;
            return DownloadMessage.ALREADY_EXISTS;
        }

        // 检查连接状态
        checkConnectionState();

        if (client == null) {
            // 创建连接
            logger.debug("create connection");
            createConnection();
            // 更新会话
            if (!updateSession()) {
                // 更新会话失败，关闭连接
                closeConnection();
                logger.debug("update session failed. sleep 15 minutes");
                Thread.sleep(15 * 60 * 1000);
                throw new Exception("update session failed");
            }
        }

        // after updating session
        page.url = updateUrl(page.url);
        if (page.url.startsWith("http://www.sciencedirect.com/science?_ob=PublicationURL&_tockey=")) {
            logger.debug("Expired URL: " + page.url);
            logger.debug("Please close current tool and run errorIssueDeleter.bat.");
            throw new Exception("Expired URL: " + page.url);
        }

        logger.debug("Downloading " + page.url);
        logger.debug("-> " + page.path);

        // 记录下载开始时间
        long downloadStartTime = Calendar.getInstance().getTimeInMillis();
        // 开始下载
        DownloadMessage dmsg = rawDownload(page);

        if (dmsg == DownloadMessage.NETWORK_ERROR) {
            // 下载失败（网络错误）
            logger.debug("Network error at 1st time!");

            // 重试
            for (int i = 1; i <= nRetries; i++) {
                logger.debug("Sleep " + (SLEEP_SECONDS_AFTER_NETWORK_ERROR) + " seconds ...");
                Thread.sleep(SLEEP_SECONDS_AFTER_NETWORK_ERROR * 1000);
                logger.debug("Retry " + i + " ...");
                dmsg = rawDownload(page);
                if (dmsg != DownloadMessage.NETWORK_ERROR) {
                    break;
                }
            }
        }

        DownloadMessage result;
        boolean needSleepAfterDownload = false;
        if (dmsg.canProceed()) {
            // 下载完成
            if (isFileValid(page)) {
                // 下载的文件正常
                result = DownloadMessage.SUCCEEDED;
                cnt_succeed++;
                logger.debug("Finished downloading.");
                needSleepAfterDownload = true;
            } else {
                // 下载的文件无效
                result = DownloadMessage.FORBIDDEN;
                cnt_forbidden++;
                logger.debug("!!!!!!!!!!!  Invalid file  !!!!!!!!!!!");
                // 移动无效文件
                String newPath = FileUtil.appendMainNameInPath(page.path, ".error");
                logger.debug("move this file to " + newPath);
                try {
                    FileUtil.move(page.path, newPath);
                } catch (Exception e) {
                    logger.debug("Failed to move!");
                    logger.debug(e);
                }
                // 检查禁止访问
                checkForbid();
            }
        } else if (dmsg == DownloadMessage.FORBIDDEN) {
            // 网络错误
            result = DownloadMessage.FORBIDDEN;
            cnt_forbidden++;
            logger.debug("Forbidden: " + page.url + "\n to " + page.path);
            // 检查网络错误
            checkForbid();
        } else {
            // 网络错误
            result = DownloadMessage.NETWORK_ERROR;
            cnt_networkError++;
            logger.debug("Failed to download " + page.url + "\n to " + page.path);
            // 检查网络错误
            checkNetworkError();
        }

        // 打印用时
        logger.debug(TimeUtil.calcDurationInSeconds(downloadStartTime) + " seconds taken.");

        if (needSleepAfterDownload) {
            // 下载后休眠
            int randomSleepSecondsAfterDownload = RandomUtil.getRandom(sleepSecondsAfterDownload + 1, 0.5);
            logger.debug("sleep " + randomSleepSecondsAfterDownload + " second"
                    + (randomSleepSecondsAfterDownload > 1 ? "s" : " ...")
            );
            // Thread.sleep(randomSleepSecondsAfterDownload * 1000);
        }

        return result;
    }


    /**
     * 判断文件是否有效
     *
     * @param page
     * @return
     */
    public boolean isFileValid(Page page) {
        return true;
    }


    /**
     * 下载（不进行过程控制）
     *
     * @return
     */
    public DownloadMessage rawDownload(Page page) {
        DownloadMessage msg;

        FileOutputStream fos = null;
        HttpGet httpget = null;
        try {
            // 请求
            httpget = new HttpGet(page.url);

            if ("HuaweiCredentials".equalsIgnoreCase(PROXY_CREDENTIALS)) {
                // HuaweiCredentials
                // 1. 打印相关信息
                logger.debug("Using HuaweiCredentials to download");
                // 3. 设置httpclient参数
                // 设置httpclient参数
                String login = PROXY_USER + ":" + PROXY_PASSWORD;
                String encoding = new BASE64Encoder().encode(login.getBytes());
                logger.debug("setHeader(\"Proxy-Authorization\", \"Basic " + encoding + "\")");
                httpget.setHeader("Proxy-Authorization", "Basic " + encoding);
            }

            HttpResponse response = client.execute(httpget, context);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                logger.debug("Error status code:" + response.getStatusLine().getStatusCode());
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                    msg = DownloadMessage.NOT_FOUND;
                } else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_FORBIDDEN) {
                    msg = DownloadMessage.FORBIDDEN;
                } else {
                    msg = DownloadMessage.NETWORK_ERROR;
                }
            } else {
                HttpEntity entity = response.getEntity();
                page.content = StringUtil.inputStreamToString(entity.getContent());

                // 创建目录（如果不存在）
                if (ENABLE_FILE) {
                    FileUtil.mkdirIfNotExist(FileUtil.getDir(page.path));
                    fos = new FileOutputStream(page.path);
                    // entity.writeTo(fos);
                    fos.write(page.content.getBytes());
                }

                msg = DownloadMessage.SUCCEEDED;
            }
        } catch (Exception e) {
            logger.debug(e);
            msg = DownloadMessage.NETWORK_ERROR;
            final String es = e.toString();
            if (e instanceof IOException) {
                if ("java.io.IOException: There is not enough space on the disk".equals(es)) {
                    logger.debug("Exit");
                    System.exit(0);
                } else if (es.startsWith("org.apache.http.conn.HttpHostConnectException: Connection to")
                        && es.endsWith(" refused")) {
                    msg = DownloadMessage.FORBIDDEN;
                }
            }
        } finally {
            if (httpget != null) {
                try {
                    httpget.abort();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return msg;
    }


    /**
     * 检查禁止访问
     *
     * @throws Exception
     */
    private void checkForbid() throws Exception {
        // 检查禁止访问次数是否达到最大
        if (cnt_forbidden >= MAX_FORBIDDEN) {
            // 关闭连接
            logger.debug("cnt_invalid>= MAX_INVALID(" + MAX_FORBIDDEN + ")");
            logger.debug("Close downloader");
            closeConnection();

            // 休眠
            int minMinute = (total_forbidden + cnt_forbidden) * sleepMinutesAfterForbiden;
            if (minMinute > 20) {
                // 最长休眠时间为20分钟
                minMinute = 20;
            }
            int rdm = RandomUtil.getRandom(minMinute, 0.3);
            logger.debug("Sleep " + rdm + " minutes");
            Thread.sleep(rdm * 60 * 1000);
        }
    }


    /**
     * 检查网络错误
     *
     * @throws Exception
     */
    private void checkNetworkError() throws Exception {
        // 检查错误次数是否达到最大
        if (cnt_networkError >= MAX_NETWORK_ERROR) {
            // 关闭连接
            logger.debug("downloader.cnt_failed>= MAX_FAILED(" + MAX_NETWORK_ERROR + ")");
            logger.debug("Close downloader");
            closeConnection();

            // 休眠1分钟
            logger.debug("Sleep 1 minutes");
            Thread.sleep(60 * 1000);
        }
    }


    /**
     * 检查连接状态
     *
     * @throws Exception
     */
    private void checkConnectionState() throws Exception {
        if (client == null) return;
        // 当前连接时长
        long connectionDuration = (System.currentTimeMillis() - startTimeOfConnection) / (60 * 1000);
        // 最大连接时长（小幅度随机浮动）
        int rdm_max_connection_minutes = RandomUtil.getRandom(maxConnectionMinutes, 0.3);
        if (connectionDuration >= rdm_max_connection_minutes) {
            // 超过最大连接时长
            logger.debug("Connection has been kept for " + connectionDuration + " minutes.");
            // 关闭连接
            logger.debug("Close connection");
            closeConnection();

            // 休眠
            int rdm_connection_sleep_minutes = RandomUtil.getRandom(sleepMinutesAfterConnection, 0.3);
            if (rdm_connection_sleep_minutes < 1) {
                rdm_connection_sleep_minutes = 1;
            }
            logger.debug("Sleep " + rdm_connection_sleep_minutes + " minutes.");
            Thread.sleep(rdm_connection_sleep_minutes * 60 * 1000);
        }
    }
}