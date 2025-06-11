package ar.edu.unlam.mobile.scaffolding.di

import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepositoryImpl
import ar.edu.unlam.mobile.scaffolding.ui.screens.SignUpViewModel
import ar.edu.unlam.mobile.scaffolding.ui.screens.UserEditViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}
