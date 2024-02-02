package com.android.imagesearch.Search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.imagesearch.data.SearchDocument
import com.android.imagesearch.retrofit.NetWorkClient
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {

    private var _searchResult = MutableLiveData<MutableList<SearchDocument>>()
    val searchResult : LiveData<MutableList<SearchDocument>> get() = _searchResult

    fun communicateNetWork(param: HashMap<String, String>) = viewModelScope.launch() {
        val responseData = NetWorkClient.searchNetWork.getSearch(param)
        val result = responseData.searchDocument?: mutableListOf()

        result.sortByDescending { it.datetime }
        _searchResult.value = result

//        responseData.searchDocument?.forEach {
//            _searchResult.value?.add(it)
//        }
////         검색 결과 datetime 최신순으로 정렬
//        _searchResult.value?.sortByDescending { it.datetime }
    }

    fun setUpSearchParameter(text: String): HashMap<String, String> {
        return hashMapOf(
            "query" to text,
            "sort" to "accuracy",
            "page" to "1",
            "size" to "80"
        )
    }
}