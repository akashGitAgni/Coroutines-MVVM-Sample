package com.akash.citysearch.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.akash.citysearch.R
import com.akash.citysearch.SearchState
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = getViewModel()

        val searchAdapter = SearchAdapter(emptyList())
        searchResultsRecylerView?.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = searchAdapter
        }

        viewModel.searchResultsLiveData.observe(this.viewLifecycleOwner, Observer { searchState ->
            if (searchState == null) {
                return@Observer
            }

            when (searchState) {
                is SearchState.Loading -> {
                    setUpdateLayoutVisibilty(View.VISIBLE)
                }

                is SearchState.Error -> {
                    setUpdateLayoutVisibilty(View.GONE)
                    context?.let {
                        val message = searchState.message ?: getString(R.string.error)
                        Snackbar.make(activity!!.rootLayout, message, Snackbar.LENGTH_LONG).show()
                    }
                }

                is SearchState.SearchLoaded -> {
                    setUpdateLayoutVisibilty(View.GONE)
                    searchAdapter.updatePosts(searchState.geonames)
                }
            }
        })

        searchButton?.setOnClickListener {
            val text = searchText?.text?.toString()

            if (text != null && text.isNotEmpty()) {
                viewModel.getSearchResults(text)
            }
        }


    }


    private fun setUpdateLayoutVisibilty(value: Int) {
        activity?.updateLayout?.apply {
            visibility = value
        }
    }

}
