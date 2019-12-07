package com.czxbnb.aurora.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.czxbnb.aurora.R
import com.czxbnb.aurora.base.BaseFragment
import com.czxbnb.aurora.databinding.FragmentArticleStepperBinding
import kotlinx.android.synthetic.main.fragment_article_stepper.*

class ArticleStepperFragment(
    private val isTitle: Boolean,
    private val articleStr: String
) :
    BaseFragment<ArticleStepperViewModel, FragmentArticleStepperBinding>(ArticleStepperViewModel::class.java) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dataBinding.viewModel = viewModel

        if (isTitle) {
            // Show the title
        } else {
            // Show the content
            tv_text.visibility = View.VISIBLE
            lyt_article_cover.visibility = View.GONE
            tv_text.text = articleStr
        }
        return dataBinding.root
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_article_stepper
    }
}