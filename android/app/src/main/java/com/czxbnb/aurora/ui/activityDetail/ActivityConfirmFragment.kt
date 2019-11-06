package com.czxbnb.aurora.ui.activityDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.czxbnb.aurora.R
import com.czxbnb.aurora.databinding.FragmentActivityConfirmBinding
import com.czxbnb.aurora.injection.ViewModelFactory
import com.czxbnb.aurora.model.activity.Activity
import com.dd.processbutton.iml.ActionProcessButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_activity_confirm.*
import kotlinx.android.synthetic.main.fragment_activity_confirm.view.*

class ActivityConfirmFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentActivityConfirmBinding
    private lateinit var viewModel: ActivityConfirmViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Bind view model
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activity_confirm, container,false)
        viewModel = ViewModelProviders.of(this,  context?.let { ViewModelFactory(it) }).get(ActivityConfirmViewModel::class.java)
        binding.viewModel = viewModel

        // Get activity value from bundle, and inject into viewModel
        viewModel.loadActivity(arguments!!.getSerializable("activity") as Activity)

        // Set progress button status and listener
        binding.root.btn_enroll.setMode(ActionProcessButton.Mode.ENDLESS)
        binding.root.btn_enroll.setOnClickListener {view ->
            viewModel.enrollActivity( "1")
        }

        // Set observer for progress button
        viewModel.progress.observe(this, Observer { progress ->
            btn_enroll.progress = progress
        })


        return binding.root
    }
}