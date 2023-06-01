package com.mhmd.moviesphotos.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.ExperimentalComposeUiApi
import coil.ImageLoader
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.mhmd.moviesphotos.R
import com.mhmd.moviesphotos.ui.navigation.Screen
import com.mhmd.moviesphotos.ui.navigation.addMoviesList
import com.mhmd.moviesphotos.ui.navigation.addMoviePhotoFull
import com.mhmd.moviesphotos.ui.navigation.addSplash
import com.mhmd.moviesphotos.ui.theme.EPLTheme
import org.koin.android.ext.android.inject

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    private val imageLoader = inject<ImageLoader>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContent {

            EPLTheme {
                BoxWithConstraints {
                    val navController = rememberAnimatedNavController()
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route,
                        builder = {
                            addSplash(
                                navController = navController,
                                width = constraints.maxWidth / 2
                            )
                            addMoviesList(
                                navController = navController,
                                imageLoader = imageLoader.value,
                                width = constraints.maxWidth / 2,
                            )
                            addMoviePhotoFull(
                                navController = navController,
                                imageLoader = imageLoader.value,
                                width = constraints.maxWidth / 2,
                            )
                        }
                    )
                }
            }
        }
    }
}














