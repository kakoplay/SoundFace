package com.android.soundface.sonido;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.soundface.ListaSonidos;
import com.android.soundface.R;

public class InicioSonido extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_video);
    }
	
	public void listarImagenes(View view){
	      Intent i = new Intent(this, ListaSonidos.class);	   
//	      if(pref.getBoolean("sonido", true)){
//	    	  soundPool.play(idClick, 1, 1, 0, 0, 1);
//	      }
	      startActivity(i);
	}
}
