package eu.veldsoft.share.with.us;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Boot receiver for alarm events registration.
 * 
 * @author Ventsislav Medarov
 */
public class BootReceiver extends WakefulBroadcastReceiver {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, NewMessageCheckService.class));
	}
}
