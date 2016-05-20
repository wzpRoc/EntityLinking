package org.ailab.nlp.gate;

import gate.*;
import gate.creole.ResourceInstantiationException;
import gate.creole.SerialAnalyserController;
import org.ailab.wimfra.util.Config;
import org.apache.log4j.Logger;



import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * User: Lu Tingming
 * Date: 2011-8-26
 * Time: 16:45:23
 * Desc: GATE工具
 */
public class GateUtil {
    public static final Logger logger = Logger.getLogger(GateUtil.class);
    // GATE安装目录
    public static final File gateHome = new File(Config.getGateHome());
    // GATE插件目录
    public static final File pluginsHome = new File(gateHome, "plugins");
    // 是否已经初始化
    private static boolean isInitialized = false;


    /**
     * GATE初始化函数
     *
     * @throws gate.util.GateException
     */
    public static void init() throws Exception {
        if (!isInitialized) {
            logger.info("GateUtil.init()");
            System.setProperty("gate.home", gateHome.getAbsolutePath());
            Gate.init();
            registerPlugins();
            isInitialized = true;
            logger.info("Finished to initialize :)");
        }
    }

    /**
     * 注册插件目录
     *
     * @throws gate.util.GateException
     */
    public static void registerPlugins() throws Exception {
        Gate.getCreoleRegister().registerDirectories(new File(pluginsHome, "ANNIE").toURL());
        Gate.getCreoleRegister().registerDirectories(new File(pluginsHome, "Tools").toURL());
        Gate.getCreoleRegister().registerDirectories(new File(pluginsHome, "IRICA").toURL());
    }


    /**
     * 构造新的控制器
     *
     * @return
     * @throws gate.creole.ResourceInstantiationException
     */
    public static SerialAnalyserController createController() throws Exception {
        init();
        return (SerialAnalyserController) Factory.createResource(
                "gate.creole.SerialAnalyserController",
                Factory.newFeatureMap(),
                Factory.newFeatureMap(),
                "test_" + Gate.genSym()
        );
    }


    /**
     * 创建PR
     *
     * @return
     * @throws gate.creole.ResourceInstantiationException
     */
    public static ProcessingResource createPR(PRDesc prDesc, int languageId) throws ResourceInstantiationException {
        FeatureMap params = Factory.newFeatureMap();
        if (prDesc.params != null) {
            params.putAll(prDesc.params);
        }

        final String className = PRNames.getPRName(prDesc.type, languageId);
        // System.out.println("create resource: " + className);

        return (ProcessingResource) Factory.createResource(
                className,
                params
        );
    }


    /**
     * 创建JAPE PR
     *
     * @return
     * @throws gate.creole.ResourceInstantiationException
     */
    public static ProcessingResource createJapePR(String grammarURL) throws ResourceInstantiationException {
        // 参数，即规则URL
        FeatureMap params = Factory.newFeatureMap();
        grammarURL = GateUtil.ensafeURL(grammarURL);
        params.put("grammarURL", grammarURL);

        // 类名
        final String className = PRNames.JAPE_TRANSDUCER;

        // 创建PR
        return (ProcessingResource) Factory.createResource(
                className,
                params
        );
    }


    /**
     * 创建JAPE Controller
     *
     * @return
     * @throws gate.creole.ResourceInstantiationException
     */
    public static SerialAnalyserController createJapeController(String grammarURL) throws Exception {
        // 创建PR
        ProcessingResource pr = createJapePR(grammarURL);

        // 创建控制器
        SerialAnalyserController controller = createController();

        // 加入PR
        controller.add(pr);

        return controller;
    }


    /**
     * 检查URL
     *
     * @param path
     * @return
     */
    public static String ensafeURL(String path) {
        if (path.indexOf(':') >= 0) {
            // 绝对路径
        } else {
            // 相对路径
            File file = new File(path);
            boolean exists;
            if (file.exists()) {
                exists = true;
            } else {
                URL resource = GateUtil.class.getClassLoader().getResource(path);
                if(resource == null) {
                    exists = false;
                } else {
                    file = new File(resource.getPath());
                    if (file.exists()) {
                        exists = true;
                    } else {
                        file = new File(GateUtil.gateHome.getAbsolutePath() + "\\" + path);
                        if (file.exists()) {
                            exists = true;
                        } else {
                            exists = false;
                            // file = new File(GateUtil.gateHome.getAbsolutePath() + path);
                        }
                    }
                }
            }

            if (exists) {
                // 文件存在，那么改成绝对路径
                try {
                    path = file.toURL().toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
        return path;
    }


    public static String ensafePath(String path) {
        if (path.indexOf(':') >= 0) {
            // 绝对路径
        } else {
            // 相对路径
            File file = new File(path);
            boolean exists;
            if (file.exists()) {
                exists = true;
            } else {
                file = new File(GateUtil.gateHome.getAbsolutePath() + "\\" + path);
                if (file.exists()) {
                    exists = true;
                } else {
                    exists = false;
                    // file = new File(GateUtil.gateHome.getAbsolutePath() + path);
                }
            }

            path = file.getAbsolutePath();
        }
        return path;
    }


    /**
     * 排序
     *
     * @param annoSet
     * @return
     */
    public static ArrayList<Annotation> sort(AnnotationSet annoSet) {
        if (annoSet == null) {
            return null;
        }

        ArrayList<Annotation> list = new ArrayList<Annotation>(annoSet.size());
        final Iterator<Annotation> iterator = annoSet.iterator();
        for (int i = 0; i < annoSet.size(); i++) {
            Annotation anno = iterator.next();

            long start = anno.getStartNode().getOffset();

            boolean isInserted = false;
            // insert it
            for (int j = 0; j < i; j++) {
                Annotation currentAnno = list.get(j);
                if (start < currentAnno.getStartNode().getOffset()) {
                    list.add(j, anno);
                    isInserted = true;
                    break;
                }
            }
            if (!isInserted) {
                list.add(anno);
            }
        }

        return list;
    }


    /**
     * 返回任意一个标注
     *
     * @param annoSet
     * @param type
     * @param start
     * @param end
     * @return
     */
    public static Annotation getAnnotation(AnnotationSet annoSet, String type, long start, long end) {
        Set<Annotation> resultSet = getAnnotationSet(annoSet, type, start, end);
        if (resultSet == null || resultSet.size() == 0) return null;
        return resultSet.iterator().next();
    }

    /**
     * 返回唯一一个标注
     *
     * @param annoSet
     * @param type
     * @param start
     * @param end
     * @return
     */
    public static Annotation getOnlyAnnotation(AnnotationSet annoSet, String type, long start, long end) throws Exception {
        Set<Annotation> resultSet = getAnnotationSet(annoSet, type, start, end);

        if (resultSet == null || resultSet.size() == 0) return null;

        if (resultSet.size() == 1) {
            return resultSet.iterator().next();
        } else {
            throw new Exception("resultSet.size(" + resultSet.size() + ")>1");
        }
    }

    /**
     * 按类型开始位置、结束位置返回标注集合
     *
     * @param annoSet
     * @param type
     * @param start
     * @param end
     * @return
     */
    public static Set<Annotation> getAnnotationSet(AnnotationSet annoSet, String type, long start, long end) {
        if (annoSet == null) return null;

        AnnotationSet tempSet = annoSet.get(type, start, end);
        Set<Annotation> resultSet = new HashSet<Annotation>();

        for (Annotation anno : tempSet) {
            if (anno.getStartNode().getOffset() == start
                    && anno.getEndNode().getOffset() == end) {
                resultSet.add(anno);
            }
        }

        return resultSet;
    }

    /**
     * 按类型开始位置、结束位置返回标注集合
     *
     * @param annoSet
     * @param start
     * @param end
     * @return
     */
    public static Set<Annotation> getAnnotationSet(AnnotationSet annoSet, long start, long end) {
        if (annoSet == null) return null;

        AnnotationSet tempSet = annoSet.get(start, end);
        Set<Annotation> resultSet = new HashSet<Annotation>();

        for (Annotation anno : tempSet) {
            if (anno.getStartNode().getOffset() == start
                    && anno.getEndNode().getOffset() == end) {
                resultSet.add(anno);
            }
        }

        return resultSet;
    }


    public static Document newDocument(String content) throws ResourceInstantiationException {
        FeatureMap params = Factory.newFeatureMap();
        params.put(Document.DOCUMENT_STRING_CONTENT_PARAMETER_NAME, content);
        params.put(Document.DOCUMENT_PRESERVE_CONTENT_PARAMETER_NAME, true);
        params.put(Document.DOCUMENT_MARKUP_AWARE_PARAMETER_NAME, false);
        Document doc = (Document) Factory.createResource("gate.corpora.DocumentImpl", params);
        doc.setSourceUrl(null);
        return doc;
    }


    /**
     * 创建包含一个文档的文集，这个文档包含参数指定的内容
     *
     * @param content
     * @return
     * @throws gate.creole.ResourceInstantiationException
     */
    public static Corpus newCorpus(String content) throws ResourceInstantiationException {
        final Corpus corpus = Factory.newCorpus("");
        corpus.add(newDocument(content));
        return corpus;
    }

}