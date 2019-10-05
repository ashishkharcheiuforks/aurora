package com.czxbnb.aurora.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.czxbnb.aurora.R
import com.czxbnb.aurora.databinding.FragmentHomeBinding
import com.czxbnb.aurora.injection.ViewModelFactory


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Bind view model
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProviders.of(this, context?.let { ViewModelFactory(it) }).get(HomeViewModel::class.java)
        binding.viewModel = viewModel

        return inflater.inflate(R.layout.fragment_home, container, false)
    }


}
