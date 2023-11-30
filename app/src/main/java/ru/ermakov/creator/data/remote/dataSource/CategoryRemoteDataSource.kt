package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.domain.model.Category

interface CategoryRemoteDataSource {
    suspend fun getCategoriesByUserId(userId: String): List<Category>

    suspend fun updateCategories(userId: String, categories: List<Category>)
}