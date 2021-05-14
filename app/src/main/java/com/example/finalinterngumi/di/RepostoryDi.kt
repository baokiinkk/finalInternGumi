package com.example.finalinterngumi.di

import com.example.finalinterngumi.data.api.ApiService
import com.example.finalinterngumi.data.repository.Repository
import com.example.finalinterngumi.data.repository.RepositoryImpl
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val RepositoryDi: Module = module {
    single<Repository> { RepositoryImpl(get()) }
}
