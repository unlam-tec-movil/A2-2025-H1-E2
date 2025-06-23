package ar.edu.unlam.mobile.scaffolding.di

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.UNLaMSocialApi
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepositoryImpl
import ar.edu.unlam.mobile.scaffolding.ui.screens.user.SignUpViewModel
import ar.edu.unlam.mobile.scaffolding.ui.screens.user.UserEditViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUNLaMSocialApi(): UNLaMSocialApi =
        Retrofit
            .Builder()
            .baseUrl(UNLaMSocialApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UNLaMSocialApi::class.java)

    @Provides
    @Singleton
    fun provideUserRepository(api: UNLaMSocialApi): UserRepository = UserRepositoryImpl(api)
}
