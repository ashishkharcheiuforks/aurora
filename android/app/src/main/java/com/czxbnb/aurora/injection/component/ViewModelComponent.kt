package com.czxbnb.aurora.injection.component

import com.czxbnb.aurora.injection.module.ApiModule
import com.czxbnb.aurora.injection.module.RepositoryModule
import com.czxbnb.aurora.ui.activity.ActivityItemViewModel
import com.czxbnb.aurora.ui.activity.ActivityViewModel
import com.czxbnb.aurora.ui.activityDetail.ActivityConfirmViewModel
import com.czxbnb.aurora.ui.activityDetail.ActivityDetailViewModel
import com.czxbnb.aurora.ui.article.ArticleStepperViewModel
import com.czxbnb.aurora.ui.article.ArticleViewModel
import com.czxbnb.aurora.ui.login.LoginViewModel
import com.czxbnb.aurora.ui.home.HomeActivityViewModel
import com.czxbnb.aurora.ui.home.HomeViewModel
import com.czxbnb.aurora.ui.main.MainViewModel
import com.czxbnb.aurora.ui.news.NewsItemViewModel
import com.czxbnb.aurora.ui.news.NewsViewModel
import com.czxbnb.aurora.ui.profile.ProfileViewModel
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

        fun apiModule(networkModule: ApiModule): Builder

        fun repositoryModule (repositoryModule: RepositoryModule): Builder
    }

    fun inject(loginViewModel: LoginViewModel)

    fun inject(mainViewModel: MainViewModel)

    fun inject(homeViewModel: HomeViewModel)

    fun inject(homeActivityViewModel: HomeActivityViewModel)

    fun inject(activityViewModel: ActivityViewModel)

    fun inject(activityItemViewModel: ActivityItemViewModel)

    fun inject(activityDetailViewModel: ActivityDetailViewModel)

    fun inject(activityConfirmViewModel: ActivityConfirmViewModel)

    fun inject(profileViewModel: ProfileViewModel)

    fun inject(newsViewModel: NewsViewModel)

    fun inject(newsItemViewModel: NewsItemViewModel)

    fun inject(articleViewModel: ArticleViewModel)

    fun inject(articleStepperViewModel: ArticleStepperViewModel)
}