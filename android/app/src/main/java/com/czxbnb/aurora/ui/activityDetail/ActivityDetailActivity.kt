package com.czxbnb.aurora.ui.activityDetail

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.czxbnb.aurora.R
import com.czxbnb.aurora.base.BaseActivity
import com.czxbnb.aurora.databinding.ActivityDetailBinding
import com.czxbnb.aurora.injection.ViewModelFactory
import com.czxbnb.aurora.model.activity.Activity
import com.czxbnb.aurora.utils.ViewAnimation
import com.czxbnb.aurora.utils.ViewUtils
import kotlinx.android.synthetic.main.activity_detail.*

class ActivityDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: ActivityDetailViewModel
    private lateinit var activity: Activity

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

        // Make status bar immersive
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            collapsing_toolbar_layout.setStatusBarScrimColor(getColor(R.color.colorPrimary))
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            collapsing_toolbar_layout.setStatusBarScrimColor(resources.getColor(R.color.colorPrimary))
        }

        // Set toolbar event
        toolbar.setNavigationOnClickListener { finish() }

        // Load activity
        activity = intent.getSerializableExtra("activity") as Activity
        viewModel.loadActivity(activity)
        title = activity.title

        // Add listeners for buttons
        addListenersForButtons()
    }

    private fun addListenersForButtons() {
        btn_description.setOnClickListener {view->
            toggleSection(view, ll_description)
        }
    }

    private fun toggleSection(bt: View, lyt: View) {
        val show = toggleArrow(bt)
        if (show) {
            ViewAnimation.expand(lyt, object : ViewAnimation.AnimListener {
                override fun onFinish() {
                    ViewUtils.nestedScrollTo(nested_scroll_view, lyt)
                }
            })
        } else {
            ViewAnimation.collapse(lyt)
        }
    }

    private fun toggleArrow(view: View): Boolean {
        return if (view.rotation == 0f) {
            view.animate().setDuration(200).rotation(180f)
            true
        } else {
            view.animate().setDuration(200).rotation(0f)
            false
        }
    }
}