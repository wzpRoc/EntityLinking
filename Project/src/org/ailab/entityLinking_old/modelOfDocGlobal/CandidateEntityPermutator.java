package org.ailab.entityLinking_old.modelOfDocGlobal;

import org.ailab.entityLinking_old.cache.EntityCache;
import org.ailab.entityLinking_old.candidateEntityGenerate.CandidateEntityGenerator;
import org.ailab.entityLinking_old.model.Label;
import org.ailab.entityLinking_old.wim.docEntity.DocEntity;
import org.ailab.entityLinking_old.wim.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Lu Tingming
 * Date: 15-5-30
 * Time: 上午2:57
 * Desc:
 */
public class CandidateEntityPermutator {
    private List<DocEntity> mentionList;
    private CandidateEntityGenerator candidateEntityGenerator;
    private Map<String, List<Entity>> candidateEntityListCache;
    private CandidateEntityPermutationList permutationList;
    private Map<Integer, Integer> sameMentionTextConstraintMap;


    public CandidateEntityPermutator(List<DocEntity> mentionList, CandidateEntityGenerator candidateEntityGenerator) {
        this.mentionList = mentionList;
        this.candidateEntityGenerator = candidateEntityGenerator;
    }


    public CandidateEntityPermutationList permutate(boolean computeLabel) {
        // 初始化每个提及的候选实体列表缓存
        candidateEntityListCache = initCandidateEntityListCache();
        // 初始化结果
        permutationList = new CandidateEntityPermutationList();

        CandidateEntityPermutation activePermutation = new CandidateEntityPermutation(mentionList.size());
        // 初始的标记
        Label label;
        if (computeLabel) {
            // 要求计算标记，那么初始为正确
            label = Label.yes;
        } else {
            // 不要求计算标记，那么初始为未知
            label = Label.unknown;
        }

        // 同mention约束
        initSameMentionTextConstraintMap();

        permutate(activePermutation, 0, label);

        return permutationList;
    }

    private void initSameMentionTextConstraintMap() {
        sameMentionTextConstraintMap = null;
        if (mentionList.size() > 1 && mentionList.size() != candidateEntityListCache.size()) {
            // 从后往前查找
            for (int currentMentionIdx = mentionList.size() - 1; currentMentionIdx > 0; currentMentionIdx--) {
                String currentMentionText = mentionList.get(currentMentionIdx).getMention();
                // 从前往后直到当前mention的前一个位置
                for (int firstMentionIdx = 0; firstMentionIdx < currentMentionIdx; firstMentionIdx++) {
                    String firstMentionText = mentionList.get(firstMentionIdx).getMention();
                    if(currentMentionText.equals(firstMentionText)) {
                        if(sameMentionTextConstraintMap == null) {
                            sameMentionTextConstraintMap = new HashMap<Integer, Integer>();
                        }
                        sameMentionTextConstraintMap.put(currentMentionIdx, firstMentionIdx);
                        break;
                    }
                }
            }
        }
    }


    private Map<String, List<Entity>> initCandidateEntityListCache() {
        Map<String, List<Entity>> candidateEntityListCache = new HashMap<String, List<Entity>>();
        for (DocEntity docEntity : mentionList) {
            String mention = docEntity.getMention();
            if (!candidateEntityListCache.containsKey(mention)) {
                List<Entity> candidateEntityList = candidateEntityGenerator.generate(mention);
                candidateEntityListCache.put(mention, candidateEntityList);
            }
        }
        return candidateEntityListCache;
    }


    protected void permutate(CandidateEntityPermutation activePermutation, int mentionIdx, Label lastLabel) {
        DocEntity mention = mentionList.get(mentionIdx);

        List<Entity> candidateEntityList;
        if(sameMentionTextConstraintMap!=null && sameMentionTextConstraintMap.containsKey(mentionIdx)) {
            int firstMentionIdx = sameMentionTextConstraintMap.get(mentionIdx);
            Entity entity = activePermutation.get(firstMentionIdx);
            candidateEntityList = new ArrayList<Entity>();
            candidateEntityList.add(entity);
        } else {
            candidateEntityList = candidateEntityListCache.get(mention.getMention());
        }

        for (int candidateEntityIdx = 0; candidateEntityIdx < candidateEntityList.size(); candidateEntityIdx++) {
            Entity candidateEntity = candidateEntityList.get(candidateEntityIdx);
            activePermutation.add(candidateEntity);

            Label label;
            if (lastLabel == Label.unknown) {
                label = Label.unknown;
            } else if (lastLabel == Label.yes) {
                if (candidateEntity.getId() == mention.getEntityId()) {
                    label = Label.yes;
                } else {
                    label = Label.no;
                }
            } else {
                label = Label.no;
            }

            if (mentionIdx == mentionList.size() - 1) {
                // 已到达最后一个mention
                // create a new permutation
                CandidateEntityPermutation currentPermutation = new CandidateEntityPermutation(activePermutation);
                currentPermutation.label = label;
                permutationList.add(currentPermutation);
            } else {
                // 到下一个mention
                permutate(activePermutation, mentionIdx + 1, label);
            }

            // 删除当前候选实体
            activePermutation.remove(mentionIdx);
        }
    }


    private static void test(String... mentionTexts) {
        System.out.println("----------------------------------------------------------------");
        List<DocEntity> mentionList = new ArrayList<DocEntity>();
        for (String mentionText : mentionTexts) {
            DocEntity mention = new DocEntity();
            mention.setMention(mentionText);
            mention.setEntityId(EntityCache.getIdByTitle(mentionText));
            mentionList.add(mention);
        }

        CandidateEntityPermutator permutator = new CandidateEntityPermutator(mentionList, new CandidateEntityGenerator(null));

        permutator.permutate(true);

        int i = 0;
        for (CandidateEntityPermutation permutation : permutator.permutationList) {
            i++;
            System.out.print(i + "\t" + permutation.label + "\t");
            for (Entity entity : permutation) {
                System.out.print(entity.getTitle() + "\t");
            }
            System.out.println();
        }
    }


    private static void test1() {
        test("南京市");
    }

    private static void test2() {
        test("南京市", "ltm");
    }

    private static void test3() {
        test("南京市", "南京市");
    }

    private static void test4() {
        test("中国", "克里", "中国");
    }


    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
    }
}
