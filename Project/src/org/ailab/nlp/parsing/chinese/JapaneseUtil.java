package org.ailab.nlp.parsing.chinese;

import org.ailab.nlp.annotation.Annotation;
import org.ailab.nlp.parsing.SentenceAnnotation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * User: Lu Tingming
 * Date: 2011-8-24
 * Time: 20:24:03
 * Desc: 中文工具
 */
public class JapaneseUtil {
    private static HashSet<Character> puncSet;
    private static HashSet<Character> puncSetOfWordBoundary;
    public static final HashSet<Character> puncSetOfSentenceEnd;
    static String casheText;
    static ArrayList<Annotation> casheParagraphList;

    static {
        puncSet = new HashSet<Character>();
        puncSet.add('、');
        puncSet.add('。');

        puncSet.add('「');
        puncSet.add('」');
        puncSet.add('（');
        puncSet.add('）');

        puncSet.add('：');
        puncSet.add('—');
        puncSet.add('…');

        puncSet.add('“');
        puncSet.add('”');
        puncSet.add('‘');
        puncSet.add('’');
        puncSet.add('（');
        puncSet.add('）');
        puncSet.add('《');
        puncSet.add('》');

        puncSet.add('．');

        puncSet.add('.');
        puncSet.add(';');
        puncSet.add('?');
        puncSet.add('!');

        puncSet.add(',');
        puncSet.add(':');
        puncSet.add('\'');
//        puncSet.add('(');
//        puncSet.add(')');

        puncSetOfWordBoundary = new HashSet<Character>();
        puncSetOfWordBoundary.add('、');
        puncSetOfWordBoundary.add('。');
        puncSetOfWordBoundary.add('？');
        puncSetOfWordBoundary.add('！');
        puncSetOfWordBoundary.add('；');

//        puncSetOfWordBoundary.add('、');
        puncSetOfWordBoundary.add('，');
        puncSetOfWordBoundary.add('：');
//        puncSetOfWordBoundary.add('—');
        puncSetOfWordBoundary.add('…');

//        puncSetOfWordBoundary.add('“');
//        puncSetOfWordBoundary.add('”');
//        puncSetOfWordBoundary.add('‘');
//        puncSetOfWordBoundary.add('’');
//        puncSetOfWordBoundary.add('（');
//        puncSetOfWordBoundary.add('）');
//        puncSetOfWordBoundary.add('《');
//        puncSetOfWordBoundary.add('》');

        puncSetOfWordBoundary.add('．');

//        puncSetOfWordBoundary.add('.');
        puncSetOfWordBoundary.add(';');
        puncSetOfWordBoundary.add('?');
        puncSetOfWordBoundary.add('!');

        puncSetOfWordBoundary.add(',');
        puncSetOfWordBoundary.add(':');
//        puncSetOfWordBoundary.add('\'');
//        puncSetOfWordBoundary.add('(');
//        puncSetOfWordBoundary.add(')');

        puncSetOfSentenceEnd = new HashSet<Character>();
        puncSetOfSentenceEnd.add('。');
        puncSetOfSentenceEnd.add('；');
        puncSetOfSentenceEnd.add('？');
        puncSetOfSentenceEnd.add('！');
        puncSetOfSentenceEnd.add('.');
        puncSetOfSentenceEnd.add(';');
        puncSetOfSentenceEnd.add('?');
        puncSetOfSentenceEnd.add('!');
        puncSetOfSentenceEnd.add('．');
    }


    public static boolean isPunctuation(char c) {
        return puncSet.contains(c);
    }

    public static boolean isPunctuationOfWordBoundary(char c) {
        return puncSetOfWordBoundary.contains(c);
    }

    /**
     * 判断是否为段落结束
     *
     * @param c
     * @return
     */
    static boolean isEndOfParagraph(char c) {
        return c == '\n' || c == '\r';
    }

    /**
     * 判断是否为句子结束
     *
     * @param c
     * @return
     */
    static boolean isEndMarkOfSentence(char c) {
        return c == '。'
                || c == '；'
                || c == '？'
                || c == '！'
                || c == '.'
                || c == ';'
                || c == '?'
                || c == '!'
                || c == '．';
    }

    /**
     * 判断是否为空白
     *
     * @param c
     * @return
     */
    static boolean isBlank(char c) {
        return c == ' '
                || c == '　'
                || c == '\t';
    }

    /**
     * 分段
     *
     * @param text
     * @return
     */
    public static ArrayList<Annotation> splitParagraph(String text) {
        if (text == null || "".equals(text)) return null;

        ArrayList<Annotation> annoList;

        // 由于分句前需要先分段，所以用户调用分句、分段过程中，重复调用了分段
        // 所以使用缓存
        if (text.equals(casheText)) {
            // 使用缓存
            return casheParagraphList;
        } else {
            // 旧缓存无效
            annoList = new ArrayList<Annotation>();
            // 设置新缓存
            casheText = text;
            casheParagraphList = annoList;
        }

        // 文本总长度
        final int totalLength = text.length();

        Annotation anno;
        int start = 0;
        int current = -1;

        // 段落是否已经开始
        boolean paragraphStarted = false;

        while (true) {
            // 往后移动
            current++;

            // 是否全部结束
            boolean finished = current >= totalLength;

            if (finished || isEndOfParagraph(text.charAt(current))) {
                // 全部结束，或者遇到段落结束符
                // 判断新的段落长度
                int length = current - start;
                if (length > 0) {
                    // 长度>0
                    anno = new Annotation(start, start+length);
                    annoList.add(anno);
                } else {
                    // 长度=0，因为前一个字符也是换行符
                }

                if (finished) {
                    // 全部结束
                    break;
                }

                // 新的段落只可能从下一个位置开始
                start = current + 1;
                // 段落未开始
                paragraphStarted = false;
            } else if (isBlank(text.charAt(current))) {
                // 空白
                if (paragraphStarted) {
                    // 段落已经开始
                    // 不管
                } else {
                    // 段落没有开始
                    // 忽略这些空白
                    start = current + 1;
                }
            } else {
                paragraphStarted = true;
            }
        }

        return annoList;
    }

    /**
     * 分句，输出的结果中包含句子的文本
     *
     * @param text
     * @return
     */
    public static List<SentenceAnnotation> splitSentence(String text) {
        List<SentenceAnnotation> list = splitSentenceWithoutText(text);
        if(list == null) return null;

        for(SentenceAnnotation anno : list){
            anno.setText(text.substring(anno.getStart(), anno.getEnd()));
        }

        return list;
    }


    /**
     * 分句
     *
     * @param text
     * @return
     */
    public static List<SentenceAnnotation> splitSentenceWithoutText(String text) {
        if (text == null || "".equals(text)) return null;

        List<SentenceAnnotation> sentenceList = new ArrayList<SentenceAnnotation>();
        ArrayList<Annotation> paragraphList;

        // 由于分句前需要先分段，所以用户调用分句、分段过程中，重复调用了分段
        // 所以使用缓存
        if (text.equals(casheText)) {
            // 使用缓存
            paragraphList = casheParagraphList;
        } else {
            // 旧缓存无效
            paragraphList = splitParagraph(text);

            // 设置新缓存
            casheText = text;
            casheParagraphList = paragraphList;
        }

        // 在段落内部分句
        for (Annotation paraAnno : paragraphList) {
            // 段落文本
            String paraText = text.substring(paraAnno.start, paraAnno.end);
            // 段落文本总长度
            final int totalLength = paraText.length();

            SentenceAnnotation sentenceAnno;
            int start = 0;
            int current = -1;

            // 句子是否已经开始
            boolean sentenceStarted = false;

            while (true) {
                // 往后移动
                current++;

                // 是否全部结束
                boolean finished = current >= totalLength;
                if (finished) {
                    // 全部结束
                    // 当前位置不算
                    int length = current - start;
                    if (length > 0) {
                        // 长度>0
                        sentenceAnno = new SentenceAnnotation(paraAnno.start+start, paraAnno.start+start+length);
                        sentenceList.add(sentenceAnno);
                    } else {
                        // 长度=0
                    }

                    break;
                }

                final char currentChar = paraText.charAt(current);
                if (isEndMarkOfSentence(currentChar)) {
                    // 遇到句子结束符
                    // 如果下一个字符是右引号（”），那么句子结束的位置向后移一位
                    if(current+1<totalLength){
                        if(paraText.charAt(current+1)=='”'){
                            current++;
                        }
                    }
                    // 当前位置是句子结束的标点符号
                    int length = current - start + 1;
                    if (length > 0) {
                        // 长度>0
                        sentenceAnno = new SentenceAnnotation(paraAnno.start+start, paraAnno.start+start+length);
                        sentenceList.add(sentenceAnno);
                    } else {
                        // 长度=0，因为前一个字符也是句子结束，或者空格
                    }

                    // 新的段落只可能从下一个位置开始
                    start = current + 1;
                    // 句子未开始
                    sentenceStarted = false;
                } else if (isBlank(currentChar)) {
                    // 空白
                    if (sentenceStarted) {
                        // 句子已经开始
                        // 不管
                    } else {
                        // 句子没有开始
                        // 忽略这些空白
                        start = current + 1;
                    }
                } else {
                    sentenceStarted = true;
                }
            }
        }

        return sentenceList;
    }
}