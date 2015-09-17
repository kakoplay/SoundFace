package com.android.soundface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bytedeco.javacv.FFmpegFrameRecorder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

//import com.googlecode.javacv.FFmpegFrameRecorder;

public class UtilesVideo {
    ProgressDialog dialog;
    FFmpegFrameRecorder recorder;
    String videoPath;
    
    AssetManager assetManager;
    Context context;
    
    public UtilesVideo(AssetManager aManager, Context ctx) {
		super();
		assetManager = aManager;
		context = ctx;
	}

    
    public void copyFileDb(String path, String filename, byte[] archivo) 
    {
    	FileOutputStream fos = null;
        try 
        {
        	String newFileName = path+"/"+filename;
        	fos=new FileOutputStream(newFileName);

            fos.write(archivo);
            fos.close();
            fos.flush();
        	
        	
        	
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
           
            if(fos!=null){
                try {
                	fos.close();
                } catch (IOException e) {
                	e.printStackTrace();
                }
            }
        }
    }

	public void copyFile(String path, String filename) 
    {
        InputStream in = null;
        OutputStream out = null;
        try 
        {
            in = assetManager.open("sounds/"+filename);
            String newFileName = path+"/"+filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) 
            {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                	 e.printStackTrace();
                }
            }
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                	e.printStackTrace();
                }
            }
        }
    }
	
	public Bitmap generarTextoEnImagen(Bitmap imagen, String upText, String upText2, String downText, String color, String letra, String tam, String colorinterior) {
        try {
        		//Resources resources = context.getResources();
        	    //float scale = resources.getDisplayMetrics().density;
        	    if(tam.equals("Smaller") || tam.startsWith("Más")){
        	    	tam = "X";
        	    }
        	    tam = tam.substring(0,1);
        	    upText = upText.toUpperCase();
        	    upText2 = upText2.toUpperCase();
        	    downText = downText.toUpperCase();
        	  
                android.graphics.Bitmap.Config bitmapConfig = imagen.getConfig();
                // set default bitmap config if none
                if(bitmapConfig == null) {
                  bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
                }
                // resource bitmaps are imutable,
                // so we need to convert it to mutable one
                imagen = imagen.copy(bitmapConfig, true);
                
                Typeface tf = Typeface.create(letra,Typeface.BOLD);
                
                Canvas canvas = new Canvas(imagen);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                
                //TIPO LETRA
                paint.setTypeface(tf);
                // COLOR
                switch (color) {
					case "Negro":
						paint.setColor(Color.BLACK);	
						break;
					case "Blanco":
						paint.setColor(Color.WHITE);	
						break;
					case "Rojo":
						paint.setColor(Color.RED);	
						break;
					case "Azul":
						paint.setColor(Color.BLUE);	
						break;
					case "Verde":
						paint.setColor(Color.GREEN);	
						break;
					case "Amarillo":
						paint.setColor(Color.YELLOW);	
						break;
					case "Black":
						paint.setColor(Color.BLACK);	
						break;
					case "White":
						paint.setColor(Color.WHITE);	
						break;
					case "Red":
						paint.setColor(Color.RED);	
						break;
					case "Blue":
						paint.setColor(Color.BLUE);	
						break;
					case "Green":
						paint.setColor(Color.GREEN);	
						break;
					case "Yellow":
						paint.setColor(Color.YELLOW);	
						break;
					default:
						paint.setColor(Color.BLACK);
						break;
				}
                
                int colorint = Color.WHITE;
                // COLOR INTERIOR
                switch (colorinterior) {
					case "Negro":
						colorint = Color.BLACK;	
						break;
					case "Blanco":
						colorint = Color.WHITE;	
						break;
					case "Rojo":
						colorint = Color.RED;	
						break;
					case "Azul":
						colorint = Color.BLUE;	
						break;
					case "Verde":
						colorint = Color.GREEN;	
						break;
					case "Amarillo":
						colorint = Color.YELLOW;	
						break;
					case "Black":
						colorint = Color.BLACK;	
						break;
					case "White":
						colorint = Color.WHITE;	
						break;
					case "Red":
						colorint = Color.RED;	
						break;
					case "Blue":
						colorint = Color.BLUE;	
						break;
					case "Green":
						colorint = Color.GREEN;	
						break;
					case "Yellow":
						colorint = Color.YELLOW;	
						break;
					default:
						colorint = Color.WHITE;
						break;
						
				}
                
                switch (tam) {
					case "P":
						paint.setTextSize(imagen.getHeight()/10);	
						break;
					case "S":
						paint.setTextSize(imagen.getHeight()/10);	
						break;
					case "N":
						paint.setTextSize(imagen.getHeight()/9);	
						break;
					case "G":
						paint.setTextSize(imagen.getHeight()/8);	
						break;	
					case "L":
						paint.setTextSize(imagen.getHeight()/8);	
						break;
					case "X":
						paint.setTextSize(imagen.getHeight()/12);	
						break;
					default:
						paint.setTextSize(imagen.getHeight()/9);
						break;
				}  
                // TAMAÑO
                //paint.setTextSize(imagen.getHeight()/9);
                //paint.setTextSize(Integer.parseInt(tam));
                // ALIGMENT
                paint.setTextAlign(Paint.Align.CENTER);
                
                // SOMBRA
                //paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(3);
                
                // the display area.
                Rect areaRect = new Rect(0, 0, imagen.getWidth(), imagen.getHeight());
                // PINTA UPPER TEXT
                Rect bounds = new Rect(areaRect);
                // measure text width
                int measure = (int) paint.measureText(upText, 0, upText.length());
                // measure text height
                //bounds.bottom = (int) (paint.descent() - paint.ascent());
                
                bounds.left = (int) ((areaRect.width() - measure) / 2.0f);
                //bounds.top += (areaRect.height() - bounds.bottom) / 2.0f;
                
                int xPos = (canvas.getWidth() / 2);
                
                canvas.drawText(upText, xPos , imagen.getHeight()/8, paint);
                if(!upText2.equals(""))
                	canvas.drawText(upText2, xPos , imagen.getHeight()/8 + paint.getTextSize() + 2 , paint);
                if(!downText.equals(""))
                	canvas.drawText(downText, xPos, (imagen.getHeight() - imagen.getHeight()/10) , paint);
                
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(colorint);
                
                canvas.drawText(upText, xPos , imagen.getHeight()/8 , paint);
                if(!upText2.equals(""))
                	canvas.drawText(upText2, xPos , imagen.getHeight()/8  + paint.getTextSize() + 2, paint);
                if(!downText.equals(""))
                	canvas.drawText(downText, xPos, (imagen.getHeight() - imagen.getHeight()/10), paint);
                
                return imagen;
                
        } catch (Exception e) {          
            return null;
        }

      }
	    
   
    
    
    public void saveBitMap(Bitmap res, String file) {
    	
	    try {
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			res.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

			//you can create a new file name "test.jpg" in sdcard folder.
			File f = new File(file);
			f.createNewFile();
			//write the bytes in file
			FileOutputStream fo = new FileOutputStream(f);
			fo.write(bytes.toByteArray());

			// remember close de FileOutput
			fo.close();
			bytes.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


	public void guardarImagenGenerada(String titulo, Bitmap resultado) throws Exception{
		
		final String appName = context.getString(R.string.app_name);
		final String imagePath = Constantes.IMAGEPATH+ "/" + appName+ "/imgGen";
		File imageDir = new File(imagePath);
        
        if (!imageDir.isDirectory() && !imageDir.mkdirs()) {
        	dialog.setMessage(context.getString(R.string.errorsave));
        }
        
        saveBitMap(resultado, imagePath +"/"+ titulo +".jpg");
        
	}


	public Bitmap obtenerImagenGenerada(String ultimaImagenGenerada) {
		final String appName = context.getString(R.string.app_name);
		final String imagePath = Constantes.IMAGEPATH+ "/" + appName+ "/imgGen";
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		//options.inSampleSize = 2;
		final Bitmap b = BitmapFactory.decodeFile(imagePath +"/"+ ultimaImagenGenerada+".jpg", options);

		return b;
        
	}
	
    

}