package com.youngbook.common.utils.runner;

import com.youngbook.common.config.Config;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class Looper extends Task {

    @Override
    public void run () {
        //super.run ();

        try {
            // 更新停止时间
            this.setStopTime ( Config.getTimeRunnerStopTime() );

            this.checkTasks ();
        }
        catch (Exception e) {

        }
    }

    @Override
    public long getStopSecond () {
        return super.getStopSecond ();
    }

    @Override
    public void setStopTime ( Date stopTime ) {
        super.setStopTime ( stopTime );
    }

    @Override
    public void setStopTime ( String stopTime ) {
        super.setStopTime ( stopTime );
    }

    public Looper () {
        super ( "TIMERUNNER_LOOPER" );
    }

    public void checkTasks () {
        HashMap<String, Task> taskContainer = TimeRunner.getTaskContainer ();
        Set<String> setTaskNames = taskContainer.keySet ();
        Iterator<String> itTaskNames = setTaskNames.iterator ();

        while (itTaskNames.hasNext ()) {
            String taskName = itTaskNames.next ();

            Task task = taskContainer.get ( taskName );
            System.out.println ( "Looper.checkTasks(): checking: " + task.getName () + " State: " + task.getState () );
            if (task.getState () == Task.TASK_RUNNING) {
                if (task.getStopSecond () <= 0) {
                    TimeRunner.stopTask ( task );

                    // 自身循环器到达停止时间，停止整个运行器
                    if (taskName.equals ( "TIMERUNNER_LOOPER" )) {
                        TimeRunner.stop ();
                    }
                }
                else {
                    System.out.println ( "Looper.checkTasks(): checking: " + task.getName () + " will start in: "
                            + task.getStartTimeInfo () );
                }
            }
        }

        System.out.println ( "Looper.checkTasks(): Check Done." );
    }

}
