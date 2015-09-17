package com.android.soundface.texto;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.soundface.Constantes;
import com.android.soundface.MainActivity;
import com.android.soundface.R;
import com.android.soundface.UtilesVideo;

public class TextLineView extends Activity implements OnTouchListener{
	
	static DrawingView dv ;   
	private Paint  mPaint;
	
	ImageView imageView;	
	Canvas canvas;
	Paint paint;
	float downx = 0, downy = 0, upx = 0, upy = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    dv = new DrawingView(this);
	    dv.setBackgroundColor(Color.rgb(0, 128, 128));
	    setContentView(dv);
	    mPaint = new Paint();
	    mPaint.setAntiAlias(true);
	    mPaint.setDither(true);
	    mPaint.setColor(Color.BLACK);
	    mPaint.setStyle(Paint.Style.STROKE);
	    mPaint.setStrokeJoin(Paint.Join.ROUND);
	    mPaint.setStrokeCap(Paint.Cap.ROUND);
	    mPaint.setStrokeWidth(10);  
	    

	}
	
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
	       getMenuInflater().inflate(R.menu.menu_dibujar, menu);
	       return true; 
	}
	
	@Override 
	public boolean onOptionsItemSelected(MenuItem item) {

	       switch (item.getItemId()) {
		       case R.id.color:	
		    	    selectColor(null);
	      			break;
	       		case R.id.tipo:
	       			selectTipo(null);
	       			break;
	       		case R.id.guardar:	  
	       			selectGuardar(null);
	       			break;
	       		case R.id.reset:	  
	       			selectReset(null);
	       			break;
	       		
	       }

	       return true;

	}
	
	public void selectColor(View v) {       
		 CharSequence colors[] = new CharSequence[] {"Red", "Green", "Blue", "Black", "White"};

		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setTitle("Color");
		 builder.setItems(colors, new DialogInterface.OnClickListener() {
		     @Override
		     public void onClick(DialogInterface dialog, int which) {
		    	 	int colorint = Color.BLACK;
	                switch (which) {
						case 0:
							colorint = Color.RED;	
							break;
						case 1:
							colorint = Color.GREEN;	
							break;
						case 2:
							colorint = Color.BLUE;	
							break;	
						case 3:
							colorint = Color.BLACK;	
							break;	
						case 4:
							colorint = Color.WHITE;	
							break;
						default:
							colorint = Color.BLACK;
							break;							
					}
	                mPaint.setColor(colorint);
		     }
		 });
		 builder.show();
	}
	
	public void selectTipo(View v) {
		CharSequence colors[] = new CharSequence[] {"Grueso", "Medio", "Fino"};

		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setTitle("Color");
		 builder.setItems(colors, new DialogInterface.OnClickListener() {
		     @Override
		     public void onClick(DialogInterface dialog, int which) {
		    	 	float colorint = mPaint.getStrokeWidth();
	                switch (which) {
						case 0:
							colorint = 10;	
							break;
						case 1:
							colorint = 5;	
							break;
						case 2:
							colorint = 1;	
							break;
						default:
							colorint = mPaint.getStrokeWidth();
							break;							
					}
	                mPaint.setStrokeWidth(colorint);
		     }
		 });
		 builder.show();
	}
	
	public void selectReset(View v) {       
		Intent i = new Intent(TextLineView.this, TextLineView.class);	   
        startActivity(i);	
	}
	
	public void selectGuardar(View v) {       
		String nameImagen = MainActivity.ultimaImagenGenerada;
    	try {
		    final String appName = TextLineView.this.getString(R.string.app_name);
			final String imagePath = Constantes.IMAGEPATH+ "/" + appName+ "/imgGen";
	
			UtilesVideo utV = new UtilesVideo(getAssets(), getApplicationContext());
			
			File file = new File(imagePath+"/"+ nameImagen+".jpg");
			file.delete();		
					
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
	     	String currentDateandTime = sdf.format(new Date());	     	
	     	
			Bitmap tosave = dv.mBitmap;
			
			utV.guardarImagenGenerada(nameImagen+"_"+currentDateandTime , tosave);
			
			MainActivity.ultimaImagenGenerada = nameImagen+"_"+currentDateandTime;
			
			Toast.makeText(TextLineView.this, getString(R.string.imagenguardada), Toast.LENGTH_SHORT).show();
		
    	} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
//	@Override
//	public void onBackPressed() {
//		Intent i = new Intent(TextLineView.this, VerListaImagenes.class);	   
//        startActivity(i);	
//        super.onBackPressed();
//
//    }
	
	
	public class DrawingView extends View {
	
	        public int width;
	        public  int height;
	        private Bitmap  mBitmap;
	        private Canvas  mCanvas;
	        private Path    mPath;
	        private Paint   mBitmapPaint;
	        Context context;
	        private Paint circlePaint;
	        private Path circlePath;
	        UtilesVideo utV;
	        		
	        public DrawingView(Context c) {
		        super(c);
		        context=c;
		        mPath = new Path();
		        mBitmapPaint = new Paint(Paint.DITHER_FLAG);  
		        circlePaint = new Paint();
		        circlePath = new Path();
		        circlePaint.setAntiAlias(true);
		        circlePaint.setColor(Color.BLUE);
		        circlePaint.setStyle(Paint.Style.STROKE);
		        circlePaint.setStrokeJoin(Paint.Join.MITER);
		        circlePaint.setStrokeWidth(8); 
		
		        utV = new UtilesVideo(getAssets(), getApplicationContext()); 
		        this.setDrawingCacheEnabled(true);
	
	        }
	
	        @Override
	        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		        super.onSizeChanged(w, h, oldw, oldh);
		        
		       	mBitmap = utV.obtenerImagenGenerada(MainActivity.ultimaImagenGenerada);
		        
		       	mBitmap = mBitmap.copy(Bitmap.Config.ARGB_8888, true);
		        
		        double inirelacion =  mBitmap.getWidth() * 1.0 / mBitmap.getHeight() * 1.0;
		        int hei = (int) (w  / inirelacion);
		        
		        mBitmap = Bitmap.createScaledBitmap(mBitmap, w, hei, false);
		        	        
		        mCanvas = new Canvas(mBitmap);
		        
		
	        }
	        @Override
	        protected void onDraw(Canvas canvas) {
		        super.onDraw(canvas);
		
		        canvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);
		
		        canvas.drawPath( mPath,  mPaint);
		
		        //canvas.drawPath( circlePath,  circlePaint);
	        }
	
	        
	        private float mX, mY;
	        private static final float TOUCH_TOLERANCE = 4;
	
	        private void touch_start(float x, float y) {
		        mPath.reset();
		        mPath.moveTo(x, y);
		        mX = x;
		        mY = y;
	        }
	        private void touch_move(float x, float y) {
		        float dx = Math.abs(x - mX);
		        float dy = Math.abs(y - mY);
		        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
		             mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2); 
		            mX = x;
		            mY = y;
		
		            circlePath.reset();
		            circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
		        }
	        }
	        private void touch_up() {
		        mPath.lineTo(mX, mY);
		        circlePath.reset();
		        // commit the path to our offscreen
		        mCanvas.drawPath(mPath,  mPaint);
		        // kill this so we don't double draw
		        mPath.reset();
	        }
	
	        @Override
	        public boolean onTouchEvent(MotionEvent event) {
		        float x = event.getX();
		        float y = event.getY();
		
		        switch (event.getAction()) {
		            case MotionEvent.ACTION_DOWN:
		                touch_start(x, y);
		                invalidate();
		                break;
		            case MotionEvent.ACTION_MOVE:
		                touch_move(x, y);
		                invalidate();
		                break;
		            case MotionEvent.ACTION_UP:
		                touch_up();
		                invalidate();
		                break;
		        }
		        return true;
	        }  
	       }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
	    switch (action) {
	    case MotionEvent.ACTION_DOWN:
	      downx = event.getX();
	      downy = event.getY();
	      break;
	    case MotionEvent.ACTION_MOVE:
	      break;
	    case MotionEvent.ACTION_UP:
	      upx = event.getX();
	      upy = event.getY();
	      canvas.drawLine(downx, downy, upx, upy, paint);
	      imageView.invalidate();
	      break;
	    case MotionEvent.ACTION_CANCEL:
	      break;
	    default:
	      break;
	    }
	    return true;
	  }
  }