package com.czxbnb.aurora.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.czxbnb.aurora.R


class SwagPoints : View {
    var mProgressWidth = 0f
    var mArcWidth = 0f
    var mTextSize = 0f
    var mPoints = 0
    var mMin = 0
    var mMax = 0
    var mStep = 0
    lateinit var mIndicatorIcon: Drawable

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val density = resources.displayMetrics.density

        // Get typed array
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SwagPoints)
        mProgressWidth = typedArray.getDimension(R.styleable.SwagPoints_progressWidth, 10f)
        mArcWidth = typedArray.getDimension(R.styleable.SwagPoints_arcWidth, 10f)
        mTextSize = typedArray.getDimension(R.styleable.SwagPoints_textSize, 12f)

        // Default attributes
        val arcColor = ContextCompat.getColor(context, R.color.grey_400)
        val progressColor = ContextCompat.getColor(context, R.color.colorPrimary)
        val textColor = ContextCompat.getColor(context, R.color.black)
        mProgressWidth = (mProgressWidth * density)
        mArcWidth = (mArcWidth * density)
        mTextSize = (mTextSize * density)
        mIndicatorIcon = ContextCompat.getDrawable(context, R.drawable.ic_home)!!

        if (attrs.attributeCount > 0) {
            val indicatorIcon = typedArray.getDrawable(R.styleable.SwagPoints_indicatorIcon)
            mIndicatorIcon = indicatorIcon!!

            val indicatorIconHalfWidth = mIndicatorIcon.intrinsicWidth / 2
            val indicatorIconHalfHeight = mIndicatorIcon.intrinsicHeight / 2
            mIndicatorIcon.setBounds(
                -indicatorIconHalfWidth,
                -indicatorIconHalfHeight,
                indicatorIconHalfWidth,
                indicatorIconHalfHeight
            )

            mPoints = typedArray.getInteger(R.styleable.SwagPoints_points, mPoints)
            mMin = typedArray.getInteger(R.styleable.SwagPoints_min, mMin)
            mMax = typedArray.getInteger(R.styleable.SwagPoints_max, mMax)
            mStep = typedArray.getInteger(R.styleable.SwagPoints_step, mStep)
        }
    }
}