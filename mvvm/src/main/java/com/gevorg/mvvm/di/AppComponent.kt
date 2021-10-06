package com.gevorg.mvvm.di

import android.app.Application
import com.gevorg.mvvm.app.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Component(
    modules = [
         AppModule::class,
         ActivityModule::class,
         FragmentBuildersModule::class,
         ViewModelModule::class,
         AndroidSupportInjectionModule::class
    ]
)
@Singleton
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: App)
}
