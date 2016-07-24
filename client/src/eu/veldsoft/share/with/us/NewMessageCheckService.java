package eu.veldsoft.share.with.us;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import eu.veldsoft.share.with.us.model.Util;
import eu.veldsoft.share.with.us.storage.MessageHistoryDatabaseHelper;

/**
 * Demon process for new message checking.
 * 
 * @author Ventsislav Medarov
 */
public class NewMessageCheckService extends Service {
	/**
	 * Application installation instance hash.
	 */
	private String instanceHash = "";

	/**
	 * Remote host domain.
	 */
	private String host = "";

	/**
	 * Name of the remote script to be called.
	 */
	private String script = "";

	/**
	 * Reference to database helper.
	 */
	private MessageHistoryDatabaseHelper helper = null;

	/**
	 * Setup alarm for service activation.
	 */
	private void setupAlarm() {
		/*
		 * Do not set if it is already there.
		 */
		if (PendingIntent.getBroadcast(this, Util.ALARM_REQUEST_CODE,
				new Intent(getApplicationContext(),
						NewMessageCheckReceiver.class),
				PendingIntent.FLAG_NO_CREATE) != null) {
			return;
		}

		/*
		 * Parameterize weak-up interval.
		 */
		long interval = AlarmManager.INTERVAL_HALF_HOUR;
		try {
			interval = getPackageManager().getServiceInfo(
					new ComponentName(NewMessageCheckService.this,
							NewMessageCheckService.this.getClass()),
					PackageManager.GET_SERVICES | PackageManager.GET_META_DATA).metaData
					.getInt("activation_interval",
							(int) AlarmManager.INTERVAL_HALF_HOUR);
		} catch (NameNotFoundException exception) {
			interval = AlarmManager.INTERVAL_HALF_HOUR;
			System.err.println(exception);
		}

		((AlarmManager) this.getSystemService(Context.ALARM_SERVICE))
				.setInexactRepeating(AlarmManager.RTC_WAKEUP, System
						.currentTimeMillis(), interval, PendingIntent
						.getBroadcast(this, Util.ALARM_REQUEST_CODE,
								new Intent(getApplicationContext(),
										NewMessageCheckReceiver.class),
								PendingIntent.FLAG_UPDATE_CURRENT));
	}

	/**
	 * Service constructor.
	 */
	public NewMessageCheckService() {
		super();
		// TODO Find better way to give name of the service.
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int id) {
		/*
		 * Check alarm.
		 */
		setupAlarm();

		/*
		 * Release wake-up lock.
		 */
		if (intent.getAction() == Intent.ACTION_BOOT_COMPLETED) {
			WakefulBroadcastReceiver.completeWakefulIntent(intent);
		}

		/*
		 * Check for new message.
		 */
		(new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				/*
				 * If there is no database helper message check can not be done.
				 */
				if (helper == null) {
					return null;
				}

				/*
				 * Check in SQLite what is the last message hash and take
				 * special care when the local SQLite database is empty.
				 */
				String lastMessageHash = helper.getLastMessageHash();

				HttpClient client = new DefaultHttpClient();
				client.getParams().setParameter(
						"http.protocol.content-charset", "UTF-8");
				HttpPost post = new HttpPost("http://" + host + "/" + script);

				JSONObject json = new JSONObject();
				try {
					json.put(Util.JSON_INSTNCE_HASH_CODE_KEY, instanceHash);
					json.put(Util.JSON_LAST_MESSAGE_HASH_CODE_KEY,
							lastMessageHash);
				} catch (JSONException exception) {
					System.err.println(exception);
				}

				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				pairs.add(new BasicNameValuePair("new_message_check", json
						.toString()));
				try {
					post.setEntity(new UrlEncodedFormEntity(pairs));
				} catch (UnsupportedEncodingException exception) {
					System.err.println(exception);
				}

				try {
					HttpResponse response = client.execute(post);

					JSONObject result = new JSONObject(EntityUtils.toString(
							response.getEntity(), "UTF-8"));

					final String messageHash = result
							.getString(Util.JSON_MESSAGE_HASH_CODE_KEY);
					final String messageRegistered = result
							.getString(Util.JSON_REGISTERED_KEY);
					boolean messageFound = result
							.getBoolean(Util.JSON_FOUND_KEY);

					/*
					 * If there is a new message open message read activity (by
					 * sending message hash as parameter).
					 */
					if (messageHash != "" && messageFound == true) {
						Intent intent = new Intent(NewMessageCheckService.this,
								AnswerMessageActivity.class);

						// TODO This activity should not start more than once
						// simultaneously.
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						/* | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT */);

						intent.putExtra(Util.PARENT_MESSAGE_HASH_KEY,
								messageHash);
						intent.putExtra(Util.REGISTERED_KEY, messageRegistered);
						startActivity(intent);
					}
				} catch (ClientProtocolException exception) {
					System.err.println(exception);
				} catch (IOException exception) {
					System.err.println(exception);
				} catch (JSONException exception) {
					System.err.println(exception);
				}

				NewMessageCheckService.this.stopSelf();
				return null;
			}
		}).execute();

		return START_NOT_STICKY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate() {
		super.onCreate();

		/*
		 * Load installation instance hash.
		 */
		instanceHash = PreferenceManager.getDefaultSharedPreferences(
				NewMessageCheckService.this).getString(
				Util.SHARED_PREFERENCE_INSTNCE_HASH_CODE_KEY, "");

		/*
		 * Load host from the manifest file.
		 */
		try {
			host = getPackageManager().getApplicationInfo(
					NewMessageCheckService.this.getPackageName(),
					PackageManager.GET_META_DATA).metaData.getString("host");
		} catch (NameNotFoundException exception) {
			System.err.println(exception);
		}

		/*
		 * Load script name from manifest file.
		 */
		try {
			script = getPackageManager().getServiceInfo(
					new ComponentName(NewMessageCheckService.this,
							NewMessageCheckService.this.getClass()),
					PackageManager.GET_SERVICES | PackageManager.GET_META_DATA).metaData
					.getString("script");
		} catch (NameNotFoundException exception) {
			System.err.println(exception);
		}

		/*
		 * Create database helper object if it is not created yet.
		 */
		if (helper == null) {
			helper = new MessageHistoryDatabaseHelper(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDestroy() {
		if (helper != null) {
			helper.close();
			helper = null;
		}
		super.onDestroy();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return new Binder() {
			Service getService() {
				return NewMessageCheckService.this;
			}
		};
	}
}
