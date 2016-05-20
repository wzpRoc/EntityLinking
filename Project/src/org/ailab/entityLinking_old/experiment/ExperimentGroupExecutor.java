package org.ailab.entityLinking_old.experiment;

import org.apache.log4j.Logger;


/**
 * User: Lu Tingming
 * Date: 15-5-17
 * Time: 下午9:55
 * Desc: 执行一组实验
 */
public class ExperimentGroupExecutor {
    protected Logger logger = Logger.getLogger(this.getClass());
    protected ExperimentGroup experimentGroup;

    public ExperimentGroupExecutor(ExperimentGroup experimentGroup) {
        this.experimentGroup = experimentGroup;
    }

    public void execute() {
        for (int i = 0; i < experimentGroup.experimentList.size(); i++) {
            Experiment experiment = experimentGroup.experimentList.get(i);
            logger.info("execute experiment " + (i + 1) + "/" + experimentGroup.experimentList.size() + "'" + experiment.name + "'");
            new ExperimentExecutor(experiment).execute();
        }
    }

    public static void main(String[] args) {
//        ExperimentGroup group = new ExperimentGroup("ALbEL", TimeUtil.getDate_Time());
//        new ExperimentGroupExecutor(group).execute();
    }
}
