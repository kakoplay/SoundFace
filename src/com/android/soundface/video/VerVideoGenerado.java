package com.android.soundface.video;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.android.soundface.MainActivity;
import com.android.soundface.R;

public class VerVideoGenerado extends Activity {
	
	private VideoView mVideoView;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_video_generado);
        
        mVideoView =(VideoView)findViewById(R.id.surface_view);
        //de forma alternative si queremos un streaming usar
        mVideoView.setVideoURI(Uri.parse(MainActivity.rutaUltimoVideoGenerado));
        //mVideoView.setVideoPath("/mnt/sdcard/video.mp4");
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.start();
        mVideoView.requestFocus();
		
                
    }
	
	public void compartirVideo(View view){
		
		String nameVideo = MainActivity.rutaUltimoVideoGenerado;
		
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
	    shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
	    shareIntent.putExtra(Intent.EXTRA_TEXT, "");
	    shareIntent.setType("video/*");

	   // final String appName = VerVideoGenerado.this.getString(R.string.app_name);	
	    // For a file in shared storage.  For data in private storage, use a ContentProvider.
		File file = new File(nameVideo);
	    Uri uri = Uri.fromFile(file);
	    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);

		
		startActivity(Intent.createChooser(shareIntent, ""));
	
	}
	
	@Override 
	protected void onDestroy() {
		super.onStop();
		mVideoView.stopPlayback();
	}
	
	@Override 
	protected void onStop() {
		super.onStop();
		mVideoView.stopPlayback();
	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, VerListaVideos.class);	   
	    startActivity(i);	
	    super.onBackPressed();
	}
	
}
