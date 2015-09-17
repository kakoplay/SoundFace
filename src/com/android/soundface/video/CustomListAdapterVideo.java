package com.android.soundface.video;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.android.soundface.MainActivity;
import com.android.soundface.R;

public class CustomListAdapterVideo extends ArrayAdapter<String> {
	 
		private final Activity context;
		private final String[] itemname;
		

		public CustomListAdapterVideo(Activity context, String[] itemname) {
			super(context, R.layout.lista_view_videos_gen, itemname);
			
			this.context=context;
			this.itemname=itemname;
			
		}
		

		public View getView(int position, View view, ViewGroup parent) {
			LayoutInflater inflater=context.getLayoutInflater();
			View rowView=inflater.inflate(R.layout.lista_view_videos_gen, null,true);
			
			TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
			ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
					
			String name = itemname[position];
			//name = name.substring(0,name.indexOf("_"));
			txtTitle.setText(name);
		   	imageView.setImageBitmap(MainActivity.verlistaVideo.get(position));
		   	
		   	CheckBox cBox = (CheckBox) rowView.findViewById(R.id.checkBox2);
		    cBox.setTag(Integer.valueOf(position)); // set the tag so we can identify the correct row in the listener
		    cBox.setChecked(false); // set the status as we stored it        
		    cBox.setOnCheckedChangeListener(mListener);
			
			return rowView;
			
		};
		
		
		OnCheckedChangeListener mListener = new OnCheckedChangeListener() {

		     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {   
		         if(isChecked)
		    		 MainActivity.videosBorrar.add((Integer)buttonView.getTag());
		    	 else
		    		 MainActivity.videosBorrar.remove((Integer)buttonView.getTag());
		     }
		};
	}