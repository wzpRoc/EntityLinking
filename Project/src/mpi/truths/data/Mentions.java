/*     */ package mpi.truths.data;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Mentions
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -383105468450056989L;
/*  12 */   private List<Mention> mentions = null;
/*     */ 
/*  14 */   private List<String> contextTokens = null;
/*     */ 
/*  16 */   private HashMap<Integer, Integer> subStrings = null;
/*     */ 
/*     */   public Mentions() {
/*  19 */     this.mentions = new LinkedList();
/*     */   }
/*     */ 
/*     */   public boolean containsOffset(int offset) {
/*  23 */     for (Mention mention : this.mentions) {
/*  24 */       if (mention.getCharOffset() == offset) {
/*  25 */         return true;
/*     */       }
/*     */     }
/*  28 */     return false;
/*     */   }
/*     */ 
/*     */   public Mention getMentionForOffset(int offset) {
/*  32 */     for (Mention mention : this.mentions) {
/*  33 */       if (mention.getCharOffset() == offset) {
/*  34 */         return mention;
/*     */       }
/*     */     }
/*  37 */     return null;
/*     */   }
/*     */ 
/*     */   public Mention getMentionForTokenId(int id) {
/*  41 */     for (Mention mention : this.mentions) {
/*  42 */       if (mention.getStartToken() == id) {
/*  43 */         return mention;
/*     */       }
/*     */     }
/*  46 */     return null;
/*     */   }
/*     */ 
/*     */   public void addMention(Mention mention) {
/*  50 */     this.mentions.add(mention);
/*     */   }
/*     */ 
/*     */   public void addMentionList(List<Mention> mentions) {
/*  54 */     if (mentions != null)
/*  55 */       this.mentions.addAll(mentions);
/*     */     else
/*  57 */       System.out.println("Propblem");
/*     */   }
/*     */ 
/*     */   public List<Mention> getMentions()
/*     */   {
/*  62 */     return this.mentions;
/*     */   }
/*     */ 
/*     */   public boolean remove(Mention mention) {
/*  66 */     return this.mentions.remove(mention);
/*     */   }
/*     */ 
/*     */   public void setContextTokens(List<String> tokens) {
/*  70 */     this.contextTokens = tokens;
/*     */   }
/*     */ 
/*     */   public List<String> getContextTokens() {
/*  74 */     return this.contextTokens;
/*     */   }
/*     */ 
/*     */   public String toString() {
/*  78 */     StringBuffer sb = new StringBuffer(200);
/*  79 */     for (int i = 0; i < this.mentions.size(); i++) {
/*  80 */       sb.append(((Mention)this.mentions.get(i)).toString()).append('\n');
/*     */     }
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public void setSubstring(HashMap<Integer, Integer> subStrings) {
/*  86 */     this.subStrings = subStrings;
/*     */   }
/*     */ 
/*     */   public HashMap<Integer, Integer> getSubstrings() {
/*  90 */     return this.subStrings;
/*     */   }
/*     */ 
/*     */   public List<String> getLimitedContextTokens(Mention mention, int distance) {
/*  94 */     int start = mention.getStartToken() - distance;
/*  95 */     int end = mention.getEndToken() + distance + 1;
/*  96 */     List limitedContext = new LinkedList();
/*  97 */     if (start < 0) {
/*  98 */       start = 0;
/*     */     }
/* 100 */     if (end > this.contextTokens.size()) {
/* 101 */       end = this.contextTokens.size();
/*     */     }
/* 103 */     for (int i = start; i < end; i++) {
/* 104 */       limitedContext.add((String)this.contextTokens.get(i));
/*     */     }
/* 106 */     return limitedContext;
/*     */   }
/*     */ }

/* Location:           D:\Datasets_of_EL\aida-yago2-dataset\aida-yago2-dataset\aida-yago2-dataset.jar
 * Qualified Name:     mpi.truths.data.Mentions
 * JD-Core Version:    0.6.2
 */