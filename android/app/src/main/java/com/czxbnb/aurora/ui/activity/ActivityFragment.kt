package com.czxbnb.aurora.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.czxbnb.aurora.R
import com.czxbnb.aurora.base.BaseFragment
import com.czxbnb.aurora.databinding.FragmentActivityBinding
import com.czxbnb.aurora.injection.ViewModelFactory
import com.czxbnb.aurora.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_activity.*

class ActivityFragment :
    BaseFragment<ActivityViewModel, FragmentActivityBinding>(ActivityViewModel::class.java) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dataBinding.rvActivity.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        dataBinding.viewModel = viewModel

        return dataBinding.root
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_activity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Define swipe refresh layout
        srl_activity.setOnRefreshListener {
            viewModel.refreshActivityListFromRemoteSource()
        }
        viewModel.activityRefreshVisibility.observe(this, Observer { refreshVisibility ->
            srl_activity.isRefreshing = refreshVisibility
        })
    }
}