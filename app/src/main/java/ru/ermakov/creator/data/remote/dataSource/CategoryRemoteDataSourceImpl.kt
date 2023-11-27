package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.remote.api.CategoryApi
import ru.ermakov.creator.domain.model.UserCategory

class CategoryRemoteDataSourceImpl(
    private val categoryApi: CategoryApi,
    private val apiExceptionLocalizer: ApiExceptionLocalizer
) : CategoryRemoteDataSource {
    override suspend fun getUserCategoriesByUserId(userId: String): List<UserCategory> {
        val userCategoriesResponse = categoryApi.getUserCategoriesByUserId(userId = userId)
        if (userCategoriesResponse.isSuccessful) {
            userCategoriesResponse.body()?.let { userCategories ->
                return userCategories
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = userCategoriesResponse)
    }

    override suspend fun updateUserCategories(userId: String, userCategories: List<UserCategory>) {
        val response = categoryApi.updateUserCategories(
            userId = userId,
            userCategories = userCategories
        )
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }
}