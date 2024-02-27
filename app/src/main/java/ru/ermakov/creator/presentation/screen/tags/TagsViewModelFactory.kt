package ru.ermakov.creator.presentation.screen.tags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.blog.GetTagsByCreatorIdUseCase
import ru.ermakov.creator.domain.useCase.tags.DeleteTagByIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class TagsViewModelFactory(
    private val getTagsByCreatorIdUseCase: GetTagsByCreatorIdUseCase,
    private val deleteTagByIdUseCase: DeleteTagByIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TagsViewModel::class.java)) {
            return TagsViewModel(
                getTagsByCreatorIdUseCase = getTagsByCreatorIdUseCase,
                deleteTagByIdUseCase = deleteTagByIdUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
