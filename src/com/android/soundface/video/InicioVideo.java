package com.android.soundface.video;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.view.View;
import android.widget.Toast;

import com.android.soundface.Constantes;
import com.android.soundface.ListaSonidos;
import com.android.soundface.MainActivity;
import com.android.soundface.R;
import com.android.soundface.UtilesVideo;
import com.android.soundface.bean.Imagen;
import com.android.soundface.imagen.InicioImagen;
import com.android.soundface.texto.AddImagen;
//import com.googlecode.javacv.FFmpegFrameGrabber;
//import com.googlecode.javacv.FFmpegFrameRecorder;
//import com.googlecode.javacv.Frame;
//import com.googlecode.javacv.FrameGrabber;

public class InicioVideo extends Activity {
	
	DoVideo tarea;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_video);
    }
	
	public void listarImagenes(View view){
	      Intent i = new Intent(this, InicioImagen.class);	   
	      i.putExtra("tipo", "video");
//	      if(pref.getBoolean("sonido", true)){
//	    	  soundPool.play(idClick, 1, 1, 0, 0, 1);
//	      }
	      startActivity(i);
	}
	
	public void addImagen(View view){
	      Intent i = new Intent(this, AddImagen.class);	  
	      i.putExtra("tipo", "video");
//	      if(pref.getBoolean("sonido", true)){
//	    	  soundPool.play(idClick, 1, 1, 0, 0, 1);
//	      }
	      startActivity(i);
	}
	
	public void listarSonidos(View view){	      
		  Intent i = new Intent(this, ListaSonidos.class);	   
//	      if(pref.getBoolean("sonido", true)){
//	    	  soundPool.play(idClick, 1, 1, 0, 0, 1);
//	      }
	      startActivity(i);
	}
	
	public void verVideo(View view){	      
		if(MainActivity.rutaUltimoVideoGenerado==null){
			Toast.makeText(InicioVideo.this, "Debe generar un video previamente", Toast.LENGTH_LONG).show();
		}else{
//			Intent intent = new Intent(Intent.ACTION_VIEW); 
//			intent.setDataAndType(Uri.parse(MainActivity.rutaUltimoVideoGenerado), "video/mp4");
//			startActivity(intent);   
			Intent i = new Intent(this, VerVideoGenerado.class);	   
	        startActivity(i);	
		 	
		}
	}
	
	
	
	public void verListaVideo(View view){      
		
		 Intent i = new Intent(this, VerListaVideos.class);	   
//		 if(pref.getBoolean("sonido", true)){
//		  	  soundPool.play(idClick, 1, 1, 0, 0, 1);
//		 }
		 startActivity(i);		
	}
	
	
	public void generateVideo(View view){
		
		  if(MainActivity.sonidoParaVideo==null && MainActivity.sonidoParaVideoUri==null){
			  Toast.makeText(InicioVideo.this, getString(R.string.debeselectsonido), Toast.LENGTH_LONG).show();
		  }else if(MainActivity.imagenesParaVideo.isEmpty()){
			  Toast.makeText(InicioVideo.this, getString(R.string.debeselectimagen), Toast.LENGTH_LONG).show();
		  }else{
			  tarea = new DoVideo();
			  tarea.execute(new ArrayList<String>());
		  }
	}
	
	public class DoVideo extends AsyncTask<List<String>, Void, String> {
        ProgressDialog dialog;
        FFmpegFrameRecorder recorder;
        String videoPath;
        
        UtilesVideo utV = new UtilesVideo(getAssets(), getApplicationContext()); 
        
        protected void onPreExecute() {
            dialog = new ProgressDialog(InicioVideo.this);
            dialog.setMessage(getString(R.string.waitvideo));
            dialog.setCancelable(false);
            dialog.show();
        };
               

        @Override
        protected String doInBackground(List<String>... params) {
        	String result = "OK";
        	final String appName = getString(R.string.app_name);
        	String sonido = "";
        	Imagen imagenMostrar = null;
        	             
            final String imagePath = Constantes.IMAGEPATH+ "/" + appName;
            final String soundPath = Constantes.MUSICPATH+ "/" + appName;
            
            //Guardo las imagenes para usarlas luego
            File imageDir = new File(imagePath);
            File soundDir = new File(soundPath);
            if (!imageDir.isDirectory() && !imageDir.mkdirs()) {
            	dialog.setMessage(getString(R.string.errordirectorio));
                return null;
            }
            if (!soundDir.isDirectory() && !soundDir.mkdirs()) {
            	dialog.setMessage(getString(R.string.errordirectorio));
                return null;
            }
            try {          
            	
            	String audio;
            	if(MainActivity.sonidoParaVideo==null){
            		audio = MainActivity.sonidoParaVideoUri;            				
            	}else{
            		//Sonido
                	sonido = "ini_"+ MainActivity.sonidoParaVideo.getNombreSonido();
                	utV.copyFile(soundPath,sonido);
                	audio = soundPath+"/"+sonido;
            	}
            	           	
            	
            	//Imagen
            	imagenMostrar = MainActivity.imagenesParaVideo.get(0);
            	if(imagenMostrar.getNombreImagen().equals("")){
            		imagenMostrar.setNombreImagen("temp.jpg");
            		imagenMostrar.setDescImagen("temp.jpg");
            	}
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagenMostrar.getImagen() , 0, imagenMostrar.getImagen().length);
            	utV.saveBitMap(bitmap, imagePath +"/"+ imagenMostrar.getNombreImagen());
            	
            	//Video
            	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
            	String currentDateandTime = sdf.format(new Date());
	            videoPath = Constantes.VIDEOPATH + "/" + "Video_"+currentDateandTime+".3gp";
	          
	        	String img = imagePath+"/"+imagenMostrar.getNombreImagen();
	        	
	        	
//	        	MediaPlayer mp = MediaPlayer.create(getApplicationContext(), Uri.parse(audio));
//	            int duration = mp.getDuration();
	                       	
	        	FrameGrabber grabber2 = new FFmpegFrameGrabber(audio);
	        	FrameGrabber grabber1 = new FFmpegFrameGrabber(img);
	        	//FrameGrabber grabber3 = new FFmpegFrameGrabber(img2);
	             
	    		grabber1.start();
	    		//grabber3.start();
	            grabber2.start();

	            recorder = new FFmpegFrameRecorder(videoPath,  grabber1.getImageWidth(), grabber1.getImageHeight(),2);
	            
	            recorder.setFormat("mp4");
	            recorder.setVideoQuality(1);
	            recorder.setFrameRate(grabber1.getFrameRate());
	            recorder.setSampleRate(grabber2.getSampleRate());
	            recorder.start();

	            Frame frame1, frame2 = null;
	            //Frame frame3 = null;

	            //while ((frame1 = grabber1.grabFrame()) != null || (frame2 = grabber2.grabFrame()) != null
	            //		|| (frame3 = grabber3.grabFrame()) != null) {
	            while ((frame1 = grabber1.grabFrame()) != null || (frame2 = grabber2.grabFrame()) != null) {
	                recorder.record(frame1);
	                //recorder.record(frame3); 
	                recorder.record(frame2); 
	                 
	            } 

	            recorder.stop(); 
	            grabber1.stop(); 
	            grabber2.stop();
	            //grabber3.stop(); 

	            File file = new File(imagePath, imagenMostrar.getNombreImagen());
	            file.delete();
	            file = new File(soundPath, sonido);
	            file.delete();
	            
	            MainActivity.sonidoParaVideo = null;
	            MainActivity.sonidoParaVideoUri = null;
            	MainActivity.imagenesParaVideo = new ArrayList<Imagen>();
            	
            	MainActivity.rutaUltimoVideoGenerado = videoPath;

            } catch (Exception e) {
                e.printStackTrace();
                result = "NOOK";
            } finally{
            	try {
					File file = new File(imagePath, imagenMostrar.getDescImagen());
					file.delete();
					file = new File(soundPath, sonido);
					file.delete();
				} catch (Exception e) {				
					e.printStackTrace();
				}
            }
            dialog.dismiss();
//            if(tarea!=null)
//    	   		tarea.cancel(true);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            if(result.equals("OK")){
            	Toast.makeText(InicioVideo.this, getString(R.string.videook), Toast.LENGTH_SHORT).show();
            }else{
            	Toast.makeText(InicioVideo.this, getString(R.string.videonook), Toast.LENGTH_SHORT).show();
            }
         
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
	 
//	 @Override
//	 public void onBackPressed() {
//		Intent i = new Intent(InicioVideo.this, MainActivity.class);	   
//	    startActivity(i);	
//	    super.onBackPressed();
//	 }
}
