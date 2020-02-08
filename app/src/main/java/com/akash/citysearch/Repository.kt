package com.akash.citysearch

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class RepositoryImpl(
    private val searchApi: SearchApi,
    private val networkState: NetworkState,
    private val application: Application
) : Repository {

    override suspend fun getSearchResults(searchText: String): SearchState =
        withContext(Dispatchers.IO) {
            //Switched context to background thread
            if (!networkState.isConnected()) {
                return@withContext SearchState.Error(application.getString(R.string.not_connected))
            }

            val response = try {
                searchApi.getsearchResults(searchText)
            } catch (e: Throwable) {
                //Sending a generic exception to the view for now

                Timber.e(e)
                return@withContext SearchState.Error(application.getString(R.string.network_error))
            }

            return@withContext if (response.isSuccessful) {
                response.body()?.let {
                    SearchState.SearchLoaded(it.geonames)
                } ?: SearchState.Error(application.getString(R.string.emty_body))
            } else {
                Timber.e(response.message())
                SearchState.Error(response.message())
            }
        }


}

interface Repository {
    suspend fun getSearchResults(searchText: String): SearchState
}