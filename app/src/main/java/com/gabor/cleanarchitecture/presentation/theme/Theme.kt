/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gabor.cleanarchitecture.presentation.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightThemeColors = lightColors(

    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryVariant = md_theme_light_onPrimaryContainer,
//    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
//    secondaryContainer = md_theme_light_secondaryContainer,
    secondaryVariant = md_theme_light_onSecondaryContainer,
//    tertiary = md_theme_light_tertiary,
//    onTertiary = md_theme_light_onTertiary,
//    tertiaryContainer = md_theme_light_tertiaryContainer,
//    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    //   errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    // onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface
//    surfaceVariant = md_theme_light_surfaceVariant,
//    onSurfaceVariant = md_theme_light_onSurfaceVariant,
//    outline = md_theme_light_outline,
//    inverseOnSurface = md_theme_light_inverseOnSurface,
//    inverseSurface = md_theme_light_inverseSurface,
)
@SuppressLint("ConflictingOnColor")
private val DarkThemeColors = darkColors(

    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
//    primaryContainer = md_theme_dark_primaryContainer,
    primaryVariant = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
//    secondaryContainer = md_theme_dark_secondaryContainer,
    secondaryVariant = md_theme_dark_onSecondaryContainer,
//    tertiary = md_theme_dark_tertiary,
//    onTertiary = md_theme_dark_onTertiary,
//    tertiaryContainer = md_theme_dark_tertiaryContainer,
//    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    // errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    // onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface
//    surfaceVariant = md_theme_dark_surfaceVariant,
//    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
//    outline = md_theme_dark_outline,
//    inverseOnSurface = md_theme_dark_inverseOnSurface,
//    inverseSurface = md_theme_dark_inverseSurface,
)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        LightThemeColors
    } else {
        DarkThemeColors
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}
