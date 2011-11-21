package br.blog.masina.beerdiary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;


public class Core extends Activity {

	final int TAKE_PHOTO_CODE = 1;
	
	// Core methods for BEERDIARY
	
	public void takePhoto() {

		final int TAKE_PHOTO_CODE = 1;

		final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(getTempFile(this)));
		startActivityForResult(intent, TAKE_PHOTO_CODE);
	}

	private File getTempFile(Context context) {
		// it will return /sdcard/image.tmp
		final File path = new File(Environment.getExternalStorageDirectory(),
				context.getPackageName());
		if (!path.exists()) {
			path.mkdir();
		}
		return new File(path, context.getPackageName() + "_photo.tmp");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case TAKE_PHOTO_CODE:
				final File file = getTempFile(this);
				try {
					Bitmap captureBmp = MediaStore.Images.Media.getBitmap(
							getContentResolver(), Uri.fromFile(file));
					// do whatever you want with the bitmap (Resize, Rename, Add
					// To Gallery, etc)
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

}
