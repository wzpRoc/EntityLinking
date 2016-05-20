package org.ailab.nlp.match;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * User: Lu Tingming
 * Date: 2011-8-24
 * Time: 19:59:02
 * Desc:
 */
public class EntryTypeContainer {
    public int nEntryType;
    public EntryType entryType;
    public Set<EntryType> entryTypeSet;

    public EntryTypeContainer(EntryType entryType) {
        nEntryType = 1;
        this.entryType = entryType;
    }

    public EntryTypeContainer(Set<EntryType> entryTypeSet) {
        this.entryTypeSet = entryTypeSet;
    }

    public int getnEntryType() {
        return nEntryType;
    }

    public void add(EntryType entryType){
        if(nEntryType==1){
            if(this.entryType.equals(entryType)){
                // do nothing
            } else {
                nEntryType++;
                entryTypeSet = new HashSet<EntryType>(2);
                entryTypeSet.add(this.entryType);
                entryTypeSet.add(entryType);
            }
        } else {
            if(entryTypeSet.contains(entryType)){
                // do nothing
            } else {
                nEntryType++;
                entryTypeSet.add(entryType);
            }
        }
    }


    public Set<EntryType> getEntryTypeSet(){
        if(nEntryType==1){
            final HashSet<EntryType> hashSet = new HashSet<EntryType>(1);
            hashSet.add(entryType);
            return hashSet;
        } else {
            return entryTypeSet;
        }
    }


    public String getMajorType(){
        if(nEntryType==1){
            return entryType.majorType;
        } else {
            for(EntryType entryType :entryTypeSet){
                return entryType.majorType;
            }
        }

        return null;
    }


    public boolean contains(String majorType){
        if(nEntryType==1){
            return entryType.majorType.equals(majorType);
        } else {
            for(EntryType entryType :entryTypeSet){
                if(entryType.majorType.equals(majorType)){
                    return true;
                }
            }
        }

        return false;
    }


    public String toString(){
        if(nEntryType==1){
            return entryType.majorType;
        } else {
            return entryTypeSet.toString();
        }
    }


/*
    public String getEntityTypes() {
        if(nEntryType==1){
            return String.valueOf(Constants.map_entityType_stringToChar.get(entryType.majorType));
        } else {
            char[] entityTypes = new char[nEntryType];
            Iterator iterator = entryTypeSet.iterator();
            for(int i=0; i<entryTypeSet.size(); i++){
                EntryType entryType = (EntryType) iterator.next();
                entityTypes[i] = Constants.map_entityType_stringToChar.get(entryType.majorType);
            }
            return new String(entityTypes);
        }
    }
*/
}
