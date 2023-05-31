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
import com.fullScreenView.persentation.ZoomableImageScreen
import com.google.accompanist.navigation.animation.composable
import com.mhmd.constants.NavigationArgumentsConstants
import com.movieslist.domain.PhotoUrl
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalAnimationApi
fun NavGraphBuilder.addMoviePhotoFull(
    navController: NavController,
    imageLoader: ImageLoader,
    width: Int,
) {
    composable(
        route = Screen.MoviePhotoFull.route + "/{${NavigationArgumentsConstants.MOVIE_PHOTO_URL}}",
        arguments = Screen.MoviePhotoFull.arguments,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {
        val decodedUrl = URLDecoder.decode(it.arguments?.getString(
            NavigationArgumentsConstants.MOVIE_PHOTO_URL
        ) ?: "", StandardCharsets.UTF_8.toString())

        ZoomableImageScreen(
            imageLoader= imageLoader,
            moviePoster = PhotoUrl(decodedUrl)
        ) {
            navController.navigateUp()
        }
    }
}