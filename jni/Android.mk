LOCAL_PATH := $(call my-dir)
 
include $(CLEAR_VARS)

LOCAL_MODULE := procesadodeimagen
LOCAL_SRC_FILES := procesadodeimagen.c
LOCAL_LDLIBS := -llog -ljnigraphics
 
include $(BUILD_SHARED_LIBRARY)