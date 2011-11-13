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
	
	public boolean insert (BreweryVO vo) {
		ContentValues cv = populate(vo); 
		return db.insert(BreweryVO.tableName, null, cv) > 0;
	}
	
	public boolean delete (BreweryVO vo) {
		String[] params = new String[] { String.valueOf(vo.getId()) };
		return db.delete(BreweryVO.tableName , "id = ?", params) > 0;
	}
	
	public boolean update (BreweryVO vo) {
		ContentValues cv = populate(vo); 
		String[] params = new String[] { String.valueOf(vo.getId()) };
		return db.update(BreweryVO.tableName, cv, "id = ?", params) > 0;
	}
	
	public BreweryVO get (int id) {
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
	
	public List<BreweryVO> getAll ( ) {
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

	private ContentValues populate(BreweryVO vo) {		
		ContentValues cv = new ContentValues();
		
	 // cv.put( ClienteVO.columns[ 0 ], vo.getId());		
		cv.put( BreweryVO.columns[ 1 ], vo.getName());
		cv.put( BreweryVO.columns[ 2 ], vo.getWebsite());
		cv.put( BreweryVO.columns[ 3 ], vo.getDescription());
		
		return cv;		
	}

}
