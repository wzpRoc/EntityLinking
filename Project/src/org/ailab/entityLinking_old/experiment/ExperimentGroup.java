package org.ailab.entityLinking_old.experiment;

import java.util.List;

/**
 * User: Lu Tingming
 * Date: 15-5-18
 * Time: 下午8:48
 * Desc:
 */
public class ExperimentGroup {
    public String batchName;
    public String batchStartTime;
    public String name;
    public String startTime;
    List<Experiment> experimentList;

    public ExperimentGroup(String batchName, String batchStartTime, String name, String startTime) {
        this.batchName = batchName;
        this.batchStartTime = batchStartTime;
        this.name = name;
        this.startTime = startTime;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getBatchStartTime() {
        return batchStartTime;
    }

    public void setBatchStartTime(String batchStartTime) {
        this.batchStartTime = batchStartTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
