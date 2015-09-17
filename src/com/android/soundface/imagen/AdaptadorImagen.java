package com.android.soundface.imagen;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.soundface.R;
import com.android.soundface.bean.Imagen;

public class AdaptadorImagen extends BaseAdapter {
	
	    private LayoutInflater inflador;
	    protected List<Imagen> listaImagenes;
	
	    public AdaptadorImagen(Context contexto, List<Imagen> listaImagenes) {
	          inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	          this.listaImagenes = listaImagenes;
	    }
	
	    public View getView(int posicion, View vistaReciclada, ViewGroup padre) {
	          Imagen imagen = listaImagenes.get(posicion);
	          if (vistaReciclada == null) {
	        	  vistaReciclada = inflador.inflate(R.layout.elemento_selector, null);
	          }
	          TextView titulo = (TextView) vistaReciclada.findViewById(R.id.titulo);
	          
	          String descripcion = imagen.getNombreImagen();
	          descripcion = descripcion.substring(0,descripcion.indexOf("."));
	          
	          titulo.setText(descripcion);
	          
	          ImageView portada = (ImageView) vistaReciclada.findViewById(R.id.portada);
	          Bitmap bitmap = BitmapFactory.decodeByteArray(imagen.getImagen() , 0, imagen.getImagen().length);
	          portada.setImageBitmap(bitmap);
	          
	          portada.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
	          return vistaReciclada;
	    }
	
	 public int getCount() {
	     return listaImagenes.size();
	 }
	
	 public Object getItem(int posicion) {
	     return listaImagenes.get(posicion);
	 }
	
	 public long getItemId(int posicion) {
	     return posicion;
	 }


}