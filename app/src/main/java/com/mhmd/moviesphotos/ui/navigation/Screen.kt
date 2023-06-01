package com.mhmd.moviesphotos.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.mhmd.constants.NavigationArgumentsConstants

sealed class Screen(val route: String, val arguments: List<NamedNavArgument>) {


    object Splash : Screen(
        route = "splash",
        arguments = emptyList()
    )

    object MoviesList : Screen(
        route = "moviesList",
        arguments = emptyList()
    )

    object MoviePhotoFull : Screen(
        route = "moviePhotoFull",
        arguments = listOf(navArgument(NavigationArgumentsConstants.MOVIE_PHOTO_URL) {
            type = NavType.StringType
        })
    )

}

