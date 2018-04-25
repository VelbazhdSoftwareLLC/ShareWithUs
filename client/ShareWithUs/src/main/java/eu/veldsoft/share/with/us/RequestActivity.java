package eu.veldsoft.share.with.us;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;
import eu.veldsoft.share.with.us.model.Util;

/**
 * Initial request message screen.
 * 
 * @author Ventsislav Medarov
 */
public class RequestActivity extends Activity {
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
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request);

		/*
		 * Activate JavaScript.
		 */
		((WebView) findViewById(R.id.spot01)).getSettings()
				.setJavaScriptEnabled(true);

		/*
		 * Load local web page as spot ad holder.
		 */
		((WebView) findViewById(R.id.spot01))
				.loadUrl("file:///android_asset/spot01.html");

		/*
		 * Load installation instance hash.
		 */
		instanceHash = PreferenceManager.getDefaultSharedPreferences(
				RequestActivity.this).getString(
				Util.SHARED_PREFERENCE_INSTNCE_HASH_CODE_KEY, "");

		/*
		 * Load host from the manifest file.
		 */
		try {
			host = getPackageManager().getApplicationInfo(
					RequestActivity.this.getPackageName(),
					PackageManager.GET_META_DATA).metaData.getString("host");
		} catch (NameNotFoundException exception) {
			System.err.println(exception);
		}

		/*
		 * Load script name from manifest file.
		 */
		try {
			script = getPackageManager().getActivityInfo(
					RequestActivity.this.getComponentName(),
					PackageManager.GET_ACTIVITIES
							| PackageManager.GET_META_DATA).metaData
					.getString("script");
		} catch (NameNotFoundException exception) {
			System.err.println(exception);
		}

		findViewById(R.id.request_send).setOnClickListener(
		/**
		 * {@inheritDoc}
		 */
		new View.OnClickListener() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void onClick(View view) {
				(new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						String messageHash = Long.toHexString(UUID.randomUUID()
								.getLeastSignificantBits());
						String message = ((EditText) findViewById(R.id.request_text))
								.getText().toString();

						/*
						 * Convert to latin letters.
						 */
						message = Util.cyrillicToLatin(message);

						HttpClient client = new DefaultHttpClient();
						client.getParams().setParameter(
								"http.protocol.content-charset", "UTF-8");
						HttpPost post = new HttpPost("http://" + host + "/"
								+ script);

						JSONObject json = new JSONObject();
						try {
							json.put(Util.JSON_INSTNCE_HASH_CODE_KEY,
									instanceHash);
							json.put(Util.JSON_MESSAGE_HASH_CODE_KEY,
									messageHash);
							json.put(Util.JSON_MESSAGE_KEY, message);
						} catch (JSONException exception) {
							System.err.println(exception);
						}

						List<NameValuePair> pairs = new ArrayList<NameValuePair>();
						pairs.add(new BasicNameValuePair("request", json
								.toString()));
						try {
							post.setEntity(new UrlEncodedFormEntity(pairs));
						} catch (UnsupportedEncodingException exception) {
							System.err.println(exception);
						}

						try {
							client.execute(post);
						} catch (ClientProtocolException exception) {
							System.err.println(exception);
						} catch (IOException exception) {
							System.err.println(exception);
						}

						return null;
					}
				}).execute();

				Toast.makeText(RequestActivity.this,
						R.string.request_message_send, Toast.LENGTH_SHORT)
						.show();
				RequestActivity.this.finish();
			}
		});
	}
}
