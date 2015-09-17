package com.android.soundface.texto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bytedeco.javacv.FFmpegFrameRecorder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.soundface.MainActivity;
import com.android.soundface.R;
import com.android.soundface.UtilesVideo;
import com.android.soundface.bean.Imagen;
import com.android.soundface.imagen.InicioImagen;
//import com.googlecode.javacv.FFmpegFrameRecorder;

public class InicioTextoImagen extends Activity {
	
	DoTextoImagen tarea;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_texto_imagen);
    }
	
	public void listarImagenes(View view){
	      Intent i = new Intent(this, InicioImagen.class);	  
	      i.putExtra("tipo", "texto");
//	      if(pref.getBoolean("sonido", true)){
//	    	  soundPool.play(idClick, 1, 1, 0, 0, 1);
//	      }
	      startActivity(i);
	}
	
	public void addImagen(View view){
	      Intent i = new Intent(this, AddImagen.class);	  
	      i.putExtra("tipo", "texto");
//	      if(pref.getBoolean("sonido", true)){
//	    	  soundPool.play(idClick, 1, 1, 0, 0, 1);
//	      }
	      startActivity(i);
	}
	
	public void addTexto(View view){
	      Intent i = new Intent(this, InicioTexto.class);	   
//	      if(pref.getBoolean("sonido", true)){
//	    	  soundPool.play(idClick, 1, 1, 0, 0, 1);
//	      }
	      startActivity(i);
	}
	
	public void generateImagenFinal(View view){
		if(MainActivity.imagenParaTexto == null){
			  Toast.makeText(InicioTextoImagen.this, getString(R.string.imagenobligatorio), Toast.LENGTH_LONG).show();
		  }else if(MainActivity.opcionesParaTexto == null){
			  Toast.makeText(InicioTextoImagen.this, getString(R.string.textoobligatorio), Toast.LENGTH_LONG).show();
		  }else{
			  tarea = new DoTextoImagen();
			  tarea.execute(MainActivity.opcionesParaTexto);
		  }
	}
	
	public void verImagenGenerada(View view){
		if(MainActivity.ultimaImagenGenerada == null  ||  MainActivity.ultimaImagenGenerada.equals("")){
			Toast.makeText(InicioTextoImagen.this, getString(R.string.imagengenobligatorio), Toast.LENGTH_LONG).show();
		}else{
	      Intent i = new Intent(this, VerImagenGenerada.class);	   
//	      if(pref.getBoolean("sonido", true)){
//	    	  soundPool.play(idClick, 1, 1, 0, 0, 1);
//	      }
	      startActivity(i);
		}
	}
	
	public void verListaImagenes(View view){		
      Intent i = new Intent(this, VerListaImagenes.class);	   
//	      if(pref.getBoolean("sonido", true)){
//	    	  soundPool.play(idClick, 1, 1, 0, 0, 1);
//	      }
      startActivity(i);		
	}
	
		
	
	public class DoTextoImagen extends AsyncTask<List<String>, Void, String> {
        ProgressDialog dialog;
        FFmpegFrameRecorder recorder;
        String videoPath;
        
        UtilesVideo utV = new UtilesVideo(getAssets(), getApplicationContext()); 
        
        protected void onPreExecute() {
            dialog = new ProgressDialog(InicioTextoImagen.this);
            dialog.setMessage(getString(R.string.imagengenok));
            dialog.setCancelable(false);
            dialog.show();
        };
               

        @Override
        protected String doInBackground(List<String>... params) {

        	Imagen imagenMostrar = null;        	
            try {          
            
            	//Imagen
            	imagenMostrar = MainActivity.imagenParaTexto;
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagenMostrar.getImagen() , 0, imagenMostrar.getImagen().length);
            
                List<String> datos = params[0];
                
                Bitmap result = utV.generarTextoEnImagen(bitmap, datos.get(1), datos.get(2), datos.get(3), datos.get(4), datos.get(5), datos.get(6), datos.get(7));
                           
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
            	String currentDateandTime = sdf.format(new Date());
            	
//            	ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            	result.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            	byte[] byteArray = stream.toByteArray();
            	
                //long resultado = MainActivity.basedatos.guardarImagenGenerada(datos.get(0),datos.get(1)+" ("+currentDateandTime+")",byteArray);
            	utV.guardarImagenGenerada(datos.get(0)+"_"+currentDateandTime , result);
            	
                MainActivity.ultimaImagenGenerada = datos.get(0)+"_"+currentDateandTime;
                //MainActivity.imagenParaTexto = null;
                //MainActivity.opcionesParaTexto = null;

            } catch (Exception e) {
            	tarea.cancel(true);
                e.printStackTrace();                
            } 

            return null;
        }

        protected void onPostExecute(String result) {
            dialog.dismiss();
           	Toast.makeText(InicioTextoImagen.this, R.string.imagenok, Toast.LENGTH_SHORT).show();
         
        }
	}
	
	 @Override
	 protected void onStop() {
	   	super.onStop();
	   	if(tarea!=null)
	   		tarea.cancel(true);
	 }
	   
	
	 @Override 
	 protected void onPause() {
		 if(tarea!=null)
			 tarea.cancel(true);
	     super.onPause();
	 }
	 
	 @Override
	public void onBackPressed() {
		Intent i = new Intent(InicioTextoImagen.this, MainActivity.class);	   
        startActivity(i);	
        super.onBackPressed();

    }
}
