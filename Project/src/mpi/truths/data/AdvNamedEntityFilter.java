/*    */ package mpi.truths.data;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class AdvNamedEntityFilter
/*    */ {
/*  9 */   private HashMap<String, String> tags = null;
/*    */ 
/*    */   public AdvNamedEntityFilter() {
/* 12 */     this.tags = new HashMap();
/* 13 */     this.tags.put("LOCATION", "LOCATION");
/* 14 */     this.tags.put("I-LOC", "I-LOC");
/* 15 */     this.tags.put("B-LOC", "I-LOC");
/* 16 */     this.tags.put("PERSON", "PERSON");
/* 17 */     this.tags.put("I-PER", "I-PER");
/* 18 */     this.tags.put("B-PER", "I-PER");
/* 19 */     this.tags.put("ORGANIZATION", "ORGANIZATION");
/* 20 */     this.tags.put("I-ORG", "I-ORG");
/* 21 */     this.tags.put("B-ORG", "I-ORG");
/* 22 */     this.tags.put("MISC", "MISC");
/* 23 */     this.tags.put("I-MISC", "I-MISC");
/* 24 */     this.tags.put("B-MISC", "I-MISC");
/*    */   }
/*    */ 
/*    */   public Mentions filter(AdvTokens tokens) {
/* 28 */     HashMap subStrings = new HashMap();
/* 29 */     Mentions mentions = new Mentions();
/*    */     try {
/* 31 */       List content = new LinkedList();
/* 32 */       for (int p = 0; p < tokens.size(); p++) {
/* 33 */         AdvToken token = tokens.getToken(p);
/* 34 */         content.add(token.getOriginal());
/*    */       }
/* 36 */       mentions.setContextTokens(content);
/* 37 */       String previous = null;
/* 38 */       int start = -1;
/* 39 */       int end = -1;
/* 40 */       for (int p = 0; p < tokens.size(); p++) {
/* 41 */         AdvToken token = tokens.getToken(p);
/* 42 */         if (previous == null) {
/* 43 */           if (this.tags.containsKey(token.getNE())) {
/* 44 */             previous = (String)this.tags.get(token.getNE());
/* 45 */             start = token.getId();
/* 46 */             end = token.getId();
/*    */           }
/* 48 */         } else if (previous.equals(token.getNE())) {
/* 49 */           end = token.getId();
/*    */         } else {
/* 51 */           Mention newMentions = getPossibleMentions(start, end, tokens);
/* 52 */           mentions.addMention(newMentions);
/* 53 */           subStrings.put(Integer.valueOf(start), Integer.valueOf(end));
/* 54 */           previous = null;
/* 55 */           if (this.tags.containsKey(token.getNE())) {
/* 56 */             previous = (String)this.tags.get(token.getNE());
/* 57 */             start = token.getId();
/* 58 */             end = token.getId();
/*    */           }
/*    */         }
/*    */       }
/* 62 */       if (previous != null) {
/* 63 */         Mention newMentions = getPossibleMentions(start, end, tokens);
/* 64 */         mentions.addMention(newMentions);
/* 65 */         subStrings.put(Integer.valueOf(start), Integer.valueOf(end));
/* 66 */         previous = null;
/*    */       }
/* 68 */       mentions.setSubstring(subStrings);
/*    */     } catch (Exception e) {
/* 70 */       e.printStackTrace();
/*    */     }
/* 72 */     return mentions;
/*    */   }
/*    */ 
/*    */   private Mention getPossibleMentions(int start, int end, AdvTokens advTokens) {
/* 76 */     String meansArg = advTokens.toText(start, end);
/* 77 */     Mention mention = new Mention();
/* 78 */     mention.setMention(meansArg);
/* 79 */     mention.setStartToken(start);
/* 80 */     mention.setId(start);
/* 81 */     mention.setEndToken(end);
/* 82 */     int firstChar = advTokens.getToken(mention.getStartToken()).getBeginIndex();
/* 83 */     int lastChar = advTokens.getToken(mention.getEndToken()).getEndIndex();
/* 84 */     int charLength = lastChar - firstChar;
/* 85 */     mention.setCharOffset(firstChar);
/* 86 */     mention.setCharLength(charLength);
/* 87 */     return mention;
/*    */   }
/*    */ }

/* Location:           D:\Datasets_of_EL\aida-yago2-dataset\aida-yago2-dataset\aida-yago2-dataset.jar
 * Qualified Name:     mpi.truths.data.AdvNamedEntityFilter
 * JD-Core Version:    0.6.2
 */