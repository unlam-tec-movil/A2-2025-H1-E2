package ar.edu.unlam.mobile.scaffolding.di

import android.app.Application
import androidx.room.Room
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.AppDatabase
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.UNLaMSocialApi
import ar.edu.unlam.mobile.scaffolding.data.repositories.FeedRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.FeedRepositoryImpl
import ar.edu.unlam.mobile.scaffolding.data.repositories.PostRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.PostRepositoryImpl
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepositoryImpl
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

    @Singleton
    @Provides
    fun providesAppDatabase(app: Application): AppDatabase =
        Room
            .databaseBuilder(
                app,
                AppDatabase::class.java,
                "app.db",
            ).build()

    @Provides
    @Singleton
    fun provideUserRepository(
        api: UNLaMSocialApi,
        db: AppDatabase,
    ): UserRepository = UserRepositoryImpl(api, db.getUserDao(), db.getUserFavDao())

    @Provides
    @Singleton
    fun provideFeedRepository(api: UNLaMSocialApi): FeedRepository = FeedRepositoryImpl(api)

    @Provides
    @Singleton
    fun providePostRepository(api: UNLaMSocialApi): PostRepository = PostRepositoryImpl(api)
}
