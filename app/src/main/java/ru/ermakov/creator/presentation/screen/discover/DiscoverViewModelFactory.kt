package ru.ermakov.creator.presentation.screen.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.chooseCategory.UpdateCategoryInListUseCase
import ru.ermakov.creator.domain.useCase.discover.GetFilteredPostPageByUserIdUseCase
import ru.ermakov.creator.domain.useCase.following.DeleteLikeFromPostUseCase
import ru.ermakov.creator.domain.useCase.following.DeletePostByIdUseCase
import ru.ermakov.creator.domain.useCase.following.GetAllCategoriesUseCase
import ru.ermakov.creator.domain.useCase.following.GetPostByUserAndPostIdsUseCase
import ru.ermakov.creator.domain.useCase.following.InsertLikeToPostUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class DiscoverViewModelFactory(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getFilteredPostPageByUserIdUseCase: GetFilteredPostPageByUserIdUseCase,
    private val updateCategoryInListUseCase: UpdateCategoryInListUseCase,
    private val deletePostByIdUseCase: DeletePostByIdUseCase,
    private val getPostByUserAndPostIdsUseCase: GetPostByUserAndPostIdsUseCase,
    private val insertLikeToPostUseCase: InsertLikeToPostUseCase,
    private val deleteLikeFromPostUseCase: DeleteLikeFromPostUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiscoverViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DiscoverViewModel(
                getCurrentUserUseCase = getCurrentUserUseCase,
                getAllCategoriesUseCase = getAllCategoriesUseCase,
                getFilteredPostPageByUserIdUseCase = getFilteredPostPageByUserIdUseCase,
                updateCategoryInListUseCase = updateCategoryInListUseCase,
                deletePostByIdUseCase = deletePostByIdUseCase,
                getPostByUserAndPostIdsUseCase = getPostByUserAndPostIdsUseCase,
                insertLikeToPostUseCase = insertLikeToPostUseCase,
                deleteLikeFromPostUseCase = deleteLikeFromPostUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}