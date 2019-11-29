package com.czxbnb.aurora.injection.module

import android.annotation.SuppressLint
import com.czxbnb.aurora.AuroraApplication
import com.czxbnb.aurora.BASE_URL
import com.czxbnb.aurora.BuildConfig
import com.czxbnb.aurora.NEWS_URL
import com.czxbnb.aurora.network.api.ActivityApi
import com.czxbnb.aurora.network.api.AuthApi
import com.czxbnb.aurora.network.api.NewsApi
import com.czxbnb.aurora.network.converter.AuroraConverterFactory
import com.czxbnb.aurora.utils.NetworkUtils
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

import java.security.cert.CertificateException

import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


@Module
@Suppress("unused")
object ApiModule {
    /**
     * Provides the Auth service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Auth service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideAuthApi(retrofit: Retrofit.Builder): AuthApi {
        return retrofit
            .baseUrl(BASE_URL)
            .addConverterFactory(AuroraConverterFactory(Gson()))
            .build()
            .create(AuthApi::class.java)
    }

    /**
     * Provides the Activity service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Activity service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideActivityApi(retrofit: Retrofit.Builder): ActivityApi {
        return retrofit
            .baseUrl(BASE_URL)
            .addConverterFactory(AuroraConverterFactory(Gson()))
            .build()
            .create(ActivityApi::class.java)
    }

    /**
     * Provides the News service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the News service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideNewsApi(retrofit: Retrofit.Builder): NewsApi {
        return retrofit
            .baseUrl(NEWS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit.Builder {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }


            // Add logging interceptor
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(logging)
            }

            // Define retrofit cache
            val myCache = Cache(AuroraApplication.getApplicationContext().cacheDir, 5 * 1024 * 1024)

            // OkHttpClient
            val okHttpClient = OkHttpClient.Builder()
                .cache(myCache)
                .addInterceptor { chain ->
                    var request = chain.request()

                    /*
                    *  we initialize the request and change its header depending on whether
                    *  the device is connected to Internet or not.
                    */
                    request =
                        if (NetworkUtils.hasNetwork(AuroraApplication.getApplicationContext())!!)
                        /*
                        *  If there is Internet, get the cache that was stored 5 seconds ago.
                        *  If the cache is older than 5 seconds, then discard it,
                        *  and indicate an error in fetching the response.
                        *  The 'max-age' attribute is responsible for this behavior.
                        */
                            request.newBuilder().header(
                                "Cache-Control",
                                "public, max-age=" + 5
                            ).build()
                        else
                        /*
                        *  If there is no Internet, get the cache that was stored 7 days ago.
                        *  If the cache is older than 7 days, then discard it,
                        *  and indicate an error in fetching the response.
                        *  The 'max-stale' attribute is responsible for this behavior.
                        *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
                        */
                            request.newBuilder().header(
                                "Cache-Control",
                                "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                            ).build()
                    // End of if-else statement

                    // Add the modified request to the chain.
                    chain.proceed(request)
                }
                .build()

            return Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}