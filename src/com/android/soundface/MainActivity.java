package com.android.soundface;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.android.soundface.bean.Imagen;
import com.android.soundface.bean.Sonido;
import com.android.soundface.db.AlmacenArchivosDb;
import com.android.soundface.db.ArchivosDb;
import com.android.soundface.texto.InicioTextoImagen;
import com.android.soundface.video.InicioVideo;

public class MainActivity extends Activity {
	
	public static ArchivosDb basedatos;
	public static AssetManager as;
	
	public static List<Imagen> imagenesParaVideo;
	public static Sonido sonidoParaVideo;
	public static String sonidoParaVideoUri;
	public static String rutaUltimoVideoGenerado;
	
	public static Imagen imagenParaTexto;
	public static List<String> opcionesParaTexto;
	public static String ultimaImagenGenerada;
	public static List<Integer> imagenesBorrar;
	public static List<Integer> videosBorrar;
	
	public static List<Bitmap> verlista;
	public static List<Bitmap> verlistaVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        Parse.initialize(this, "QHkRN9N34gKgDVBqLCcJRet0cX8dhuZLAM1vjkOn","UvCUuRd1eOqVlyBnUawogTMNbTjbSKvE8sc9GXMT");
//        PushService.setDefaultPushCallback(getco, AplicacionImagenes.class);
//		ParseInstallation.getCurrentInstallation().saveInBackground();
        
        basedatos = new AlmacenArchivosDb(this);
        
        imagenesParaVideo = new ArrayList<Imagen>();
        imagenesBorrar = new ArrayList<Integer>();
        videosBorrar = new ArrayList<Integer>();
        
        as = getAssets();
    }
    
    
    public void inicioVideo(View view){
	      Intent i = new Intent(this, InicioVideo.class);
	      startActivity(i);
	}
    
    public void inicioTextoImagen(View view){
	      Intent i = new Intent(this, InicioTextoImagen.class);
	      startActivity(i);
	}
    
    public void salir(View view){
	      finish();
	}
    
    public static void inicializacionBaseDatos(){
    	try {
    	
    		
    		String[] fileList = as.list("images");
    		String[] fileListS = as.list("sounds");
    		
    		for (String titulo : fileList) {
    			if(titulo.startsWith("ini_")){
	    			InputStream in = as.open("images/"+titulo);
	    			BufferedInputStream buf = new BufferedInputStream(in);
	                Bitmap bitmap = BitmapFactory.decodeStream(buf);
	                
	                ByteArrayOutputStream stream = new ByteArrayOutputStream();
	                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
	                byte[] byteArray = stream.toByteArray();
	                
	                String nombre = titulo.replaceAll("ini_","").substring(0,titulo.indexOf("."));
	    			
	                basedatos.guardarImagen(nombre, nombre.replaceAll("_"," ").replaceAll("_"," "), byteArray);
	                
	                try {
	    				stream.close();
	    			} catch (IOException e) {			
	    				e.printStackTrace();
	    			}
    			}
			}
    		
    		for (String titulo : fileListS) {
    			if(titulo.startsWith("ini_")){
	    			InputStream in = as.open("sounds/"+titulo);
	    			BufferedInputStream buf = new BufferedInputStream(in);
	                 
	                byte[] byteImage1 = new byte[buf.available()]; 
	                buf.read(byteImage1); 
	    			
	                String nombre = titulo.replaceAll("ini_","").substring(0,titulo.indexOf("."));
	                
	                basedatos.guardarSonido(nombre, nombre.replaceAll("_"," "), byteImage1);
    			}
			}
			
			
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}
    
    @Override
    protected void onDestroy() {    	
    	super.onDestroy();
    	MainActivity.basedatos.closedatabase();
    }
    
    @Override
    protected void onStop() {
    	super.onStop();    	
    	MainActivity.basedatos.closedatabase();
    }
    
    @Override protected void onStart() {
        super.onStart();
    }
  
    @Override 
    protected void onResume() {
        super.onResume();    
    }
  
    @Override 
    protected void onPause() {    
        super.onPause();
    }
 
    @Override 
    protected void onRestart() {
    	super.onRestart();    	
    }
    
//    @Override
//	public void onBackPressed() {
//		finish();
//		super.onBackPressed();
//    }
    
}
