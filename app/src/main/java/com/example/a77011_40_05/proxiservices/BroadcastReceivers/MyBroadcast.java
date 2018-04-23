package android.com.exemplegooglecoudmessaging2.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String fromId = intent.getStringExtra("from");

        Toast.makeText(context,
                "From " + fromId + ": " + intent.getStringExtra("message"),
                Toast.LENGTH_LONG).show();
    }
}
