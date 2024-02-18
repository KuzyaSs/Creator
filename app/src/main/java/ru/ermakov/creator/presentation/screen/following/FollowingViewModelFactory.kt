package ru.ermakov.creator.presentation.screen.following

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.account.SignOutUseCase
import ru.ermakov.creator.domain.useCase.chooseCategory.UpdateCategoryInListUseCase
import ru.ermakov.creator.domain.useCase.following.DeleteLikeFromPostUseCase
import ru.ermakov.creator.domain.useCase.following.DeletePostByIdUseCase
import ru.ermakov.creator.domain.useCase.following.GetAllCategoriesUseCase
import ru.ermakov.creator.domain.useCase.following.GetFilteredFollowingPostPageByUserIdUseCase
import ru.ermakov.creator.domain.useCase.following.GetPostByUserAndPostIdsUseCase
import ru.ermakov.creator.domain.useCase.following.InsertLikeToPostUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class FollowingViewModelFactory(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getFilteredFollowingPostPageByUserIdUseCase: GetFilteredFollowingPostPageByUserIdUseCase,
    private val updateCategoryInListUseCase: UpdateCategoryInListUseCase,
    private val deletePostByIdUseCase: DeletePostByIdUseCase,
    private val getPostByUserAndPostIdsUseCase: GetPostByUserAndPostIdsUseCase,
    private val insertLikeToPostUseCase: InsertLikeToPostUseCase,
    private val deleteLikeFromPostUseCase: DeleteLikeFromPostUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FollowingViewModel(
                getCurrentUserUseCase = getCurrentUserUseCase,
                getAllCategoriesUseCase = getAllCategoriesUseCase,
                getFilteredFollowingPostPageByUserIdUseCase = getFilteredFollowingPostPageByUserIdUseCase,
                updateCategoryInListUseCase = updateCategoryInListUseCase,
                deletePostByIdUseCase = deletePostByIdUseCase,
                getPostByUserAndPostIdsUseCase = getPostByUserAndPostIdsUseCase,
                insertLikeToPostUseCase = insertLikeToPostUseCase,
                deleteLikeFromPostUseCase = deleteLikeFromPostUseCase,
                signOutUseCase = signOutUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}