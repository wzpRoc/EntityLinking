package org.ailab.tool;

import org.ailab.entityLinking.wim.entityName.EntityName;
import org.ailab.entityLinking.wim.entityName.EntityNameBL;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 15-12-23
 * Time: 下午10:32
 */
public class EntityNameMap {
    private static EntityNameMap instance;
    private double minProbOfNameToEntity = 0.01;
    private Map<String, Map<Integer, Double>> nameToEntityIdsMap;

    public static EntityNameMap getInstance() {
        if(instance == null) {
            instance = new EntityNameMap();
        }
        return instance;
    }

    public EntityNameMap() {
        init();
    }

    public EntityNameMap(double minProbOfNameToEntity) {
        this.minProbOfNameToEntity = minProbOfNameToEntity;
        init();
    }

    private void init() {
        nameToEntityIdsMap = new HashMap<String, Map<Integer, Double>>();
        List<EntityName> entityNameList = (new EntityNameBL()).getList(minProbOfNameToEntity);

        for(EntityName entityName : entityNameList) {
            String nameKey = entityName.getName();
            Map<Integer, Double> entityIds = nameToEntityIdsMap.get(nameKey);
            if(entityIds == null) {
                entityIds = new HashMap<Integer, Double>();
                nameToEntityIdsMap.put(nameKey, entityIds);
            }
            entityIds.put(entityName.getEntityId(), entityName.getProbOfNameToEntity());
        }
    }

    public Map<Integer, Double> getEntityIdToProbMap(String entityName) {
        if(entityName == null) {
            return null;
        } else {
            return nameToEntityIdsMap.get(entityName);
        }
    }

    public boolean containsName(String name) {
        if(name == null) {
            return false;
        } else {
            return nameToEntityIdsMap.get(name)!=null;
        }
    }

}
