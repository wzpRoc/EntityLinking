package org.ailab.nlp.match;

import org.ailab.nlp.LanguageConstants;
import org.ailab.nlp.annotation.*;
import org.ailab.nlp.parsing.SentenceAnnotation;
import org.ailab.nlp.parsing.TokenAnnotation;
import org.ailab.nlp.parsing.chinese.ChineseUtil;
import org.ailab.nlp.parsing.chinese.JapaneseUtil;
import org.ailab.nlp.parsing.english.EnglishUtil;
import org.ailab.nlp.parsing.english.RussianUtil;
import org.ailab.wimfra.database.DBUtil;
import org.ailab.wimfra.util.FileUtil;
import org.apache.log4j.Logger;

import org.ailab.wimfra.util.StringUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.*;

/**
 * User: Lu Tingming
 * Date: 2011-8-24
 * Time: 19:28:36
 * Desc: 正向最大匹配算法
 */
public class ForwardMaxMatcher {
    // 日志工具
    private Logger logger = Logger.getLogger(this.getClass());

    // 中文中，英文单词是否整词匹配
    protected static boolean wholeEnglishWordOnlyInChinese = true;
    // 日文中，英文单词是否整词匹配
    protected static boolean wholeEnglishWordOnlyInJapanese = true;

    // 最大条目长度
    protected int maxEntryLength;
    protected int minEntryLengthAllowed = 1;
    protected HashMap<String, EntryTypeContainer> entryMap;
    protected boolean toUseFeatureMap = true;

    // 是否在同一位置使用多个标注
    // true: featureMap中放置majorType, minorType, 当同一位置（文本）被匹配多次时，使用多个标注，
    // false: featureMap中放置EntryTypeContainner，当同一位置（文本）被匹配多次时，使用一个标注，但是EntryTypeContainner中含有多个entryType 
    protected boolean usingEntryTypeContainer = false;
    protected boolean removeUnwantedAnno = false;
    protected Set<String> unsuggestedEntityNames;


    /**
     */
    public ForwardMaxMatcher() {
        entryMap = new HashMap<String, EntryTypeContainer>();
    }


    /**
     * 从文件载入词典
     */
    public void loadDirectoriesFromFiles(String path_directoryList, String chaset) throws Exception {
        File f = new File(path_directoryList);
        String absolutePath = f.getAbsolutePath();
        if (!f.exists()) {
            absolutePath = System.getProperty("gate.home") + "/" + path_directoryList;
            f = new File(absolutePath);
            if (!f.exists()) {
                throw new Exception("Can not find list file from:\n"
                        + "\t" + path_directoryList
                        + "\nAND\t" + absolutePath
                );
            }
        }
        // System.out.println(absolutePath);

        List<String> dirDefinitionList = FileUtil.readLines(absolutePath);
        // 目录所在位置
        String dir_dirctorys = FileUtil.getDir(absolutePath);

        maxEntryLength = 0;

        for (String dirDefinition : dirDefinitionList) {
            // airports.lst:location:airport
            String[] as = dirDefinition.split(":");
            String directoryName = as[0];
            String path_directory = dir_dirctorys + directoryName;
            String majorType = as[1];
            String minorType = as.length < 3 ? null : as[2];
            logger.debug(path_directory + "\t" + majorType + "\t" + minorType);
            loadDirectoryFromFile(path_directory, new EntryType(majorType, minorType), chaset);
        }
    }


    /**
     * 载入词典
     */
    protected void loadDirectoryFromTable(String tableName, String nameFiledName, EntryType entryType) throws UnsupportedEncodingException, SQLException {
        loadDirectoryFromTable(tableName, nameFiledName, null, entryType);
    }


    /**
     * 载入词典
     */
    protected void loadDirectoryFromTable(String tableName, String nameFiledName, String condition, EntryType entryType) throws UnsupportedEncodingException, SQLException {
        String sql = "SELECT " + nameFiledName + " FROM " + tableName;
        if(StringUtil.notEmpty(condition)) {
            sql += " " + condition;
        }
        List<String> entryNameList = DBUtil.getStringList(sql);
        constructDirectory(entryNameList, entryType);
    }


    /**
     * 载入词典
     */
    protected void loadDirectoryFromFile(String path_directory, EntryType entryType, String chaset) throws UnsupportedEncodingException {
        // 读入条目列表
        ArrayList<String> entryNameList = FileUtil.readLines(path_directory, chaset);
        constructDirectory(entryNameList, entryType);
    }


    /**
     * 构造词典
     */
    protected void constructDirectory(List<String> entryNameList, EntryType entryType) {
        for (String entryName : entryNameList) {
            addEntryToDirectory(entryName, entryType);
        }
    }


    /**
     * 加入到词典
     */
    public void addEntryToDirectory(String majorType, String entryName) {
        addEntryToDirectory(entryName, new EntryType(majorType, null));
    }


    /**
     * 加入到词典
     */
    public void addEntryToDirectory(String entryName, EntryTypeContainer entryTypeContainer) {
        checkAndUpdateMaxEntryLength(entryName);
        entryMap.put(entryName, entryTypeContainer);
    }


    /**
     * 加入到词典
     */
    public void addEntryToDirectory(String entryName, EntryType entryType) {
        if (Constants.NAME_TYPE_UNWANTED_STRING.equals(entryType.majorType)) {
            // ok
        } else {
            if (!needToToBeAdd(entryName)) {
                return;
            }
        }

        // 得到这个entry的类型
        EntryTypeContainer etc = entryMap.get(entryName);
        // 加入map
        if (etc == null) {
            // 之前没有这个条目
            addEntryToDirectory(entryName, new EntryTypeContainer(entryType));
        } else {
            // 找到这个条目
            etc.add(entryType);
        }
    }

    private void checkAndUpdateMaxEntryLength(String entryName) {
        int len = entryName.length();
        if (len > maxEntryLength) {
            maxEntryLength = len;
        }
    }


    /**
     * 返回一个名字是否需要加入条目表
     */
    protected boolean needToToBeAdd(String entryName) {
        // 不管是什么类型的实体，不管什么语种，一个字符的，都不要
        return entryName != null
                && entryName.length() >= minEntryLengthAllowed
                && (unsuggestedEntityNames == null || !unsuggestedEntityNames.contains(entryName));
    }


    /**
     * 删除
     */
    public void deleteEntry(String majorType, String entryName) {
        deleteEntry(entryName, new EntryType(majorType, null));
    }


    /**
     * 删除
     */
    public void deleteEntry(String entryName, EntryType entryType) {
        final EntryTypeContainer entryTypeContainer = entryMap.get(entryName);
        if (entryTypeContainer == null) {
            // do nothing
        } else {
            if (entryTypeContainer.nEntryType == 0) {
                // do nothing
            } else if (entryTypeContainer.nEntryType == 1) {
                // 只有一个
                if (entryType.equals(entryTypeContainer.entryType)) {
                    // 类型匹配
                    entryMap.remove(entryName);
                } else {
                    // 类型不匹配
                    // 不管
                }
            } else {
                // 多个类型
                // 循环
                for (EntryType entryTypeInSet : entryTypeContainer.entryTypeSet) {
                    if (entryTypeInSet.equals(entryType)) {
                        // 类型匹配
                        entryMap.remove(entryName);
                        break;
                    }
                }
            }
        }
    }


    /**
     * 正向最大匹配
     */
    public List<IAnnotation> forwardMaxMatch(int languageId, String text) throws Exception {
        if (languageId == LanguageConstants.IDX_CHINESE || languageId == LanguageConstants.IDX_TRADITIONAL_CHINESE) {
            return forwardMaxMatchForChinese(text, null);
        } else if (languageId == LanguageConstants.IDX_ENGLISH) {
            return forwardMaxMatchForEnglish(text, true, null);
        } else if (languageId == LanguageConstants.IDX_JAPANESE) {
            return forwardMaxMatchForJapanese(text, null);
        } else if (languageId == LanguageConstants.IDX_RUSSIAN) {
            return forwardMaxMatchForRussian(text, true, null);
        } else {
            throw new Exception("Unsupported language: id=" + languageId);
        }
    }


    /**
     * 正向最大匹配
     *
     * @param languageId   语种
     * @param text         文本
     * @param sentenceList 可以为空
     */
    public AnnotationList forwardMaxMatch(int languageId, String text, List<SentenceAnnotation> sentenceList) throws Exception {
        if (languageId == LanguageConstants.IDX_CHINESE || languageId == LanguageConstants.IDX_TRADITIONAL_CHINESE) {
            return forwardMaxMatchForChinese(text, sentenceList);
        } else if (languageId == LanguageConstants.IDX_ENGLISH) {
            return forwardMaxMatchForEnglish(text, true, sentenceList);
        } else if (languageId == LanguageConstants.IDX_JAPANESE) {
            return forwardMaxMatchForJapanese(text, sentenceList);
        } else if (languageId == LanguageConstants.IDX_RUSSIAN) {
            return forwardMaxMatchForRussian(text, true, sentenceList);
        } else {
            throw new Exception("Unsupported language: id=" + languageId);
        }
    }


    /**
     * 正向最大匹配
     *
     * @param text
     */
    public AnnotationList forwardMaxMatchForEnglish(String text, boolean wholeWordsOnly, List<SentenceAnnotation> sentenceList) throws Exception {
        // 解析
        if (sentenceList == null) {
            sentenceList = EnglishUtil.parseSenToken(text);
        }

        Set<Integer> tokenBoundarySet;
        if (wholeWordsOnly) {
            // 整词匹配，需要给定分割点
            // 下面开始构造所有分割点的集合
            tokenBoundarySet = new HashSet<Integer>();
            if (sentenceList != null) {
                for (Annotation sentenceAnno : sentenceList) {
                    for (Annotation tokenAnno : ((SentenceAnnotation) sentenceAnno).tokenAnnotationList) {
                        tokenBoundarySet.add(tokenAnno.getStart());
                        tokenBoundarySet.add(tokenAnno.getEnd());
                    }
                }
            }
        } else {
            tokenBoundarySet = null;
        }

        return forwardMaxMatch(text, sentenceList, wholeWordsOnly, tokenBoundarySet);
    }


    /**
     * 正向最大匹配
     *
     * @param text
     */
    public AnnotationList forwardMaxMatchForRussian(String text, boolean wholeWordsOnly, List<SentenceAnnotation> sentenceList) throws Exception {
        // 解析
        if (sentenceList == null) {
            sentenceList = RussianUtil.parseSenToken(text);
        }

        Set<Integer> tokenBoundarySet;
        if (wholeWordsOnly) {
            // 整词匹配，需要给定分割点
            // 下面开始构造所有分割点的集合
            tokenBoundarySet = new HashSet<Integer>();
            if (sentenceList != null) {
                for (Annotation sentenceAnno : sentenceList) {
                    for (TokenAnnotation tokenAnno : ((SentenceAnnotation) sentenceAnno).tokenAnnotationList) {
                        tokenBoundarySet.add(tokenAnno.getStart());
                        tokenBoundarySet.add(tokenAnno.getEnd());
                    }
                }
            }
        } else {
            tokenBoundarySet = null;
        }

        return forwardMaxMatch(text, sentenceList, wholeWordsOnly, tokenBoundarySet);
    }


    public AnnotationList forwardMaxMatchForChinese(String text, List<SentenceAnnotation> sentenceList) {
        // 不需要整词匹配
        if (sentenceList == null) {
            sentenceList = splitShortSentencesForChinese(text);
        }
        final AnnotationList list = forwardMaxMatch(text, sentenceList, false, null);

        if (wholeEnglishWordOnlyInChinese) {
            // 检查其中的英文单词是否是整词，如果不是整词，那么去除之
            // 定义新的列表
            AnnotationList listFiltered = new AnnotationList(list.size());

            for (IAnnotation annotation : list) {
                if (isPartialEnglishChunk(text, annotation)) {
                    // reject it
                } else {
                    listFiltered.add(annotation);
                }
            }

            return listFiltered;
        } else {
            return list;
        }
    }


    /**
     * 正向最大匹配
     *
     * @param text
     */
    public AnnotationList forwardMaxMatchForJapanese(String text, List<SentenceAnnotation> sentenceList) {
        if (sentenceList == null) {
            sentenceList = splitShortSentencesForJapanese(text);
        }
        // 不需要整词匹配
        final AnnotationList list = forwardMaxMatch(text, sentenceList, false, null);

        if (wholeEnglishWordOnlyInJapanese) {
            // 检查其中的英文单词是否是整词，如果不是整词，那么去除之
            // 定义新的列表
            AnnotationList listFiltered = new AnnotationList(list.size());

            for (IAnnotation annotation : list) {
                if (isPartialEnglishChunk(text, annotation)) {
                    // reject it
                } else {
                    listFiltered.add(annotation);
                }
            }

            return listFiltered;
        } else {
            return list;
        }
    }


    /**
     * 判断指定的token标注是否是一个英文块的一部分
     *
     * @param text
     * @param tokenAnno
     * @return
     */
    public boolean isPartialEnglishChunk(String text, IAnnotation tokenAnno) {
        int len = tokenAnno.getEnd() - tokenAnno.getStart();

        if (len == 0) {
            return false;
        }

        // 检查首字母是否是英文
        char firstChar = text.charAt(tokenAnno.getStart());
        if (EnglishUtil.isEnglishLetter(firstChar)) {
            // 首字母是英文
            // 检查前面是否有字符
            if (tokenAnno.getStart() >= 1) {
                // 检查前一个字母是否是英文
                char previousChar = text.charAt(tokenAnno.getStart() - 1);
                if (EnglishUtil.isEnglishLetter(previousChar)) {
                    // 前一个也是英文字母，那么应该结合
                    return true;
                }
            }
        }

        // 检查末字母是否是英文
        char lastChar = text.charAt(tokenAnno.getEnd() - 1);
        if (EnglishUtil.isEnglishLetter(lastChar)) {
            // 末字母是英文
            // 检查后面是否有字符
            if (tokenAnno.getEnd() <= text.length() - 2) {
                // 检查后一个字母是否是英文
                char nextChar = text.charAt(tokenAnno.getEnd());
                if (EnglishUtil.isEnglishLetter(nextChar)) {
                    // 后一个也是英文字母，那么应该结合
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * 正向最大匹配
     */
    public AnnotationList forwardMaxMatch(String text, List<SentenceAnnotation> shortSenList, boolean wholeWordsOnly, Set<Integer> wordBoundarySet) {
        AnnotationList lookupList = new AnnotationList();

        if (text == null || "".equals(text)) {
            return lookupList;
        }

        // 对每一句，做正向最大匹配
        for (Annotation shortSenAnno : shortSenList) {
            int endOfShortSen = shortSenAnno.end;
            for (int start = shortSenAnno.start; start < endOfShortSen; ) {
                if (wholeWordsOnly && start > 0 && wordBoundarySet != null && !wordBoundarySet.contains(start)) {
                    // 既不是开头，也不是词分隔位置，那么是词中间，不允许匹配
                    start++;
                    continue;
                }
                // 可能的最大长度，取短句长度和最大词典条目长度的较小值
                int maxLenOfPossible = Math.min(shortSenAnno.end - start, maxEntryLength);
                int nextStart = -1;
                // 从大到小寻找
                for (int lenOfPiece = maxLenOfPossible; lenOfPiece > 0; lenOfPiece--) {
                    int end = start + lenOfPiece;
                    if (wholeWordsOnly && end < endOfShortSen && wordBoundarySet != null && !wordBoundarySet.contains(end)) {
                        // 既不是结束，也不是词分隔位置，那么是词中间，不允许匹配
                        continue;
                    }
                    String piece = text.substring(start, end);
                    // 如果在不提示列表，那么跳过
                    EntryTypeContainer etc = entryMap.get(piece);
                    if (unsuggestedEntityNames != null && unsuggestedEntityNames.contains(piece)
                            && (etc == null || (etc!=null && !etc.contains(Constants.NAME_TYPE_UNWANTED_STRING)))) {
                        // do nothing
                    } else {
                        if (etc != null) {
                            if (usingEntryTypeContainer) {
                                // 使用EntryTypeContainer
                                HashMap<String, Object> featureMap = new HashMap<String, Object>(1);
                                featureMap.put("entryTypeContainer", etc);
                                // 主类型是啥？——第一个匹配到的类型
                                lookupList.add(new Annotation(start, start + lenOfPiece, etc.getMajorType(), piece, featureMap));
                            } else {
                                if (etc.nEntryType == 1) {
                                    // 只有一个类型
                                    final HashMap<String, Object> featureMap = etc.entryType.toFeatureMap();
                                    lookupList.add(new Annotation(start, start + lenOfPiece, etc.getMajorType(), piece, featureMap));
                                } else {
                                    // 多个类型
                                    for (EntryType entryType : etc.entryTypeSet) {
                                        final HashMap<String, Object> featureMap = entryType.toFeatureMap();
                                        lookupList.add(new Annotation(start, start + lenOfPiece, entryType.majorType, piece, featureMap));
                                    }
                                }
                            }
                            // 已经匹配上，不再匹配更小长度的字符串
                            nextStart = end;
                            break;
                        }
                    }
                }
                if (nextStart == -1) {
                    // 没有匹配上，后移一位
                    start++;
                } else {
                    // 匹配上了，以后从匹配得到的词的结束位置开始搜索
                    start = nextStart;
                }
            }
        }

        if (removeUnwantedAnno) {
            removeUnwantedAnno(lookupList);
        }
        return lookupList;
    }


    public void removeUnwantedAnno(AnnotationList lookupList) {
        if (lookupList == null || lookupList.size() == 0) {
            return;
        }

        List<Integer> unwantedIdxList = new ArrayList<Integer>();
        for (int i = 0; i < lookupList.size(); i++) {
            IAnnotation anno = lookupList.get(i);
            if (Constants.NAME_TYPE_UNWANTED_STRING.equals(anno.getFeature("majorType"))) {
                unwantedIdxList.add(i);
            }
        }

        if (unwantedIdxList.size() > 0) {
            for (int i = unwantedIdxList.size() - 1; i >= 0; i--) {
                lookupList.remove(unwantedIdxList.get(i).intValue());
            }
        }
    }

    /**
     * 将文本分为短句
     */
    public List<SentenceAnnotation> splitShortSentencesForChinese(String text) {
        List<SentenceAnnotation> annoList = new ArrayList<SentenceAnnotation>();

        if (text == null || "".equals(text)) return annoList;

        List<SentenceAnnotation> longSenList = ChineseUtil.splitSentence(text);

        List<SentenceAnnotation> shortSenList = new ArrayList<SentenceAnnotation>();

        for (Annotation longSenAnno : longSenList) {
            int start = longSenAnno.start;
            final int endOfLongSen = longSenAnno.end;
            int i = start;
            while (true) {
                if (i == endOfLongSen) {
                    // 长句结束，退出
                    @SuppressWarnings({"UnnecessaryLocalVariable"})
                    int end = i;
                    if (start < end) {
                        shortSenList.add(new SentenceAnnotation(start, end));
                    }
                    break;
                }
                if (ChineseUtil.isPunctuationOfWordBoundary(text.charAt(i))) {
                    // 短句结束
                    // 结束位置不包括当前字符
                    @SuppressWarnings({"UnnecessaryLocalVariable"})
                    int end = i;
                    if (start < end) {
                        shortSenList.add(new SentenceAnnotation(start, end));
                    }
                    start = i + 1;
                } else {
                    // 短句未结束
                }
                i++;
            }
        }

        return shortSenList;
    }


    /**
     * 将文本分为短句
     */
    public List<SentenceAnnotation> splitShortSentencesForJapanese(String text) {
        List<SentenceAnnotation> annoList = new ArrayList<SentenceAnnotation>();

        if (text == null || "".equals(text)) return annoList;

        List<SentenceAnnotation> longSenList = JapaneseUtil.splitSentence(text);

        List<SentenceAnnotation> shortSenList = new ArrayList<SentenceAnnotation>();

        for (Annotation longSenAnno : longSenList) {
            int start = longSenAnno.start;
            final int endOfLongSen = longSenAnno.end;
            int i = start;
            while (true) {
                if (i == endOfLongSen) {
                    // 长句结束，退出
                    @SuppressWarnings({"UnnecessaryLocalVariable"})
                    int end = i;
                    if (start < end) {
                        shortSenList.add(new SentenceAnnotation(start, end));
                    }
                    break;
                }
                if (JapaneseUtil.isPunctuationOfWordBoundary(text.charAt(i))) {
                    // 短句结束
                    // 结束位置不包括当前字符
                    @SuppressWarnings({"UnnecessaryLocalVariable"})
                    int end = i;
                    if (start < end) {
                        shortSenList.add(new SentenceAnnotation(start, end));
                    }
                    start = i + 1;
                } else {
                    // 短句未结束
                }
                i++;
            }
        }

        return shortSenList;
    }


    public boolean isUsingEntryTypeContainer() {
        return usingEntryTypeContainer;
    }

    public void setUsingEntryTypeContainer(boolean usingEntryTypeContainer) {
        this.usingEntryTypeContainer = usingEntryTypeContainer;
    }


    public void onUnsuggestedEntityNameAdded(String unsuggestedEntityName) {
        this.entryMap.remove(unsuggestedEntityName);
    }

    public boolean isToUseFeatureMap() {
        return toUseFeatureMap;
    }

    public void setToUseFeatureMap(boolean toUseFeatureMap) {
        this.toUseFeatureMap = toUseFeatureMap;
    }

    public EntryTypeContainer getEntryTypeContainer(String entry) {
        return entryMap.get(entry);
    }
}
