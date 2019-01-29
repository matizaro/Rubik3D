package com.mycompany.myjpctapp;

/**
 * Created by Welcome on 2015-09-18.
 */
public class HSTable implements Comparable<HSTable>{
    public final long time;
    public final String moves;
    public HSTable(long t, String m){
        time=t;
        moves=m;
    }
    public String getStringTime() {
        if (time!=0) {
            int milis = (int) time % 1000;
            int seconds = (int) (time / 1000) % 60;
            int minutes = (int) (time / (60 * 1000)) % 60;
            return (minutes < 10 ? "0" : "") + minutes + ":"
                    + (seconds < 10 ? "0" : "") + seconds + ":"
                    + (milis < 100 ? "0" : "") + (milis < 10 ? "0" : "") + milis;
        }else
            return "00:00:000";
    }
    public String toS(){
        return String.valueOf(time)+" "+moves;
    }

    @Override
    public int compareTo(HSTable another) {
        return (int)(time-another.time);
    }
}
