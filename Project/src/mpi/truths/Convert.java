/*     */ package mpi.truths;
/*     */ 
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import mpi.truths.data.AdvToken;
/*     */ import mpi.truths.data.AdvTokens;
/*     */ import mpi.truths.data.Data;
/*     */ import mpi.truths.data.Mention;
/*     */ import mpi.truths.data.Mentions;
/*     */ 
/*     */ public class Convert
/*     */ {
/*  17 */   private String publicFile = "./data/public-aida.tsv";
/*     */   private static final String finalName = "AIDA-YAGO2-dataset.tsv";
/*  21 */   private File result = null;
/*     */ 
/*  23 */   private String conllPath = "./data/conll";
/*     */ 
/*  25 */   private Data data = null;
/*     */ 
/*     */   public Convert() {
/*     */   }
/*     */ 
/*     */   public Convert(String conllPath, String publicFile) {
/*  31 */     this.conllPath = conllPath;
/*  32 */     this.publicFile = publicFile;
/*     */   }
/*     */ 
/*     */   public void start() {
/*  36 */     this.data = new Data();
/*  37 */     this.data.init(this.conllPath, this.publicFile);
/*     */     try {
/*  39 */       File f = new File(this.publicFile);
/*  40 */       this.result = new File(f.getParentFile(), "AIDA-YAGO2-dataset.tsv");
/*     */     } catch (Exception e) {
/*  42 */       e.printStackTrace();
/*     */     }
/*  44 */     writeData();
/*     */   }
/*     */ 
/*     */   private void writeData() {
/*     */     try {
/*  49 */       FileOutputStream fos = new FileOutputStream(this.result);
/*  50 */       BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
/*  51 */       for (String docId : this.data.getDocIds()) {
/*  52 */         writer.write("-DOCSTART- (" + docId + ")\n");
/*  53 */         AdvTokens tokens = (AdvTokens)this.data.getDocuments().get(docId);
/*  54 */         AdvToken prevToken = null;
/*  55 */         for (int i = 0; i < tokens.size(); i++) {
/*  56 */           AdvToken token = tokens.getToken(i);
/*  57 */           if ((prevToken != null) && (token.getSentence() > prevToken.getSentence())) {
/*  58 */             writer.write("\n");
/*     */           }
/*  60 */           Mention mention = getTargetMention(token.getId(), docId);
/*  61 */           if (mention != null) {
/*  62 */             String text = mention.getMention();
/*  63 */             int iterateTo = mention.getEndToken() + 1;
/*  64 */             boolean start = true;
/*  65 */             char startDef = 'B';
/*  66 */             for (; i < iterateTo; i++) {
/*  67 */               token = tokens.getToken(i);
/*  68 */               writer.write(token.getOriginal() + "\t" + startDef + "\t" + text + "\t" + mention.getGroundTruthResult() + "\n");
/*  69 */               if (start) {
/*  70 */                 startDef = 'I';
/*  71 */                 start = false;
/*     */               }
/*     */             }
/*  74 */             i--;
/*     */           } else {
/*  76 */             writer.write(token.getOriginal() + "\n");
/*     */           }
/*  78 */           prevToken = token;
/*     */         }
/*  80 */         writer.write("\n");
/*     */       }
/*  82 */       writer.flush();
/*  83 */       writer.close();
/*     */     } catch (Exception e) {
/*  85 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private Mention getTargetMention(int id, String docId) {
/*  90 */     Mentions mentions = (Mentions)this.data.getDocMentions().get(docId);
/*  91 */     return mentions.getMentionForTokenId(id);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/*  95 */     String publicFile = "D:\\wzp.newsML.test\\AIDA-YAGO2-annotations.tsv";
/*  96 */     String conllPath = null;
/*     */     try {
/*  98 */       while (conllPath == null) {
/*  99 */         System.out.flush();
/* 100 */         System.out.println("Specify the directory containing the CoNLL 2003 files 'eng.train', 'eng.testa', and 'eng.testb':");
/* 101 */         conllPath = read(true);
/*     */ 
/* 103 */         File dir = new File(conllPath);
/*     */ 
/* 105 */         File train = new File(dir, "eng.train");
/* 106 */         File testa = new File(dir, "eng.testa");
/* 107 */         File testb = new File(dir, "eng.testb");
/*     */ 
/* 109 */         if ((!train.exists()) || (!testa.exists()) || (!testb.exists())) {
/* 110 */           System.out.println("The specified directory did not contain the required files!\n");
/* 111 */           conllPath = null;
/*     */         }
/*     */       }
/*     */ 
/* 115 */       File annotations = new File(publicFile);
/*     */ 
/* 117 */       if (!annotations.exists()) {
/* 118 */         System.out.println("'AIDA-YAGO2-annotations.tsv' could not be found, make sure it is in the directory where you call the jar!");
/* 119 */         System.exit(10);
/*     */       }
/*     */     } catch (Exception e) {
/* 122 */       System.out.println("There was a Problem:");
/* 123 */       e.printStackTrace();
/* 124 */       return;
/*     */     }
/* 126 */     if ((conllPath != null) && (publicFile != null)) {
/* 127 */       Convert convert = new Convert(conllPath, publicFile);
/* 128 */       convert.start();
/* 129 */       System.out.println("The dataset was created: AIDA-YAGO2-dataset.tsv");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String read(boolean directory) throws IOException {
/* 134 */     int c = 1;
/* 135 */     StringBuffer sb = new StringBuffer(20);
/* 136 */     while ((c = (char)System.in.read()) >= 0) {
/* 137 */       if (c == 10) {
/*     */         break;
/*     */       }
/* 140 */       sb.append((char)c);
/*     */     }
/* 142 */     File f = new File(sb.toString().trim());
/* 143 */     if (!f.exists()) {
/* 144 */       System.out.println("WARNING: The file or folder " + sb.toString() + " does not exist.");
/* 145 */       return null;
/*     */     }
/* 147 */     if ((directory) && (!f.isDirectory())) {
/* 148 */       System.out.println("WARNING: " + sb.toString() + " is not a directory.");
/* 149 */       return null;
/*     */     }
/* 151 */     if ((!directory) && (!f.isFile())) {
/* 152 */       System.out.println("WARNING: " + sb.toString() + " is not a file.");
/* 153 */       return null;
/*     */     }
/* 155 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           D:\Datasets_of_EL\aida-yago2-dataset\aida-yago2-dataset\aida-yago2-dataset.jar
 * Qualified Name:     mpi.truths.Convert
 * JD-Core Version:    0.6.2
 */