package br.blog.masina.beerdiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	// Main menu actions

	public void onClickBrewery(View v) {
		startActivity(new Intent(getBaseContext(), BreweryInsertActivity.class));
	}

	public void onClickStyle(View v) {
		startActivity(new Intent(getBaseContext(), BreweryListActivity.class));
	}

}
