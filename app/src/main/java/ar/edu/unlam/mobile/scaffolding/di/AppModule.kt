package ar.edu.unlam.mobile.scaffolding.di

import android.app.Application
import androidx.room.Room
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.AppDatabase
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.UNLaMSocialApi
import ar.edu.unlam.mobile.scaffolding.data.repositories.FeedRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.FeedRepositoryImpl
import ar.edu.unlam.mobile.scaffolding.data.repositories.FeedRepositoryTest
import ar.edu.unlam.mobile.scaffolding.data.repositories.FeedRepositoryTestImpl
import ar.edu.unlam.mobile.scaffolding.data.repositories.PostRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.PostRepositoryImpl
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserFavRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserFavRepositoryImpl
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUNLaMSocialApi(): UNLaMSocialApi {
        val logging =
            HttpLoggingInterceptor().apply {
                level =
                    HttpLoggingInterceptor.Level.BODY
            }

        val client =
            OkHttpClient
                .Builder()
                .addInterceptor(logging)
                .build()

        return Retrofit
            .Builder()
            .baseUrl(UNLaMSocialApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UNLaMSocialApi::class.java)
    }

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
    ): UserRepository = UserRepositoryImpl(api, db.getUserDao())

    @Provides
    @Singleton
    fun provideFeedRepository(
        api: UNLaMSocialApi,
        db: AppDatabase,
    ): FeedRepository = FeedRepositoryImpl(api, db.getUserDao())

    @Provides
    @Singleton
    fun providePostRepository(
        api: UNLaMSocialApi,
        db: AppDatabase,
    ): PostRepository = PostRepositoryImpl(api, db.getUserDao())

    @Provides
    @Singleton
    fun provideUserFavRepository(db: AppDatabase): UserFavRepository = UserFavRepositoryImpl(db.getUserFavDao())

    @Provides
    @Singleton
    fun providesFeedRepositoryTest(
        api: UNLaMSocialApi,
        db: AppDatabase,
    ): FeedRepositoryTest = FeedRepositoryTestImpl(api, db.getUserDao())
}
