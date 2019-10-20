package com.czxbnb.aurora.ui.activityDetail

import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.czxbnb.aurora.R
import com.czxbnb.aurora.base.BaseActivity
import com.czxbnb.aurora.databinding.ActivityDetailBinding
import com.czxbnb.aurora.injection.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*

class ActivityDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: ActivityDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Bind view model
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this))
            .get(ActivityDetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Initialize toolbar
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Load activity
        viewModel.loadActivity(intent!!.getStringExtra("id"))
    }
}