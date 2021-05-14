package com.example.finalinterngumi.di

import com.example.finalinterngumi.ui.blank.BlankViewmodel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

// File này ta sẽ tạo ra các module của tầng ViewModel

val blankViewmodelDi: Module = module {
    viewModel { BlankViewmodel(get()) }
}
