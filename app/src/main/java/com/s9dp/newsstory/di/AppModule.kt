package com.s9dp.newsstory.di

import android.content.Context
import android.net.ConnectivityManager
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.s9dp.newsstory.BuildConfig
import com.s9dp.newsstory.data.services.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }


    // create for retrofit object
    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun getInstance(client: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        val format = Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
            allowStructuredMapKeys = true
            encodeDefaults = true
        }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(format.asConverterFactory(contentType))
            .client(client)
            .build()
    }

    // create for retrofit instance
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }


    //create for network connectivity manager object
    @Singleton
    @Provides
    fun provideInternetConnectivity(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

}