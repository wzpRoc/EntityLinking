package org.ailab.nlp.parsing.chinese;

import ICTCLAS.I3S.AC.ICTCLAS50;
import org.ailab.nlp.annotation.Annotation;
import org.ailab.nlp.annotation.IAnnotation;
import org.ailab.nlp.parsing.ParsingUtil;
import org.ailab.nlp.parsing.SentenceAnnotation;
import org.ailab.nlp.parsing.TokenAnnotation;
import org.ailab.wimfra.util.Config;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


/**
 * User: Lu Tingming
 * Date: 2011-8-15
 * Time: 22:23:35
 * Desc: 中文语法分析
 */
@SuppressWarnings({"ALL"})
public class ChineseParser {
    // 默认的ICTCLAS安装目录
    public static final String defaultHome = "D:\\ICTCLAS\\ICTCLAS50_Windows_64_JNI\\API";

    // ICTCLAS安装目录
    private String homeOfIctclas;

    // 用户词典的编码
    private String encodingOfUserDic;

    // 用户词典的路径
    private String userDicPath;

    // ICTCLAS对象
    public ICTCLAS50 ictclas50;

    // 全局实例
    private static ChineseParser instance;


    /**
     * 获得实例
     *
     * @return
     * @throws Exception
     */
    public static ChineseParser getInstance() {
        if (instance == null) {
            instance = new ChineseParser();
            instance.init(Config.getIctclasHome(), Config.getIctclasUserDicPath(), Config.getIctclasUserDicEncoding());
        }
        return instance;
    }


    /**
     * 初始化
     *
     * @param homeOfIctclas
     */
    public void init(String homeOfIctclas, String userDicPath, String encodingOfUserDic) {
        if (homeOfIctclas == null || "".equals(homeOfIctclas)) {
            homeOfIctclas = Config.getIctclasHome();
        }

        if (userDicPath == null || "".equals(userDicPath)) {
            userDicPath = Config.getIctclasUserDicPath();
        }

        if (encodingOfUserDic == null || "".equals(encodingOfUserDic)) {
            encodingOfUserDic = Config.getIctclasUserDicEncoding();
        }

        this.homeOfIctclas = homeOfIctclas;
        this.userDicPath = userDicPath;
        this.encodingOfUserDic = encodingOfUserDic;

        System.out.println("java.library.path=" + System.getProperty("java.library.path"));
        ictclas50 = new ICTCLAS50();
        //初始化
        try {
            if (ictclas50.ICTCLAS_Init(this.homeOfIctclas.getBytes("GB2312")) == false) {
                System.err.println("Init Fail!");
                throw new RuntimeException("ICTCLAS init failed!");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        if (userDicPath != null && !"".equals(userDicPath)) {
            byte[] usrdirb = userDicPath.getBytes();
            //第一个参数为用户字典路径，第二个参数为用户字典的编码类型(0:type unknown;1:ASCII码;2:GB2312,GBK,GB10380;3:UTF-8;4:BIG5)
            int encoding;
            if (encodingOfUserDic == null
                    || "GBK".equalsIgnoreCase(encodingOfUserDic)
                    || "GB2312".equalsIgnoreCase(encodingOfUserDic)
                    ) {
                encoding = 2;
            } else {
                encoding = 3;
            }
            int nCount = ictclas50.ICTCLAS_ImportUserDictFile(usrdirb, encoding);
            System.out.println("导入用户词个数" + nCount);
        }
    }


    /**
     * 解析文本,返回标注列表
     */
    public synchronized static List<TokenAnnotation> parse(String text) {
//        System.out.println(System.getProperty("java.library.path"));
        return getInstance().parseByInstance(text);
    }


    /**
     * 解析文本,返回句子标注列表，句子中包含词列表
     */
    public synchronized static List<SentenceAnnotation> parseSentenceToken(String text) throws Exception {
        List<TokenAnnotation> tokenAnnotationList = getInstance().parseByInstance(text);

        List<SentenceAnnotation> sentenceAnnotationList = ChineseUtil.splitSentence(text);
        ParsingUtil.setSentenceSeqToToken(sentenceAnnotationList, tokenAnnotationList, true);

        return sentenceAnnotationList;
    }


    /**
     * 解析文本,返回标注列表
     */
    public synchronized List<TokenAnnotation> parseByInstance(String text) {
        List<TokenAnnotation> al = new ArrayList<TokenAnnotation>();
        if(text == null) return al;
        //处理字符串
        final byte[] bytesOfString;
        try {
            bytesOfString = text.getBytes("GB2312");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        byte nativeBytes[] = ictclas50.nativeProcAPara(bytesOfString, 2, 1);

        int startInString = 0;

        //处理结果转化
        //for (int i = 0; i < nativeBytes.length; i++) {
        for (int i = 0; i < nativeBytes.length; ) {
            //获取词语在输入句子中的开始位置
            byte a[] = Arrays.copyOfRange(nativeBytes, i, i + 4);
            i += 4;
            int start = byteToInt2(a);
            start = Integer.reverseBytes(start);

            //获取词语的长度
            byte b[] = Arrays.copyOfRange(nativeBytes, i, i + 4);
            i += 4;
            int length = byteToInt2(b);
            length = Integer.reverseBytes(length);

            //获取词性ID
            byte c[] = Arrays.copyOfRange(nativeBytes, i, i + 4);
            String sPos = new String(c).trim();
            i += 4;

            //获取词性 lutm
            byte ss[] = Arrays.copyOfRange(nativeBytes, i + 4, i + 8);
            int posId = byteToInt2(ss);
            posId = Integer.reverseBytes(posId);

            i += 8;

            //获取词语ID
            byte j[] = Arrays.copyOfRange(nativeBytes, i, i + 4);
            i += 4;
            int word_ID = byteToInt2(j);
            word_ID = Integer.reverseBytes(word_ID);

            //获取词语类型，查看是否是用户字典
            byte k[] = Arrays.copyOfRange(nativeBytes, i, i + 4);
            i += 4;
            int word_type = byteToInt2(k);
            word_type = Integer.reverseBytes(word_type);

            //获取词语权重
            byte w[] = Arrays.copyOfRange(nativeBytes, i, i + 4);
            i += 4;
            int weight = byteToInt2(w);
            weight = Integer.reverseBytes(weight);

            // 词形
            // String string = sInput.substring(startInString, startInString+lengthInString);
            String string = null;
            try {
                string = new String(bytesOfString, start, length, "GB2312");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            int lengthOfString = string.length();

            //将处理结果赋值给结构体
            // final IctclasCategoryStruct categoryStruct = IctclasUtil.getStructById(posId);
            final IctclasCategoryStruct categoryStruct = IctclasUtil.getStructByCategory(sPos.toLowerCase());
            if (categoryStruct == null) {
                throw new RuntimeException("Can not find category struct by sPos (" + sPos + ")");
            }
            TokenAnnotation annotation = new TokenAnnotation(categoryStruct.category, startInString, startInString + lengthOfString, string);

//            final IctclasCategoryStruct categoryStructById = IctclasUtil.getStructById(posId);
//            if (categoryStructById == null) {
//                throw new Exception("Can not find category struct by id (" + posId + ")");
//            }
//            annotation.categoryById = categoryStructById.category;

            startInString = startInString + lengthOfString;
            // System.out.println(annotation);
            al.add(annotation);
        }

        return al;
    }


    /**
     * 分词
     */
    public static synchronized String split(String text, boolean needPosTag) throws Exception {
        return getInstance().splitByInstance(text, needPosTag);
    }


    /**
     * 分词
     */
    public String splitByInstance(String text, boolean needPosTag) throws Exception {
        //处理字符串
        final byte[] bytesOfString = text.getBytes("GB2312");
        byte nativeBytes[] = ictclas50.ICTCLAS_ParagraphProcess(bytesOfString, 2, needPosTag ? 1 : 0);
        String string = new String(nativeBytes, "GB2312");

        return string;
    }


    /**
     * 释放资源
     */
    public void release() {
        ictclas50.ICTCLAS_Exit();
    }


    /**
     * 字节转为整数
     *
     * @param b
     * @return
     */
    public static int byteToInt2(byte[] b) {
        int mask = 0xff;
        int temp = 0;
        int n = 0;
        for (int i = 0; i < 4; i++) {
            n <<= 8;
            temp = b[i] & mask;
            n |= temp;
        }
        return n;
    }


    /**
     * 获得分割的索引
     *
     * @param annoSet
     * @return
     */
    public HashSet<Integer> getSegIdxSet(ArrayList<IctclasAnnotation> annoSet) {
        HashSet<Integer> segIdxSet = new HashSet<Integer>();

        for (IctclasAnnotation anno : annoSet) {
            segIdxSet.add(anno.start);
            segIdxSet.add(anno.end);
        }

        return segIdxSet;
    }


    /**
     * 测试处理文件
     */
    public static void testICTCLAS_FileProcess() {
        try {
            ICTCLAS50 testICTCLAS50 = new ICTCLAS50();
            //分词所需库的路径
            String argu = "D:/soft/ICTCLAS/ICTCLAS50_Windows_32_JNI/API";
            //初始化
            if (testICTCLAS50.ICTCLAS_Init(argu.getBytes("GB2312")) == false) {
                System.out.println("Init Fail!");
                return;
            }

            //输入文件名
            String Inputfilename = "d:\\wzp.newsML.test.txt";
            byte[] Inputfilenameb = Inputfilename.getBytes();//将文件名string类型转为byte类型

            //分词处理后输出文件名
            String Outputfilename = "d:\\test_result.txt";
            byte[] Outputfilenameb = Outputfilename.getBytes();//将文件名string类型转为byte类型

            //文件分词(第一个参数为输入文件的名,第二个参数为文件编码类型,第三个参数为是否标记词性集1 yes,0 no,第四个参数为输出文件名)
            testICTCLAS50.ICTCLAS_FileProcess(Inputfilenameb, 0, 0, Outputfilenameb);

            int nCount = 0;
            String usrdir = "userdict.txt"; //用户字典路径
            byte[] usrdirb = usrdir.getBytes();//将string转化为byte类型
            //第一个参数为用户字典路径，第二个参数为用户字典的编码类型(0:type unknown;1:ASCII码;2:GB2312,GBK,GB10380;3:UTF-8;4:BIG5)
            nCount = testICTCLAS50.ICTCLAS_ImportUserDictFile(usrdirb, 0);//导入用户字典,返回导入用户词语个数
            System.out.println("导入用户词个数" + nCount);
            nCount = 0;

            String Outputfilename1 = "testing_result.txt";
            byte[] Outputfilenameb1 = Outputfilename1.getBytes();//将文件名string类型转为byte类型

            //文件分词(第一个参数为输入文件的名,第二个参数为文件编码类型,第三个参数为是否标记词性集1 yes,0 no,第四个参数为输出文件名)
            testICTCLAS50.ICTCLAS_FileProcess(Inputfilenameb, 0, 0, Outputfilenameb1);


        } catch (Exception ex) {
        }

    }


    /**
     * 测试分割
     */
    public static void testSplit() {
//        String text = "  a\n\n" + " bc\n\n";
        String text = "　本报讯（记者易靖）记者昨天从铁道部获悉，铁道部新闻发言人王勇平不再担任铁道部新闻发言人、政治部宣传部部长职务。\n" +
                "\n" +
                "　　王勇平原任铁道部政治部副主任、政治部宣传部部长、铁道部新闻发言人职务。铁道部相关负责人昨晚表示，王勇平不再担任铁道部新闻发言人、政治部宣传部部长职务。他强调，“这不是免职或被停职，而是正常的职务变动，王勇平的级别待遇没变，调到哪个部门还没定。”对于王勇平是否仍担任铁道部政治部副主任一职，该负责人并未透露。\n" +
                "\n" +
                "　　有知情人士透露，接任铁道部新闻发言人职务的，可能是哈尔滨铁路局党委书记韩江平。他表示，这一调整应与王勇平在温州事故新闻发布会表现欠妥有关。\n" +
                "\n" +
                "　　7月24日晚，在“7·23”动车追尾事故发生26个小时后，铁道部在温州水心饭店召开新闻发布会，王勇平通报了事故情况，并回答了部分记者的提问。\n" +
                "\n" +
                "　　在回答“为什么要掩埋车头”时，王勇平解释，参与救援的人告诉他，为填平泥潭，方便救援，并称“他们是这么说的，至于你信不信（由你），我反正信了”。在回答记者“为何在宣布没有生命体征、停止救援后，又发现小女孩项炜伊时”，王勇平回答：“这只能说是生命的奇迹。”这些回答引发网友对铁道部的质疑，并成为网络流行语，该句式也被称为“高铁体”。\n" +
                "\n" +
                "　　此后，王勇平返京，未再在公众面前露面，也未再接受采访。日前曾有传言称，王勇平的工作已被调整，由新的新闻发言人代替其工作；另一种说法是，王勇平回京后即被停职。7月29日，铁道部新闻处有关负责人证实，王勇平仍为铁道部新闻发言人，工作没有调整，“他不是事故责任人，也不是救援人员，只是做了他职责范围内的事情（开新闻发布会），没有问题”。\n" +
                "\n" +
                "　　知情人士介绍，王勇平职务的调整可能就是近几天做出的决定。";

        ChineseParser p = new ChineseParser();

        ArrayList<Annotation> pal = ChineseUtil.splitParagraph(text);
        for (Annotation a : pal) {
            System.out.println(a.toString() + "\t[" + text.substring(a.start, a.end) + "]");
        }

        System.out.println("------------------------");

        List<SentenceAnnotation> sal = ChineseUtil.splitSentence(text);
        for (Annotation a : sal) {
            System.out.println(a.toString() + "\t[" + text.substring(a.start, a.end) + "]");
        }
    }


    private static void testParse() throws Exception {
        ChineseParser parser = new ChineseParser();
        parser.init(null, null, null);

        String text = "据苏安社报道，奥巴马担任美国总统。";

        List<TokenAnnotation> annoList = parser.parseByInstance(text);

        parser.release();

        for (IAnnotation a : annoList) {
            System.out.println(a.getType() + " " + a.getText());
        }
    }


    /**
     * for wzp.newsML.test
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        testParse();

        String text = "据苏安社报道，奥巴马担任美国总统。";
//        String text = "北京市，简称“京”，是中华人民共和国首都、直辖市和京津冀城市群的中心，中国的政治、文化、交通、科技创新和国际交往中心，中国经济、金融的决策和管理中心。北京是世界上最大的城市之一，具有重要的国际影响力。人类发展指数位居中国大陆省级行政区第一位。科尔尼咨询公司（AtKearne商业活动、人力资源、信息交流政治参与等五个领域对全球城市作出排指数和新兴城市的前景：全球城市现在和未来》的报告中，北京排名第8位。";
        List<TokenAnnotation> tokenAnnotationList = parse(text);

        for (IAnnotation a : tokenAnnotationList) {
            System.out.println(a.getType() + " " + a.getText());
        }
    }

}
