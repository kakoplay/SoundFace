#include "com_android_soundface_texto_VerImagenGenerada.h"
#include <android/log.h>
#include <android/bitmap.h>

#define LOG_TAG "procesadodeimagen"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

typedef struct
{
       uint8_t red;
       uint8_t green;
       uint8_t blue;
       uint8_t alpha;
} rgba;

/*Conversion a grises por pixel*/
JNIEXPORT void JNICALL Java_com_android_soundface_texto_VerImagenGenerada_convertirGrises
            (JNIEnv * env, jobject obj, jobject bitmapcolor,jobject bitmapgris)
{
       AndroidBitmapInfo infocolor;
       void* pixelscolor;
       AndroidBitmapInfo infogris;
       void* pixelsgris;
       int ret;
       int y;
       int x;
       LOGI("convertirGrises");
       if ((ret = AndroidBitmap_getInfo(env, bitmapcolor, &infocolor)) < 0) {
             LOGE("AndroidBitmap_getInfo() failed ! error=%d", ret);
             return;
       }

       if ((ret = AndroidBitmap_getInfo(env, bitmapgris, &infogris)) < 0) {
             LOGE("AndroidBitmap_getInfo() failed ! error=%d", ret);
       return;
       }

       LOGI("imagen color :: ancho %d;alto %d;avance %d;formato %d;flags %d", infocolor.width, infocolor.height, infocolor.stride,
                                  infocolor.format, infocolor.flags);
             if (infocolor.format != ANDROID_BITMAP_FORMAT_RGBA_8888) {
                    LOGE("Bitmap no es formato RGBA_8888 !");
             return;
             }

       LOGI("imagen color :: ancho %d;alto %d;avance %d;formato %d;flags %d",
                                  infogris.width, infogris.height, infogris.stride,
                                  infogris.format, infogris.flags);
             if (infogris.format != ANDROID_BITMAP_FORMAT_RGBA_8888) {
                    LOGE("Bitmap no es formato RGBA_8888 !");
             return;
             }

       if ((ret = AndroidBitmap_lockPixels(env, bitmapcolor, &pixelscolor))
                    < 0) {
             LOGE("AndroidBitmap_lockPixels() failed ! error=%d", ret);
       }

       if ((ret = AndroidBitmap_lockPixels(env, bitmapgris, &pixelsgris))<0) {
             LOGE("AndroidBitmap_lockPixels() fallo ! error=%d", ret);
       }
       // modificacion pixeles en el algoritmo de escala grises
       for (y=0;y<infocolor.height;y++) {
             rgba * line = (rgba *) pixelscolor;
             rgba * grisline = (rgba *) pixelsgris;
             for (x=0;x<infocolor.width;x++) {
                    float output = (line[x].red + line[x].green + line[x].blue)/3;
                           if (output > 255) output = 255;
                           grisline[x].red = grisline[x].green = grisline[x].blue = (uint8_t) output;
                           grisline[x].alpha = line[x].alpha;
             }
             pixelscolor = (char *)pixelscolor + infocolor.stride;
             pixelsgris = (char *) pixelsgris + infogris.stride;
       }

       LOGI("unlocking pixels");
       AndroidBitmap_unlockPixels(env, bitmapcolor);
       AndroidBitmap_unlockPixels(env, bitmapgris);
}



/*Conversion a sepia por pixel*/
JNIEXPORT void JNICALL Java_com_android_soundface_texto_VerImagenGenerada_convertirSepia
            (JNIEnv * env, jobject obj, jobject bitmapcolor,jobject bitmapgris)
{
       AndroidBitmapInfo infocolor;
       void* pixelscolor;
       AndroidBitmapInfo infogris;
       void* pixelsgris;
       int ret;
       int y;
       int x;
       LOGI("convertirGrises");
       if ((ret = AndroidBitmap_getInfo(env, bitmapcolor, &infocolor)) < 0) {
             LOGE("AndroidBitmap_getInfo() failed ! error=%d", ret);
             return;
       }

       if ((ret = AndroidBitmap_getInfo(env, bitmapgris, &infogris)) < 0) {
             LOGE("AndroidBitmap_getInfo() failed ! error=%d", ret);
       return;
       }

       LOGI("imagen color :: ancho %d;alto %d;avance %d;formato %d;flags %d", infocolor.width, infocolor.height, infocolor.stride,
                                  infocolor.format, infocolor.flags);
             if (infocolor.format != ANDROID_BITMAP_FORMAT_RGBA_8888) {
                    LOGE("Bitmap no es formato RGBA_8888 !");
             return;
             }

       LOGI("imagen color :: ancho %d;alto %d;avance %d;formato %d;flags %d",
                                  infogris.width, infogris.height, infogris.stride,
                                  infogris.format, infogris.flags);
             if (infogris.format != ANDROID_BITMAP_FORMAT_RGBA_8888) {
                    LOGE("Bitmap no es formato RGBA_8888 !");
             return;
             }

       if ((ret = AndroidBitmap_lockPixels(env, bitmapcolor, &pixelscolor))
                    < 0) {
             LOGE("AndroidBitmap_lockPixels() failed ! error=%d", ret);
       }

       if ((ret = AndroidBitmap_lockPixels(env, bitmapgris, &pixelsgris))<0) {
             LOGE("AndroidBitmap_lockPixels() fallo ! error=%d", ret);
       }
       // modificacion pixeles en el algoritmo de escala grises
       //outputRed = (inputRed * .393) + (inputGreen *.769) + (inputBlue * .189)
       //outputGreen = (inputRed * .349) + (inputGreen *.686) + (inputBlue * .168)
       //outputBlue = (inputRed * .272) + (inputGreen *.534) + (inputBlue * .131)
       int depth = 20;
       for (y=0;y<infocolor.height;y++) {
             rgba * line = (rgba *) pixelscolor;
             rgba * grisline = (rgba *) pixelsgris;
             for (x=0;x<infocolor.width;x++) {

                    float output_red = (line[x].red * .393) + (line[x].green *.769) + (line[x].blue * .189);
                    float output_gre = (line[x].red * .349) + (line[x].green *.686) + (line[x].blue * .168);
                    float output_blu = (line[x].red * .272) + (line[x].green *.534) + (line[x].blue * .131);

                    if (output_red > 255) output_red = 255;
                    if (output_gre > 255) output_gre = 255;
                    if (output_blu > 255) output_blu = 255;

                    grisline[x].red = (uint8_t) output_red;
                    grisline[x].green = (uint8_t) output_gre;
                    grisline[x].blue = (uint8_t) output_blu;

                    grisline[x].alpha = line[x].alpha;
             }
             pixelscolor = (char *)pixelscolor + infocolor.stride;
             pixelsgris = (char *) pixelsgris + infogris.stride;
       }

       LOGI("unlocking pixels");
       AndroidBitmap_unlockPixels(env, bitmapcolor);
       AndroidBitmap_unlockPixels(env, bitmapgris);
}


/*Conversion a sepia por pixel*/
JNIEXPORT void JNICALL Java_com_android_soundface_texto_VerImagenGenerada_addMarco
            (JNIEnv * env, jobject obj, jobject bitmapcolor,jobject bitmapgris)
{
       AndroidBitmapInfo infocolor;
       void* pixelscolor;
       AndroidBitmapInfo infogris;
       void* pixelsgris;
       int ret;
       int y;
       int x;
       LOGI("addMarco");
       if ((ret = AndroidBitmap_getInfo(env, bitmapcolor, &infocolor)) < 0) {
             LOGE("AndroidBitmap_getInfo() failed ! error=%d", ret);
             return;
       }

       if ((ret = AndroidBitmap_getInfo(env, bitmapgris, &infogris)) < 0) {
             LOGE("AndroidBitmap_getInfo() failed ! error=%d", ret);
       return;
       }

       LOGI("imagen color :: ancho %d;alto %d;avance %d;formato %d;flags %d", infocolor.width, infocolor.height, infocolor.stride,
                                  infocolor.format, infocolor.flags);
             if (infocolor.format != ANDROID_BITMAP_FORMAT_RGBA_8888) {
                    LOGE("Bitmap no es formato RGBA_8888 !");
             return;
             }

       LOGI("imagen color :: ancho %d;alto %d;avance %d;formato %d;flags %d",
                                  infogris.width, infogris.height, infogris.stride,
                                  infogris.format, infogris.flags);
             if (infogris.format != ANDROID_BITMAP_FORMAT_RGBA_8888) {
                    LOGE("Bitmap no es formato RGBA_8888 !");
             return;
             }

       if ((ret = AndroidBitmap_lockPixels(env, bitmapcolor, &pixelscolor))
                    < 0) {
             LOGE("AndroidBitmap_lockPixels() failed ! error=%d", ret);
       }

       if ((ret = AndroidBitmap_lockPixels(env, bitmapgris, &pixelsgris))<0) {
             LOGE("AndroidBitmap_lockPixels() fallo ! error=%d", ret);
       }

       for (y=0;y<infocolor.height;y++) {

    	     if((y>=0 && y<10)||(y>infocolor.height-10 && y<=infocolor.height)){
    	    	 rgba * line = (rgba *) pixelscolor;
    	    	 rgba * grisline = (rgba *) pixelsgris;
    	    	 for (x=0;x<infocolor.width;x++) {
    	    		 grisline[x].red = 0;
					 grisline[x].green = 0;
					 grisline[x].blue = 0;

					 grisline[x].alpha = line[x].alpha;
    	    	 }
    	    	 pixelscolor = (char *)pixelscolor + infocolor.stride;
    	    	 pixelsgris = (char *) pixelsgris + infogris.stride;

    	     }else{
    	    	 rgba * line = (rgba *) pixelscolor;
				 rgba * grisline = (rgba *) pixelsgris;
				 for (x=0;x<infocolor.width;x++) {
					 if((x>=0 && x<10) || (x>infocolor.width-10 && x<=infocolor.width)){
						 grisline[x].red = 0;
						 grisline[x].green = 0;
						 grisline[x].blue = 0;
					 }else{
						 grisline[x].red = line[x].red;
						 grisline[x].green = line[x].green;
						 grisline[x].blue = line[x].blue;
					 }

					 grisline[x].alpha = line[x].alpha;
				 }
				 pixelscolor = (char *)pixelscolor + infocolor.stride;
				 pixelsgris = (char *) pixelsgris + infogris.stride;
    	     }
       }

       LOGI("unlocking pixels");
       AndroidBitmap_unlockPixels(env, bitmapcolor);
       AndroidBitmap_unlockPixels(env, bitmapgris);
}
