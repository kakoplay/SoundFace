package com.android.soundface;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.soundface.bean.Sonido;

public class ListaSonidos extends Activity{
	
	ListView listView;  
    Button confirmBtn;
    Button test_btn;
    Button stop_btn;
    Button exit_btn;
    Button externo_btn;
    MiAdaptadorSonidos listAdapter;  
    List<Sonido> list; 
   
    Sonido sonidoSeleccionado; 
    
    MediaPlayer mediaPlayer;
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.lista_sonidos_principal);  
  
        list = MainActivity.basedatos.obtenerTodosSonidos();
          
        listView = (ListView) findViewById(R.id.sms_model_list);  
        confirmBtn = (Button) findViewById(R.id.confirm_btn); 
        test_btn = (Button) findViewById(R.id.test_btn);
        stop_btn = (Button) findViewById(R.id.stop_btn);
        exit_btn = (Button) findViewById(R.id.exit_btn);
        externo_btn = (Button) findViewById(R.id.externo_btn);
  
        listAdapter = new MiAdaptadorSonidos(getApplicationContext());  
        listAdapter.setList(list);  
        listView.setAdapter(listAdapter);  
          
//        final Intent intent = new Intent(MainActivity.this,NextActivity.class);  
//        intent.putExtra("selectedBook", "ssssss");  
        listView.setOnItemClickListener(new OnItemClickListener() {  
            @Override  
            public void onItemClick(AdapterView<?> parent, View view,  int position, long id) {  
                listAdapter.select(position);  
                  
                Sonido sonido = (Sonido)listAdapter.getItem(position);  
                String value = sonido.getNombreSonido();  
                
                sonidoSeleccionado = sonido;
            }  
        });         

        test_btn.setOnClickListener(new View.OnClickListener() {              
            @Override  
            public void onClick(View v) {  
            	if(sonidoSeleccionado!=null){
            		if (mediaPlayer!=null) mediaPlayer.reset();            		
            		playMusic(sonidoSeleccionado.getSonido(),sonidoSeleccionado.getNombreSonido());	                
            	}
            }  
        });
        
        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override  
            public void onClick(View v) {
            	if(mediaPlayer!=null)
            		mediaPlayer.stop(); 
            }  
        });
        
        confirmBtn.setOnClickListener(new View.OnClickListener() {            
            @Override  
            public void onClick(View v) {  
                MainActivity.sonidoParaVideo = sonidoSeleccionado;
                MainActivity.sonidoParaVideoUri = null;
                Toast.makeText(ListaSonidos.this, getString(R.string.selectsonido), Toast.LENGTH_LONG).show();
            }  
        });
        
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override  
            public void onClick(View v) {  
            	finish();
            }  
        });
        
        
        externo_btn.setOnClickListener(new View.OnClickListener() {
            @Override  
            public void onClick(View v) {  
            	Intent intent_upload = new Intent();
                intent_upload.setType("audio/*");
                intent_upload.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent_upload,1);
            }  
        });
        
        
  
    }
    
    @Override 
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

      if(requestCode == 1){
        if(resultCode == RESULT_OK){
            Uri uri = data.getData(); 
            String[] proj = { MediaStore.Images.Media.DATA };
		    CursorLoader loader = new CursorLoader(getApplicationContext(), uri, proj, null, null, null);
		    Cursor cursor = loader.loadInBackground();
		    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    cursor.moveToFirst();
		    
		    MainActivity.sonidoParaVideoUri = cursor.getString(column_index);
            MainActivity.sonidoParaVideo = null;
            Toast.makeText(ListaSonidos.this, getString(R.string.selectsonido), Toast.LENGTH_LONG).show();
        }
      }
      super.onActivityResult(requestCode, resultCode, data);
    }
    
    private void playMusic(byte[] mp3SoundByteArray, String name) {
    	
    	 try{    		
    		mediaPlayer = new MediaPlayer();
    	    AssetFileDescriptor descriptor = getAssets().openFd("sounds/ini_"+name);
    	    mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
    	    descriptor.close();

    	    mediaPlayer.prepare();
    	    mediaPlayer.start();
    	          	  
    	 }
    	 catch (IOException ex) 
    	 {
    	     ex.printStackTrace();
    	 }
    	

    }

    
	
	@Override 
	protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
        }
	}
	
	@Override 
	protected void onStop() {
        super.onDestroy();
        if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
        }
	}
	
//	@Override
//	 public void onBackPressed() {
//		Intent i = new Intent(this, InicioVideo.class);	   
//	    startActivity(i);	
//	    super.onBackPressed();
//	 }
    

}
