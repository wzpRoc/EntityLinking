/*     */ package mpi.truths.data;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class AdvTokens
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8015832523759790735L;
/*  11 */   private String docId = null;
/*     */ 
/*  13 */   private List<AdvToken> tokens = null;
/*     */ 
/*  15 */   private List<String> problems = null;
/*     */ 
/*  17 */   private String originalStart = "";
/*     */ 
/*  19 */   private String originalEnd = "";
/*     */ 
/*     */   public AdvTokens() {
/*  22 */     this.tokens = new LinkedList();
/*  23 */     this.problems = new LinkedList();
/*     */   }
/*     */ 
/*     */   public AdvTokens(String docId) {
/*  27 */     this.docId = docId;
/*  28 */     this.tokens = new LinkedList();
/*  29 */     this.problems = new LinkedList();
/*     */   }
/*     */ 
/*     */   public String getDocId() {
/*  33 */     return this.docId;
/*     */   }
/*     */ 
/*     */   public AdvToken getToken(int position) {
/*  37 */     return (AdvToken)this.tokens.get(position);
/*     */   }
/*     */ 
/*     */   public int size() {
/*  41 */     return this.tokens.size();
/*     */   }
/*     */ 
/*     */   public void addToken(AdvToken token) {
/*  45 */     this.tokens.add(token);
/*     */   }
/*     */ 
/*     */   public String toString() {
/*  49 */     StringBuffer sb = new StringBuffer(200);
/*  50 */     for (int i = 0; i < this.tokens.size(); i++) {
/*  51 */       sb.append(((AdvToken)this.tokens.get(i)).toString()).append('\n');
/*     */     }
/*  53 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toText(int startToken, int endToken) {
/*  57 */     StringBuffer sb = new StringBuffer(200);
/*  58 */     for (int i = startToken; i <= endToken; i++) {
/*  59 */       sb.append(((AdvToken)this.tokens.get(i)).getOriginal());
/*  60 */       if (i + 1 <= endToken) {
/*  61 */         sb.append(((AdvToken)this.tokens.get(i)).getOriginalEnd());
/*     */       }
/*     */     }
/*  64 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toText() {
/*  68 */     StringBuffer sb = new StringBuffer(200);
/*  69 */     sb.append(this.originalStart);
/*  70 */     for (int i = 0; i < this.tokens.size(); i++) {
/*  71 */       sb.append(((AdvToken)this.tokens.get(i)).getOriginal());
/*  72 */       sb.append(((AdvToken)this.tokens.get(i)).getOriginalEnd());
/*     */     }
/*  74 */     sb.append(this.originalEnd);
/*  75 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public void logProblem(String problem) {
/*  79 */     this.problems.add(problem);
/*     */   }
/*     */ 
/*     */   public boolean hasProblem() {
/*  83 */     return this.problems.size() > 0;
/*     */   }
/*     */ 
/*     */   public List<String> getProblems() {
/*  87 */     return this.problems;
/*     */   }
/*     */ 
/*     */   protected void setOriginalStart(String text) {
/*  91 */     this.originalStart = text;
/*     */   }
/*     */ 
/*     */   protected void setOriginalEnd(String text) {
/*  95 */     this.originalEnd = text;
/*     */   }
/*     */ 
/*     */   public String getOriginalStart() {
/*  99 */     return this.originalStart;
/*     */   }
/*     */ 
/*     */   public String getOriginalEnd() {
/* 103 */     return this.originalEnd;
/*     */   }
/*     */ }

/* Location:           D:\Datasets_of_EL\aida-yago2-dataset\aida-yago2-dataset\aida-yago2-dataset.jar
 * Qualified Name:     mpi.truths.data.AdvTokens
 * JD-Core Version:    0.6.2
 */