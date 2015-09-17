package com.android.soundface.fragment;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

import com.android.soundface.MainActivity;
import com.android.soundface.R;
import com.android.soundface.bean.Imagen;
import com.android.soundface.imagen.AdaptadorImagen;
import com.android.soundface.imagen.AplicacionImagenes;
import com.android.soundface.imagen.InicioImagen;

public class SelectorFragment extends Fragment {
	
   private Activity actividad;
   private GridView gridview;
   private AdaptadorImagen adaptador;
   private List<Imagen> listImagenes;
   private String tipo;
   
   public SelectorFragment(String tipo) {
	   super();
	   this.tipo = tipo;
   }

   @Override
   public void onAttach(Activity actividad) {
      super.onAttach(actividad);
      this.actividad = actividad;
      AplicacionImagenes app = (AplicacionImagenes) actividad.getApplication();
      adaptador = app.getAdaptador();
      listImagenes = app.getListaimagenes();
   }
 
   @Override
   public View onCreateView(LayoutInflater inflador, ViewGroup contenedor,
           Bundle savedInstanceState) {
        
	   View vista = inflador.inflate(R.layout.fragment_imagenes,contenedor, false);
        gridview = (GridView) vista.findViewById(R.id.gridview);
        gridview.setAdapter(adaptador);
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
            	((InicioImagen) actividad).mostrarDetalle((int) id);
            }            
        });
        
        
        gridview.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> padre, final View v, final int posicion, final long id) {
            	
                  AlertDialog.Builder menu = new AlertDialog.Builder(actividad);
                  CharSequence[] opciones = { getString(R.string.seleccionar), getString(R.string.descartar), getString(R.string.eliminar)};
                  menu.setItems(opciones, new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int opcion) {
                                switch (opcion) {
	                                case 0: //Seleccionar
	                                	if(tipo.equals("texto")){
		                        	    	MainActivity.imagenParaTexto = listImagenes.get((int) id);
		                        	    }else{
		                        	    	MainActivity.imagenesParaVideo.add(listImagenes.get((int) id));
		                        	    }	                                	
	                                	dialog.dismiss();
	                                	break;
	                                case 1: //No seleccionar
	                                	if(tipo.equals("texto")){
		                        	    	MainActivity.imagenParaTexto = null;
		                        	    }else{
		                        	    	//MainActivity.imagenesParaVideo.remove(listImagenes.get((int) id));
		                        	    	MainActivity.imagenesParaVideo.clear();
		                        	    }	                                	                          
	                                	dialog.dismiss();
	                                	break;
	                                case 2: //No seleccionar
	                                	MainActivity.basedatos.eliminarImagen(listImagenes.get((int) id));
	                                	dialog.dismiss();
	                                	launchIntent(tipo);
	                                	break;
                                }
                         }
                  });
                  menu.create().show();
                  return true;
            }
        });
        
        
        return vista;
    }
   
    private void launchIntent(String tipo) {
       Intent i = new Intent(this.getActivity(), InicioImagen.class);
       i.putExtra("tipo", tipo);  
       startActivity(i); 
    }
   
}