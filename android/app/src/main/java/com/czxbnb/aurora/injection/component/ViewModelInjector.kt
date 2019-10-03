package com.czxbnb.aurora.injection.component

import com.czxbnb.aurora.injection.module.NetworkModule
import com.czxbnb.aurora.ui.auth.login.LoginViewModel
import com.czxbnb.aurora.ui.post.PostListViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified PostListViewModel.
     * @param postListViewModel PostListViewModel in which to inject the dependencies
     */
    fun inject(postListViewModel: PostListViewModel)

    /**
     * Injects required dependencies into the specified LoginViewModel
     * @param loginViewModel LoginViewModel in which to inject the dependencies
     */
    fun inject(loginViewModel: LoginViewModel)

    @Component.Builder
    interface Builder {
        fun build() : ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}