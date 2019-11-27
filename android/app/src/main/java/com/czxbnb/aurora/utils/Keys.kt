package com.czxbnb.aurora.utils

object Keys {
    init {
        System.loadLibrary("native-lib"
        )
    }

    external fun getGoogleMapsKey() : String

    external fun getGoogleNewsKey() : String
}