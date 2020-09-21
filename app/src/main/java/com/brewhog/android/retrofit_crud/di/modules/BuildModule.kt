package com.brewhog.android.retrofit_crud.di.modules

import com.brewhog.android.retrofit_crud.view.BookActivity
import com.brewhog.android.retrofit_crud.view.BookFragment
import com.brewhog.android.retrofit_crud.view.ListFragment
import com.brewhog.android.retrofit_crud.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
abstract class BuildModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindBookActivity(): BookActivity

    @ContributesAndroidInjector
    abstract fun bindListFragment(): ListFragment

    @ContributesAndroidInjector
    abstract fun bindBookFragment(): BookFragment
}