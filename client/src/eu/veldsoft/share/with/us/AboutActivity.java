package eu.veldsoft.share.with.us;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * About the application screen.
 * 
 * @author Ventsislav Medarov
 */
public class AboutActivity extends Activity {
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

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

	}
}
