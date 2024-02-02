package com.android.imagesearch.Collection

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.android.imagesearch.Search.SearchFragment
import com.android.imagesearch.Search.SearchViewModel
import com.android.imagesearch.data.SearchDocument
import com.android.imagesearch.databinding.FragmentCollectionBinding

class CollectionFragment : Fragment() {
    private val binding by lazy { FragmentCollectionBinding.inflate(layoutInflater) }
//    private val viewModel by lazy { ViewModelProvider(this).get(CollectionViewModel::class.java) }
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

    private fun initRecyclerView() {
        adapter = CollectionAdapter(SearchFragment.selectedItem)
        binding.rvCollaction.adapter = adapter
        binding.rvCollaction.layoutManager = GridLayoutManager(requireContext(), 2)
        // 리사이클러 뷰에서 이벤트 시 주변 아이템 깜빡이는 버그 해결 위해서 추가
        binding.rvCollaction.itemAnimator = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()

        // 아이템 클릭 시 삭제
        adapter.itemClick = object : CollectionAdapter.ItemClick {
            override fun onClick(position: Int) {
                SearchFragment.selectedItem.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        }
        Log.d(
            "com/android/imagesearch/Collection",
            "selectedItems = ${SearchFragment.selectedItem}"
        )
        super.onViewCreated(view, savedInstanceState)
    }
}