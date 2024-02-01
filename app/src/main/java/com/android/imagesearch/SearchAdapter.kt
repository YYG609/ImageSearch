package com.android.imagesearch

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.imagesearch.data.SearchDocument
import com.android.imagesearch.databinding.ItemRecyclerviewSearchBinding
import com.bumptech.glide.Glide
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class SearchAdapter(var mItems: MutableList<SearchDocument>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    interface ItemClick {
        fun onClick(item: SearchDocument, position: Int)
    }

    var itemClick: ItemClick? = null

    inner class ViewHolder(binding: ItemRecyclerviewSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val thumbnail = binding.ivSearchedImage
        val source = binding.tvSource
        val time = binding.tvTime
        val clickItem = binding.llMenu
        val heart = binding.ivLike
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerviewSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Glide로 url 받아오기
        Glide.with(holder.itemView.context)
            .load(mItems[position].thumbnail_url)
            .into(holder.thumbnail)
        // 출처 사이트
        holder.source.text = mItems[position].display_sitename
        // 아이템 클릭 시 이벤트
        holder.clickItem.setOnClickListener {
            itemClick?.onClick(mItems[position], position)
            if (holder.heart.visibility == INVISIBLE) {
                holder.heart.visibility = VISIBLE
            } else {
                holder.heart.visibility = INVISIBLE
            }
        }
        // 시간 포멧 변경
        val parsed = OffsetDateTime.parse(mItems[position].datetime)
        val formatter = parsed.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        holder.time.text = formatter
    }

    override fun getItemCount(): Int {
        return mItems.size
    }
}