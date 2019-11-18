package com.czxbnb.aurora.ui.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.czxbnb.aurora.R
import kotlinx.android.synthetic.main.fragment_no_internet.*


class NoInternetFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_no_internet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_retry.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction().hide(this).commit()
        }
    }
}