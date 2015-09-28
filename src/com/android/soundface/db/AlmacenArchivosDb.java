package com.android.soundface.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.soundface.MainActivity;
import com.android.soundface.bean.Imagen;
import com.android.soundface.bean.Sonido;

public class AlmacenArchivosDb extends SQLiteOpenHelper implements ArchivosDb{
	
	SQLiteDatabase dataBase;
	
	public AlmacenArchivosDb(Context context) {
		super(context, "soundface", null, 31);
	}
		
	@Override 
	public void onCreate(SQLiteDatabase db) {	
		db.execSQL("DROP TABLE IF EXISTS archivos");
		db.execSQL("DROP TABLE IF EXISTS sonidos");
		db.execSQL("DROP TABLE IF EXISTS imagenes");
		db.execSQL("DROP TABLE IF EXISTS imagenesGeneradas");

		
		db.execSQL("CREATE TABLE imagenes ("+	
				"id_imagen INTEGER PRIMARY KEY AUTOINCREMENT, "+	
				"nombre_imagen TEXT, desc_imagen TEXT, imagen BLOB)");
		
		db.execSQL("CREATE TABLE sonidos ("+	
				"id_sonido INTEGER PRIMARY KEY AUTOINCREMENT, "+	
				"nombre_sonido TEXT, desc_sonido TEXT, sonido BLOB)");

		dataBase = db;
		MainActivity.inicializacionBaseDatos();	
	}
	
	@Override    
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
			db.execSQL("DROP TABLE IF EXISTS archivos");
			db.execSQL("DROP TABLE IF EXISTS sonidos");
			db.execSQL("DROP TABLE IF EXISTS imagenes");
			db.execSQL("DROP TABLE IF EXISTS imagenesGeneradas");
	
			
			db.execSQL("CREATE TABLE imagenes ("+	
					"id_imagen INTEGER PRIMARY KEY AUTOINCREMENT, "+	
					"nombre_imagen TEXT, desc_imagen TEXT, imagen BLOB)");
			
			db.execSQL("CREATE TABLE sonidos ("+	
					"id_sonido INTEGER PRIMARY KEY AUTOINCREMENT, "+	
					"nombre_sonido TEXT, desc_sonido TEXT, sonido BLOB)");
			

			
			dataBase = db;
			MainActivity.inicializacionBaseDatos();			
			
		
	}
	
	
	@Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {        
        return super.getReadableDatabase();
    }
	
	
    public void closedatabase() throws SQLiteException{
		
		if(dataBase!=null){
			dataBase.close();
		}
	}
	

    @Override
	public void eliminarImagen(Imagen imagen) {
    	SQLiteDatabase database;
		if(dataBase==null){
			database = getWritableDatabase();
		}else{
			database = dataBase;
		}
		
		database.delete("imagenes", "id_imagen=" + imagen.getIdImagen(), null);
	    
		
	}
	

	
	public void guardarImagen(String name, String desc, byte[] image) throws SQLiteException{
		
		SQLiteDatabase database;
		if(dataBase==null){
			database = getWritableDatabase();
		}else{
			database = dataBase;
		}
		
	    ContentValues cv = new  ContentValues();
	    cv.put("nombre_imagen",  name);
	    cv.put("desc_imagen",  desc);
	    cv.put("imagen",   image);
	    database.insert( "imagenes" , null, cv );
	    
	    //database.close();
	}
	

	@Override
	public List<Imagen> obtenerTodasImagenes() {
		
		List<Imagen> result = new ArrayList<Imagen>();
		
		SQLiteDatabase database;
		if(dataBase==null){
			database = getReadableDatabase();
		}else{
			database = dataBase;
		}
		
		Cursor cursor = database.rawQuery("SELECT id_imagen, nombre_imagen, desc_imagen, imagen FROM imagenes", null);
		
		while (cursor.moveToNext()){		
			try {
				result.add(new Imagen(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getBlob(3)));
			} catch (Exception e) {				
				e.printStackTrace();
			}	
		}
		cursor.close();		
		//database.close();		
		return result;
		
	}
	
	
	
	
	
	public void guardarSonido(String name, String desc, byte[] image) throws SQLiteException{
		
		SQLiteDatabase database;
		if(dataBase==null){
			database = getWritableDatabase();
		}else{
			database = dataBase;
		}
		 
		
	    ContentValues cv = new  ContentValues();
	    cv.put("nombre_sonido",  name);
	    cv.put("desc_sonido",  desc);
	    cv.put("sonido",   image);
	    database.insert( "sonidos" , null, cv );
	    
	    //database.close();	
	}
	
	@Override
	public List<Sonido> obtenerTodosSonidos() {
		
		List<Sonido> result = new ArrayList<Sonido>();
		
		SQLiteDatabase db = getReadableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT id_sonido,nombre_sonido,desc_sonido,sonido FROM sonidos", null);
		
		while (cursor.moveToNext()){		
			result.add(new Sonido(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getBlob(3)));	
		}
		cursor.close();		
		//db.close();		
		return result;
		
	}

	
	
	
	@Override
	public List<Imagen> obtenerTodasImagenesGeneradas() {
		
		List<Imagen> result = new ArrayList<Imagen>();
		
		SQLiteDatabase db = getReadableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT id_imageng, nombre_imageng, desc_imageng, imagen FROM imagenesGeneradas", null);
		
		while (cursor.moveToNext()){		
			result.add(new Imagen(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getBlob(3)));	
		}
		cursor.close();		
		//db.close();		
		return result;
		
	}
	
	@Override
	public Imagen obtenerImagenGenerada(long id) {
		
		Imagen result = null;
		
		SQLiteDatabase db = getReadableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT id_imageng, nombre_imageng, desc_imageng, imageng FROM imagenesGeneradas where id_imageng = '"+Long.toString(id)+"'", null);
		
		while (cursor.moveToNext()){		
			result = new Imagen(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getBlob(3));	
		}
		cursor.close();		
		//db.close();		
		return result;
		
	}
	
	public long guardarImagenGenerada(String name, String desc, byte[] image) throws SQLiteException{
		
		SQLiteDatabase database;
		if(dataBase==null){
			database = getWritableDatabase();
		}else{
			database = dataBase;
		}
		
	    ContentValues cv = new  ContentValues();
	    cv.put("nombre_imageng",  name);
	    cv.put("desc_imageng",  desc);
	    cv.put("imageng",   image);
	    long result = database.insert( "imagenesGeneradas" , null, cv );
	    
	   // database.close();
	    
	    return result;
	}

	@Override
	public void guardarArchivo(String name, byte[] image) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] obtenerArchivo(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	

}
