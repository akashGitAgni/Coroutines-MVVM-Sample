package com.akash.citysearch.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.akash.citysearch.Geonames
import com.akash.citysearch.R
import kotlinx.android.synthetic.main.adapter_search.view.*


class SearchAdapter(
    private var listOfPosts: List<Geonames>
) :
    RecyclerView.Adapter<PostViewHolder>() {

    private val filteredList: MutableList<Geonames> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_search, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = filteredList[position]
        holder.bindView(post)
        holder.itemView.setOnClickListener {
        }
    }

    fun updatePosts(posts: List<Geonames>) {
        listOfPosts = posts
        filteredList.clear()
        filteredList.addAll(listOfPosts)
        notifyDataSetChanged()
    }

    fun getFilter(): Filter {
        return exampleFilter
    }

    private val exampleFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults();

            if (constraint == null || constraint.isEmpty()) {
                results.values = ArrayList(listOfPosts)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }

                // results.values = listOfPosts.filter { it..contains(filterPattern) }
            }

            return results

        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            filteredList.clear()
            filteredList.addAll(results.values as List<Geonames>)
            notifyDataSetChanged()
        }
    }
}

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(geoNames: Geonames) {
        itemView.apply {
            city.text = geoNames.name
            state.text = geoNames.adminName1
            country.text = geoNames.countryName
        }
    }
}

