package com.peligro.pda.peligro;

import android.app.IntentService;
import android.content.Intent;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MyRefreshDataService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyRefreshDataService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            String foo1 = intent.getStringExtra("foo1");
        }finally {
            MyApp myApp=(MyApp)super.getApplicationContext();
            myApp.releaseWakeLock();
        }

    }
}
