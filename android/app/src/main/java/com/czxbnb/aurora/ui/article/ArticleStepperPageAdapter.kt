package com.czxbnb.aurora.ui.article

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.czxbnb.aurora.model.news.Article

class ArticleStepperPageAdapter (fragmentManager: FragmentManager, val article: Article) :
        FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> ArticleStepperFragment(true, article)
            1 -> ArticleStepperFragment(false, article)
            else -> throw IllegalArgumentException("fragment not exist!")
        }
    }

}
