package ru.ermakov.creator.presentation.screen.blog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.blog.FollowUseCase
import ru.ermakov.creator.domain.useCase.blog.GetCreatorByIdUseCase
import ru.ermakov.creator.domain.useCase.blog.IsFollowerByUserAndCreatorIdsUseCase
import ru.ermakov.creator.domain.useCase.blog.IsSubscriberUseCase
import ru.ermakov.creator.domain.useCase.blog.UnfollowUseCase
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class BlogViewModelFactory(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val isFollowerByUserAndCreatorIdsUseCase: IsFollowerByUserAndCreatorIdsUseCase,
    private val followUseCase: FollowUseCase,
    private val unfollowUseCase: UnfollowUseCase,
    private val isSubscriberUseCase: IsSubscriberUseCase,
    private val getCreatorByIdUseCase: GetCreatorByIdUseCase,
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
                getCreatorByIdUseCase = getCreatorByIdUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw Exception("Unknown ViewModel class")
    }
}