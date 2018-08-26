package com.youngbook.common.utils.runner;

import com.youngbook.common.utils.TimeUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

/**
 * 增加状态
 * @author liyang
 *
 */
public class Task extends TimerTask {

    // 运行状态
    public static final int TASK_RUNNING    = 0;

    // 停止状态
    public static final int TASK_STOPPED    = 1;

    // 挂起状态
    public static final int TASK_HANG_UP    = 2;

    // 重复执行
    public static final int TASK_REPEATED   = 1;

    // 不是重复执行
    public static final int TASK_UNREPEATED = 0;

    // 任务名称
    private String          name            = "";

    // 任务状态
    private int             state           = -1;

    // 是否重复执行
    private int             isRepeated      = Task.TASK_UNREPEATED;

    // 开始时间
    private Date            startTime       = null;

    private Date            realStartTime   = null;

    // 结束时间
    private Date            stopTime        = null;

    // 重复周期（秒）
    private long            repeatSecond    = -1;

    /**
     * 构造函数
     * @param name 任务名称
     * @param startTime 开始时间，格式：yyyy-mm-dd hh24:mi:ss
     * @param stopTime 结束时间，格式：yyyy-mm-dd hh24:mi:ss
     * @param repeatSecond 循环执行时间（秒）
     */
    public Task ( String name, String startTime, String stopTime, long repeatSecond ) {
        this.setName ( name );
        this.setStartTime ( startTime );
        this.setStopTime ( stopTime );
        this.setRepeatSecond ( repeatSecond );
    }

    /**
     * 设置停止天数，任务将在此时间（天）之后停止，从开始时间计算，往后多少天停止
     * @param day 停止的天数
     * @throws Exception
     */
    public void setStopDay ( int day ) throws Exception {
        long second = day * 24 * 60 * 60;
        this.setStopSecond ( second );
    }

    /**
     * 设置停止秒数，任务将在此时间（秒）之后停止，从开始时间计算，往后多少秒停止
     * @param second 停止的秒数
     * @throws Exception
     */
    public void setStopSecond ( long second ) throws Exception {
        String startTime = "";
        if (this.getStartSecond () > 0) {
            startTime = TimeUtils.getTime ( TimeUtils.getNow(), (int) this.getStartSecond (), "SECOND" );
        }

        else {
            startTime = TimeUtils.getNow();
        }
        String stopTime = TimeUtils.getTime ( startTime, (int) second, "SECOND" );

        this.setStopTime ( stopTime );
    }

    public Task ( String name ) {
        this.name = name;
    }

    public Task () {

    }

    /**
     * 获得即将执行的时间
     * @return
     */
    public long getStartTimeInfo () {
        long time = 0;
        Date now = Calendar.getInstance ().getTime ();
        time = this.repeatSecond * 1000 - (now.getTime () - this.realStartTime.getTime ()) % (this.repeatSecond * 1000);

        return time / 1000;
    }

    public Date getStopTime () {
        return this.stopTime;
    }

    public void setStopTime ( Date stopTime ) {
        this.stopTime = stopTime;
    }

    public long getRepeatSecond () {
        return this.repeatSecond;
    }

    /**
     * 获得重复执行周期（秒）
     * @param repeatSecond
     */
    public void setRepeatSecond ( long repeatSecond ) {
        this.repeatSecond = repeatSecond;
    }

    /**
     * 获得开始时间，距离现在多少秒。
     * 如果对象设置的开始时间已经过去，则返回0秒，表示可以立即执行
     * @return
     */
    public long getStartSecond () {
        long start = -1;

        Date now = Calendar.getInstance ().getTime ();

        start = this.getStartTime ().getTime () - now.getTime ();

        System.out.println ( "Task.getStartMinute(): [" + this.getName () + "] will start at " + start + " second(s)." );

        if (start != -1) {
            this.isRepeated = Task.TASK_REPEATED;
        }

        if (start < 0) {
            this.setRealStartTime ( new Date ( now.getTime () ) );

            start = 0;
        }
        else {
            this.setRealStartTime ( startTime );
        }

        return start / 1000;
    }

    /**
     * 获得停止时间，距离现在多少秒
     * @return
     */
    public long getStopSecond () {
        long stop = -1;

        Date now = Calendar.getInstance ().getTime ();

        stop = this.getStopTime ().getTime () - now.getTime ();

        System.out.println ( "Task.getStopSecond(): [" + this.getName () + "] will stop at " + stop / 1000
                + " second(s)." );

        return stop / 1000;
    }

    public int getIsRepeated () {
        return this.isRepeated;
    }

    public void setIsRepeated ( int isRepeated ) {
        this.isRepeated = isRepeated;
    }

    public Date getStartTime () {
        return this.startTime;
    }

    public void setStartTime ( Date startTime ) {
        this.startTime = startTime;
    }

    /**
     * 初始化开始时间。
     * 时间格式为：yyyy-mm-dd hh24:mi:ss
     * @param startTime
     */
    public void setStartTime ( String startTime ) {
        String[] times = startTime.split ( " " );
        String[] dates = times[0].split ( "-" );
        String[] time = times[1].split ( ":" );
        Calendar date = Calendar.getInstance ();
        date.set ( Integer.valueOf ( dates[0] ), Integer.valueOf ( dates[1] ) - 1, Integer.valueOf ( dates[2] ),
                Integer.valueOf ( time[0] ), Integer.valueOf ( time[1] ), Integer.valueOf ( time[2] ) );

        System.out.println ( "Task.setStartTime(): " + date.getTimeInMillis () );
        this.startTime = date.getTime ();

    }

    /**
     * 初始化结束时间
     * 时间格式为：yyyy-mm-dd hh24:mi:ss
     * @param stopTime
     */
    public void setStopTime ( String stopTime ) {
        String[] times = stopTime.split ( " " );
        String[] dates = times[0].split ( "-" );
        String[] time = times[1].split ( ":" );
        Calendar date = Calendar.getInstance ();
        date.set ( Integer.valueOf ( dates[0] ), Integer.valueOf ( dates[1] ) - 1, Integer.valueOf ( dates[2] ),
                Integer.valueOf ( time[0] ), Integer.valueOf ( time[1] ), Integer.valueOf ( time[2] ) );

        System.out.println ( "Task.setStartTime(): " + date.getTimeInMillis () );
        this.stopTime = date.getTime ();

    }

    public int getState () {
        return this.state;
    }

    public void setState ( int state ) {
        this.state = state;
    }

    public String getName () {
        return this.name;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    @Override
    public void run () {
        System.out.println ( "Task.run(): " + this.getName () + " runs." );
    }

    public Date getRealStartTime () {
        return this.realStartTime;
    }

    public void setRealStartTime ( Date realStartTime ) {
        this.realStartTime = realStartTime;
    }

}
