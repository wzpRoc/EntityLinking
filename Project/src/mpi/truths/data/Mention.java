/*    */ package mpi.truths.data;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Mention
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 3177945435296705498L;
/*    */   private String mention;
/*    */   private int startToken;
/*    */   private int endToken;
/* 15 */   private String groundTruthEntity = null;
/*    */   private int charOffset;
/*    */   private int charLength;
/* 20 */   private int id = -1;
/*    */ 
/*    */   public String getMention()
/*    */   {
/* 26 */     return this.mention;
/*    */   }
/*    */ 
/*    */   public int getStartToken() {
/* 30 */     return this.startToken;
/*    */   }
/*    */ 
/*    */   public int getEndToken() {
/* 34 */     return this.endToken;
/*    */   }
/*    */ 
/*    */   public void setStartToken(int start) {
/* 38 */     this.startToken = start;
/*    */   }
/*    */ 
/*    */   public void setEndToken(int end) {
/* 42 */     this.endToken = end;
/*    */   }
/*    */ 
/*    */   public int getCharOffset()
/*    */   {
/* 47 */     return this.charOffset;
/*    */   }
/*    */ 
/*    */   public int getCharLength()
/*    */   {
/* 52 */     return this.charLength;
/*    */   }
/*    */ 
/*    */   public void setCharOffset(int offset) {
/* 56 */     this.charOffset = offset;
/*    */   }
/*    */ 
/*    */   public void setCharLength(int length)
/*    */   {
/* 61 */     this.charLength = length;
/*    */   }
/*    */ 
/*    */   public void setMention(String mention) {
/* 65 */     this.mention = mention;
/*    */   }
/*    */ 
/*    */   public void setGroundTruthResult(String result) {
/* 69 */     this.groundTruthEntity = result;
/*    */   }
/*    */ 
/*    */   public String getGroundTruthResult() {
/* 73 */     return this.groundTruthEntity;
/*    */   }
/*    */ 
/*    */   public int getId() {
/* 77 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(int id) {
/* 81 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 85 */     if (this.groundTruthEntity != null) {
/* 86 */       return this.id + " " + this.mention + "->" + this.groundTruthEntity + "(" + this.startToken + ", " + this.endToken + ")  <" + this.charOffset + "," + this.charLength + "> ";
/*    */     }
/* 88 */     return this.id + " " + this.mention + " (" + this.startToken + ", " + this.endToken + ")  <" + this.charOffset + "," + this.charLength + "> ";
/*    */   }
/*    */ }

/* Location:           D:\Datasets_of_EL\aida-yago2-dataset\aida-yago2-dataset\aida-yago2-dataset.jar
 * Qualified Name:     mpi.truths.data.Mention
 * JD-Core Version:    0.6.2
 */