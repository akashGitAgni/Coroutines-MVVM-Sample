package com.akash.citysearch.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.citysearch.Repository
import com.akash.citysearch.SearchState
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel(
    private val repository: Repository
) :
    ViewModel() {

    private val privateState = MutableLiveData<SearchState>()

    val searchResultsLiveData: LiveData<SearchState> = privateState

    fun getSearchResults(searchText: String) = viewModelScope.launch {
        privateState.value = SearchState.Loading
        Timber.d("getPostsState() called....")
        privateState.value = repository.getSearchResults(searchText)
        Timber.d("repository.getPostsState() executed....")
    }

}