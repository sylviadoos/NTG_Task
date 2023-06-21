package com.ntgclarity.currencyconverter.di

import android.content.Context
import androidx.room.Room
import androidx.room.TypeConverters

import com.ntgclarity.currencyconverter.database.CurrenciesDao
import com.ntgclarity.currencyconverter.database.CurrenciesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CurrenciesDatabase {
        return Room.databaseBuilder(
            appContext,
            CurrenciesDatabase::class.java,
            "Currencies"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

    @Provides
    fun provideChannelDao(currenciesDatabase: CurrenciesDatabase): CurrenciesDao {
        return currenciesDatabase.currenciesDao
    }

}