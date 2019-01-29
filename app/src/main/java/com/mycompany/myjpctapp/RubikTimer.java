package com.mycompany.myjpctapp;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import java.util.Timer;

/**
 * Created by Welcome on 2015-09-14.
 */
public class RubikTimer {
    Timer rubikTimer;
    public long startTime=0, pauseTime=0, currentTime=0;
    boolean isRunning=false, isPaused=false;
    public RubikTimer(){

    }
    public void start(){
        if(StaticThings.rubikActivity!=null && !isRunning){
            isRunning=true;
            isPaused=false;
            rubikTimer = new Timer();
            startTime=System.currentTimeMillis();
            rubikTimer.schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                        StaticThings.rubikActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            currentTime = System.currentTimeMillis();
                                    StaticThings.timer.setText(getStringTime());
                        }
                    });
                }
            }, 0, 10);
        }
    }
    public void stop(){
        if(rubikTimer!=null && isRunning){
            rubikTimer.cancel();
            isRunning=false;
            isPaused=false;
        }
    }
    public void reset(){
        if(rubikTimer!=null && isRunning){
            rubikTimer.cancel();
            isRunning=false;
            isPaused=true;
            startTime=0;
            pauseTime=0;
            currentTime=0;
        }
    }
    public void pause(){
        if(rubikTimer!=null && isRunning){
            isPaused=true;
            pauseTime = System.currentTimeMillis();
            rubikTimer.cancel();
            isRunning=false;
        }
    }
    public void resume(){
        if(StaticThings.rubikActivity!=null && !isRunning) {
            rubikTimer = new Timer();
            isPaused=false;
            isRunning=true;
            long resumeTime = System.currentTimeMillis();
            long delay = resumeTime - pauseTime;
            startTime+=delay;
            rubikTimer.schedule(new java.util.TimerTask() {
                public void run() {
                    StaticThings.rubikActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            currentTime = System.currentTimeMillis();
                            SpannableStringBuilder time = getStringTime();
                            StaticThings.timer.setText(time);
                        }
                    });
                }
            }, 0, 10);
        }
    }
    public long getTime(){
        return (currentTime - startTime);
    }

    public SpannableStringBuilder getStringTime() {
        SpannableStringBuilder timeText;
        if (isRunning) {
            long time = (currentTime - startTime);
            int milis = (int) time % 1000;
            int seconds = (int) (time / 1000) % 60;
            int minutes = (int) (time / (60 * 1000)) % 60;

            timeText = StaticThings.convetTime(minutes, seconds, milis);
        }else{
            timeText = new SpannableStringBuilder("00:00:000");
            timeText.setSpan(new CustomTypefaceSpan("", StaticThings.robotoThin), 0, timeText.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }

        return timeText;
    }
}
