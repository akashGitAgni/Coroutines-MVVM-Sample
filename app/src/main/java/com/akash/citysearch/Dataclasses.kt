package com.akash.citysearch

/*
*
* {
   "totalResultsCount":4590,
   "geonames":[
      {
         "adminCode1":"CA",
         "lng":"-122.41942",
         "geonameId":5391959,
         "toponymName":"San Francisco",
         "countryId":"6252001",
         "fcl":"P",
         "population":864816,
         "countryCode":"US",
         "name":"San Francisco",
         "fclName":"city, village,...",
         "countryName":"United States",
         "fcodeName":"seat of a second-order administrative division",
         "adminName1":"California",
         "lat":"37.77493",
         "fcode":"PPLA2"
      },

*
*
*
* */


data class Geonames(
    val name: String = "",
    val adminName1: String = "",
    val countryName: String = ""
)

data class SearchResults(val totalResultsCount: Int, val geonames: List<Geonames>)


sealed class SearchState {
    object Loading : SearchState()
    data class Error(val message: String?) : SearchState()
    data class SearchLoaded(val geonames: List<Geonames>) : SearchState()
}