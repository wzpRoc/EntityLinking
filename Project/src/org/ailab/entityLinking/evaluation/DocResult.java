package org.ailab.entityLinking.evaluation;

import org.ailab.wimfra.util.NumberUtil;
import org.ailab.wimfra.util.StringUtil;

/**
 * User: lutingming
 * Date: 16-1-8
 * Time: 上午9:47
 */
public class DocResult {
    public int docId;
    public int mentionTotal;
    public int mentionCorrect;
    public int errorScore;

    public DocResult(int docId) {
        this.docId = docId;
    }

    public void print() {
        print(null);
    }

    public void print(String head) {
        if(head!=null) {
            System.out.print(head);
        }
        System.out.println(
                "docId="+ StringUtil.leftFill(docId, 5, ' ')+", " +
                "mentionTotal="+ StringUtil.leftFill(mentionTotal, 3, ' ')+", " +
                        "mentionCorrect="+StringUtil.leftFill(mentionCorrect, 3, ' ')+", " +
                        "errorScore="+StringUtil.leftFill(errorScore, 3, ' ')+", " +
                        "accuracy="+ NumberUtil.format(getAccuracy(), 4, 4));
    }

    private double getAccuracy() {
        return (mentionCorrect*1.0/mentionTotal);
    }

}
