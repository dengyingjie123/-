package com.youngbook.common.utils.runner;

import com.youngbook.common.config.Config;
import com.youngbook.service.task.FdcgCustomerAccountBindTask;
import com.youngbook.service.task.FdcgCustomerAccountUnbindTask;
import com.youngbook.service.task.fdcg.FdcgCustomerChangeMobileTask;
import com.youngbook.service.task.fdcg.FdcgCustomerTask;

import java.util.HashMap;
import java.util.Timer;

/**
 * 周期执行类。
 * <p/>
 * 使用方法：
 * Task task = new Task ("Test");
 * task.setStartTime ( "2010-01-05 20:52:00" );
 * task.setStopTime ( "2010-1-6 11:33:30" );
 * task.setRepeatSecond ( 2 );
 * <p/>
 * TimeRunner.init ();
 * TimeRunner.addTask ( task );
 *
 * @author liyang
 */
public class TimeRunner {
    private static Timer taskRunner = new Timer();
    private static HashMap<String, Task> taskContainer = new HashMap<String, Task>();

    private static Task looper = null;

    public static HashMap<String, Task> getTaskContainer() {
        return taskContainer;
    }

    public static void setTaskContainer(HashMap<String, Task> taskContainer) {
        TimeRunner.taskContainer = taskContainer;
    }

    public static void setTaskRunner(Timer taskRunner) {
        TimeRunner.taskRunner = taskRunner;
    }

    public TimeRunner() throws Exception {
        if (taskRunner == null) {
            taskRunner = new Timer();
        }

        if (taskContainer == null) {
            taskContainer = new HashMap<String, Task>();
        }

        if (looper == null) {
            looper = new Looper();
            looper.setName("TIMERUNNER_LOOPER");
            looper.setStartTime("2010-1-6 0:0:0");
            //looper.setStopTime ( "2010-1-9 13:37:0" );
            looper.setStopTime(Config.getTimeRunnerStopTime());
            //looper.setStopSecond ( 60 );
            looper.setRepeatSecond(5);


            TimeRunner.addTask(looper);

        }
    }

    public static void init() throws Exception {
        new TimeRunner();
    }

    public static Timer getTaskRunner() {
        return taskRunner;
    }

    /**
     * 立即执行任务
     *
     * @param task
     */
    public static void runTask(Task task) {
        task.run();
    }

    /**
     * 立即执行任务
     *
     * @param taskName 任务名称
     */
    public static void runTask(String taskName) {
        Task task = TimeRunner.getTaskContainer().get(taskName);
        task.run();
        task.setState(Task.TASK_RUNNING);
    }

    /**
     * 停止任务
     *
     * @param task
     */
    public static void stopTask(Task task) {
        task.cancel();
        task.setState(Task.TASK_STOPPED);
        TimeRunner.getTaskContainer().remove(task.getName());
    }

    /**
     * 停止整个运行器
     */
    public static void stop() {
        TimeRunner.getTaskRunner().cancel();
    }

    /**
     * 停止任务
     *
     * @param taskName 任务名称
     */
    public static void stopTask(String taskName) {
        Task task = TimeRunner.getTaskContainer().remove(taskName);
        task.cancel();

    }

    public int getTaskNumber() {
        return TimeRunner.getTaskContainer().size();
    }

    /**
     * 增加周期执行的任务
     *
     * @param task 任务
     * @return
     */
    public static boolean addTask(Task task) {
        boolean isDone = false;
        try {
            taskContainer.put(task.getName(), task);
            if (task.getStopSecond() > 0) {
                TimeRunner.getTaskRunner().schedule(task, task.getStartSecond() * 1000,
                        task.getRepeatSecond() * 1000);
                task.setState(Task.TASK_RUNNING);
            }
            isDone = true;
        } catch (Exception e) {
            e.printStackTrace();
            isDone = false;
        }

        return isDone;
    }

    public boolean deleteTask(Task task) throws Exception {
        boolean isDone = false;
        try {
            task.cancel();
            TimeRunner.getTaskRunner().purge();
            TimeRunner.getTaskContainer().remove(task.getName());
        } catch (Exception e) {
            throw e;
        }
        return isDone;
    }


    public void run() {
        try {
//            DailyChecker dailyChecker = new DailyChecker ();
//            dailyChecker.setName ( "DailyChecker" );
//            dailyChecker.setStartTime ( "2010-01-05 20:52:00" );
//            dailyChecker.setStopTime ( "2010-1-23 13:53:30" );
//            dailyChecker.setRepeatSecond ( 3 );
//
//            FsJZCL fsJzclDaily = new FsJZCL ();
//            fsJzclDaily.setName ( "JZCL" );
//
//            TimeRunner.init ();
//            TimeRunner.addTask ( fsJzclDaily );
//
//            TranxServiceImpl tranxService=new TranxServiceImpl();
//            String URL11https="https://113.108.182.3/aipg/ProcessServlet";
//            tranxService.batchDaiShou(URL11https, false);

//            TimeRunner.init();
//
//            // Web 首页统计系统数据
//            UpdateSystemStatisticsDateTask taskofUpdateDate = new UpdateSystemStatisticsDateTask();
//            TimeRunner.addTask(taskofUpdateDate);
//

            // 开启扫描提现线程 30 秒一次
//            ScanBatchPaymentDataTask scanBatchPaymentDataTask = new ScanBatchPaymentDataTask();
//            TimeRunner.addTask(scanBatchPaymentDataTask);

            // 开启通联结果查询扫描 5 秒一次
//            AllinpayQueryTask allinpayQueryTask = new AllinpayQueryTask();
//            TimeRunner.addTask(allinpayQueryTask);

//
//            // 短信扫描 10 秒一次
//            SmsSenderTask smsSenderTask = new SmsSenderTask();
//            TimeRunner.addTask(smsSenderTask);


            /**
             * 富友手机支付反馈任务
             */
//            FuiouMobilePayCallbackTask fuiouMobilePayCallbackTask = new FuiouMobilePayCallbackTask();
//            TimeRunner.addTask(fuiouMobilePayCallbackTask);

            /**
             * 富友网关支付反馈任务
             */
//            FuiouPCPayCallbackTask fuiouPCPayCallbackTask = new FuiouPCPayCallbackTask();
//            TimeRunner.addTask(fuiouPCPayCallbackTask);

            FdcgCustomerTask fdcgCustomerTask = new FdcgCustomerTask();
            TimeRunner.addTask(fdcgCustomerTask);

            FdcgCustomerAccountBindTask fdcgCustomerAccountBindTask = new FdcgCustomerAccountBindTask();
            TimeRunner.addTask(fdcgCustomerAccountBindTask);
//
            FdcgCustomerAccountUnbindTask fdcgCustomerAccountUnbindTask = new FdcgCustomerAccountUnbindTask();
            TimeRunner.addTask(fdcgCustomerAccountUnbindTask);


            FdcgCustomerChangeMobileTask fdcgCustomerChangeMobileTask = new FdcgCustomerChangeMobileTask();
            TimeRunner.addTask(fdcgCustomerChangeMobileTask);


            // 设置订单超时时间 20分钟执行一次
//            OrderTask orderTask = new OrderTask();
//            TimeRunner.addTask(orderTask);
//
//            ArchiveLogDataTask archiveLogDataTask = new ArchiveLogDataTask();
//            TimeRunner.addTask(archiveLogDataTask);
//
//
//            // 查询通联支付网关支付情况，5分钟执行一次
//            AllinpayOrderQueryBatchTask allinpayOrderQueryBatchTask = new AllinpayOrderQueryBatchTask();
//            TimeRunner.addTask(allinpayOrderQueryBatchTask);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
