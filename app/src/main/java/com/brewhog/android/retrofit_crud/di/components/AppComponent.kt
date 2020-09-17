package com.brewhog.android.retrofit_crud.di.components

import android.app.Application
import com.brewhog.android.retrofit_crud.App
import com.brewhog.android.retrofit_crud.di.modules.AppModule
import com.brewhog.android.retrofit_crud.di.modules.BuildModule
import com.brewhog.android.retrofit_crud.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        BuildModule::class,
        AppModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: DaggerApplication?)
    //    fun inject(app: App)

}