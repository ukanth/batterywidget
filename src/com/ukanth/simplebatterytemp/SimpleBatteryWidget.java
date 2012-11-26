package com.ukanth.simplebatterytemp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.RemoteViews;
import android.widget.Toast;

public class SimpleBatteryWidget extends AppWidgetProvider {

    private String batteryTemperature;

    private BroadcastReceiver myBatteryReceiver = new BroadcastReceiver() {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
	    if (arg1.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
		batteryTemperature = String.valueOf((float) arg1.getIntExtra(
			"temperature", 0) / 10) + "C";
	    }
	}

    };

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
	super.onDeleted(context, appWidgetIds);
	Toast.makeText(context, "deleting...", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
	    int[] appWidgetIds) {
	super.onUpdate(context, appWidgetManager, appWidgetIds);
	final int N = appWidgetIds.length;
	
	context.getApplicationContext().registerReceiver(this.myBatteryReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
	
	for (int i = 0; i < N; i++) {
	    int awID = appWidgetIds[i];
	    RemoteViews v = new RemoteViews(context.getPackageName(),R.layout.widget);
	    v.setTextViewText(R.id.textView1, batteryTemperature);
	    appWidgetManager.updateAppWidget(awID, v);
	    Toast.makeText(context, "updated.." + batteryTemperature, Toast.LENGTH_LONG).show();
	}
    }
}
