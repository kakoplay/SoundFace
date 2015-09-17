package com.android.soundface;

import android.os.Environment;

public class Constantes {

	public static final String IMAGEPATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
	public static final String MUSICPATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).toString();
	public static final String VIDEOPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera";
	

}
