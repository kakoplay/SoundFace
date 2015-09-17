package com.android.soundface.db;

import java.util.List;

import com.android.soundface.bean.Imagen;
import com.android.soundface.bean.Sonido;

public interface ArchivosDb {
	
	 //Archivos
	 public void guardarArchivo(String name, byte[] image);
	 public byte[] obtenerArchivo(String name);
	 
	 //Imagenes
	 public void guardarImagen(String name, String descripcion, byte[] image);	
	 public List<Imagen> obtenerTodasImagenes();
	 	 
	 //Sonidos
	 public void guardarSonido(String name, String descripcion, byte[] sound);
	 public List<Sonido> obtenerTodosSonidos();
	 
	 //Imagenes Generadas
	 public long guardarImagenGenerada(String name, String desc, byte[] image);
	 public List<Imagen> obtenerTodasImagenesGeneradas();
	 public Imagen obtenerImagenGenerada(long id);
	 
	 public void closedatabase();
	 public void eliminarImagen(Imagen imagen); 
	 
}