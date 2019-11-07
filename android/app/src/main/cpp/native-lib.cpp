//
// Created by Zixin Cheng on 2019-10-26.
//

#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_czxbnb_aurora_utils_Keys_getGoogleMapsKey(JNIEnv *env, jobject object) {
    std::string api_key = "AIzaSyB10Nzmnh3PpnLuFw6IjxX7GxrnFXfZURc";
    return env->NewStringUTF(api_key.c_str());
}