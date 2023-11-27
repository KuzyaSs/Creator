package ru.ermakov.creator.domain.repository

import ru.ermakov.creator.domain.model.UserCategory

interface CategoryRepository {
    suspend fun getUserCategoriesByUserId(userId: String): List<UserCategory>
    suspend fun updateUserCategories(userId: String, userCategories: List<UserCategory>)
}