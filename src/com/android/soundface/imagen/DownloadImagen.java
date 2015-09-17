package com.android.soundface.imagen;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.soundface.MainActivity;
import com.android.soundface.R;

public class DownloadImagen extends Activity{
	
	String nombre = "";
	Bitmap imagen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download_imagen);
		
//		Formato recibido
//		{
//			  "alert": "Cuerpo del mensaje",
//			  "badge": "url de la imagen",			  
//			  "title": "Titulo del mensaje",
//	          "name": "nombre de la imagen"
//		}
//		{
//			  "alert": "Desde soundface te enviamos una nueva imagen",
//			  "badge": "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcS3b5ivy_vBQhs3zTk96UX-pWZnOzngF2cbLHRwMD7Vb1CXUuRSAQ",
//			  "title": "SOUNDFACE",
//	          "name" : "Nueva.jpg"
//		}
		
		try {
	      JSONObject json = new JSONObject(getIntent().getExtras().getString("com.parse.Data"));
	      //String mensaje = json.getString("alert");
	      String url = json.getString("badge");
	      //String titulo = json.getString("title");
	      nombre = json.getString("name");
	      
	      imagen = getBitmapFromURL(url);
	      
	      if(imagen==null){
	    	  Toast.makeText(getApplicationContext(),getString(R.string.errordownlad), Toast.LENGTH_LONG).show();
	    	  finish();
	      }else{	      
		      ImageView imageview = (ImageView) findViewById(R.id.imagenDisplay);
		        
		      if(imageview!=null){
		       	imageview.setImageBitmap(imagen);        	
		      }  
	      }
	      
	    } catch (JSONException e) {
	      Log.d("SF", "JSONException: " + e.getMessage());
	    }
		
	}
	
	public void guardarImagen(View view){
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		imagen.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
		
	    MainActivity.basedatos.guardarImagen(nombre, nombre, byteArray);
	    Toast.makeText(getApplicationContext(), getString(R.string.imagenguardada), Toast.LENGTH_LONG).show();
	    
	    try {
			stream.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
   
    public void descartarImagen(View view){
	      finish();
	}
	
	
	private Bitmap getBitmapFromURL(String src) {
	    try {
	        java.net.URL url = new java.net.URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}
