package com.gabor.cleanarchitecture.presentation.utils

import java.util.Locale

object LocaleUtils {

    fun getCurrentLocale(): Locale = Locale.getDefault()
    fun isLocaleChanged(locale1: Locale, locale2: Locale): Boolean {
        return !(locale1.language == locale2.language && locale1.country == locale2.country)

    }
    fun getCurrentLocaleAsString(): String = Locale.getDefault().toLanguageTag()
}