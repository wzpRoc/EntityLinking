package org.ailab.nlp.match;

/**
 * User: Lu Tingming
 * Date: 2011-8-24
 * Time: 19:49:09
 * Desc: 字典中的一个条目
 */
public class Entry {
    public String name;
    public EntryType entryType;

    public Entry(String name, EntryType entryType) {
        this.name = name;
        this.entryType = entryType;
    }
}
