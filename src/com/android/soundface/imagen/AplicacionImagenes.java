package com.android.soundface.imagen;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.soundface.Constantes;
import com.android.soundface.MainActivity;
import com.android.soundface.R;
import com.android.soundface.bean.Imagen;
import com.parse.Parse;
import com.parse.ParseInstallation;

public class AplicacionImagenes extends Application {
	 
    private List<Imagen> listaImagenes;
    private AdaptadorImagen adaptador;

    @Override
    public void onCreate() {
          listaImagenes = new ArrayList<Imagen>();//MainActivity.basedatos.obtenerTodasImagenes();  
          adaptador = new AdaptadorImagen (this, listaImagenes);
          
          Parse.initialize(this, "QHkRN9N34gKgDVBqLCcJRet0cX8dhuZLAM1vjkOn","UvCUuRd1eOqVlyBnUawogTMNbTjbSKvE8sc9GXMT");
          ParseInstallation.getCurrentInstallation().saveInBackground();
          
    }

    public void filldata() {
    	listaImagenes = MainActivity.basedatos.obtenerTodasImagenes();
    	adaptador = new AdaptadorImagen (this, listaImagenes);
    }
    
    public void filldataTextoImagen() {
    	List<Imagen> result = new ArrayList<Imagen>();
		
		final String appName = getApplicationContext().getString(R.string.app_name);
		final String imagePath = Constantes.IMAGEPATH+ "/" + appName+ "/imgGen";
		
		File folder = new File(imagePath);
		File[] listOfFiles = folder.listFiles();

	    for (int i = 0; i < listOfFiles.length; i++) {
	    	
	    	BitmapFactory.Options options = new BitmapFactory.Options();
			//options.inSampleSize = 2;
			Bitmap bmp = BitmapFactory.decodeFile(imagePath +"/"+ listOfFiles[i].getName(), options);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			
			Imagen img = new Imagen(0, listOfFiles[i].getName(), listOfFiles[i].getName(), byteArray);
			result.add(img);
			
			try {
				stream.close();
			} catch (IOException e) {			
				e.printStackTrace();
			}
	    }
    	adaptador = new AdaptadorImagen (this, result);
    }
    
    public AdaptadorImagen getAdaptador() {
          return adaptador;
    }

    public List<Imagen> getListaimagenes() {
          return listaImagenes;
    }
}