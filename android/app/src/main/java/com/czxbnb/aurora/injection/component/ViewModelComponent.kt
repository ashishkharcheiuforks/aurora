package com.czxbnb.aurora.injection.component

import com.czxbnb.aurora.injection.module.ApiModule
import com.czxbnb.aurora.injection.module.RepositoryModule
import com.czxbnb.aurora.ui.activity.ActivityItemViewModel
import com.czxbnb.aurora.ui.activity.ActivityViewModel
import com.czxbnb.aurora.ui.activityDetail.ActivityConfirmViewModel
import com.czxbnb.aurora.ui.activityDetail.ActivityDetailViewModel
import com.czxbnb.aurora.ui.auth.login.LoginViewModel
import com.czxbnb.aurora.ui.home.HomeActivityViewModel
import com.czxbnb.aurora.ui.home.HomeViewModel
import com.czxbnb.aurora.ui.main.MainViewModel
import com.czxbnb.aurora.ui.post.PostListViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(ApiModule::class), (RepositoryModule::class)])
interface ViewModelComponent {
    @Component.Builder
    interface Builder {
        fun build() : ViewModelComponent

        fun networkModule(networkModule: ApiModule): Builder
    }

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

    /**
     * Injects required dependencies into the specified MainViewModel
     * @param mainViewModel MainViewModel in which to inject the dependencies
     */
    fun inject(mainViewModel: MainViewModel)

    /**
     * Injects required dependencies into the specified HomeViewModel
     * @param homeViewModel HomeViewModel in which to inject the dependencies
     */
    fun inject(homeViewModel: HomeViewModel)

    /**
     * Injects required dependencies into the specified HomeActivityViewModel
     * @param homeActivityViewModel HomeActivityViewModel in which to inject the dependencies
     */
    fun inject(homeActivityViewModel: HomeActivityViewModel)

    /**
     * Injects required dependencies into the specified activityViewModel
     * @param activityViewModel activityViewModel in which to inject the dependencies
     */
    fun inject(activityViewModel: ActivityViewModel)

    /**
     * Injects required dependencies into the specified activityItemViewModel
     * @param activityItemViewModel activityItemViewModel in which to inject the dependencies
     */
    fun inject(activityItemViewModel: ActivityItemViewModel)

    /**
     * Injects required dependencies into the specified activityDetailViewModel
     * @param activityDetailViewModel activityDetailViewModel in which to inject the dependencies
     */
    fun inject(activityDetailViewModel: ActivityDetailViewModel)

    /**
     * Injects required dependencies into the specified activityConfirmViewModel
     * @param activityConfirmViewModel activityConfirmViewModel in which to inject the dependencies
     */
    fun inject(activityConfirmViewModel: ActivityConfirmViewModel)
}