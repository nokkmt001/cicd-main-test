package com.phat.mytestcicd.di

import com.phat.mytestcicd.BuildConfig
import com.phat.mytestcicd.data.Api
import com.phat.mytestcicd.data.local.AppDatabase
import com.phat.mytestcicd.data.local.UserLocalDataSource
import com.phat.mytestcicd.data.local.UserLocalDataSourceImpl
import com.phat.mytestcicd.data.remote.UserRemoteDataSource
import com.phat.mytestcicd.data.remote.UserRemoteDataSourceImpl
import com.phat.mytestcicd.data.repository.UserRepository
import com.phat.mytestcicd.data.repository.UserRepositoryImpl
import com.phat.mytestcicd.domain.GetUserUseCase
import com.phat.mytestcicd.presentation.viewmodel.UserViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("https://dns.google/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(Api::class.java)
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(if (enableLog) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
            .callTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    single<UserRemoteDataSource> { UserRemoteDataSourceImpl(get()) }
    single<UserLocalDataSource> { UserLocalDataSourceImpl(get()) }

    single { AppDatabase.getInstance(get()) }

    single<UserRepository> { UserRepositoryImpl(get(), get()) }

}

val domainModule = module {
    factory { GetUserUseCase(get()) }
}

val presentationModule = module {
    viewModel { UserViewModel(get()) }
}

val enableLog = BuildConfig.DEBUG

