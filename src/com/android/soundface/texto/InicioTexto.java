package com.android.soundface.texto;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.soundface.MainActivity;
import com.android.soundface.R;

public class InicioTexto extends Activity {
	
	EditText titulo;
	private static String TITULOID = "titulo";
	
	SharedPreferences preferencias;
	Integer id;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_texto);
        
        titulo = (EditText) findViewById(R.id.titulo);
        
//      SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
//    	String currentDateandTime = sdf.format(new Date());
    	
    	preferencias = this.getSharedPreferences(TITULOID, Context.MODE_PRIVATE);
    	String s = preferencias.getString("idtitulo", "0");
    	id = Integer.parseInt(s)+1;
    	
        titulo.setText("Imagen_"+id);
        
        
    }
	
	public void okText(View view){
		
		
		if(titulo==null || titulo.equals("")){
			Toast.makeText(this, getString(R.string.tituloobligatorio), Toast.LENGTH_SHORT).show();
		}else{
		
			EditText textoSup = (EditText) findViewById(R.id.textoSup);
			EditText textoSup2 = (EditText) findViewById(R.id.textoSup2);
			EditText textoInf = (EditText) findViewById(R.id.textoInf);
			Spinner spinner = (Spinner) findViewById(R.id.color);
			Spinner letra = (Spinner) findViewById(R.id.letra);
			Spinner tam = (Spinner) findViewById(R.id.tam);
			Spinner colorinterior = (Spinner) findViewById(R.id.colorinterior);
			
			List<String> opciones = new ArrayList<String>();
			opciones.add(titulo.getText().toString());
			opciones.add(textoSup.getText().toString());
			opciones.add(textoSup2.getText().toString());
			opciones.add(textoInf.getText().toString());
			opciones.add(String.valueOf(spinner.getSelectedItem()));
			opciones.add(String.valueOf(letra.getSelectedItem()));
			opciones.add(String.valueOf(tam.getSelectedItem()));
			opciones.add(String.valueOf(colorinterior.getSelectedItem()));
			
			MainActivity.opcionesParaTexto = opciones;
			
			SharedPreferences.Editor editor = preferencias.edit();
	        editor.putString("idtitulo", id+"");
	        editor.commit();
			
			finish();
		}
		
	}

}
