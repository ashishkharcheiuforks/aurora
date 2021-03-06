package com.czxbnb.aurora.view

import android.view.View

import androidx.core.widget.NestedScrollView

object ViewUtils {
    fun nestedScrollTo(nested: NestedScrollView, targetView: View) {
        nested.post { nested.scrollTo(500, targetView.bottom) }
    }
}
