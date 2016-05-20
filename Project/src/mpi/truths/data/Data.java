/*     */ package mpi.truths.data;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Reader;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class Data
/*     */ {
/*  18 */   private HashMap<String, AdvTokens> documents = null;
/*     */ 
/*  20 */   private HashMap<String, Mentions> docMentions = null;
/*     */ 
/*  22 */   private List<String> docIds = null;
/*     */ 
/*  24 */   private HashSet<String> punctuations = null;
/*     */ 
/*  26 */   private HashMap<String, List<Pair<Integer, String>>> mapping = null;
/*     */ 
/*     */   public Data() {
/*  29 */     this.documents = new HashMap();
/*  30 */     this.docMentions = new HashMap();
/*  31 */     this.docIds = new LinkedList();
/*  32 */     this.mapping = new HashMap();
/*  33 */     this.punctuations = new HashSet();
/*  34 */     this.punctuations.add(".");
/*  35 */     this.punctuations.add(":");
/*  36 */     this.punctuations.add(",");
/*  37 */     this.punctuations.add(";");
/*  38 */     this.punctuations.add("!");
/*  39 */     this.punctuations.add("?");
/*  40 */     this.punctuations.add("'s");
/*     */   }
/*     */ 
/*     */   public void init(String path, String mappingFile) {
/*  44 */     readMappings(new File(mappingFile));
/*  45 */     if (path.endsWith("/")) {
/*  46 */       path = path.substring(0, path.length() - 1);
/*     */     }
/*  48 */     initFromFile(new File(path + "/eng.train"), "");
/*  49 */     initFromFile(new File(path + "/eng.testa"), "testa");
/*  50 */     initFromFile(new File(path + "/eng.testb"), "testb");
/*     */   }
/*     */ 
/*     */   private void readMappings(File f) {
/*     */     try {
/*  55 */       Reader in = new InputStreamReader(new FileInputStream(f), "UTF-8");
/*  56 */       BufferedReader breader = new BufferedReader(in);
/*  57 */       String line = breader.readLine();
/*  58 */       String id = null;
/*  59 */       int position = -1;
/*  60 */       String target = null;
/*  61 */       while (line != null)
/*  62 */         if (line.trim().length() == 0) {
/*  63 */           line = breader.readLine();
/*     */         }
/*     */         else {
/*  66 */           if (line.startsWith("-DOCSTART-")) {
/*  67 */             int start = line.indexOf("(") + 1;
/*  68 */             int end = line.indexOf(")");
/*  69 */             id = line.substring(start, end);
/*     */           } else {
/*  71 */             String[] split = line.split("\t");
/*  72 */             position = Integer.parseInt(split[0]);
/*  73 */             target = split[1];
/*     */ 
/*  75 */             if (split.length > 2) {
/*  76 */               target = target + "\t" + split[2];
/*     */ 
/*  78 */               if (split.length > 3) {
/*  79 */                 target = target + "\t" + split[3];
/*     */ 
/*  81 */                 if (split.length > 4) {
/*  82 */                   target = target + "\t" + split[4];
/*     */ 
/*  84 */                   if (split.length > 5) {
/*  85 */                     target = target + "\t" + split[5];
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */ 
/*  91 */             List pairs = null;
/*  92 */             if (this.mapping.containsKey(id)) {
/*  93 */               pairs = (List)this.mapping.get(id);
/*     */             } else {
/*  95 */               pairs = new LinkedList();
/*  96 */               this.mapping.put(id, pairs);
/*     */             }
/*  98 */             pairs.add(new Pair(Integer.valueOf(position), target));
/*     */           }
/* 100 */           line = breader.readLine();
/*     */         }
/*     */     } catch (Exception e) {
/* 103 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initFromFile(File f, String id)
/*     */   {
/* 109 */     int count = 0;
/* 110 */     int sentence = -1;
/* 111 */     AdvTokens tokens = null;
/*     */     try {
/* 113 */       int position = -1;
/* 114 */       int index = 0;
/* 115 */       Reader reader = new FileReader(f);
/* 116 */       BufferedReader breader = new BufferedReader(reader);
/* 117 */       String line = breader.readLine();
/* 118 */       while (line != null) {
/* 119 */         if (line.startsWith("-DOCSTART-")) {
/* 120 */           count++;
/* 121 */           if (tokens != null) {
/* 122 */             AdvNamedEntityFilter filter = new AdvNamedEntityFilter();
/* 123 */             Mentions mentions = filter.filter(tokens);
/* 124 */             mapTargetEntities(tokens.getDocId(), mentions);
/* 125 */             this.docMentions.put(tokens.getDocId(), mentions);
/* 126 */             this.documents.put(tokens.getDocId(), tokens);
/* 127 */             this.docIds.add(tokens.getDocId());
/*     */           }
/* 129 */           sentence = -1;
/* 130 */           position = -1;
/* 131 */           tokens = null;
/*     */         } else {
/* 133 */           if (line.trim().length() == 0) {
/* 134 */             sentence++;
/* 135 */             line = breader.readLine();
/* 136 */             continue;
/*     */           }
/* 138 */           position++;
/* 139 */           StringTokenizer st = new StringTokenizer(line);
/* 140 */           String word = null;
/* 141 */           String pos = null;
/* 142 */           String ner = null;
/* 143 */           if (st.hasMoreTokens()) {
/* 144 */             word = st.nextToken();
/*     */           }
/* 146 */           if (st.hasMoreTokens()) {
/* 147 */             pos = st.nextToken();
/*     */           }
/* 149 */           if (st.hasMoreTokens()) {
/* 150 */             st.nextToken();
/*     */           }
/* 152 */           if (st.hasMoreTokens()) {
/* 153 */             ner = st.nextToken();
/*     */           }
/* 155 */           if (tokens == null) {
/* 156 */             int value = this.docIds.size() + 1;
/* 157 */             tokens = new AdvTokens(value + id + " " + word);
/*     */           }
/* 159 */           if ((this.punctuations.contains(word)) && (tokens.size() > 0)) {
/* 160 */             AdvToken at = tokens.getToken(tokens.size() - 1);
/* 161 */             at.setOriginalEnd("");
/* 162 */             index--;
/*     */           }
/* 164 */           int endIndex = index + word.length();
/* 165 */           AdvToken at = new AdvToken(position, word, " ", index, endIndex, sentence, 0, "null", pos, ner);
/* 166 */           index = endIndex + 1;
/* 167 */           tokens.addToken(at);
/*     */         }
/* 169 */         line = breader.readLine();
/*     */       }
/* 171 */       if (tokens != null) {
/* 172 */         AdvNamedEntityFilter filter = new AdvNamedEntityFilter();
/* 173 */         Mentions mentions = filter.filter(tokens);
/* 174 */         mapTargetEntities(tokens.getDocId(), mentions);
/* 175 */         this.docMentions.put(tokens.getDocId(), mentions);
/* 176 */         this.documents.put(tokens.getDocId(), tokens);
/* 177 */         this.docIds.add(tokens.getDocId());
/*     */       }
/* 179 */       breader.close();
/* 180 */       reader.close();
/*     */     } catch (Exception e) {
/* 182 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void mapTargetEntities(String docId, Mentions mentions) {
/* 187 */     List mappings = (List)this.mapping.get(docId);
/* 188 */     if (mappings == null) {
/* 189 */       System.out.println("Warning: there are no mappings for document " + docId);
/*     */     }
/* 191 */     if (mappings.size() != mentions.getMentions().size()) {
/* 192 */       System.out.println("Warning: the number of mentions do not match for " + docId);
/*     */     }
/* 194 */     Iterator iter = mentions.getMentions().iterator();
/* 195 */     while (iter.hasNext()) {
/* 196 */       Mention mention = (Mention)iter.next();
/* 197 */       boolean done = false;
/* 198 */       Iterator mapIter = mappings.iterator();
/* 199 */       while ((!done) && (mapIter.hasNext())) {
/* 200 */         Pair map = (Pair)mapIter.next();
/* 201 */         if (((Integer)map.first).intValue() == mention.getStartToken()) {
/* 202 */           done = true;
/* 203 */           mention.setGroundTruthResult((String)map.second);
/*     */         }
/*     */       }
/* 206 */       if (!done)
/* 207 */         System.out.println("(" + docId + ") Failed to find ground truth for " + mention);
/*     */     }
/*     */   }
/*     */ 
/*     */   public HashMap<String, AdvTokens> getDocuments()
/*     */   {
/* 214 */     return this.documents;
/*     */   }
/*     */ 
/*     */   public HashMap<String, Mentions> getDocMentions() {
/* 218 */     return this.docMentions;
/*     */   }
/*     */ 
/*     */   public List<String> getDocIds() {
/* 222 */     return this.docIds;
/*     */   }
/*     */ }

/* Location:           D:\Datasets_of_EL\aida-yago2-dataset\aida-yago2-dataset\aida-yago2-dataset.jar
 * Qualified Name:     mpi.truths.data.Data
 * JD-Core Version:    0.6.2
 */