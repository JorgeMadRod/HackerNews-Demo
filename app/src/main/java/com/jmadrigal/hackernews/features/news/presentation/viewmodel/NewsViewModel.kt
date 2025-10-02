package com.jmadrigal.hackernews.features.news.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmadrigal.hackernews.features.news.data.model.Hit
import com.jmadrigal.hackernews.features.news.domain.usecase.DeleteHitUseCase
import com.jmadrigal.hackernews.features.news.domain.usecase.GetLatestNewsUseCase
import com.jmadrigal.hackernews.utils.GenericDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getLatestNewsUseCase: GetLatestNewsUseCase,
    private val deleteHitUseCase: DeleteHitUseCase) : ViewModel() {

    private val _hits = MutableStateFlow<GenericDataState<List<Hit>>>(GenericDataState.IDLE())
    val hits = _hits.asStateFlow()

    fun getLatestNews() {
        _hits.value = GenericDataState.IDLE()
        viewModelScope.launch {
            _hits.value = GenericDataState.Loading()
            val result = getLatestNewsUseCase()
            _hits.value = result
        }
    }

    fun deleteHit(id: String) {
        viewModelScope.launch {
            deleteHitUseCase(id)
        }
    }
}