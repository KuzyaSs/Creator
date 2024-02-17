package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.remote.api.CategoryApi
import ru.ermakov.creator.domain.model.Category

class CategoryRemoteDataSourceImpl(
    private val categoryApi: CategoryApi,
    private val apiExceptionLocalizer: ApiExceptionLocalizer
) : CategoryRemoteDataSource {
    override suspend fun getAllCategories(): List<Category> {
        val categoriesResponse = categoryApi.getAllCategories()
        if (categoriesResponse.isSuccessful) {
            categoriesResponse.body()?.let { categories ->
                return categories
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = categoriesResponse)
    }


    override suspend fun getCategoriesByUserId(userId: String): List<Category> {
        val categoriesResponse = categoryApi.getCategoriesByUserId(userId = userId)
        if (categoriesResponse.isSuccessful) {
            categoriesResponse.body()?.let { categories ->
                return categories
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = categoriesResponse)
    }

    override suspend fun updateCategories(userId: String, categories: List<Category>) {
        val response = categoryApi.updateCategories(
            userId = userId,
            categories = categories
        )
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }
}