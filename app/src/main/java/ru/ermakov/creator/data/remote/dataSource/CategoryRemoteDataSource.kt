package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.domain.model.UserCategory

interface CategoryRemoteDataSource {
    suspend fun getUserCategoriesByUserId(userId: String): List<UserCategory>

    suspend fun updateUserCategories(userId: String, userCategories: List<UserCategory>)
}