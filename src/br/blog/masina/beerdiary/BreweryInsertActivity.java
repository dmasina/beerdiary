package br.blog.masina.beerdiary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class BreweryInsertActivity extends Activity {

	final String brewery_pics = Environment.getExternalStorageDirectory()
			.toString() + "/beerdiary/pics/brewery";
	final String tmpfile = "picture.tmp";
	final String pic_default = brewery_pics + "/default.jpg";
	Bitmap captureBmp = null;
	EditText name, website, description;
	ImageButton image;
	Boolean showDefaultPicture = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brewery_edit);

		Log.d("life_cycle", "onCreate");
		Log.d("brewery_pics", brewery_pics);
		Log.d("tmpfile", tmpfile);
		Log.d("pic_default", pic_default);

		name = (EditText) findViewById(R.id.edt_name);
		website = (EditText) findViewById(R.id.edt_website);
		description = (EditText) findViewById(R.id.edt_description);
		image = (ImageButton) findViewById(R.id.imgButton);

		/*
		 * Log.i("DP", String.valueOf(showDefaultPicture));
		 * 
		 * if ( !this.showDefaultPicture) {
		 * 
		 * Log.i("Not null", "Objeto not null"); Log.i("DP",
		 * String.valueOf(showDefaultPicture));
		 *//*
			 * try { captureBmp = Media.getBitmap(getContentResolver(),
			 * Uri.fromFile(new File(pic_default))); } catch
			 * (FileNotFoundException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } catch (IOException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */

		/*
		 * try { captureBmp = Media.getBitmap(getContentResolver(),
		 * Uri.fromFile(new File(brewery_pics + "/picture.tmp")));
		 * 
		 * image.setImageBitmap(this.getCaptureBmp());
		 * onCreate(savedInstanceState); Log.i("DP",
		 * String.valueOf(showDefaultPicture));
		 * 
		 * } catch (FileNotFoundException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace();
		 * 
		 * } } else {
		 * 
		 * Log.i("Not null", "Passei retoooooooooooooooooO!"); Log.i("DP",
		 * String.valueOf(showDefaultPicture));
		 * 
		 * }
		 */

	}



	@Override
	protected void onResume() {

		Log.d("life_cycle", "onResume");

		// TODO Auto-generated method stub
		super.onResume();

		DAO dao = new DAO(getBaseContext());
		BreweryVO state = dao.breweryRestoreState();

		// Restore EditText
		
		if (state.getName() != null ) {  		
			name.setText(state.getName());
		} else if (state.getWebsite()!= null ) {
			website.setText(state.getWebsite());
		} else if (state.getDescription()!= null ) { 
			description.setText(state.getDescription());
		}
		
		// Restore ImageButton
	/*	
		try {
			//captureBmp = Media.getBitmap(getContentResolver(),
			//		Uri.fromFile(new File(brewery_pics + "/" + tmpfile)));
			//image.setImageBitmap(captureBmp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
	}

	@Override
	protected void onPause() {

		Log.d("life_cycle", "onPause");

		// TODO Auto-generated method stub
		super.onPause();

		BreweryVO value = new BreweryVO(name.getText().toString(), website
				.getText().toString(), description.getText().toString());

		DAO dao = new DAO(getBaseContext());

		if (dao.brewerySaveState(value)) {
			Log.i("onPause", "Stated successufully saved.");
			//this.finish();
		}

	}

	@Override
	protected void onStart() {

		Log.d("life_cycle", "onStart");

		// TODO Auto-generated method stub
		super.onStart();
	}

	
	
	
	
	// Action when button Save is pressed. ----------------------------------
	
	
	public void onClick(View v) {

		BreweryVO value = new BreweryVO(name.getText().toString(), website
				.getText().toString(), description.getText().toString());

		// Test required fields

		int result = value.checkFields();

		if (result == 0) {

			DAO dao = new DAO(getBaseContext());

			if (dao.breweryInsert(value)) {
				Toast.makeText(getBaseContext(), R.string.msg_insert_success,
						Toast.LENGTH_SHORT).show();
				dao.breweryCleanState();
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
				.setNegativeButton(camera,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								/*
								 * Toast.makeText(getBaseContext(), "CAMERA",
								 * Toast.LENGTH_SHORT).show();
								 */
								takePhoto();
							}
						});

		AlertDialog alert = builder.create();
		// alert.setTitle("Title");
		// alert.setIcon(R.drawable.ic_launcher);
		alert.show();
	}

	// --------------------------------------------------------------------------

	public void takePhoto() {

		final int TAKE_PHOTO_CODE = 1;

		final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(getTempFile(this, tmpfile)));
		startActivityForResult(intent, TAKE_PHOTO_CODE);
		Log.i("TAKE_PHOTO_CODE", String.valueOf(TAKE_PHOTO_CODE));

	}

	private File getTempFile(Context context, String tmpfile) {

		final File path = new File(brewery_pics);

		if (!path.exists()) {
			path.mkdirs();
			Log.i("Porra3", "porra3");
		}
		// return new File(path, context.getPackageName() + "_photo.tmp");
		return new File(brewery_pics, tmpfile);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Log.d("life_cycle", "onActivityResult");

		final int TAKE_PHOTO_CODE = 1;

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case TAKE_PHOTO_CODE:
				final File file = getTempFile(this, tmpfile);
				try {
					/*
					 * Bitmap captureBmp = MediaStore.Images.Media.getBitmap(
					 * getContentResolver(), Uri.fromFile(file));
					 */
					setCaptureBmp(MediaStore.Images.Media.getBitmap(
							getContentResolver(), Uri.fromFile(file)));
					// do whatever you want with the bitmap (Resize, Rename, Add
					// To Gallery, etc)
					;
					image.setImageBitmap(captureBmp);
					this.showDefaultPicture = false;
					Log.i("OK!", "Troquei a foto!");

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

	public Boolean getShowDefaultPicture() {
		return showDefaultPicture;
	}

	public void setShowDefaultPicture(Boolean showDefaultPicture) {
		this.showDefaultPicture = showDefaultPicture;
	}

	public Bitmap getCaptureBmp() {
		return captureBmp;
	}

	public void setCaptureBmp(Bitmap captureBmp) {
		this.captureBmp = captureBmp;
	}

	public EditText getName() {
		return name;
	}

	public void setName(EditText name) {
		this.name = name;
	}

	public EditText getWebsite() {
		return website;
	}

	public void setWebsite(EditText website) {
		this.website = website;
	}

	public EditText getDescription() {
		return description;
	}

	public void setDescription(EditText description) {
		this.description = description;
	}

	public ImageButton getImage() {
		return image;
	}

	public void setImage(ImageButton image) {
		this.image = image;
	}

}
