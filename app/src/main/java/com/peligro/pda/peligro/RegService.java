package com.peligro.pda.peligro;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RegService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RegService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

      String RegKey=intent.getStringExtra("token");
        userData.regKey=RegKey;
        String request=ConnectionUtility.url+"UpdateRegKey?phone="+userData.phone+"&RegKey="+RegKey;
        try {
            String response = ConnectionUtility.getResponse(request);
            if (response.equals("success")) {
                Toast.makeText(this, "id succesfully stored", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Registration failed ", Toast.LENGTH_LONG).show();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(this, "Connectivity error", Toast.LENGTH_LONG).show();

        }

    }}
