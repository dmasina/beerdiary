package br.blog.masina.beerdiary;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BreweryInsertActivity extends Activity {

	EditText name, website, description;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brewery_edit);

		name = (EditText) findViewById( R.id.edt_name );
		website  = (EditText) findViewById( R.id.edt_website );
		description  = (EditText) findViewById( R.id.edt_description );
	
	}
	
	public void onClick( View v ) {
		BreweryVO value = new BreweryVO( name.getText().toString(),
										 website.getText().toString(),
										 description.getText().toString());
		
		DAO dao = new DAO(getBaseContext());
		
		if (dao.insert(value)) {
			Toast.makeText(getBaseContext(), R.string.msg_insert_success,
					Toast.LENGTH_SHORT).show();
			this.finish();
		}
	}
}
