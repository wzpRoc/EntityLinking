package org.ailab.wimfra.util;


import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * User: Lu Tingming
 * Date: 2011-8-28
 * Time: 13:06:46
 * Desc: 系统配置
 */
public class Config {
    private static Logger logger = Logger.getLogger(Config.class);

    private static Properties p = new Properties();

    private static String classes_dir;

    public static String contextPath = "/IRICA";
    public static int USER_MESSAGE_UPDATE_INTERVAL_SECOND = 5 * 60;
//    public static int USER_MESSAGE_UPDATE_INTERVAL_SECOND = 10;

    private static String webRootDir = "D:\\Projects\\IRICA\\Project\\out\\artifacts\\unnamed";

    private static String gateHome = "D:\\GATE";
    private static String stanfordNERClassifierPath = "E:\\ProjectsOfOthers\\StanfordNLP\\stanford-ner-2011-06-19\\classifiers\\all.3class.distsim.crf.ser.gz";

    private static String ictclasHome = "D:\\ICTCLAS\\ICTCLAS2013";
    private static String ictclasUserDicPath = "D:\\ICTCLAS\\ICTCLAS50_Windows_32_JNI\\API\\userDict.txt";
    private static String ictclasUserDicEncoding = "GBK";
    private static String imageSavePath ="E:/file/images/";
    private static String fileSavePath ="E:/uploadFile/";


    // 启用发送邮件
    private static boolean enablePostMail = false;
    // 启用发送短信
    private static boolean enablePostSM = false;

    private static boolean enableMatchTag = true;

    private static boolean enablePreprocess = true;
    private static boolean enableNER = false;
    private static boolean enableDBNELoader = true;
    private static boolean enableAuthorPublisherExtraction = true;
    private static boolean enableDocEntityStat = false;
    private static boolean enableGEOExtraction = false;
    private static boolean enableEventExtraction = false;
    private static boolean enableQuotationExtraction = false;
    private static boolean enableRelationExtraction = false;
    private static boolean enableOpinionMining = false;
    private static boolean enableTopicClustering = false;
    private static boolean enableSubjectAnalysis = false;

    private static boolean enableSimplifiedChinese = true;
    private static boolean enableTraditionalChinese = true;
    private static boolean enableEnglish = true;
    private static boolean enableJapanese = true;
    private static boolean enableRussian = true;

    // 反馈处理组ID
    private static int feedbackDealGroupId = 7;

    // 实体缓存初始化限制，-1表示不限
    private static int entityCacheInitLimit = -1;

    /////////////////////////////////////////////////////////////////////////////////////
    // 代理开始
    public static boolean PROXY_ENABLE = false;
    public static String PROXY_HOST;
    public static int PROXY_PORT;
    public static String PROXY_USER;
    public static String PROXY_PASSWORD;
    public static String PROXY_CREDENTIALS;
    public static String PROXY_NTCredentials1_usernamePassword;
    public static String PROXY_NTCredentials2_username;
    public static String PROXY_NTCredentials2_password;
    public static String PROXY_NTCredentials2_workstation;
    public static String PROXY_NTCredentials2_domain;

    // 代理结束
    /////////////////////////////////////////////////////////////////////////////////////

    public static String rssCrawlerPages;
    public static boolean demoMode = false;
    public static String currentDate = null;

    static {
        reinit();
    }

    public static void reinit() {

        try {
            URL resource = Config.class.getClassLoader().getResource("config.properties");
            if (resource == null) {
                throw new Exception("Can not find config.properties!");
            }
            File file = new File(resource.toURI());
            classes_dir = FileUtil.appendSeparater(file.getParent());

            //读取配置文件
            InputStream is = Config.class.getClassLoader().getResourceAsStream("config.properties");
            p.load(is);

            String temp = p.getProperty("contextPath");
            if (temp != null) {
                contextPath = temp;
            }

            webRootDir = p.getProperty("webRootDir");
            if (StringUtil.isEmpty(webRootDir)) {
                System.err.println("webRootDir not set, using .");
            }
            if (StringUtil.notEmpty(webRootDir)) {
                if (!webRootDir.endsWith(System.getProperty("file.separator"))) {
                    webRootDir += System.getProperty("file.separator");
                }
            }

            gateHome = p.getProperty("gateHome");
            stanfordNERClassifierPath = p.getProperty("stanfordNERClassifierPath");
            enableMatchTag = "true".equalsIgnoreCase(p.getProperty("enableMatchTag"));

            ictclasHome = p.getProperty("ictclasHome");
            ictclasUserDicPath = p.getProperty("ictclasUserDicPath");
            ictclasUserDicEncoding = p.getProperty("ictclasUserDicEncoding");

            enablePostMail = "true".equalsIgnoreCase(p.getProperty("enablePostMail")) || "1".equalsIgnoreCase(p.getProperty("enablePostMail"));
            enablePostSM = "true".equalsIgnoreCase(p.getProperty("enablePostSM")) || "1".equalsIgnoreCase(p.getProperty("enablePostSM"));

            enablePreprocess = "true".equalsIgnoreCase(p.getProperty("enablePreprocess"));
            enableNER = "true".equalsIgnoreCase(p.getProperty("enableNER"));
            enableDBNELoader = "true".equalsIgnoreCase(p.getProperty("enableDBNELoader"));
            enableAuthorPublisherExtraction = "true".equalsIgnoreCase(p.getProperty("enableAuthorPublisherExtraction"));
            enableDocEntityStat = "true".equalsIgnoreCase(p.getProperty("enableDocEntityStat"));
            enableGEOExtraction = "true".equalsIgnoreCase(p.getProperty("enableGEOExtraction"));
            enableEventExtraction = "true".equalsIgnoreCase(p.getProperty("enableEventExtraction"));
            enableQuotationExtraction = "true".equalsIgnoreCase(p.getProperty("enableQuotationExtraction"));
            enableRelationExtraction = "true".equalsIgnoreCase(p.getProperty("enableRelationExtraction"));
            enableOpinionMining = "true".equalsIgnoreCase(p.getProperty("enableOpinionMining"));
            enableTopicClustering = "true".equalsIgnoreCase(p.getProperty("enableTopicClustering"));
            enableSubjectAnalysis = "true".equalsIgnoreCase(p.getProperty("enableSubjectAnalysis"));

            enableSimplifiedChinese = "true".equalsIgnoreCase(p.getProperty("enableSimplifiedChinese"));
            enableTraditionalChinese = "true".equalsIgnoreCase(p.getProperty("enableTraditionalChinese"));
            enableEnglish = "true".equalsIgnoreCase(p.getProperty("enableEnglish"));
            enableJapanese = "true".equalsIgnoreCase(p.getProperty("enableJapanese"));
            enableRussian = "true".equalsIgnoreCase(p.getProperty("enableRussian"));

            entityCacheInitLimit = getPropertyInt("entityCacheInitLimit", -1);

            rssCrawlerPages = p.getProperty("rssCrawlerPages");
            if (rssCrawlerPages == null) {
                System.err.println("rssCrawlerPages is null");
            }

            demoMode = "true".equalsIgnoreCase(p.getProperty("demoMode"));
            currentDate = p.getProperty("currentDate");
            imageSavePath =p.getProperty("imageSavePath");
            fileSavePath=p.getProperty("fileSavePath");

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    private static String getProperty(String name) {
        return p.getProperty(name);
    }


    private static int getPropertyInt(String name, int defaultValue) {
        final String property = p.getProperty(name);
        if (StringUtil.isEmpty(property)) {
            logger.debug("property " + name + " not configured, use " + defaultValue);
            return defaultValue;
        }
        try {
            return Integer.parseInt(property.trim());
        } catch (Exception e) {
            logger.error(e);
            logger.debug("failed to parse property " + name + ", use " + defaultValue);
            return defaultValue;
        }
    }

    /**
     * 得到GATE路径
     *
     * @return
     */
    public static String getGateHome() {
        return gateHome;
    }

    public static boolean isEnableNER() {
        return enableNER;
    }

    public static boolean isEnableDocEntityStat() {
        return enableDocEntityStat;
    }

    public static boolean isEnableGEOExtraction() {
        return enableGEOExtraction;
    }

    public static boolean isEnableEventExtraction() {
        return enableEventExtraction;
    }

    public static boolean isEnableRelationExtraction() {
        return enableRelationExtraction;
    }

    public static boolean isEnableOpinionMining() {
        return enableOpinionMining;
    }

    public static boolean isEnableTopicClustering() {
        return enableTopicClustering;
    }

    public static boolean isEnableSimplifiedChinese() {
        return enableSimplifiedChinese;
    }

    public static boolean isEnableTraditionalChinese() {
        return enableTraditionalChinese;
    }

    public static boolean isEnableEnglish() {
        return enableEnglish;
    }

    public static boolean isEnableJapanese() {
        return enableJapanese;
    }

    public static boolean isEnableRussian() {
        return enableRussian;
    }

    public static boolean isEnablePreprocess() {
        return enablePreprocess;
    }

    public static boolean isEnableSubjectAnalysis() {
        return enableSubjectAnalysis;
    }

    public static String getStanfordNERClassifierPath() {
        return stanfordNERClassifierPath;
    }

    public static String getIctclasHome() {
        return ictclasHome;
    }

    public static boolean isEnableMatchTag() {
        return enableMatchTag;
    }

    public static boolean isEnableDBNELoader() {
        return enableDBNELoader;
    }

    public static void setEnableDBNELoader(boolean enableDBNELoader) {
        Config.enableDBNELoader = enableDBNELoader;
    }

    public static boolean isEnableQuotationExtraction() {
        return enableQuotationExtraction;
    }

    public static void setEnableQuotationExtraction(boolean enableQuotationExtraction) {
        Config.enableQuotationExtraction = enableQuotationExtraction;
    }

    public static String getIctclasUserDicPath() {
        return ictclasUserDicPath;
    }

    public static void setIctclasUserDicPath(String ictclasUserDicPath) {
        Config.ictclasUserDicPath = ictclasUserDicPath;
    }

    public static String getIctclasUserDicEncoding() {
        return ictclasUserDicEncoding;
    }

    public static void setIctclasUserDicEncoding(String ictclasUserDicEncoding) {
        Config.ictclasUserDicEncoding = ictclasUserDicEncoding;
    }

    public static boolean isEnableAuthorPublisherExtraction() {
        return enableAuthorPublisherExtraction;
    }

    public static void setEnableAuthorPublisherExtraction(boolean enableAuthorPublisherExtraction) {
        Config.enableAuthorPublisherExtraction = enableAuthorPublisherExtraction;
    }

    public static int getFeedbackDealGroupId() {
        return feedbackDealGroupId;
    }

    public static String getClasses_dir() {
        return classes_dir;
    }

    public static String getWebRootDir() {
        return webRootDir;
    }

    public static void setWebRootDir(String webRootDir) {
        Config.webRootDir = webRootDir;
    }

    public static boolean isEnablePostMail() {
        return enablePostMail;
    }

    public static boolean isEnablePostSM() {
        return enablePostSM;
    }

    public static int getEntityCacheInitLimit() {
        return entityCacheInitLimit;
    }

    public static void setEntityCacheInitLimit(int entityCacheInitLimit) {
        Config.entityCacheInitLimit = entityCacheInitLimit;
    }

    public static String getContextPath() {
        return contextPath;
    }

    public static void setContextPath(String contextPath) {
        Config.contextPath = contextPath;
    }

    public static String getImageSavePath() {
        return imageSavePath;
    }

    public static void setImageSavePath(String imageSavePath) {
        Config.imageSavePath = imageSavePath;
    }

    public static String getFileSavePath() {
        return fileSavePath;
    }

    public static void setFileSavePath(String fileSavePath) {
        Config.fileSavePath = fileSavePath;
    }

    public static void main(String[] args) {

    }
}
