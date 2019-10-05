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
import com.czxbnb.aurora.databinding.FragmentHomeBinding
import com.czxbnb.aurora.injection.ViewModelFactory
import com.google.android.material.snackbar.Snackbar


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
        binding.rvActivity.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        viewModel = ViewModelProviders.of(this, context?.let { ViewModelFactory(it) }).get(HomeViewModel::class.java)
        binding.viewModel = viewModel

        // Add error observer
        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError(errorMessage)
        })

        return binding.root
    }

    private fun showError(errorMessage:String){
       Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

}
