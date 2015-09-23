package com.android.soundface.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import com.android.soundface.R;
import com.android.soundface.bean.Imagen;
import com.android.soundface.imagen.AplicacionImagenes;

public class DetalleFragment extends Fragment {
    
	public static String ARG_ID_LIBRO = "id_libro";
	public static String TIPO_ACCION = "tipoAccion";
    MediaPlayer mediaPlayer;
    MediaController mediaController;
    static String tipoAccion;
    
    int idSeleccionado;

    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor,
                 Bundle savedInstanceState) {
          View vista = inflador.inflate(R.layout.fragment_detalle, contenedor, false);
          Bundle args = getArguments();
          if (args != null) {
                 int position = args.getInt(ARG_ID_LIBRO);
                 String tipoAccion = args.getString(TIPO_ACCION);
                 mostrarDetalleImagen(position, vista, tipoAccion);
          } else {
        	  	 mostrarDetalleImagen(0, vista, "");
          }
          return vista;
    }

    private void mostrarDetalleImagen(int id, View vista, String tipoAccion) {
    	
    	  //this.tipoAccion = tipoAccion;
    	
    	  AplicacionImagenes app = (AplicacionImagenes) getActivity().getApplication();
    	  //app.filldata();
          Imagen imagen = app.getListaimagenes().get(id);
          
          String descripcion = imagen.getDescImagen();
          descripcion = descripcion.substring(0,descripcion.indexOf("."));
          
          ((TextView) vista.findViewById(R.id.titulo)).setText(descripcion);
          //((TextView) vista.findViewById(R.id.autor)).setText(imagen.getDescImagen());
          
          ImageView imagenPrincipal = (ImageView) vista.findViewById(R.id.portada);
          
          Imagen imagenMostrar = app.getListaimagenes().get(id);
          Bitmap bitmap = BitmapFactory.decodeByteArray(imagenMostrar.getImagen() , 0, imagenMostrar.getImagen().length);
          imagenPrincipal.setImageBitmap(bitmap);
          
          idSeleccionado = id;
          
          //vista.setOnTouchListener(this);
          
    }

    public void mostrarDetalleImagen(int id, String tipoAccion) {
    	mostrarDetalleImagen(id, getView(), tipoAccion);
    }
   
//    @Override
//	public boolean onTouch(View v, MotionEvent event) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//    
   
    
    

//    @Override
//    public boolean onTouch(View vista, MotionEvent evento) {
////          mediaController.show();
////          return false;
//	    	 AlertDialog.Builder menu = new AlertDialog.Builder(getActivity());
//	         CharSequence[] opciones = { "Seleccionar", "No seleccionar"};
//	         menu.setItems(opciones, new DialogInterface.OnClickListener() {
//	                public void onClick(DialogInterface dialog, int opcion) {
//	                	   AplicacionImagenes app = (AplicacionImagenes) getActivity().getApplication();
//	                       switch (opcion) {
//	                           case 0: //Seleccionar
//	                        	    if(DetalleFragment.tipoAccion.equals("texto")){
//	                        	    	MainActivity.imagenParaTexto = app.getListaimagenes().get(idSeleccionado);
//	                        	    }else{
//	                        	    	MainActivity.imagenesParaVideo.add(app.getListaimagenes().get(idSeleccionado));
//	                        	    }
//	                           		break;
//	                           case 1: //No seleccionar
//	                        	   	if(DetalleFragment.tipoAccion.equals("texto")){
//	                        	    	MainActivity.imagenParaTexto = null;
//	                        	    }else{
//	                        	    	MainActivity.imagenesParaVideo.remove(app.getListaimagenes().get(idSeleccionado));
//	                        	    }                          
//	                           		break;
//	                       }
//	                       dialog.dismiss();
//	                }
//	         });
//	         menu.create().show();
//	         return true;
//    }



}