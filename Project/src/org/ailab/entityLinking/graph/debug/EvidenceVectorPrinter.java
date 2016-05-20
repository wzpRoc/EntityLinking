package org.ailab.entityLinking.graph.debug;

import org.ailab.entityLinking.graph.Node;
import org.ailab.entityLinking.graph.Vector;
import org.ailab.entityLinking.wim.candidateEntity.CandidateEntity;
import org.ailab.entityLinking.wim.mention.Mention;
import org.ailab.wimfra.util.NumberUtil;
import org.ailab.wimfra.util.time.TimeUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 16-1-7
 * Time: 下午8:27
 */
public class EvidenceVectorPrinter {

    public static void print(
            List<Mention> mentionList,
            Map<Integer, Mention> idToMentionMap,
            Set<Integer> mentionCorrectNodeIdxSet,
            Set<Integer> mentionEndNodeIdxSet,
            List<Node> nodeList,
            List<Vector> vectorList) {
        Table table = new Table();

        ///////// A. Table Head
        {
            TR headerRow = new TR();
            table.add(headerRow);
            headerRow.className = "tableHead";
            // 1. 行的头单元格
            headerRow.add(new TD("tableLeftHead", "&nbsp;<br>&nbsp;"));

            ////////////////////////////////////////////// index
            for(int iterationIdx=0; iterationIdx<vectorList.size(); iterationIdx++) {
                String text = String.valueOf(iterationIdx);
                TD td = new TD(text);
                headerRow.add(td);
                if(iterationIdx==0) {
                    // baseline，加条线
                    td.addStyle("border-right: 2px solid #000000");
                }
            }
        }

        /////////
        for(int rowNodeIdx=0; rowNodeIdx<nodeList.size(); rowNodeIdx++) {
            TR tr = new TR();
            table.add(tr);
            if (rowNodeIdx == mentionList.size() - 1) {
                tr.className = "mentionsFinishedBottom";
            } else if(mentionEndNodeIdxSet.contains(rowNodeIdx+1)) {
                tr.className = "mentionCandidatesFinishedBottom";
            }

            ////////////////////////////////////////////// head
            TD rowHead = new TD();
            tr.add(rowHead);
            rowHead.className="tableLeftHead";

            Node node = nodeList.get(rowNodeIdx);
            if(node instanceof Mention) {
                rowHead.text ="m" + rowNodeIdx + " " + ((Mention)node).getName();
            } else {
                CandidateEntity candidate = (CandidateEntity) node;
                rowHead.text ="m"+" e " + candidate.getWikiTitle();
            }
            if(mentionCorrectNodeIdxSet.contains(rowNodeIdx)) {
                rowHead.addStyle("background-color: #98fb98");
            }

            ////////////////////////////////////////////// data
            for(int iterationIdx=0; iterationIdx<vectorList.size(); iterationIdx++) {
                // 获得评分，创建单元格
                Vector evidenceVector = vectorList.get(iterationIdx);
                Double score = evidenceVector.get(rowNodeIdx);
                String text = NumberUtil.format(score, 4, 4);
                TD td = new TD(text);
                tr.add(td);

                // 获得预期实体的评分
                if(node instanceof CandidateEntity) {
                    Mention mention = idToMentionMap.get(((CandidateEntity) node).getMentionId());
                    if(mention.correctNodeIdx>=0) {
                        // 存在正确答案
                        double scoreOfCorrectNode = evidenceVector.get(mention.correctNodeIdx);
                        if(mention.correctNodeIdx == rowNodeIdx) {
                            // 我是预期正确答案
                            // todo: 应该和max比较，暂时没有
                            if(score>=scoreOfCorrectNode) {
                                // 分数最高，绿色背景
                                td.addStyle("background-color: #98fb98");
                            } else {
                                // 计算错误，绿色字体
                                td.addStyle("color: #98fb98");
                            }
                        } else {
                            // 不是预期正确答案
                            if(score>=scoreOfCorrectNode) {
                                // >=比正确答案分数还高
                                // 属于捣蛋行为，显示红色
                                td.addStyle("background-color: #ff8888");
                            }
                        }
                    } else {
                        // 不存在正确答案，显示橙色
                        td.addStyle("color: orange");
                    }
                }

                // 第一列右侧打竖线
                if(iterationIdx==0) {
                    td.addStyle("border-right: 2px solid #000000");
                }
            }
        }

        table.save("d:\\elHTML\\" + TimeUtil.getDate_Time() + "_evidence.htm");
    }
}
