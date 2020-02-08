package com.akash.citysearch

import com.akash.citysearch.search.SearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataSourceModule = module {

    single { createRetrofitInstance() }

    single { get<Retrofit>().create(SearchApi::class.java) }

    single {
        RepositoryImpl(
            get(),
            get(),
            androidApplication()
        ) as Repository
    }

}

val networkStateModule = module {
    single { NetworkState(androidApplication()) }
}


val viewmodelModule = module {

    viewModel { SearchViewModel(get()) }
}

fun createRetrofitInstance(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://api.geonames.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}