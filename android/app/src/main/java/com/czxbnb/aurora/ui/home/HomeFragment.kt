package com.czxbnb.aurora.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.czxbnb.aurora.R
import com.czxbnb.aurora.base.BaseFragment
import com.czxbnb.aurora.databinding.FragmentHomeBinding
import com.czxbnb.aurora.injection.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(HomeViewModel::class.java) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Bind view model
        super.onCreateView(inflater, container, savedInstanceState)
        dataBinding.viewModel = viewModel
        dataBinding.rvActivity.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        return dataBinding.root
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }
}
