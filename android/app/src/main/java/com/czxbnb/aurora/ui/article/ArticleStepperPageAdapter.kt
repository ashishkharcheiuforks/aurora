package com.czxbnb.aurora.ui.article

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ArticleStepperPageAdapter (fragmentManager: FragmentManager, val article: List<String>) :
        FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return article.size + 1
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> ArticleStepperFragment(true, "")
            in 1..article.size + 1 -> ArticleStepperFragment(false, article[position])
            else -> throw IllegalArgumentException("fragment not exist!")
        }
    }

}
