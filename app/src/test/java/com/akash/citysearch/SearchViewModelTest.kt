package com.akash.citysearch

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.akash.citysearch.search.SearchViewModel
import com.akash.searchpostsandcomments.CoroutinesTestRule
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get

class SearchViewModelTest : AutoCloseKoinTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    lateinit var subject: SearchViewModel
    private val networkState = mockk<NetworkState>()
    private val application: Application = mockk()
    @Before
    fun before() {

        startKoin {
            androidContext(application)
            modules(listOf(dataSourceModule, viewmodelModule, networkStateModule))
        }

        loadKoinModules(module {
            single(override = true) { networkState }
        })

        every { application.getString(any()) } returns "Test String"
        every { networkState.isConnected() } returns true
    }

    // Tod name change
    @Test
    fun test_refresh() = runBlocking {
        subject = get()

        subject.getSearchResults("San Francisco").join()
        subject.searchResultsLiveData.observeForTesting {
            assertThat(subject.searchResultsLiveData.value).isInstanceOf(SearchState.SearchLoaded::class.java)
        }

    }
}

fun <T> LiveData<T>.observeForTesting(block: () -> Unit) {
    val observer = Observer<T> { Unit }
    try {
        observeForever(observer)
        block()
    } finally {
        removeObserver(observer)
    }
}