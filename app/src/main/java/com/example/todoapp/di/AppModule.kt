package com.example.todoapp.di

import android.content.Context
import com.example.todoapp.data.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * Created by PR72510 on 11/8/20.
 */

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = ToDoDatabase.getDatabase(context)

    @Provides
    fun provideTodoDao(db: ToDoDatabase) = db.todoDao()
}