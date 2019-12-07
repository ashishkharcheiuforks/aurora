package com.czxbnb.aurora.ui.article

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.czxbnb.aurora.R
import com.czxbnb.aurora.base.BaseActivity
import com.czxbnb.aurora.databinding.ActivityArticleBinding
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : BaseActivity<ArticleViewModel, ActivityArticleBinding>(ArticleViewModel::class.java) {

    private val MAX_STEP = 4
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.viewModel = viewModel

        // adding bottom dots
        bottomProgressDots(0)

    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_article
    }

    private fun bottomProgressDots(current_index: Int) {
        val dots =
            arrayOfNulls<ImageView>(MAX_STEP)
        layoutDots.removeAllViews()
        for (i in dots.indices) {
            dots[i] = ImageView(this)
            val width_height = 10
            val params =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams(width_height, width_height))
            params.setMargins(10, 10, 10, 10)
            dots[i]!!.layoutParams = params
            dots[i]!!.setImageResource(R.drawable.shape_circle)
            dots[i]!!
                .setColorFilter(resources.getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN)
            layoutDots.addView(dots[i])
        }
        if (dots.isNotEmpty()) {
            dots[current_index]!!.setImageResource(R.drawable.shape_circle)
            dots[current_index]?.setColorFilter(resources.getColor(R.color.grey_80), PorterDuff.Mode.SRC_IN)
        }
    }

    private fun initComponent() {

    }
}