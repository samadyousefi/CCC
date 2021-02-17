/*
 * Copyright (c) 2021 Mustafa Ozhan. All rights reserved.
 */

@file:Suppress("unused")

package com.github.mustafaozhan.ccc.client.di

import com.github.mustafaozhan.ccc.client.viewmodel.BarViewModel
import com.github.mustafaozhan.ccc.client.viewmodel.CalculatorViewModel
import com.github.mustafaozhan.ccc.client.viewmodel.CurrenciesViewModel
import com.github.mustafaozhan.ccc.client.viewmodel.MainViewModel
import com.github.mustafaozhan.ccc.client.viewmodel.RemoveAdsViewModel
import com.github.mustafaozhan.ccc.client.viewmodel.RemoveAdsViewModel
import com.github.mustafaozhan.ccc.client.viewmodel.SettingsViewModel
import com.github.mustafaozhan.ccc.common.log.kermit
import com.github.mustafaozhan.ccc.common.nsUserDefaults
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

fun initIOS(userDefaults: NSUserDefaults) = initClient(
    module {
        // todo koin doesn't support to have it as single then use with get() for Objective-C classes
        nsUserDefaults = userDefaults
    }
).also {
    kermit.d { "KoinIOS initIOS" }
}

actual val clientModule: Module = module {
    single { SettingsViewModel(get(), get(), get(), get()) }
    single { MainViewModel(get()) }
    single { CurrenciesViewModel(get(), get()) }
    single { CalculatorViewModel(get(), get(), get(), get()) }
    single { BarViewModel(get()) }
    single { RemoveAdsViewModel(get()) }
    single { BarViewModel(get()) }
}

fun <T> Koin.getDependency(objCClass: ObjCClass): T? = getOriginalKotlinClass(objCClass)?.let {
    getDependency(it)
}
