package com.peligro.pda.peligro;

import android.app.Application;
import android.content.Context;
import android.os.PowerManager;

/**
 * Created by Atif on 6/21/2015.
 */
public class MyApp extends Application {
    private PowerManager.WakeLock wakeLock;

    @Override
    public void onCreate() {
        super.onCreate();
        PowerManager powerManager=(PowerManager)super.getSystemService(Context.POWER_SERVICE);
        this.wakeLock=powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyLock");
    }

    public synchronized boolean acquireWakeLock(){
        if(this.wakeLock.isHeld()){
            return false;
        }
        else{
           this.wakeLock.acquire();
            return true;
        }
    }
    public synchronized boolean releaseWakeLock(){
        if(this.wakeLock.isHeld()){
            this.wakeLock.release();
            return true;
        }
        else{
            return false;
        }
    }
}
