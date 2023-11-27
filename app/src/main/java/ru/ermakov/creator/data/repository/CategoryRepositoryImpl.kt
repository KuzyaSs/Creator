package ru.ermakov.creator.data.repository

import ru.ermakov.creator.data.remote.dataSource.CategoryRemoteDataSource
import ru.ermakov.creator.domain.model.UserCategory
import ru.ermakov.creator.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val categoryRemoteDataSource: CategoryRemoteDataSource
) : CategoryRepository {
    override suspend fun getUserCategoriesByUserId(userId: String): List<UserCategory> {
        return categoryRemoteDataSource.getUserCategoriesByUserId(userId = userId)
    }

    override suspend fun updateUserCategories(userId: String, userCategories: List<UserCategory>) {
        return categoryRemoteDataSource.updateUserCategories(
            userId = userId,
            userCategories = userCategories
        )
    }
}