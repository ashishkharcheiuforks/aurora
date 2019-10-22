package com.czxbnb.aurora.utils

import android.view.View

import androidx.core.widget.NestedScrollView

object ViewUtils {
    fun nestedScrollTo(nested: NestedScrollView, targetView: View) {
        nested.post { nested.scrollTo(500, targetView.bottom) }
    }
}
