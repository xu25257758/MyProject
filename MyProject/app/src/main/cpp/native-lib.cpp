#include <jni.h>
#include <string>
#include "test.c"

extern "C" JNIEXPORT jstring

JNICALL
Java_com_example_myproject_ui_activity_JniActivity_stringFromJNI(JNIEnv *env, jobject instance) {
    return env->NewStringUTF(min());
}