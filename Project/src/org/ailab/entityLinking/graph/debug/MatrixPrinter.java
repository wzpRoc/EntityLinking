package org.ailab.entityLinking.graph.debug;

import org.ailab.entityLinking.graph.Matrix;
import org.ailab.entityLinking.graph.Node;
import org.ailab.entityLinking.wim.candidateEntity.CandidateEntity;
import org.ailab.entityLinking.wim.mention.Mention;
import org.ailab.wimfra.util.NumberUtil;
import org.ailab.wimfra.util.time.TimeUtil;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 16-1-7
 * Time: 下午8:27
 */
public class MatrixPrinter {

    public static void print(
            List<Mention> mentionList,
            Set<Integer> mentionCorrectNodeIdxSet,
            Set<Integer> mentionEndNodeIdxSet,
            List<Node> nodeList,
            Matrix transferMatrix) {
        Table table = new Table();

        ///////// A. Table Head
        {
            TR header = new TR();
            table.add(header);
            header.className = "tableHead";
            // 1. 行的头单元格
            header.add(new TD("tableLeftHead", ""));
            // 2. 行的mention部分
            for (int i = 0; i < mentionList.size(); i++) {
                Mention mention = mentionList.get(i);
                TD td = new TD("m" + i + "<br>" + mention.getName());
                header.add(td);
                if (i == mentionList.size() - 1) {
                    td.className = "mentionsFinishedRight";
                }
            }
            // 3. 行的candidate部分
            for (int i = 0; i < mentionList.size(); i++) {
                Mention mention = mentionList.get(i);
                for (int i_candidate = 0; i_candidate < mention.getCandidateList().size(); i_candidate++) {
                    CandidateEntity candidate = mention.getCandidateList().get(i_candidate);
                    TD td = new TD("m" + i + " e" + i_candidate + "<br>" + candidate.getWikiTitle());
                    if (candidate.getEntityId() == mention.getEntityId()) {
                        td.className = "correctEntity";
                    } else if (i_candidate == mention.getCandidateList().size() - 1) {
                        td.className = "mentionCandidatesFinishedRight";
                    }
                    header.add(td);
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
            for(int colNodeIdx=0; colNodeIdx<nodeList.size(); colNodeIdx++) {
                Double d = transferMatrix.get(rowNodeIdx, colNodeIdx);
                String s = NumberUtil.format(d, 4, 4);
                TD td = new TD(s);
                tr.add(td);
                if (colNodeIdx == mentionList.size() - 1) {
                    td.className = "mentionsFinishedRight";
                } else if(mentionEndNodeIdxSet.contains(colNodeIdx+1)) {
                    td.className = "mentionCandidatesFinishedRight";
                }

                if(mentionCorrectNodeIdxSet.contains(rowNodeIdx) || mentionCorrectNodeIdxSet.contains(colNodeIdx)) {
                    td.addStyle("background-color: #98fb98");
                }
            }
        }

        table.save("d:\\elHTML\\" + TimeUtil.getDate_Time() + "_matrix.htm");
    }
}
