package com.android.soundface.bean;

import java.io.Serializable;

public class Imagen implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idImagen;
	private String nombreImagen;
	private String descImagen;
	private byte[] imagen;
	
	public Imagen(Integer idImagen, String nombreImagen, String descImagen, byte[] imagen) {
		super();
		this.idImagen = idImagen;
		this.nombreImagen = nombreImagen;
		this.descImagen = descImagen;
		this.imagen = imagen;
	}
	

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}


	public Integer getIdImagen() {
		return idImagen;
	}


	public void setIdImagen(Integer idImagen) {
		this.idImagen = idImagen;
	}


	public String getDescImagen() {
		return descImagen;
	}


	public void setDescImagen(String descImagen) {
		this.descImagen = descImagen;
	}


	public byte[] getImagen() {
		return imagen;
	}


	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	
	

}
