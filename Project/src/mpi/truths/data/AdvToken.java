/*     */ package mpi.truths.data;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class AdvToken
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 585919047497016142L;
/*   9 */   private int id = -1;
/*     */ 
/*  11 */   private String original = null;
/*     */ 
/*  13 */   private String originalEnd = "";
/*     */ 
/*  15 */   private int beginIndex = -1;
/*     */ 
/*  17 */   private int endIndex = -1;
/*     */ 
/*  19 */   private int length = -1;
/*     */ 
/*  21 */   private int sentence = -1;
/*     */ 
/*  23 */   private int paragraph = -1;
/*     */ 
/*  25 */   private String tag = null;
/*     */ 
/*  27 */   private String ptag = null;
/*     */ 
/*  29 */   private String ne = null;
/*     */ 
/*  31 */   private int standfordTokenId = -1;
/*     */ 
/*     */   public AdvToken(int id, String original, int beginIndex, int endIndex, int paragraph, String tag) {
/*  34 */     this.id = id;
/*  35 */     this.original = original;
/*  36 */     this.beginIndex = beginIndex;
/*  37 */     this.endIndex = endIndex;
/*  38 */     this.paragraph = paragraph;
/*  39 */     this.length = (this.endIndex - this.beginIndex);
/*  40 */     this.tag = tag;
/*     */   }
/*     */ 
/*     */   public AdvToken(int id, String original, String originalEnd, int beginIndex, int endIndex, int sentence, int paragraph, String tag, String pos, String ne) {
/*  44 */     this.id = id;
/*  45 */     this.original = original;
/*  46 */     this.originalEnd = originalEnd;
/*  47 */     this.beginIndex = beginIndex;
/*  48 */     this.endIndex = endIndex;
/*  49 */     this.sentence = sentence;
/*  50 */     this.paragraph = paragraph;
/*  51 */     this.length = (this.endIndex - this.beginIndex);
/*  52 */     this.tag = tag;
/*  53 */     this.ptag = pos;
/*  54 */     this.ne = ne;
/*     */   }
/*     */ 
/*     */   public int getId() {
/*  58 */     return this.id;
/*     */   }
/*     */ 
/*     */   protected void setId(int value) {
/*  62 */     this.id = value;
/*     */   }
/*     */ 
/*     */   protected void setStandfordId(int value) {
/*  66 */     this.standfordTokenId = value;
/*     */   }
/*     */ 
/*     */   public int getStandfordId() {
/*  70 */     return this.standfordTokenId;
/*     */   }
/*     */ 
/*     */   public String getOriginal() {
/*  74 */     return this.original;
/*     */   }
/*     */ 
/*     */   public String getOriginalEnd() {
/*  78 */     return this.originalEnd;
/*     */   }
/*     */ 
/*     */   public void setOriginalEnd(String value) {
/*  82 */     this.originalEnd = value;
/*     */   }
/*     */ 
/*     */   public int getBeginIndex() {
/*  86 */     return this.beginIndex;
/*     */   }
/*     */ 
/*     */   public int getEndIndex() {
/*  90 */     return this.endIndex;
/*     */   }
/*     */ 
/*     */   protected void setSentence(int value) {
/*  94 */     this.sentence = value;
/*     */   }
/*     */ 
/*     */   public int getSentence() {
/*  98 */     return this.sentence;
/*     */   }
/*     */ 
/*     */   public int getParagraph() {
/* 102 */     return this.paragraph;
/*     */   }
/*     */ 
/*     */   protected void setParagraph(int value) {
/* 106 */     this.paragraph = value;
/*     */   }
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 114 */     return this.tag;
/*     */   }
/*     */ 
/*     */   protected void setNE(String ne) {
/* 118 */     this.ne = ne;
/*     */   }
/*     */ 
/*     */   public String getNE() {
/* 122 */     return this.ne;
/*     */   }
/*     */ 
/*     */   protected void setPOS(String pos) {
/* 126 */     this.ptag = pos;
/*     */   }
/*     */ 
/*     */   public String getPOS() {
/* 130 */     return this.ptag;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 134 */     return this.id + "/" + this.standfordTokenId + " :: " + this.original + " :: " + this.tag + " / " + this.ne + " / " + this.ptag + " (" + this.beginIndex + "," + this.endIndex + ";" + this.length + ") " + " {" + this.sentence + "/" + this.paragraph + "}";
/*     */   }
/*     */ }

/* Location:           D:\Datasets_of_EL\aida-yago2-dataset\aida-yago2-dataset\aida-yago2-dataset.jar
 * Qualified Name:     mpi.truths.data.AdvToken
 * JD-Core Version:    0.6.2
 */