package ru.ermakov.creator.data.repository

import ru.ermakov.creator.data.remote.dataSource.CategoryRemoteDataSource
import ru.ermakov.creator.domain.model.Category
import ru.ermakov.creator.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val categoryRemoteDataSource: CategoryRemoteDataSource
) : CategoryRepository {
    override suspend fun getAllCategories(): List<Category> {
        return categoryRemoteDataSource.getAllCategories()
    }

    override suspend fun getCategoriesByUserId(userId: String): List<Category> {
        return categoryRemoteDataSource.getCategoriesByUserId(userId = userId)
    }

    override suspend fun updateCategories(userId: String, categories: List<Category>) {
        return categoryRemoteDataSource.updateCategories(
            userId = userId,
            categories = categories
        )
    }
}