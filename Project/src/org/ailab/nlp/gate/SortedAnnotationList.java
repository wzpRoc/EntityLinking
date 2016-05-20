package org.ailab.nlp.gate;

import gate.Annotation;
import gate.AnnotationSet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-3-28
 * Time: 21:02:48
 */
public class SortedAnnotationList extends Vector<Annotation> {
    public SortedAnnotationList() {
        super();
    }

    public boolean add(Annotation anno) {
        // 带插入的标注的位置
        long insertPosi = anno.getStartNode().getOffset() + anno.getEndNode().getOffset();
        for (int i = 0; i < size(); ++i) {
            // 列表中的标注
            Annotation currAnnot = get(i);
            // 列表中的标注的位置
            long currPosi = currAnnot.getStartNode().getOffset() + currAnnot.getEndNode().getOffset();
            if (insertPosi < currPosi) {
                insertElementAt(anno, i);
                return true;
            }
        }

        int size = size();
        insertElementAt(anno, size);
        return true;
    }

    public boolean addSortedExclusive(Annotation annot) {
        Annotation currAnnot = null;
        for (int i = 0; i < size(); ++i) {
            currAnnot = (Annotation) get(i);
            if (annot.overlaps(currAnnot)) {
                return false;

            } //if

        } //for
        long annotStart = annot.getStartNode().getOffset();
        long currStart;
        for (int i = 0; i < size(); ++i) {
            currAnnot = (Annotation) get(i);
            currStart = currAnnot.getStartNode().getOffset();
            if (annotStart < currStart) {
                insertElementAt(annot, i);
                return true;

            } //if

        } //for

        int size = size();
        insertElementAt(annot, size);
        return true;
    } //addSortedExclusive

    public boolean addSortedDistinct(Annotation annot) {
        Annotation currAnnot = null;
        for (int i = 0; i < size(); ++i) {
            currAnnot = (Annotation) get(i);
            if (annot.getStartNode().getOffset() == currAnnot.getStartNode().getOffset() && annot.getEndNode().getOffset() == currAnnot.getEndNode().getOffset()) {
                return false;
            } //if
        } //for

        long annotStart = annot.getStartNode().getOffset();
        long currStart;
        for (int i = 0; i < size(); ++i) {
            currAnnot = (Annotation) get(i);
            currStart = currAnnot.getStartNode().getOffset();
            if (annotStart < currStart) {
                insertElementAt(annot, i);
                return true;

            } //if

        } //for

        int size = size();
        insertElementAt(annot, size);
        return true;
    } //addSortedExclusive


    /**
     * @param set
     */
    public void add(AnnotationSet set) {
        Iterator ite = set.iterator();
        Annotation ann;
        while (ite.hasNext()) {
            ann = (Annotation) ite.next();
            this.add(ann);
        }
    }

    public boolean addSortedExclusiveAnnotation(AnnotationSet set) {
        boolean res = true;
        Iterator ite = set.iterator();
        Annotation ann;
        while (ite.hasNext()) {
            ann = (Annotation) ite.next();
            res = res && this.addSortedExclusive(ann);
        }
        return res;
    }

    public boolean addSortedDistinctAnnotation(AnnotationSet set) {
        boolean res = true;
        Iterator ite = set.iterator();
        Annotation ann;
        while (ite.hasNext()) {
            ann = (Annotation) ite.next();
            this.addSortedDistinct(ann);
        }
        return res;
    }

    public Annotation getAnnotation(int i) {
        return (Annotation) get(i);
    }

    public ArrayList<String> getSuperNstTermString(Annotation subAnno) {
        ArrayList<String> list = null;

        long startOffset = subAnno.getStartNode().getOffset();
        long endOffset = subAnno.getEndNode().getOffset();

        for (int i = 0; i < this.size(); i++) {
            Annotation currAnno = this.get(i);

            // 如果是同一个标注，那么肯定不是父术语
            if (currAnno == subAnno) continue;

            long startOffsetOfCurrAnno = currAnno.getStartNode().getOffset();
            long endOffsetOfCurrAnno = currAnno.getEndNode().getOffset();

            if (startOffset >= startOffsetOfCurrAnno
                    && endOffset <= endOffsetOfCurrAnno
                    && endOffset - startOffset < endOffsetOfCurrAnno - startOffsetOfCurrAnno) {
                if (list == null) {
                    list = new ArrayList<String>();
                }
                list.add((String) currAnno.getFeatures().get("s"));
            }
        }

        return list;
    }


    /**
     * 找到起始位置为anchorPosi的标注
     *
     * @param anchorPosi
     * @return
     */
    public Annotation findAnnoStartAt(long anchorPosi) {
        for (int i = 0; i < this.size(); i++) {
            Annotation anno = this.get(i);
            final Long pos = anno.getStartNode().getOffset();
            if (pos < anchorPosi) {
                continue;
            } else if (pos == anchorPosi) {
                return anno;
            } else {
                return null;
            }
        }

        return null;
    }


    /**
     * 找到指定标注前的第一个标注
     * @param anchorAnno
     * @return
     */
    public Annotation findAnnoBefore(Annotation anchorAnno) {
        final long anchorStart = anchorAnno.getStartNode().getOffset();
        final long anchorEnd = anchorAnno.getEndNode().getOffset();
        final long anchorLen = anchorEnd - anchorStart;
        Annotation lastAnno = null;

        for (Annotation anno : this) {
            final Long currStart = anno.getStartNode().getOffset();
            final Long currEnd = anno.getEndNode().getOffset();
            final long currLen = currEnd - currStart;

            if(anchorLen == 0){
                if(currLen == 0){
                    if(currStart>=anchorStart){
                        // |
                        // |
                        break;
                    }
                } else {
                    if(currEnd>anchorStart){
                        //   |
                        // CCC
                        break;
                    }
                }
            } else {
                if(currLen == 0){
                    if(currStart>anchorEnd){
                        // AAA
                        // |
                        break;
                    }
                } else {
                    if(currStart>=anchorStart){
                        // AAA
                        // CCC
                        break;
                    }
                }
            }
            lastAnno = anno;
        }

        return lastAnno;
    }


    /**
     * 找到指定标注后的第一个标注
     *
     * @param anchorAnno
     * @return
     */
    public Annotation findAnnoAfter(Annotation anchorAnno) {
        final long anchorStart = anchorAnno.getStartNode().getOffset();
        final long anchorEnd = anchorAnno.getEndNode().getOffset();
        final long anchorLen = anchorEnd - anchorStart;

        for (Annotation anno : this) {
            final Long currStart = anno.getStartNode().getOffset();
            final Long currEnd = anno.getEndNode().getOffset();
            final long currLen = currEnd - currStart;

            if(anchorLen == 0){
                if(currLen == 0){
                    if(currStart>anchorStart){
                        // |
                        //  |
                        return anno;
                    }
                } else {
                    if(currStart>=anchorStart){
                        // |
                        // CCC
                        return anno;
                    }
                }
            } else {
                if(currLen == 0){
                    if(currStart>=anchorEnd){
                        // AAA
                        //   |
                        return anno;
                    }
                } else {
                    if(currStart>=anchorEnd){
                        // AAA
                        //    CCC
                        return anno;
                    }
                }
            }
        }

        return null;
    }


    /**
     * 找到startPosi和endPosi中间的标注（闭区间，含startPosi和endPosi）
     *
     * @param startPosi
     * @param endPosi
     * @return
     */
    public ArrayList<Annotation> findAnnoBetween(long startPosi, long endPosi) {
        ArrayList<Annotation> list = new ArrayList<Annotation>();
        for (Annotation anno : this) {
            final Long currentStartPosi = anno.getStartNode().getOffset();
            final Long currentEndPosi = anno.getEndNode().getOffset();
            if (startPosi <= currentStartPosi && currentEndPosi <= endPosi) {
                list.add(anno);
            }
        }

        return list;
    }


    /**
     * 在当前对象内，找到指定标注的位置范围内的标注
     *
     * @param anchorAnno
     * @return
     */
    public ArrayList<Annotation> findAnnoIn(Annotation anchorAnno) {
        return findAnnoBetween(
                anchorAnno.getStartNode().getOffset(),
                anchorAnno.getEndNode().getOffset()
        );
    }
}
