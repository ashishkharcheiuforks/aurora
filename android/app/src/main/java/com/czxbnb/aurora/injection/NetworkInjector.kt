package com.czxbnb.aurora.injection

import com.czxbnb.aurora.BASE_URL
import com.czxbnb.aurora.network.ActivityApi
import com.czxbnb.aurora.network.AuthApi
import com.czxbnb.aurora.network.PostApi
import com.czxbnb.aurora.network.UnsafeHttpClient.getUnsafeOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
@Suppress("unused")
object NetworkInjector {
    /**
     * Provides the Post service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Post service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun providePostApi(retrofit: Retrofit): PostApi {
        return retrofit.create(PostApi::class.java)
    }

    /**
     * Provides the Auth service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Auth service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    /**
     * Provides the Activity service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Activity service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideActivityApi(retrofit: Retrofit): ActivityApi {
        return retrofit.create(ActivityApi::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getUnsafeOkHttpClient().build())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
}