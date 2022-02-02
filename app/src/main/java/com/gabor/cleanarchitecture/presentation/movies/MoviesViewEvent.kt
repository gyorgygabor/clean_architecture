package com.gabor.cleanarchitecture.presentation.movies

import java.util.Locale

sealed class MoviesViewEvent {
    object OnViewInitialized : MoviesViewEvent()
    object OnBottomReached : MoviesViewEvent()
    class OnOpenDetailsClicked(val movie: MovieViewItem) : MoviesViewEvent()
    class OnConfigurationChanged(val newLocale: Locale) : MoviesViewEvent()
}
