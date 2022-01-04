package com.gabor.cleanarchitecture.presentation.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabor.cleanarchitecture.domain.movies.usecases.GetMoviesUseCase
import com.gabor.cleanarchitecture.domain.invoke
import com.gabor.cleanarchitecture.presentation.utils.LocaleUtils
import com.gabor.cleanarchitecture.presentation.utils.statehandler.ViewEventHolderImpl
import com.gabor.cleanarchitecture.presentation.utils.statehandler.ViewEventHolder
import com.gabor.cleanarchitecture.presentation.utils.statehandler.ViewStateHolderImpl
import com.gabor.cleanarchitecture.presentation.utils.statehandler.ViewStateHolder
import com.gabor.cleanarchitecture.presentation.utils.statehandler.handleErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) :
    ViewModel(),
    ViewEventHolder by ViewEventHolderImpl(),
    ViewStateHolder<MoviesViewState> by ViewStateHolderImpl() {

    private val tag = javaClass.simpleName
    private var appLocale = LocaleUtils.getCurrentLocale()


    fun onViewInitialised() {
        updateState {
            MoviesViewState(showLoading = true)
        }
        viewModelScope.launch {
            getMoviesUseCase().handle(onError = { error->
                Log.d(tag, "movies error response: $error")
                // hide the progressbar
                updateState {
                    it!!.copy(showLoading = false)
                }
                // pass the error to the error handler
                handleErrors(error){
                    // if the error handler can not handle the error, we can do it here
                }
            }, onSuccess = { response ->
                Log.d(tag, "movies success response: $response")
                updateState {
                    it!!.copy(showLoading = false, listItems = response)
                }
            })
        }
    }

    fun onConfigurationChanged(newLocale: Locale) {
        val isLocaleChanged = LocaleUtils.isLocaleChanged(appLocale, newLocale)
        Log.d(tag, "isLocaleChanged$isLocaleChanged")
        // reload the data with the new language
        if (isLocaleChanged){
            onViewInitialised()
        }
        appLocale = newLocale
    }
}

