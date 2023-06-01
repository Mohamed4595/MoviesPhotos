package com.mhmd.moviesphotos.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import coil.ImageLoader
import com.google.accompanist.navigation.animation.composable
import com.movieslist.presentation.ui.MoviesListScreen
import com.movieslist.presentation.ui.MoviesListViewModel
import org.koin.androidx.compose.getViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addMoviesList(
    navController: NavController,
    imageLoader: ImageLoader,
    width: Int,
) {
    composable(
        route = Screen.MoviesList.route,
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
    ) {
        val viewModel = getViewModel<MoviesListViewModel>()
        MoviesListScreen(
            uiState = viewModel.state.value,
            events = viewModel::onTriggerEvent,
            navigateToFullScreen = {
                val encodedUrl =
                    URLEncoder.encode(it.photoUrl.value, StandardCharsets.UTF_8.toString())

                navController.navigate("${Screen.MoviePhotoFull.route}/${encodedUrl}")
            },
            imageLoader = imageLoader,
        )
    }
}
