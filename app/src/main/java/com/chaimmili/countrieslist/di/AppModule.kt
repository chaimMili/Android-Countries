package com.chaimmili.countrieslist.di

import android.content.Context
import androidx.room.Room
import com.chaimmili.countrieslist.model.repository.CountriesRepository
import com.chaimmili.countrieslist.model.repository.CountriesRepositoryImpl
import com.chaimmili.countrieslist.model.db.AppDatabase
import com.chaimmili.countrieslist.model.db.Converters
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
        moshi: Moshi
    ): AppDatabase {
        Converters.initialize(moshi)
        return Room.databaseBuilder(context, AppDatabase::class.java, "countries_db").build()
    }

    @Singleton
    @Provides
    fun provideCountriesDao(db: AppDatabase) = db.countryDao()

    @Singleton
    @Provides
    fun provideCountriesRepository(
        countriesRepositoryImpl: CountriesRepositoryImpl
    ): CountriesRepository = countriesRepositoryImpl
}