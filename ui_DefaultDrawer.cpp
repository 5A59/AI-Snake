#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include "ui_DefaultDrawer.h"

JNIEXPORT void JNICALL Java_ui_DefaultDrawer_jniclear
  (JNIEnv *, jobject){
	  system("clear");
}
