package org.ailab.nlp.parsing.chinese;

import org.ailab.nlp.annotation.Annotation;

/**
 * User: Lu Tingming
 * Date: 2011-8-16
 * Time: 12:21:23
 * Desc:
 */
public class IctclasAnnotation extends Annotation {
    // public int iPOS; //POS,词性ID
    // public String sPOS;//word type词性
    // public int word_ID; //word_ID,词语ID

    //Is the word of the user's dictionary?(0-no,1-yes)查看词语是否为用户字典中词语
    public int word_type;

    // public int weight;// word weight,词语权重

    // 词性
    public String category;
    public String categoryById;


    /**
     * 构造函数
     * @param start
     * @param length
     * @param word_type
     * @param string
     * @param category
     */
    public IctclasAnnotation(int start, int length, int word_type, String string, String category) {
        this.start = start;
        this.end = start+length;
        this.word_type = word_type;
        this.string = string;
        this.category = category;
    }

    public String toString(){
        String stringToDisplay;

        if(string!=null){
            stringToDisplay = string
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
        } else {
            stringToDisplay = string;
        }

        return "start="+start
                +", end="+end
                +", category="+category
                +", string="+stringToDisplay
                +", word_type="+word_type;
    }

}
