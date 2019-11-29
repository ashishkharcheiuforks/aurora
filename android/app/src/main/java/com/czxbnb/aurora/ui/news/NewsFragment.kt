package com.czxbnb.aurora.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.czxbnb.aurora.R
import com.czxbnb.aurora.base.BaseFragment
import com.czxbnb.aurora.databinding.FragmentNewsBinding
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : BaseFragment<NewsViewModel, FragmentNewsBinding>(NewsViewModel::class.java) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dataBinding.rvNews.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        dataBinding.viewModel = viewModel

        return dataBinding.root
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_news
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Define swipe refresh layout
        srl_news.setOnRefreshListener {
            viewModel.getNews()
        }

        viewModel.newsRefreshVisibility.observe(viewLifecycleOwner, Observer { refreshVisibility ->
            srl_news.isRefreshing = refreshVisibility
        })
    }
}