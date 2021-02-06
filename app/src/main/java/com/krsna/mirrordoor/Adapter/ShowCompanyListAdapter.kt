package com.krsna.mirrordoor.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.krsna.mirrordoor.Interfaces.Callbacks
import com.krsna.mirrordoor.Model.Company
import com.krsna.mirrordoor.R
import com.krsna.mirrordoor.databinding.ItemCompanyBinding

class ShowCompanyListAdapter(
    private val mContext: Context,
    val mList: MutableList<Company>,
    callback: Callbacks.ShowCompanyListAdapterListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Callbacks.ShowCompanyListAdapterListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemCompanyBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_company,
            parent,
            false
        )
        return TagViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TagViewHolder) {

            val binding = holder.binding
            val item = mList[position]

            binding.tvCompanyName.text = item.company_name
            binding.tvWebsite.text = item.website
            binding.tvType.text = item.type
            if(item.reviews != null) {
                binding.tvReviews.text = "⭐ ${item.reviews.toString()}"
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class TagViewHolder(val binding: ItemCompanyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {

            }
        }
    }
}