package com.czxbnb.aurora.ui.article

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.czxbnb.aurora.R
import com.czxbnb.aurora.base.BaseFragment
import com.czxbnb.aurora.databinding.FragmentArticleStepperBinding
import com.czxbnb.aurora.model.news.Article
import kotlinx.android.synthetic.main.activity_article.*
import kotlinx.android.synthetic.main.fragment_article_stepper.*

class ArticleStepperFragment(
    private val isTitle: Boolean,
    private val article: Article
) :
    BaseFragment<ArticleStepperViewModel, FragmentArticleStepperBinding>(ArticleStepperViewModel::class.java) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dataBinding.viewModel = viewModel

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isTitle) {
            // Show the title
            tv_date.text = article.publishedAt
            tv_title.text = article.title
            tv_provider.text = article.author
        } else {
            // Show the content
            tv_text.visibility = View.VISIBLE
            lyt_article_cover.visibility = View.GONE
            tv_text.text = article.content
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_article_stepper
    }
}