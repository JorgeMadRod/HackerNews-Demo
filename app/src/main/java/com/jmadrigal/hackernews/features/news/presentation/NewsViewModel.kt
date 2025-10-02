package com.jmadrigal.hackernews.features.news.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmadrigal.hackernews.features.news.data.Hit
import com.jmadrigal.hackernews.features.news.domain.DeleteHitUseCase
import com.jmadrigal.hackernews.features.news.domain.GetLatestNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getLatestNewsUseCase: GetLatestNewsUseCase,
    private val deleteHitUseCase: DeleteHitUseCase) : ViewModel() {

    private val _hits = MutableStateFlow<List<Hit>>(emptyList())
    val hits: StateFlow<List<Hit>> = _hits

    fun getLatestNews() {
        _hits.value = emptyList()
        viewModelScope.launch {
            val result = getLatestNewsUseCase()
            _hits.value = result

            Log.v("--->", "viewmodel getLatestNews")
        }
    }

    fun deleteHit(id:String){
        viewModelScope.launch {
            deleteHitUseCase(id)
        }
    }
}