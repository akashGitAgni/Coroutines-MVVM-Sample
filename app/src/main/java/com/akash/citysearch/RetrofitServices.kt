package com.akash.citysearch

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
const val USER_NAME = "xyz"
interface SearchApi {

    /*
    *
    * When searching with the text field, pass the string to the following API:
http://api.geonames.org/searchJSON?name_startsWith={text}&maxRows=10&username=keep_truckin

Example API call:
http://api.geonames.org/searchJSON?name_startsWith=san%20francis&maxRows=10&username=keep_truckin
*
*
* http://api.geonames.org/search?name_startsWith=San%20Francisco&maxRows=10&username=keep_truckin&type=json

    *
    * */

    @GET("search")
    suspend fun getsearchResults(
        @Query("name_startsWith") searchText: String,
        @Query("maxRows") maxRows: Int = 10,
        @Query("username") username: String = USER_NAME,
        @Query("type") type: String = "json"
    ): Response<SearchResults>
}

