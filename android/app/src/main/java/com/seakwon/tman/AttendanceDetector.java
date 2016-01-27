package com.seakwon.tman;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AttendanceDetector extends BroadcastReceiver {
    private static final String MDM_ACTION = "com.sds.DMMonitor.receiver.DMMonitorReeciver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(MDM_ACTION) &&
            intent.getExtras().getString("DMCommand").equals("Camera")) {
            boolean status = intent.getExtras().getBoolean("DMValue");
            // FIXME: Should keep the status data for MainActivity to use it later.
        }
    }
}