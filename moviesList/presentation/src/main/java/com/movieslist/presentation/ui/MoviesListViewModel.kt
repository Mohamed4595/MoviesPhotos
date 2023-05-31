package com.movieslist.presentation.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.Queue
import com.mhmd.core.domain.UIComponent
import com.mhmd.core.domain.UiState
import com.mhmd.core.util.Logger
import com.movieslist.interactors.GetMovies
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MoviesListViewModel(
    private val getMovies: GetMovies,
    private val logger: Logger,
) : ViewModel() {

    val state: MutableState<UiState<MoviesListState>> = mutableStateOf(
        UiState.Loading(
            progressBarState = ProgressBarState.Loading,
            state = MoviesListState()
        )
    )

    init {
        onTriggerEvent(MoviesListEvents.GetMovies)
    }

    fun onTriggerEvent(event: MoviesListEvents) {
        when (event) {
            is MoviesListEvents.GetMovies -> {
                getMovies()
            }

            is MoviesListEvents.GetNextPageMovies -> {
                getNextPageMovies()
            }

            is MoviesListEvents.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }
        }
    }

    private fun getMovies() {
        val currentState = getCurrentState()
        getMovies.execute(currentState.page)
            .onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        state.value = UiState.Loading(
                            progressBarState = dataState.progressBarState,
                            state = currentState
                        )
                    }

                    is DataState.Success -> {
                        state.value =
                            UiState.Success(
                                state = currentState.copy(
                                    movies = dataState.data?.results ?: listOf(),
                                    totalPages = dataState.data?.totalPages ?: 0
                                )
                            )
                    }

                    is DataState.Error -> {
                        state.value = UiState.Error(
                            errorMessage = when (val uiComponent = dataState.uiComponent) {
                                is UIComponent.None -> uiComponent.message
                                is UIComponent.Dialog -> uiComponent.title + "\n" + uiComponent.description
                            },
                            state = currentState
                        )

                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getCurrentState(): MoviesListState {
        return when (val result = state.value) {
            is UiState.Error -> result.state
            is UiState.Loading -> result.state
            is UiState.Success -> result.state
        }
    }

    private fun getNextPageMovies() {
        if (state.value is UiState.Success) {
            val currentState = getCurrentState()

            getMovies.execute(
                currentState.page + 1
            ).onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        state.value = UiState.Success(
                            state = currentState.copy(isLoadingNextPage = dataState.progressBarState)
                        )
                    }

                    is DataState.Success -> {
                        state.value = UiState.Success(
                            state = currentState.copy(
                                movies = currentState.movies.plus(
                                    dataState.data?.results ?: listOf()
                                ), page = currentState.page + 1
                            )
                        )
                    }

                    is DataState.Error -> {
                        when (val uiComponent = dataState.uiComponent) {
                            is UIComponent.None -> logger.log("getMovies: ${uiComponent.message}")
                            else -> appendToMessageQueue(uiComponent)
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        val currentState = getCurrentState()
        val queue = currentState.errorQueue
        if (!queue.items.contains(uiComponent)) {
            queue.add(uiComponent)
            when (state.value) {
                is UiState.Error -> {
                    state.value = UiState.Error(
                        errorMessage = "",
                        state = currentState.copy(errorQueue = Queue(mutableListOf()))
                    )
                    state.value = UiState.Error(
                        errorMessage = "",
                        state = currentState.copy(errorQueue = queue)
                    )

                }

                is UiState.Loading -> {
                    state.value = UiState.Loading(
                        state = currentState.copy(errorQueue = Queue(mutableListOf()))
                    )
                    state.value = UiState.Loading(
                        state = currentState.copy(errorQueue = queue)
                    )

                }

                is UiState.Success -> {
                    state.value = UiState.Success(
                        state = currentState.copy(errorQueue = Queue(mutableListOf()))
                    )
                    state.value = UiState.Success(
                        state = currentState.copy(errorQueue = queue)
                    )
                }
            }
        }

    }

    private fun removeHeadMessage() {
        val currentState = getCurrentState()
        val queue = currentState.errorQueue
        if (!queue.isEmpty()) {
            queue.remove()
            when (state.value) {
                is UiState.Error -> {
                    state.value = UiState.Error(
                        errorMessage = "",
                        state = currentState.copy(errorQueue = Queue(mutableListOf()))
                    )
                    state.value = UiState.Error(
                        errorMessage = "",
                        state = currentState.copy(errorQueue = queue)
                    )

                }

                is UiState.Loading -> {
                    state.value = UiState.Loading(
                        state = currentState.copy(errorQueue = Queue(mutableListOf()))
                    )
                    state.value = UiState.Loading(
                        state = currentState.copy(errorQueue = queue)
                    )

                }

                is UiState.Success -> {
                    state.value = UiState.Success(
                        state = currentState.copy(errorQueue = Queue(mutableListOf()))
                    )
                    state.value = UiState.Success(
                        state = currentState.copy(errorQueue = queue)
                    )
                }
            }
        }

    }
}












