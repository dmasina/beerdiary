package br.blog.masina.beerdiary;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DAO {
	
	private Context ctx;
	private SQLiteDatabase db;
	
	public DAO(Context ctx) {
		this.ctx = ctx;
		db = new DBHelper(ctx).getReadableDatabase();
		Log.d("DAO", this.ctx.toString());
	}

	public boolean brewerySaveState (BreweryVO vo) {
		Log.d("DAO", "brewerySaveState()");
		ContentValues cv = breweryPopulate(vo); 
		
		Log.d("DAO", "Saved Name: " + vo.getName());		
		Log.d("DAO", "Saved Website: " + vo.getWebsite());
		Log.d("DAO", "Saved Description: " + vo.getDescription());

		return db.update(BreweryVO.tableState, cv, null, null) > 0;
	}
	
	public void breweryCleanState () {
		Log.d("DAO", "breweryCleanState()");
		BreweryVO vo = new BreweryVO(null, null, null);
		ContentValues cv = breweryPopulate(vo);
		db.update(BreweryVO.tableState, cv, null, null);
	}
	
	public BreweryVO breweryRestoreState () {
		Log.d("DAO", "breweryRestoreState()");
		
		BreweryVO client = null;
		
		Cursor c = db.query(BreweryVO.tableState, 
				BreweryVO.columns, null, null, null, null, null );
		
		Log.d("DAO - COUNT", String.valueOf(c.getCount()));	
				
		if ( c.moveToFirst() ) {
			
			Log.d("DAO", "VOU RECUPERAR O VO");
			client = new BreweryVO(
				c.getString(c.getColumnIndex("name")),
				c.getString(c.getColumnIndex("website")),
				c.getString(c.getColumnIndex("description")));
							
			Log.d("DAO - RestoreState - Name", c.getString(c.getColumnIndex("name")));		
			Log.d("DAO - RestoreState - Website", c.getString(c.getColumnIndex("website")));
			Log.d("DAO - RestoreState - Description", c.getString(c.getColumnIndex("description")));
	
		} else {
				
				Log.d("DAO", "VOU CRIRAR O VO VAZIO");
				client = new BreweryVO(null, null, null);
		} 
		
		c.close();		
		return client;
	}
	
	public boolean breweryInsert (BreweryVO vo) {
		ContentValues cv = breweryPopulate(vo);
		return db.insert(BreweryVO.tableName, null, cv) > 0;
	}
	
	public boolean breweryDelete (BreweryVO vo) {
		String[] params = new String[] { String.valueOf(vo.getId()) };
		return db.delete(BreweryVO.tableName , "id = ?", params) > 0;
	}
	
	public boolean breweryUpdate (BreweryVO vo) {
		ContentValues cv = breweryPopulate(vo); 
		String[] params = new String[] { String.valueOf(vo.getId()) };
		return db.update(BreweryVO.tableName, cv, "id = ?", params) > 0;
	}
	
	public BreweryVO breweryGet (int id) {
		String[] params = new String[] { String.valueOf(id) };
		Cursor c = db.query(BreweryVO.tableName, 
				BreweryVO.columns, "id = ?", params, null, null, null );
		
		BreweryVO client = null;

		if ( c.moveToFirst() ) {
			client = new BreweryVO( 
					c.getInt(c.getColumnIndex("id")),
					c.getString(c.getColumnIndex("name")),
					c.getString(c.getColumnIndex("website")),
					c.getString(c.getColumnIndex("description")));
		}
		c.close();
		
		return client;
	}
	
	public List<BreweryVO> breweryGetAll ( ) {
		Cursor c = db.query( BreweryVO.tableName, 
				BreweryVO.columns, null, null, null, null, null );
		
		List<BreweryVO> list = new ArrayList<BreweryVO>();
		
		while ( c.moveToNext() ) {
			BreweryVO cliente = new BreweryVO( 
					c.getInt(c.getColumnIndex("id")),
					c.getString(c.getColumnIndex("name")),
					c.getString(c.getColumnIndex("website")),
					c.getString(c.getColumnIndex("description")));
			list.add( cliente );
		}		
		c.close();
		
		return list;
	}

	private ContentValues breweryPopulate(BreweryVO vo) {		
		ContentValues cv = new ContentValues();
		
	 // cv.put( ClienteVO.columns[ 0 ], vo.getId());		
		cv.put( BreweryVO.columns[ 1 ], vo.getName());
		cv.put( BreweryVO.columns[ 2 ], vo.getWebsite());
		cv.put( BreweryVO.columns[ 3 ], vo.getDescription());
		
		return cv;		
	}

}
