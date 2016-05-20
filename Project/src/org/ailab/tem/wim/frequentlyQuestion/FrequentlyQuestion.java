package org.ailab.tem.wim.frequentlyQuestion;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2014-02-22
 * 功能描述：常见问题
 */
public class FrequentlyQuestion extends BaseDTO {
    // ID
    protected int id;

    // 所属目录Id
    protected int catalogId;

    // 问题
    protected String question;

    // 答案
    protected String answer;

    // 编辑人
    protected String editorName;

    // 编辑时间
    protected String editTime;


    public FrequentlyQuestion() {
    }

    public FrequentlyQuestion(String questionContent, int catalogId) {
        question = questionContent;
        this.catalogId = catalogId;
        answer="抱歉，暂无答案";
    }

    /**
     * 得到ID
     *
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 得到所属目录Id
     *
     * @return 所属目录Id
     */
    public int getCatalogId() {
        return catalogId;
    }

    /**
     * 设置所属目录Id
     *
     * @param catalogId 所属目录Id
     */
    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    /**
     * 得到问题
     *
     * @return 问题
     */
    public String getQuestion() {
        return question;
    }

    /**
     * 设置问题
     *
     * @param question 问题
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * 得到答案
     *
     * @return 答案
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 设置答案
     *
     * @param answer 答案
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * 得到编辑人
     *
     * @return 编辑人
     */
    public String getEditorName() {
        return editorName;
    }

    /**
     * 设置编辑人
     *
     * @param editorName 编辑人
     */
    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    /**
     * 得到编辑时间
     *
     * @return 编辑时间
     */
    public String getEditTime() {
        return editTime;
    }

    /**
     * 设置编辑时间
     *
     * @param editTime 编辑时间
     */
    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

}
