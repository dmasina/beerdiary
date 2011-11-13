package br.blog.masina.beerdiary;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils.StringSplitter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BreweryInsertActivity extends Activity {

	private static final int SELECT_PHOTO = 0;
	final int IMAGE_FROM_FILE = 0;
	final int IMAGE_FROM_CAMERA = 1;

	EditText name, website, description;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brewery_edit);

		name = (EditText) findViewById(R.id.edt_name);
		website = (EditText) findViewById(R.id.edt_website);
		description = (EditText) findViewById(R.id.edt_description);

	}

	// Action when button Save is pressed.

	public void onClick(View v) {

		BreweryVO value = new BreweryVO(name.getText().toString(), website
				.getText().toString(), description.getText().toString());

		// Test required fields

		int result = value.checkFields();

		if (result == 0) {

			DAO dao = new DAO(getBaseContext());

			if (dao.insert(value)) {
				Toast.makeText(getBaseContext(), R.string.msg_insert_success,
						Toast.LENGTH_SHORT).show();
				this.finish();
			}

		} else {

			EditText edt = (EditText) findViewById(result);
			edt.requestFocus();

			Toast.makeText(getBaseContext(), R.string.msg_required_field,
					Toast.LENGTH_SHORT).show();
		}
	}

	// Action when the picture is pressed.

	public void onClickImage(View v) {

		String question = getString(R.string.msg_picture_file_or_camera);
		String file = getString(R.string.str_file);
		String camera = getString(R.string.str_camera);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(question)
				.setCancelable(false)
				.setPositiveButton(file, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Toast.makeText(getBaseContext(), "FILE",
						Toast.LENGTH_SHORT).show();
					}
				})
				.setNegativeButton(camera, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Toast.makeText(getBaseContext(), "CAMERA",
						Toast.LENGTH_SHORT).show();
					}
				});

		AlertDialog alert = builder.create();
		// alert.setTitle("Title");
		// alert.setIcon(R.drawable.ic_launcher);
		alert.show();
	}
}
