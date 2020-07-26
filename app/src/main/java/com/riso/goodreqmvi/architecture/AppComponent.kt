package com.riso.goodreqmvi.architecture

import com.riso.goodreqmvi.data.api.ApiService
import com.riso.goodreqmvi.presenters.ListPresenter
import com.riso.goodreqmvi.presenters.ApiInteractor
import com.riso.goodreqmvi.presenters.ApiInteractorImpl
import com.riso.goodreqmvi.presenters.ProfilePresenter
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [CommonModule::class, AnotherModule::class])
interface AppComponent {
    fun provideAppActivityManager(): AppActivityManager
    fun provideListPresenter(): ListPresenter
    fun provideProfilePresenter(): ProfilePresenter
    fun provideApiInteractor(): ApiInteractor
}

@Module
abstract class CommonModule{
    @Singleton
    @Binds
    abstract fun provideAppActivityManager(appActivityManagerImpl: AppActivityManagerImpl): AppActivityManager
    @Singleton
    @Binds
    abstract fun provideApiInteractor(apiInteractorImpl: ApiInteractorImpl): ApiInteractor

}
@Module
class AnotherModule{
    @Singleton
    @Provides
    fun provideApiService() = ApiService.apiService

}
