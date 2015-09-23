package com.android.soundface.imagen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.android.soundface.MainActivity;
import com.android.soundface.R;
import com.android.soundface.fragment.DetalleFragment;
import com.android.soundface.fragment.SelectorFragment;

public class InicioImagen extends FragmentActivity {
		
	String tipoAccion;
	int idSeleccionado;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      AplicacionImagenes app = (AplicacionImagenes) getApplication();
	      app.filldata();	      
	      setContentView(R.layout.imagenes);
	      
	      Bundle bundle = getIntent().getExtras();
	      tipoAccion = bundle.getString("tipo");
	      
	      if (findViewById(R.id.contenedor_pequeno) != null) {
	    	   SelectorFragment primerFragment = new SelectorFragment(tipoAccion);
	    	   getSupportFragmentManager().beginTransaction().add(R.id.contenedor_pequeno, primerFragment).commit();
	      }
	}
	
	public void mostrarDetalle(int id) {
	       DetalleFragment detalleFragment = (DetalleFragment)
	          getSupportFragmentManager().findFragmentById(R.id.detalle_fragment);
	       if (detalleFragment != null) {
	             detalleFragment.mostrarDetalleImagen(id, tipoAccion);
	       } else {
	             DetalleFragment nuevoFragment = new DetalleFragment();
	             Bundle args = new Bundle();
	             args.putInt(DetalleFragment.ARG_ID_LIBRO, id);
	             args.putString(DetalleFragment.TIPO_ACCION, tipoAccion);
	             nuevoFragment.setArguments(args);
	             FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
	             transaccion.replace(R.id.contenedor_pequeno, nuevoFragment);
	             transaccion.addToBackStack(null);
	             transaccion.commit();
	       }
	       idSeleccionado = id;
	}
	
	 public void selecionarImagen(View view){
	    AplicacionImagenes app = (AplicacionImagenes) getApplication();
    	if(tipoAccion.equals("texto")){
	    	MainActivity.imagenParaTexto = app.getListaimagenes().get(idSeleccionado);
	    }else{
	    	MainActivity.imagenesParaVideo.add(app.getListaimagenes().get(idSeleccionado));
	    }
    	Toast.makeText(this, getString(R.string.imagenseleccionada), Toast.LENGTH_SHORT).show();
	}
	 
//	 @Override
//	 public void onBackPressed() {
//		Intent i = new Intent(this, InicioVideo.class);	   
//	    startActivity(i);	
//	    super.onBackPressed();
//	 }
	 
	 
}
		
