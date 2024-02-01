package com.android.imagesearch

import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.imagesearch.databinding.ItemRecyclerviewSearchBinding
import com.bumptech.glide.Glide
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class CollectionAdapter(var mItems: MutableList<SelectedItem>) :
    RecyclerView.Adapter<CollectionAdapter.ViewHolder>() {

    interface ItemClick {
        fun onClick(position: Int)
    }

    var itemClick: ItemClick? = null

    inner class ViewHolder(binding: ItemRecyclerviewSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val thumbnail = binding.ivSearchedImage
        val source = binding.tvSource
        val time = binding.tvTime
        val clickItem = binding.llMenu
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
            .load(mItems[position].thumbnail)
            .into(holder.thumbnail)
        // 출처 사이트
        holder.source.text = mItems[position].time
        // 아이템 클릭 이벤트
        holder.clickItem.setOnClickListener {
            itemClick?.onClick(position)
        }
        // 시간 포멧 변경
        val parsed = OffsetDateTime.parse(mItems[position].time)
        val formatter = parsed.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        holder.time.text = formatter
    }

    override fun getItemCount(): Int {
        return mItems.size
    }
}