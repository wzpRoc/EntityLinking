/*    */ package mpi.truths.data;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Pair<F, S>
/*    */   implements Comparable<Pair<F, S>>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -9050365080458307106L;
/*    */   public F first;
/*    */   public S second;
/*    */ 
/*    */   public F first()
/*    */   {
/* 25 */     return this.first;
/*    */   }
/*    */ 
/*    */   public void setFirst(F first)
/*    */   {
/* 30 */     this.first = first;
/*    */   }
/*    */ 
/*    */   public S second()
/*    */   {
/* 35 */     return this.second;
/*    */   }
/*    */ 
/*    */   public void setSecond(S second)
/*    */   {
/* 40 */     this.second = second;
/*    */   }
/*    */ 
/*    */   public Pair(F first, S second)
/*    */   {
/* 46 */     this.first = first;
/* 47 */     this.second = second;
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 51 */     return this.first.hashCode() ^ this.second.hashCode();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object obj)
/*    */   {
/* 57 */     return ((obj instanceof Pair)) && (((Pair)obj).first.equals(this.first)) && (((Pair)obj).second.equals(this.second));
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 62 */     return this.first + "/" + this.second;
/*    */   }
/*    */ 
/*    */   public int compareTo(Pair<F, S> o)
/*    */   {
/* 67 */     int firstCompared = ((Comparable)this.first).compareTo(o.first());
/* 68 */     if (firstCompared != 0) return firstCompared;
/* 69 */     return ((Comparable)this.second).compareTo(o.second());
/*    */   }
/*    */ }

/* Location:           D:\Datasets_of_EL\aida-yago2-dataset\aida-yago2-dataset\aida-yago2-dataset.jar
 * Qualified Name:     mpi.truths.data.Pair
 * JD-Core Version:    0.6.2
 */