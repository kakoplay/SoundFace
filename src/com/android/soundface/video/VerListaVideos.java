package com.android.soundface.video;

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
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

public class VerListaVideos extends Activity{
	
	ListView list;
	List<String> itemname;	
	String[] item;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ver_lista_videos_gen);
		
		Map<String,Bitmap> data = loadData();
		
		Iterator<Entry<String, Bitmap>> it = data.entrySet().iterator();
		
		itemname = new ArrayList<String>();
		MainActivity.verlistaVideo = new ArrayList<Bitmap>();
		
		int i = 0;
	    while (it.hasNext()) {
	        Map.Entry<String, Bitmap> e = (Map.Entry<String, Bitmap>)it.next();
	        itemname.add(e.getKey());
	        MainActivity.verlistaVideo.add(e.getValue());
	        i++;
	    }

	    item = new String[itemname.size()];
	    item = itemname.toArray(item);
		
		CustomListAdapterVideo adapter=new CustomListAdapterVideo(this, item);
		list=(ListView)findViewById(R.id.list);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {
 
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				MainActivity.rutaUltimoVideoGenerado = Constantes.VIDEOPATH + "/" + item[+position];
				Intent i = new Intent(VerListaVideos.this, VerVideoGenerado.class);	   
		        startActivity(i);
//		        finish();
				
			}
		});
		
		list.setOnItemLongClickListener(new OnItemLongClickListener() {
			 
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				eliminarVideo(view, VerListaVideos.this, item[+position]);
				return true;
			}
		});
	}
	
	private Map<String,Bitmap> loadData(){
		
		Map<String,Bitmap> result = new HashMap<String,Bitmap>();
		
		File f = Environment.getExternalStorageDirectory();
        String path = f.getAbsolutePath() + "/DCIM/Camera";
       
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

	    for (int i = 0; i < listOfFiles.length; i++) {
	    	if(listOfFiles[i].getName().startsWith("Video_")){
	    		Bitmap thumb = ThumbnailUtils.createVideoThumbnail(path+"/"+ listOfFiles[i].getName(),MediaStore.Images.Thumbnails.MINI_KIND);
	    		result.put(listOfFiles[i].getName(),thumb);
	    	}
	    }
    	
	    return result;
	}
	
	private void deleteVideo(String name){
		
		final String imagePath = Constantes.VIDEOPATH;//+ "/" + name;
		
		File file = new File(imagePath, name);
        file.delete();
	
        MainActivity.rutaUltimoVideoGenerado = "";
	}
	
	
	private void eliminarVideo(View view, final Context ctx, final String item){
    	LayoutInflater li = LayoutInflater.from(ctx);
		View promptsView = li.inflate(R.layout.delete_producto, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
		alertDialogBuilder.setView(promptsView);

		alertDialogBuilder.setPositiveButton("Eliminar",new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int id) {
			    	deleteVideo(item);
			    	Toast.makeText(ctx, "Registro eliminado", Toast.LENGTH_SHORT).show();
			    	((Activity)ctx).finish();
			    }
		})		
		.setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
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
	      			verVideo(null);
	      			break;
	       		case R.id.eliminar:
	       			eliminarVideosSeleccionados(null);
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
		
	}

	private void eliminarVideosSeleccionados(Object object) {
		
		if(MainActivity.videosBorrar!=null && !MainActivity.videosBorrar.isEmpty()){
			new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("")
	        .setMessage(R.string.confirm)
	        .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
	        	@Override
	            public void onClick(DialogInterface dialog, int which) {
	        		List<Integer> datosEliminar= MainActivity.videosBorrar;
	        		for (Integer integer : datosEliminar) {			
	        			deleteVideo(item[integer]);			
	        		}
	        		MainActivity.videosBorrar = new ArrayList<Integer>();
	        		Toast.makeText( VerListaVideos.this, getString(R.string.regeliminados), Toast.LENGTH_SHORT).show();
	        		finish();
	        		
	        		Intent i = new Intent(VerListaVideos.this, VerListaVideos.class);	   
	                startActivity(i);
	                VerListaVideos.this.finish();    
	            }
	
	        })
	        .setNegativeButton(R.string.no, null)
	        .show();
		}
		
		
			
	}
	
	private void verVideo(Object object) {
		List<Integer> datosEliminar= MainActivity.videosBorrar;
		if(datosEliminar.size()>1){
			Toast.makeText( VerListaVideos.this, getString(R.string.unosolo), Toast.LENGTH_SHORT).show();
		}else if(datosEliminar.size()<1){
			Toast.makeText( VerListaVideos.this, getString(R.string.nohayselec), Toast.LENGTH_SHORT).show();
		}else{
			MainActivity.rutaUltimoVideoGenerado = Constantes.VIDEOPATH + "/" + item[datosEliminar.get(0)];
			Intent i = new Intent(VerListaVideos.this, VerVideoGenerado.class);	   
	        startActivity(i);	
		}	
	}
	
	
	@Override
	protected void onStop() {
		super.onStop();
		MainActivity.videosBorrar = new ArrayList<Integer>();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		MainActivity.videosBorrar = new ArrayList<Integer>();
	}
	
//	@Override
//	public void onBackPressed() {
//		Intent i = new Intent(VerListaVideos.this, InicioVideo.class);	   
//        startActivity(i);	
//        super.onBackPressed();
//    }
	
}