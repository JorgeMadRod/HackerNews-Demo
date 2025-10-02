package com.jmadrigal.hackernews.features.news.domain

import com.jmadrigal.hackernews.features.news.data.NewsRepository
import javax.inject.Inject

class DeleteHitUseCase @Inject constructor(
    private val repository: NewsRepository) {

    suspend operator fun invoke(id:String) =
        repository.deleteNews(id)
}