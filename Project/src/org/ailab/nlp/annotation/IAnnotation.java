package org.ailab.nlp.annotation;

import java.util.HashMap;

/**
 * User: Lu Tingming
 * Date: 2011-8-17
 * Time: 20:16:03
 * Desc: 标注
 */
public interface IAnnotation {
    public String getType();

    public void setType(String type);

    public int getStart();

    public void setStart(int start);

    public int getEnd();

    public void setEnd(int end);

    public String getText();

    public void setText(String string);

    public boolean getCorrect();

    public void setCorrect(boolean correct);

    public HashMap<String, Object> getFeatureMap();

    public void setFeatureMap(HashMap<String, Object> featureMap);

    public void setFeature(String key, Object value);

    public Object getFeature(String key);
}