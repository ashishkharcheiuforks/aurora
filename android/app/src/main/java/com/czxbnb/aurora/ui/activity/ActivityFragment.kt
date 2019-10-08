package com.czxbnb.aurora.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.czxbnb.aurora.R
import com.czxbnb.aurora.databinding.FragmentActivityBinding
import com.czxbnb.aurora.injection.ViewModelFactory
import com.czxbnb.aurora.ui.home.HomeViewModel

class ActivityFragment : Fragment() {
    private lateinit var binding: FragmentActivityBinding
    private lateinit var viewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Bind the view model
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activity, container, false)
        binding.rvActivity.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProviders.of(this, context?.let { ViewModelFactory(it) })
            .get(ActivityViewModel::class.java)
        binding.viewModel = viewModel

        // Add error observer

        return binding.root
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }
}