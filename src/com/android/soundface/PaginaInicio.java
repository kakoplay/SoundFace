package com.android.soundface;

import android.app.Activity;
import android.os.Bundle;

public class PaginaInicio extends Activity{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        ImageView imgAssets = (ImageView) findViewById(R.id.imgAssets);
//        AssetManager assetManager = getAssets();
//        try {         
//            InputStream ims = assetManager.open("fondoladrillo1.jpg");         
//            Drawable d = Drawable.createFromStream(ims, null);         
//            imgAssets.setImageDrawable(d);
//        }
//        catch(IOException ex) {
//            return;
//        }
    }
	
}
