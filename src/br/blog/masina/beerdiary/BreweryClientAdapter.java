package br.blog.masina.beerdiary;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

public class BreweryClientAdapter extends BaseAdapter {

	private List<BreweryVO> list;
	private Context ctx;
	
	private LayoutInflater inflater;
	
	public BreweryClientAdapter( Context ctx, List<BreweryVO> vos ) {
		this.list = vos;
		this.ctx  = ctx;
		
		inflater  = ( LayoutInflater )ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
	}
	
	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get( position );
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView( int position, View convertView, ViewGroup parent ) {

		BreweryVO client = list.get( position );
		
		if ( convertView == null ) {
			convertView = inflater.inflate( R.layout.brewery_list, null );
		}
		
		EditText name = ( EditText )convertView.findViewById( R.id.edt_name );
		name.setText( client.getName() );
		
		EditText website = ( EditText )convertView.findViewById( R.id.edt_website );
		website.setText( client.getWebsite() );
		
		EditText description = ( EditText )convertView.findViewById( R.id.edt_description );
		description.setText( client.getDescription() );
		
		return convertView;
	}

}
