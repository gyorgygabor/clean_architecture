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
package com.gabor.cleanarchitecture.presentation.utils

import java.util.Locale

object LocaleUtils {

    fun getCurrentLocale(): Locale = Locale.getDefault()
    fun isLocaleChanged(locale1: Locale, locale2: Locale): Boolean {
        return !(locale1.language == locale2.language && locale1.country == locale2.country)
    }
    fun getCurrentLocaleAsString(): String = Locale.getDefault().toLanguageTag()
}
