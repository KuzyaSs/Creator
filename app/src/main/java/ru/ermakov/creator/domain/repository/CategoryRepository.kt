package ru.ermakov.creator.domain.repository

import ru.ermakov.creator.domain.model.Category

interface CategoryRepository {
    suspend fun getAllCategories(): List<Category>

    suspend fun getCategoriesByUserId(userId: String): List<Category>

    suspend fun updateCategories(userId: String, categories: List<Category>)
}