package com.android.soundface.bean;

import java.io.Serializable;

public class Sonido implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idSonido;
	private String nombreSonido;
	private String descSonido;
	private byte[] sonido;
	
	private boolean isSelected = false; 
	
	

	public Sonido(Integer idSonido, String nombreSonido, String descSonido, byte[] sonido) {
		super();
		this.idSonido = idSonido;
		this.nombreSonido = nombreSonido;
		this.descSonido = descSonido;
		this.sonido = sonido;
	}

	public String getNombreSonido() {
		return nombreSonido;
	}

	public void setNombreSonido(String nombreSonido) {
		this.nombreSonido = nombreSonido;
	}
	
	public Integer getIdSonido() {
		return idSonido;
	}

	public void setIdSonido(Integer idSonido) {
		this.idSonido = idSonido;
	}

	public String getDescSonido() {
		return descSonido;
	}

	public void setDescSonido(String descSonido) {
		this.descSonido = descSonido;
	}

	public byte[] getSonido() {
		return sonido;
	}

	public void setSonido(byte[] sonido) {
		this.sonido = sonido;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	

}
