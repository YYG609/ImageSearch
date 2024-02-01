package com.android.imagesearch

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.imagesearch.data.SearchDocument
import com.android.imagesearch.databinding.FragmentSearchBinding
import com.android.imagesearch.retrofit.NetWorkClient
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }

    private var searchResult = mutableListOf<SearchDocument>()

    companion object {
        var selectedItem = mutableListOf<SelectedItem>()
    }

    private lateinit var adapter: SearchAdapter

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
        adapter = SearchAdapter(searchResult)
        binding.rvMain.adapter = adapter
        binding.rvMain.layoutManager = GridLayoutManager(requireContext(), 2)
        // 리사이클러 뷰에서 이벤트 시 주변 아이템 깜빡이는 버그 해결 위해서 추가
        binding.rvMain.itemAnimator = null

        binding.btnSearch.setOnClickListener {
            communicateNetWork(setUpSearchParameter(binding.etSearch.text.toString()))
            // 마지막 검색어 저장
            saveData()
            // 키보드 숨기기
            binding.btnSearch.clearFocus()
            val inputMethodManager =
                activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        // 아이템 클릭 시 보관함으로
        adapter.itemClick = object : SearchAdapter.ItemClick {
            override fun onClick(item: SearchDocument, position: Int) {
                val selectedThumbnail = item.thumbnail_url
                val selectedSource = item.display_sitename
                val selectedTime = item.datetime
                if (selectedItem.contains(
                        SelectedItem(
                            selectedThumbnail,
                            selectedSource,
                            selectedTime
                        )
                    )
                ) {
                    selectedItem.remove(
                        SelectedItem(
                            selectedThumbnail,
                            selectedSource,
                            selectedTime
                        )
                    )
                } else {
                    selectedItem.add(SelectedItem(selectedThumbnail, selectedSource, selectedTime))
                }
                Log.d("Search", "SelectedItem = ${selectedItem}")
            }
        }

        // 플로팅 버튼
        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 1000 }
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 1000 }
        var isTop = true
        binding.rvMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (binding.rvMain.canScrollVertically(-1) == false && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.fbFloating.startAnimation(fadeOut)
                    binding.fbFloating.visibility = View.GONE
                    isTop = true
                } else {
                    if (isTop) {
                        binding.fbFloating.visibility = View.VISIBLE
                        binding.fbFloating.startAnimation(fadeIn)
                        isTop = false
                    }
                }
            }
        })
        binding.fbFloating.setOnClickListener {
            binding.rvMain.smoothScrollToPosition(0)
        }

        // 앱 재시작 시 마지막 검색어 불러오기
        loadData()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun communicateNetWork(param: HashMap<String, String>) = lifecycleScope.launch() {
        val responseData = NetWorkClient.searchNetWork.getSearch(param)
        searchResult.clear()

        responseData.searchDocument?.forEach {
            searchResult.add(it)
        }
        // 검색 결과 datetime 최신순으로 정렬
        searchResult.sortByDescending { it.datetime }

        adapter.mItems = searchResult
        adapter.notifyDataSetChanged()
    }

    private fun setUpSearchParameter(text: String): HashMap<String, String> {
        return hashMapOf(
            "query" to text,
            "sort" to "accuracy",
            "page" to "1",
            "size" to "80"
        )
    }

    private fun saveData() {
        val pref = activity?.getSharedPreferences("pref", MODE_PRIVATE)
        val edit = pref?.edit()
        edit?.putString("searchWord", binding.etSearch.text.toString())
        edit?.apply()
    }

    private fun loadData() {
        val pref = activity?.getSharedPreferences("pref", MODE_PRIVATE)
        binding.etSearch.setText(pref?.getString("searchWord", ""))
    }
}