package xyz.poolp.swatchtime.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import xyz.poolp.feature.time.TimeViewModel

val timeModule = module {
    viewModelOf(::TimeViewModel)
}
