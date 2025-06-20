package ar.edu.unlam.mobile.scaffolding.di

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.FeedApi
import ar.edu.unlam.mobile.scaffolding.data.repositories.FeedRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepositoryImpl
import ar.edu.unlam.mobile.scaffolding.ui.screens.SignUpViewModel
import ar.edu.unlam.mobile.scaffolding.ui.screens.UserEditViewModel
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
    fun provideUserRepository(): UserRepository {
        // TODO: Agregar datasources
        return UserRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideSignUpViewModel(repository: UserRepository): SignUpViewModel = SignUpViewModel(userRepository = repository)

    @Provides
    @Singleton
    fun provideUserEditViewModel(repository: UserRepository): UserEditViewModel = UserEditViewModel(userRepository = repository)

    @Provides
    @Singleton
    fun provideApi(): Retrofit {//provider de retrofit que en teoria cualquiera puede usarlo para el llamado a la API
        return Retrofit.Builder()
            .baseUrl("https://tuiter.fragua.com.ar/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideFeedApi() : FeedApi {//Esto lo coloque aqui pero relamente no estoy seguro si esta bien
        return provideApi().create(FeedApi::class.java)//pero como sigue siendo un llamado a una API lo dejare aqui de momento
    }

}
