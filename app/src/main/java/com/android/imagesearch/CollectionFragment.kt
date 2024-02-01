package com.android.imagesearch

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.android.imagesearch.data.SearchDocument
import com.android.imagesearch.databinding.FragmentCollectionBinding
import com.android.imagesearch.databinding.FragmentSearchBinding

class CollectionFragment : Fragment() {
    private val binding by lazy { FragmentCollectionBinding.inflate(layoutInflater) }
    private lateinit var adapter: CollectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = CollectionAdapter(SearchFragment.selectedItem)
        binding.rvCollaction.adapter = adapter
        binding.rvCollaction.layoutManager = GridLayoutManager(requireContext(), 2)

        // 아이템 클릭 시 삭제
        adapter.itemClick = object : CollectionAdapter.ItemClick {
            override fun onClick(position: Int) {
                SearchFragment.selectedItem.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        }
        Log.d("Collection", "selectedItems = ${SearchFragment.selectedItem}")
        super.onViewCreated(view, savedInstanceState)
    }
}