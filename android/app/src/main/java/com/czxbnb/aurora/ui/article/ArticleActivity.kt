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
import com.czxbnb.aurora.model.news.Article
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : BaseActivity<ArticleViewModel, ActivityArticleBinding>(ArticleViewModel::class.java) {

    private val MAX_STEP = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.viewModel = viewModel

        // adding bottom dots
        bottomProgressDots(0)

        // Set adapter
        val articleStepperPageAdapter = ArticleStepperPageAdapter(supportFragmentManager, intent.getSerializableExtra("article") as Article)
        vp_news.adapter = articleStepperPageAdapter
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
            val params =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams(10, 10))
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
}