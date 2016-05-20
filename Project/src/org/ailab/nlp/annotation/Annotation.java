package org.ailab.nlp.annotation;

import gate.FeatureMap;
import gate.util.InvalidOffsetException;
import gate.util.SimpleFeatureMapImpl;

import java.util.HashMap;

/**
 * User: Lu Tingming
 * Date: 2011-8-17
 * Time: 20:16:03
 * Desc: 标注
 */
public class Annotation implements IAnnotation {
    // 标注类型：token/person/location/lookup/...
    public String type;

    // 开始位置
    public int start;

    // 结束位置
    public int end;

    // 字符串
    public String string;

    // 句子序号
    public int sentenceSeq;

    // 属性表
    public HashMap<String, Object> featureMap;

    // 正确性
    public boolean correct;


    /**
     * 构造函数
     */
    public Annotation() {
    }


    /**
     * 构造函数
     */
    public Annotation(String type, int start, int end, HashMap<String, Object> featureMap) {
        this.type = type;
        this.start = start;
        this.end = end;
        this.featureMap = featureMap;
    }


    /**
     * 构造函数
     */
    public Annotation(String type, int start, int end) {
        this.type = type;
        this.start = start;
        this.end = end;
    }


    /**
     * 构造函数
     */
    public Annotation(String type, int start, int end, String string) {
        this.type = type;
        this.start = start;
        this.end = end;
        this.string = string;
    }


    /**
     * 构造函数
     */
    public Annotation(int start, int end) {
        this.start = start;
        this.end = end;
    }


    /**
     * 构造函数
     */
    public Annotation(int start, int end, HashMap<String, Object> featureMap) {
        this.start = start;
        this.end = end;
        this.featureMap = featureMap;
    }


    /**
     * 构造函数
     */
    public Annotation(int start, int end, String string, HashMap<String, Object> featureMap) {
        this.start = start;
        this.end = end;
        this.string = string;
        this.featureMap = featureMap;
    }


    /**
     * 构造函数
     */
    public Annotation(int start, int end, String type, String string, HashMap<String, Object> featureMap) {
        this.start = start;
        this.end = end;
        this.type = type;
        this.string = string;
        this.featureMap = featureMap;
    }


    /**
     * 构造函数
     */
    public Annotation(int start, int end, String string) {
        this.start = start;
        this.end = end;
        this.string = string;
    }


    public static Annotation createWithType(int start, int end, String type){
        Annotation anno = new Annotation(start, end);
        anno.type = type;

        return anno;
    }


    public void addFeature(String key, Object value) {
        if (featureMap == null) {
            featureMap = new HashMap<String, Object>();
        }
        featureMap.put(key, value);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        if (string != null) {
            sb.append(string).append(", ");
        }

        if (type != null) {
            sb.append("type=").append(type).append(", ");
        }

        sb.append("start=").append(start).append(", end=").append(end);

        if (featureMap != null) {
            sb.append(", ").append(featureMap);
        }

        return sb.toString();
    }


    public String getKey(){
        return new StringBuilder()
                .append(type).append(' ')
                .append(start).append(' ')
                .append(end)
                .toString();
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getText() {
        return string;
    }

    public void setText(String string) {
        this.string = string;
    }

    public HashMap<String, Object> getFeatureMap() {
        return featureMap;
    }

    public void setFeatureMap(HashMap<String, Object> featureMap) {
        this.featureMap = featureMap;
    }

    public void setFeature(String key, Object value) {
        if(featureMap == null){
            featureMap = new HashMap<String, Object>();
        }

        featureMap.put(key, value);
    }

    public Object getFeature(String key) {
        if(featureMap == null){
            return null;
        } else {
            return featureMap.get(key);
        }

    }

    public boolean getCorrect(){
        return correct;
    }

    public void setCorrect(boolean correct){
        this.correct = correct;
    }


    /**
     * 将GATE的标注设置到当前标注中
     * @param ga
     */
    public void setGateAnnotation(gate.Annotation ga){
        type = ga.getType();
        start = ga.getStartNode().getOffset().intValue();
        end = ga.getEndNode().getOffset().intValue();

        if(ga.getFeatures()!=null && !ga.getFeatures().isEmpty()) {
            this.featureMap = new HashMap<String, Object>(ga.getFeatures().size());
            for(Object key : ga.getFeatures().keySet()) {
                this.featureMap.put((String) key, ga.getFeatures().get(key));
            }
        }
    }

    public void addToGateAnnotationSet(gate.AnnotationSet annoSet) throws InvalidOffsetException {
        FeatureMap fm = new SimpleFeatureMapImpl();
        if (featureMap != null) {
            fm.putAll(featureMap);
        }
        annoSet.add((long) start, (long) end, type, fm);
    }

    public int getSentenceSeq() {
        return sentenceSeq;
    }

    public void setSentenceSeq(int sentenceSeq) {
        this.sentenceSeq = sentenceSeq;
    }
}
