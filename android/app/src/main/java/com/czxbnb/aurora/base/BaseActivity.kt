package com.czxbnb.aurora.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.czxbnb.aurora.R
import com.czxbnb.aurora.injection.ViewModelFactory
import com.czxbnb.aurora.ui.error.NoInternetFragment
import com.czxbnb.aurora.ui.login.LoginActivity
import com.gyf.immersionbar.ImmersionBar

abstract class BaseActivity<ViewModel : BaseViewModel, DataBinding : ViewDataBinding>(
    private val viewModelClass: Class<ViewModel>
) : AppCompatActivity() {
    lateinit var viewModel: ViewModel
    lateinit var dataBinding: DataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Define immersion Bar
        ImmersionBar.with(this)
            .statusBarDarkFont(true)
            .init()

        // Perform data binding
        dataBinding = DataBindingUtil.setContentView(this, getLayoutRes())
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(viewModelClass)
        dataBinding.lifecycleOwner = this

        // Observe error
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage)

            if (this !is LoginActivity) {
                val fragment = NoInternetFragment()
                supportFragmentManager.beginTransaction().apply {
                    if (fragment.isAdded) {
                        show(fragment)
                    } else {
                        add(R.id.container, fragment)
                    }
                }.commit()
            }
        })
    }

    abstract fun getLayoutRes(): Int

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }
}