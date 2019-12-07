package com.czxbnb.aurora.ui.article

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.czxbnb.aurora.R
import com.czxbnb.aurora.model.news.Article
import kotlinx.android.synthetic.main.item_article_stepper.view.*

class ArticleViewPagerAdapter(private val article: List<String>, private val context: Context) : PagerAdapter(){

    override fun getCount(): Int {
        return article.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.item_article_stepper, container, false)
        if (position > 0) {
            view.tv_text.visibility = View.VISIBLE
            view.lyt_article_cover.visibility = View.GONE
            view.tv_text.text = article[position]
        }
        container.addView(view)
        return view
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }
}