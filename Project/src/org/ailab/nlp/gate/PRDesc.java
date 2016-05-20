package org.ailab.nlp.gate;

import java.util.HashMap;

/**
 * User: Lu Tingming
 * Date: 2011-8-28
 * Time: 12:53:35
 * Desc: Processing Resource 的描述
 */
public class PRDesc {
    // 类型：tokenizer/gazetteer/ner/japeTransducer
    public String type;

    // 参数
    public HashMap<String, Object> params;

    /**
     * 构造函数
     */
    public PRDesc(String type, Object... paramNameAndValues) {
        this.type = type;

        if(paramNameAndValues!=null){
            params = new HashMap<String, Object>();
            int nParam = paramNameAndValues.length/2;
            for(int i=0; i<nParam; i++){
                params.put((String) paramNameAndValues[i], paramNameAndValues[i+1]);
            }
        }
    }
}
