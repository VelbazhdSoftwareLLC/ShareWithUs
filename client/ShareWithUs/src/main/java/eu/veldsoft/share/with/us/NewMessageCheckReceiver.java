package eu.veldsoft.share.with.us;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Alarm listener for new message checking.
 * 
 * @author Ventsislav Medarov
 */
public class NewMessageCheckReceiver extends BroadcastReceiver {
	/**
	 * Receiver constructor.
	 */
	public NewMessageCheckReceiver() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, NewMessageCheckService.class));
	}
}
