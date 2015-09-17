package com.android.soundface.texto;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.soundface.AyudaListaImagenes;
import com.android.soundface.Constantes;
import com.android.soundface.MainActivity;
import com.android.soundface.R;

public class VerListaImagenes extends Activity{
	
	ListView list;
	List<String> itemname;	
	String[] item;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ver_lista_imagenes_gen);
		
		Map<String,Bitmap> data = loadData();
		
		Iterator<Entry<String, Bitmap>> it = data.entrySet().iterator();
		
		itemname = new ArrayList<String>();
		MainActivity.verlista = new ArrayList<Bitmap>();
		
		int i = 0;
	    while (it.hasNext()) {
	        Map.Entry<String, Bitmap> e = (Map.Entry<String, Bitmap>)it.next();
	        itemname.add(e.getKey());
	        MainActivity.verlista.add(e.getValue());
	        i++;
	    }

	    item = new String[itemname.size()];
	    item = itemname.toArray(item);
		
		CustomListAdapter adapter=new CustomListAdapter(this, item);
		list=(ListView)findViewById(R.id.list);
		list.setAdapter(adapter);
				
		list.setOnItemClickListener(new OnItemClickListener() {
 
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MainActivity.ultimaImagenGenerada = item[+position].replace(".jpg", "");
				Intent i = new Intent(VerListaImagenes.this, VerImagenGenerada.class);	   
		        startActivity(i);	
			}
		});
		
		list.setOnItemLongClickListener(new OnItemLongClickListener() {
			 
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {				
				eliminarImagen(view, VerListaImagenes.this, item[+position]);
				return false;
			}
		});
	}
	
	private Map<String,Bitmap> loadData(){
		
		Map<String,Bitmap> result = new HashMap<String,Bitmap>();
		
		final String appName = getApplicationContext().getString(R.string.app_name);
		final String imagePath = Constantes.IMAGEPATH+ "/" + appName+ "/imgGen";
		
		File folder = new File(imagePath);
		File[] listOfFiles = folder.listFiles();

	    for (int i = 0; i < listOfFiles.length; i++) {
	    	BitmapFactory.Options options = new BitmapFactory.Options();
			//options.inSampleSize = 2;
			Bitmap bmp = BitmapFactory.decodeFile(imagePath +"/"+ listOfFiles[i].getName(), options);
			result.put(listOfFiles[i].getName(),bmp);
	    }
    	
	    return result;
	}
	
	private void deleteImagen(String name){
	
		final String appName = getApplicationContext().getString(R.string.app_name);
		final String imagePath = Constantes.IMAGEPATH+ "/" + appName+ "/imgGen";
		
		File file = new File(imagePath, name);
        file.delete();
	
        MainActivity.ultimaImagenGenerada = "";
	}
	
	private void eliminarImagen(View view, final Context ctx, final String item){
    	LayoutInflater li = LayoutInflater.from(ctx);
		View promptsView = li.inflate(R.layout.delete_producto, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
		alertDialogBuilder.setView(promptsView);

		alertDialogBuilder.setPositiveButton(getString(R.string.eliminar),new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int id) {
			    	deleteImagen(item);
			    	Toast.makeText(ctx, getString(R.string.regeliminado), Toast.LENGTH_SHORT).show();
			    	((Activity)ctx).finish();
			    }
		})		
		.setNegativeButton(getString(R.string.cancelar),new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {			    	
			    	dialog.dismiss();
			    }
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
	       getMenuInflater().inflate(R.menu.menu_lista_imagenes, menu);
	       return true; 
	}

	   

	@Override 
	public boolean onOptionsItemSelected(MenuItem item) {

	       switch (item.getItemId()) {
		       case R.id.ver:
	      			verImagen(null);
	      			break;
	       		case R.id.eliminar:
	       			eliminarImagenesSeleccionadas(null);
	       			break;
	       		case R.id.ayuda:
	       			lanzarAcercaDe(null);
	       			break;
	       }

	       return true;

	}

	private void lanzarAcercaDe(Object object) {
		Intent i = new Intent(this, AyudaListaImagenes.class);	     
	    startActivity(i);
	    
//	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//	    builder.setMessage("Explicar como funciona esta página")
//	            .setTitle("AYUDA")
//	            .setCancelable(false)
//	            .setNeutralButton("Aceptar",
//	                    new DialogInterface.OnClickListener() {
//	                        public void onClick(DialogInterface dialog, int id) {
//	                            dialog.cancel();
//	                        }
//	                    });
//	    AlertDialog alert = builder.create();
//	    alert.show();
	    
		
	}

	private void eliminarImagenesSeleccionadas(Object object) {
		
		if(MainActivity.imagenesBorrar!=null && !MainActivity.imagenesBorrar.isEmpty()){
			new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("")
	        .setMessage(R.string.confirm)
	        .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
	        	@Override
	            public void onClick(DialogInterface dialog, int which) {
	        		List<Integer> datosEliminar= MainActivity.imagenesBorrar;
	        		for (Integer integer : datosEliminar) {			
	        			deleteImagen(item[integer]);			
	        		}
	        		MainActivity.imagenesBorrar = new ArrayList<Integer>();
	        		Toast.makeText( VerListaImagenes.this, getString(R.string.regeliminados), Toast.LENGTH_SHORT).show();
	        		finish();
	        		
	        		Intent i = new Intent(VerListaImagenes.this, VerListaImagenes.class);	   
	                startActivity(i);	
	                VerListaImagenes.this.finish();
	            }
	
	        })
	        .setNegativeButton(R.string.no, null)
	        .show();
		}
        //return true;
		
	}
	
	private void verImagen(Object object) {
		List<Integer> datosEliminar= MainActivity.imagenesBorrar;
		if(datosEliminar.size()>1){
			Toast.makeText( VerListaImagenes.this, getString(R.string.unosolo), Toast.LENGTH_SHORT).show();
		}else if(datosEliminar.size()<1){
			Toast.makeText( VerListaImagenes.this, getString(R.string.nohayselec), Toast.LENGTH_SHORT).show();
		}else{
			MainActivity.ultimaImagenGenerada = item[datosEliminar.get(0)].replace(".jpg", "");
			Intent i = new Intent(VerListaImagenes.this, VerImagenGenerada.class);	   
	        startActivity(i);	
		}	
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		MainActivity.imagenesBorrar = new ArrayList<Integer>();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		MainActivity.imagenesBorrar = new ArrayList<Integer>();
	}
	
//	@Override
//	public void onBackPressed() {
//		Intent i = new Intent(VerListaImagenes.this, InicioTextoImagen.class);	   
//        startActivity(i);	
//        super.onBackPressed();
//
//    }
}