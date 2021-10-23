package com.example.kotlinexample.di.module

import android.content.Context
import com.example.kotlinexample.BuildConfig
import com.example.kotlinexample.data.api.ApiHelper
import com.example.kotlinexample.data.api.ApiHelperImpl
import com.example.kotlinexample.data.api.ApiService
import com.example.kotlinexample.data.database.DatabaseBuilder
import com.example.kotlinexample.data.database.DatabaseHelper
import com.example.kotlinexample.data.database.DatabaseHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class HttpLogInterceptor

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthInterceptor

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL;

    @HttpLogInterceptor
    @Provides
    fun provideHttpLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(@HttpLogInterceptor httpLoggingInterceptor: HttpLoggingInterceptor) = if(BuildConfig.DEBUG) {
        val okHttpClient = OkHttpClient
            .Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        okHttpClient.addInterceptor(httpLoggingInterceptor)

        okHttpClient.build()
    } else {
        val okHttpClient = OkHttpClient
            .Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        okHttpClient.addInterceptor(httpLoggingInterceptor)

        okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelperImpl: ApiHelperImpl) : ApiHelper = apiHelperImpl

    @Provides
    @Singleton
    fun provideDBHelper(@ApplicationContext context: Context) : DatabaseHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(context))
}