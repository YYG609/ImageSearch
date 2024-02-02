package com.android.imagesearch.Collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.imagesearch.Search.SearchFragment
import com.android.imagesearch.data.SearchDocument

//class CollectionViewModel : ViewModel() {
//    private var _searchResult = MutableLiveData<MutableList<SearchDocument>>()
//    val searchResult : LiveData<MutableList<SearchDocument>> get() = _searchResult
//
//    fun removeItem(position: Int){
//        SearchFragment.selectedItem.removeAt(position)
//    }
//}