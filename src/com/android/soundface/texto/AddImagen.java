package com.android.soundface.texto;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.android.soundface.MainActivity;
import com.android.soundface.R;
import com.android.soundface.bean.Imagen;

public class AddImagen extends  Activity {

	private ImageView imageview = null;
	private ProgressDialog dialog;
	private String tipoAccion;
	
	private Bitmap change;
	private Uri urichange;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.add_imagen);
	      
	      Bundle bundle = getIntent().getExtras();
	      tipoAccion = bundle.getString("tipo");
	      
	      imageview = (ImageView) findViewById(R.id.ivDisplay);
	   
	}
	
	public void camera(View view){
		Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(takePicture, 0);
	}
	
	public void gallery(View view){
		Intent pickPhoto = new Intent(Intent.ACTION_PICK,
	           android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(pickPhoto , 1);
	}
	
	public void seleccionar(View view){
		dialog = new ProgressDialog(AddImagen.this);
        dialog.setMessage(getString(R.string.selectimage));
        dialog.setCancelable(false);
        dialog.show();
        
        if(tipoAccion.equals("texto")){
        	MainActivity.imagenParaTexto = viewToByte();
	    }else{
	    	MainActivity.imagenesParaVideo.add(viewToByte());
	    }
				
		dialog.dismiss();
		
		finish();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
		switch(requestCode) {
			case 0:
			    if(resultCode == RESULT_OK){
			        Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data"); 
		            imageview.setImageBitmap(photo);
		            change = photo;
			    }	
			    break; 
			case 1:
			    if(resultCode == RESULT_OK){  
			        Uri selectedImage = imageReturnedIntent.getData();
			        int orientacion = getOrientation(getApplicationContext(),selectedImage);
			        if(orientacion==90){
			        	InputStream imageStream;
		                try {
		                    imageStream = getContentResolver().openInputStream(selectedImage);
		                    BitmapFactory.Options options=new BitmapFactory.Options();
		                    //options.inSampleSize = 8;
		                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream, null, options );
		                    
		                    Matrix matrix = new Matrix();
				        	matrix.postRotate(90);
				        	Bitmap rotatedBitmap = Bitmap.createBitmap(yourSelectedImage, 0, 0, yourSelectedImage.getWidth(), yourSelectedImage.getHeight(), matrix, true);
				        	imageview.setImageBitmap(rotatedBitmap);
				        	change = rotatedBitmap;
		                } catch (FileNotFoundException e) {		                    
		                    e.printStackTrace();		                    
		                }
			        }else{
			        	imageview.setImageURI(selectedImage);
			        	urichange = selectedImage;
			        }
			    }
			    break;
		}
	}
	
	public Imagen viewToByte(){
		try {			
			Bitmap bitmap = ((BitmapDrawable)imageview.getDrawable()).getBitmap();
			
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);			
			byte[] image=stream.toByteArray();
			
			Imagen img = new Imagen(0, "", "",image);
			
			try {
				stream.close();
			} catch (IOException e) {			
				e.printStackTrace();
			}
			
			return img;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static int getOrientation(Context context, Uri photoUri) {
	    Cursor cursor = context.getContentResolver().query(photoUri,
	            new String[] { MediaStore.Images.ImageColumns.ORIENTATION },
	            null, null, null);

	    try {
	        if (cursor.moveToFirst()) {
	            return cursor.getInt(0);
	        } else {
	            return -1;
	        }
	    } finally {
	        cursor.close();
	    }
	}
	
//	@Override
//	 public void onBackPressed() {
//		Intent i = new Intent(this, InicioVideo.class);	   
//	    startActivity(i);	
//	    super.onBackPressed();
//	 }
	
	
//	@Override
//	public void onSaveInstanceState(Bundle savedInstanceState) {
//	  super.onSaveInstanceState(savedInstanceState);
//	  if(change != null)
//		  savedInstanceState.putParcelable("bitmap", change);
//	  if(urichange != null)
//		  savedInstanceState.putParcelable("uri", urichange);
//	}
//	
//	@Override
//	public void onRestoreInstanceState(Bundle savedInstanceState) {
//	  super.onRestoreInstanceState(savedInstanceState);
//
//	  if(change != null){
//		  Bitmap bm = savedInstanceState.getParcelable("bitmap");
//		  imageview.setImageBitmap(bm);
//	  }
//	  if(urichange != null){
//		  Uri url = savedInstanceState.getParcelable("uri");
//		  imageview.setImageURI(url);
//	  }
//	}
	
}
