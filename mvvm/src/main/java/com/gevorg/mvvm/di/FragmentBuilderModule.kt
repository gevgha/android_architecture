package com.gevorg.mvvm.di

import com.gevorg.mvvm.fragments.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
@Module
abstract class FragmentBuildersModule {

   @ContributesAndroidInjector
   abstract fun contributeNewsFragment(): LoginFragment

}