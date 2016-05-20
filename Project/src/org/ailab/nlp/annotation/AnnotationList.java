package org.ailab.nlp.annotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Lu Tingming
 * Date: 2012-3-27
 * Time: 20:18:21
 * Desc:
 */
public class AnnotationList extends ArrayList<IAnnotation> {
    public AnnotationList() {
    }

    public AnnotationList(int initialCapacity) {
        super(initialCapacity);
    }

    public AnnotationList(List<IAnnotation> paraAnnoList) {
        super(paraAnnoList);
    }


    /**
     * 将标注列表存入位置到标注的映射
     *
     * @return
     */
    public Map<String, IAnnotation> getStartEndToAnnoMap() {
        Map<String, IAnnotation> startEndToAnnoMap = new HashMap<String, IAnnotation>(size());
        for (IAnnotation annotation : this) {
            startEndToAnnoMap.put(annotation.getStart() + "_" + annotation.getEnd(), annotation);
        }

        return startEndToAnnoMap;
    }


    /**
     * 按照开始和结束位置，找到第一个符合条件的标注
     */
    public IAnnotation findByPosition(IAnnotation anno) {
        return findFirstByPosition(anno.getStart(), anno.getEnd());
    }


    /**
     * 按照开始和结束位置，找到第一个符合条件的标注
     *
     * @param start
     * @param end
     * @return
     */
    public IAnnotation findFirstByPosition(int start, int end) {
        for (IAnnotation anno : this) {
            if (anno.getStart() < start) {
                // continue
            } else if (anno.getStart() == start) {
                if (anno.getEnd() < end) {
                    // continue
                } else if (anno.getEnd() == end) {
                    return anno;
                } else {
                    // 过掉了
                    return null;
                }
            } else {
                // 过掉了
                return null;
            }
        }

        return null;
    }


    /**
     * @param start
     * @param end
     * @return
     */
    public AnnotationList findBetween(int start, int end) {
        AnnotationList result = new AnnotationList();

        for (IAnnotation annotation : this) {
            if (annotation.getStart() >= start && annotation.getEnd() <= end) {
                result.add(annotation);
            }
        }

        return result;
    }


    /**
     * @param start
     * @param end
     * @return
     */
    public AnnotationList findOverlappedAnnotations(int start, int end) {
        AnnotationList result = new AnnotationList();

        for (IAnnotation annotation : this) {
            if (annotation.getStart() >= end || annotation.getEnd() <= start) {
            } else {
                result.add(annotation);
            }
        }

        return result;
    }


    /**
     * 找外部标注，含恰好匹配
     *
     * @param start
     * @param end
     * @return
     */
    public IAnnotation findOuterAnnotation(int start, int end) {
        for (IAnnotation annotation : this) {
            if (annotation.getStart() <= start) {
                if (annotation.getEnd() >= end) {
                    // ok
                    return annotation;
                } else {
                    // continue
                }
            } else {
                return null;
            }
        }

        return null;
    }


    /**
     * @param start
     * @param end
     * @return
     */
    public int deleteOverlappedAnnotations(int start, int end) {
        List<Integer> overlappedIdx = new ArrayList<Integer>();

        for (int i = 0; i < size(); i++) {
            IAnnotation annotation = get(i);
            if (annotation.getStart() >= end || annotation.getEnd() <= start) {
            } else {
                overlappedIdx.add(i);
            }
        }

        for (int i = overlappedIdx.size() - 1; i >= 0; i--) {
            this.remove(i);
        }

        return overlappedIdx.size();
    }


    /**
     * 删除内部标注，含正好匹配
     *
     * @param start
     * @param end
     * @return
     */
    public int deleteInnerAnnotations(int start, int end) {
        List<Integer> toBeDeletedIdxList = new ArrayList<Integer>();

        for (int i = 0; i < size(); i++) {
            IAnnotation annotation = get(i);
            if (annotation.getStart() >= start) {
                if (annotation.getEnd() <= end) {
                    toBeDeletedIdxList.add(i);
                } else {
                    break;
                }
            }
        }

        for (int i = toBeDeletedIdxList.size() - 1; i >= 0; i--) {
            this.remove(i);
        }

        return toBeDeletedIdxList.size();
    }


    /**
     * 删除内部标注，含正好匹配
     *
     * @param start
     * @param end
     * @return
     */
    public List<IAnnotation> findInnerAnnotations(int start, int end) {
        List<IAnnotation> innerAnnotationList = new ArrayList<IAnnotation>();

        for (int i = 0; i < size(); i++) {
            IAnnotation annotation = get(i);
            if (annotation.getStart() >= start) {
                if (annotation.getEnd() <= end) {
                    innerAnnotationList.add(annotation);
                } else {
                    break;
                }
            }
        }

        return innerAnnotationList;
    }


    /**
     * 按位置插入
     *
     * @return
     */
    public int insert(AnnotationList annoList) {
        int fromIndex = -1;
        for (IAnnotation anno : annoList) {
            fromIndex = insert(anno, fromIndex + 1);
        }

        return fromIndex;
    }


    /**
     * 按位置插入
     *
     * @param annoToInsert
     * @return
     */
    public int insert(IAnnotation annoToInsert) {
        return insert(annoToInsert, 0);
    }


    /**
     * 按位置插入
     *
     * @param annoToInsert
     * @return
     */
    public int insert(IAnnotation annoToInsert, int fromIndex) {
        if (size() == 0) {
            add(annoToInsert);
            return 0;
        }

        for (int i = fromIndex; i < size(); i++) {
            IAnnotation annotation = get(i);
            if (annoToInsert.getStart() < annotation.getStart()) {
                add(i, annoToInsert);
                return i;
            } else if (annoToInsert.getStart() == annotation.getStart()) {
                if (annoToInsert.getEnd() < annotation.getEnd()) {
                    add(i, annoToInsert);
                    return i;
                } else if (annoToInsert.getEnd() == annotation.getEnd()) {
                    add(i, annoToInsert);
                    return i;
                } else {
                    add(i + 1, annoToInsert);
                    return i + 1;
                }
            } else {
                if (i + 1 < size()) {
                    // 尚未到结尾
                    IAnnotation nextAnnotation = get(i + 1);
                    if (annoToInsert.getStart() < nextAnnotation.getStart()) {
                        add(i + 1, annoToInsert);
                        return i + 1;
                    } else if (annoToInsert.getStart() == nextAnnotation.getStart()) {
                        add(i + 1, annoToInsert);
                        return i + 1;
                    } else {
                        // continue
                    }
                } else {
                    // 已经到结尾
                    add(annoToInsert);
                    return i + 1;
                }
            }
        }

        this.add(annoToInsert);
        return this.size() - 1;
    }


    public void remove(List<IAnnotation> annoList) {
        for(IAnnotation annotation : annoList) {
            this.remove(annotation);
        }
    }


    public IAnnotation getLast() {
        return this.get(this.size() - 1);
    }
}