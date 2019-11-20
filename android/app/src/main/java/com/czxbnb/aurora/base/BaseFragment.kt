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
import com.czxbnb.aurora.R
import com.czxbnb.aurora.injection.ViewModelFactory
import com.czxbnb.aurora.model.event_bus.MessageEvent
import com.czxbnb.aurora.ui.error.NoInternetFragment
import kotlinx.android.synthetic.main.activity_main.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


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

        // Observe Error
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage)

            val fragment = NoInternetFragment()
            activity!!.supportFragmentManager.beginTransaction().apply {
                if (fragment.isAdded) {
                    show(fragment)
                } else {
                    add(R.id.container, fragment)
                }
            }.commit()
        })

        // Register event bus receiver
        EventBus.getDefault().register(this)

        return dataBinding.root
    }

    abstract fun getLayoutRes(): Int

    private fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Unregister event bus receiver
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    fun onErrorEvent(messageEvent: MessageEvent) {
        Toast.makeText(context,messageEvent.message, Toast.LENGTH_LONG).show()
    }
}

