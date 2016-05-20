package org.ailab.wimfra.util;

import java.util.HashSet;
import java.util.Set;

/**
 * User: Lu Tingming
 * Date: 2012-7-4
 * Time: 15:29:06
 * Desc:
 */
public class TrimLowerStringSet extends HashSet<String> {
    public TrimLowerStringSet() {
        super();
    }

    public TrimLowerStringSet(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public boolean contains(Object key) {
        return super.contains(normalize(key));
    }


    @Override
    public boolean add(String value) {
        return super.add(normalize(value));
    }


    public boolean addAll(Set<String> set) {
        if(set == null){
            return false;
        }

        for(String value:set) {
            this.add(value);
        }

        return true;
    }


    public String normalize(Object key){
        return normalize((String) key);
    }


    public String normalize(String key){
        if(key == null){
            return null;
        }

        return key.trim().toLowerCase();
    }
}