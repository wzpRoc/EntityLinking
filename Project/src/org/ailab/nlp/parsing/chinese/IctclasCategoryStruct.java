package org.ailab.nlp.parsing.chinese;

/**
 * User: Lu Tingming
 * Date: 2011-8-16
 * Time: 12:21:23
 * Desc: ICTCLAS tag的结构体
 */
public class IctclasCategoryStruct {
    public int id;
    public String category;
    public int categoryLevel;
    public String category1;
    public String category2;
    public String category3;
    public String comment;

    public IctclasCategoryStruct(int id, int categoryLevel, String category, String category1, String category2, String category3, String comment) {
        this.id = id;
        this.category = category;
        this.categoryLevel = categoryLevel;
        this.category1 = category1;
        this.category2 = category2;
        this.category3 = category3;
        this.comment = comment;
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("category=").append(category);
        sb.append(", categoryLevel=").append(categoryLevel);
        sb.append(", category1=").append(category1);
        sb.append(", category2=").append(category2);
        sb.append(", category3=").append(category3);
        sb.append(", comment=").append(comment);

        return sb.toString();
    }
}