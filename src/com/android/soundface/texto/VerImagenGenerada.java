package com.android.soundface.texto;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.soundface.Constantes;
import com.android.soundface.MainActivity;
import com.android.soundface.R;
import com.android.soundface.UtilesVideo;

public class VerImagenGenerada extends Activity{
	
	private ImageView imageview;
	private Bitmap img = null;
	private Bitmap imgGrises = null;
	private Bitmap imgSepia = null;
	private Bitmap imgSave = null;
	
	static {
        System.loadLibrary("procesadodeimagen");
    }
	public native void convertirGrises(Bitmap bitmapIn,Bitmap bitmapOut);
	public native void convertirSepia(Bitmap bitmapIn,Bitmap bitmapOut);
	    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_imagen_generada);
        
        imageview = (ImageView) findViewById(R.id.imagenDisplay);
        
        UtilesVideo utV = new UtilesVideo(getAssets(), getApplicationContext()); 
        img = utV.obtenerImagenGenerada(MainActivity.ultimaImagenGenerada);
        
        if(img!=null){
        	imageview.setImageBitmap(img);        	
        }
        
        
        imageview.setOnClickListener(new View.OnClickListener() {
        	
        	boolean isImageFitToScreen;
        	
            @Override
            public void onClick(View v) {
                if(isImageFitToScreen) {
                    isImageFitToScreen=false;
                    imageview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    imageview.setAdjustViewBounds(true);
                }else{
                    isImageFitToScreen=true;
                    imageview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    imageview.setScaleType(ImageView.ScaleType.FIT_XY);
                    Toast.makeText(VerImagenGenerada.this, getString(R.string.imagenguardada), Toast.LENGTH_SHORT).show();
                }
            }
        });
                
    }
	
	public void verCompleto(View v) {        
		imageview.setImageBitmap(img);
		imgSave = img;
    }
	
	public void resetImagen(View v) {        
		imageview.setImageBitmap(img);
		imgSave = img;
    }

    public void convertirGris(View v) {       
        imgGrises = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Config.ARGB_8888);
        convertirGrises(img , imgGrises);
        imageview.setImageBitmap(imgGrises);
        imgSave = imgGrises;
    }
    
    public void convertirSep(View v) {       
        imgSepia = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Config.ARGB_8888);
        convertirSepia(img , imgSepia);
        imageview.setImageBitmap(imgSepia);
        imgSave = imgSepia;
    }
    
    public void saveChanges(View v) {       
    	String nameImagen = MainActivity.ultimaImagenGenerada;
    	try {
		    final String appName = VerImagenGenerada.this.getString(R.string.app_name);
			final String imagePath = Constantes.IMAGEPATH+ "/" + appName+ "/imgGen";
	
			File file = new File(imagePath+"/"+ nameImagen+".jpg");
			file.delete();		
					
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
	     	String currentDateandTime = sdf.format(new Date());
	     	
	     	
			UtilesVideo utV = new UtilesVideo(getAssets(), getApplicationContext()); 
		
			utV.guardarImagenGenerada(nameImagen+"_"+currentDateandTime , imgSave);
			
			 MainActivity.ultimaImagenGenerada = nameImagen+"_"+currentDateandTime;
			 
			 Toast.makeText(VerImagenGenerada.this, getString(R.string.imagenguardada), Toast.LENGTH_SHORT).show();
		
    	} catch (Exception e) {			
			e.printStackTrace();
		}
		
    }
    
    
	
    public void dibujarImagen(View view){
		
    	Intent i = new Intent(this, TextLineView.class);
	    startActivity(i);
	
	}
    
	public void compartirImagen(View view){
		
		String nameImagen = MainActivity.ultimaImagenGenerada;
		
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
	    shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
	    shareIntent.setType("image/*");

	    final String appName = VerImagenGenerada.this.getString(R.string.app_name);
		final String imagePath = Constantes.IMAGEPATH+ "/" + appName+ "/imgGen";
	    // For a file in shared storage.  For data in private storage, use a ContentProvider.
		File file = new File(imagePath+"/"+ nameImagen+".jpg");
	    Uri uri = Uri.fromFile(file);
	    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		
		startActivity(Intent.createChooser(shareIntent, getString(R.string.compartir)));
	
	}
	
	public void eliminarImagen(View view){
		
		final String appName = getApplicationContext().getString(R.string.app_name);
		final String imagePath = Constantes.IMAGEPATH+ "/" + appName+ "/imgGen";
		
		File file = new File(imagePath, MainActivity.ultimaImagenGenerada+".jpg");
        file.delete();
        
        Toast.makeText( VerImagenGenerada.this, getString(R.string.imagendescartada), Toast.LENGTH_SHORT).show();
        
        MainActivity.ultimaImagenGenerada = "";
        finish();
	}
	
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
	       getMenuInflater().inflate(R.menu.menu_ver_imagen, menu);
	       return true; 
	}

	   

	@Override 
	public boolean onOptionsItemSelected(MenuItem item) {

	       switch (item.getItemId()) {
		       case R.id.grises:
		    	    convertirGris(null);
	      			break;
	       		case R.id.sepia:
	       			convertirSep(null);
	       			break;
	       		case R.id.reset:
	       			resetImagen(null);
	       			break;
	       		case R.id.saveChanges:
	       			saveChanges(null);
	       			break;
	       		case R.id.share:
	       			compartirImagen(null);
	       			break;
	       		case R.id.dibujar:
	       			dibujarImagen(null);
	       			break;
	       }

	       return true;

	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, VerListaImagenes.class);	   
        startActivity(i);	
        super.onBackPressed();
    }
	
}
