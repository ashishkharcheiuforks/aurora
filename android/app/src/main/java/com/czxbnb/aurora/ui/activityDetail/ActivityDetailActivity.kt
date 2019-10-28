package com.czxbnb.aurora.ui.activityDetail

import android.os.Build
import android.os.Bundle
import android.text.Html
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
import com.czxbnb.aurora.view.ViewAnimation
import com.czxbnb.aurora.view.ViewUtils
import com.google.android.gms.maps.*
import kotlinx.android.synthetic.main.activity_detail.*
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng


class ActivityDetailActivity : BaseActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: ActivityDetailViewModel
    private lateinit var activity: Activity
    private lateinit var googleMap: GoogleMap
    private lateinit var supportMapFragment: SupportMapFragment

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

        // Format content
        activity.content = Html.fromHtml(activity.content).toString()
        activity.requirements = Html.fromHtml(activity.requirements).toString()

        // Add listeners for buttons
        addListenersForButtons()

        // Initialize map
        supportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)

        // Add listener for enroll button
        btn_enroll.setOnClickListener {
            val activityConfirmFragment = ActivityConfirmFragment()
            val bundle = Bundle()
            bundle.putSerializable("activity", activity)
            activityConfirmFragment.arguments = bundle
            activityConfirmFragment.show(
                supportFragmentManager,
                ActivityConfirmFragment::class.java.canonicalName
            )
        }
    }

    private fun addListenersForButtons() {
        ll_container_description.setOnClickListener {
            toggleSection(btn_description, ll_description)
        }

        btn_description.setOnClickListener {
            toggleSection(btn_description, ll_description)
        }

        ll_container_time.setOnClickListener {
            toggleSection(btn_time, ll_time)
        }

        btn_time.setOnClickListener {
            toggleSection(btn_time, ll_time)
        }

        ll_container_location.setOnClickListener {
            toggleSection(btn_location, ll_location)
        }

        btn_location.setOnClickListener {
            toggleSection(btn_location, ll_location)
        }

        ll_container_requirement.setOnClickListener {
            toggleSection(btn_requirement, ll_requirement)
        }

        btn_requirement.setOnClickListener {
            toggleSection(btn_requirement, ll_requirement)
        }

        toggleSection(btn_description, ll_description)
        toggleSection(btn_time, ll_time)
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

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        // Set google maps fragment attributes
        this.googleMap.uiSettings.setAllGesturesEnabled(false)
        this.googleMap.uiSettings.isMapToolbarEnabled = true

        // Get location
        viewModel.getActivity().observe(this, Observer { activity ->
            val location = LatLng(activity.lat, activity.lng)
            this.googleMap.addMarker(MarkerOptions().position(location).title(activity.title))
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18.0f))
        })
    }
}