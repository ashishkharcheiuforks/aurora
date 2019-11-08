package com.czxbnb.aurora.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.czxbnb.aurora.injection.ViewModelFactory

abstract class BaseFragment<ViewModel : BaseViewModel, DataBinding : ViewDataBinding>(
    private val viewModelClass: Class<ViewModel>
) : Fragment() {
    lateinit var viewModel: ViewModel
    lateinit var dataBinding: DataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        viewModel =
            ViewModelProviders.of(this, context?.let { ViewModelFactory(it) }).get(viewModelClass)
        super.onCreateView(inflater, container, savedInstanceState)
        dataBinding.lifecycleOwner = this
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage)
        })
        return dataBinding.root
    }

    abstract fun getLayoutRes(): Int

    private fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }
}

