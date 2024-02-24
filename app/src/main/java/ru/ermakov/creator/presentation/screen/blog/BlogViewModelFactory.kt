package ru.ermakov.creator.presentation.screen.blog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.blog.FollowUseCase
import ru.ermakov.creator.domain.useCase.blog.GetFilteredPostPageByUserAndCreatorIdsUseCase
import ru.ermakov.creator.domain.useCase.blog.GetTagsByCreatorIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCreatorByUserIdUseCase
import ru.ermakov.creator.domain.useCase.blog.IsFollowerByUserAndCreatorIdsUseCase
import ru.ermakov.creator.domain.useCase.blog.IsSubscriberUseCase
import ru.ermakov.creator.domain.useCase.blog.UnfollowUseCase
import ru.ermakov.creator.domain.useCase.following.DeleteLikeFromPostUseCase
import ru.ermakov.creator.domain.useCase.following.DeletePostByIdUseCase
import ru.ermakov.creator.domain.useCase.following.GetPostByUserAndPostIdsUseCase
import ru.ermakov.creator.domain.useCase.following.InsertLikeToPostUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class BlogViewModelFactory(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val isFollowerByUserAndCreatorIdsUseCase: IsFollowerByUserAndCreatorIdsUseCase,
    private val followUseCase: FollowUseCase,
    private val unfollowUseCase: UnfollowUseCase,
    private val isSubscriberUseCase: IsSubscriberUseCase,
    private val getCreatorByUserIdUseCase: GetCreatorByUserIdUseCase,
    private val getFilteredPostPageByUserAndCreatorIdsUseCase: GetFilteredPostPageByUserAndCreatorIdsUseCase,
    private val getTagsByCreatorId: GetTagsByCreatorIdUseCase,
    private val deletePostByIdUseCase: DeletePostByIdUseCase,
    private val getPostByUserAndPostIdsUseCase: GetPostByUserAndPostIdsUseCase,
    private val insertLikeToPostUseCase: InsertLikeToPostUseCase,
    private val deleteLikeFromPostUseCase: DeleteLikeFromPostUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BlogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BlogViewModel(
                getCurrentUserIdUseCase = getCurrentUserIdUseCase,
                isFollowerByUserAndCreatorIdsUseCase = isFollowerByUserAndCreatorIdsUseCase,
                followUseCase = followUseCase,
                unfollowUseCase = unfollowUseCase,
                isSubscriberUseCase = isSubscriberUseCase,
                getCreatorByUserIdUseCase = getCreatorByUserIdUseCase,
                getFilteredPostPageByUserAndCreatorIdsUseCase = getFilteredPostPageByUserAndCreatorIdsUseCase,
                getTagsByCreatorId = getTagsByCreatorId,
                deletePostByIdUseCase = deletePostByIdUseCase,
                getPostByUserAndPostIdsUseCase = getPostByUserAndPostIdsUseCase,
                insertLikeToPostUseCase = insertLikeToPostUseCase,
                deleteLikeFromPostUseCase = deleteLikeFromPostUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw Exception("Unknown ViewModel class")
    }
}