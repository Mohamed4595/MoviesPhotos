package com.fullScreenView.persentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mhmd.components.DefaultScreenUI
import com.mhmd.components.MoviesTopAppBar
import com.mhmd.components.R
import com.movieslist.domain.PhotoUrl

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun ZoomableImageScreen(
    moviePoster: PhotoUrl,
    imageLoader: ImageLoader,
    navigateUp: () -> Unit
) {
    val scale = remember { mutableStateOf(1f) }
    val rotationState = remember { mutableStateOf(1f) }

    DefaultScreenUI {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MoviesTopAppBar(
                prefixIconRes = R.drawable.ic_arrow_back,
                title = stringResource(id = R.string.zoomable_image),
                onPrefixIconClick = {
                    navigateUp()
                }
            )

            Box(
                modifier = Modifier
                    .clip(RectangleShape) // Clip the box content
                    .fillMaxSize() // Give the size you want...
                    .background(MaterialTheme.colors.background)
                    .pointerInput(Unit) {
                        detectTransformGestures { _, _, zoom, rotation ->
                            scale.value *= zoom
                            rotationState.value += rotation
                        }
                    }
            ) {
                val painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(moviePoster.value)
                        .build(), imageLoader
                )
                Image(
                    modifier = Modifier.fillMaxSize()
                        .align(Alignment.Center) // keep the image centralized into the Box
                        .graphicsLayer(
                            // adding some zoom limits (min 50%, max 200%)
                            scaleX = maxOf(.5f, minOf(3f, scale.value)),
                            scaleY = maxOf(.5f, minOf(3f, scale.value)),
                            rotationZ = rotationState.value
                        ),
                    contentDescription = null,
                    painter = painter
                )
            }
        }


    }
}








