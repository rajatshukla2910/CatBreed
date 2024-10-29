package com.example.myapp.di

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.myapp.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    fun provideFragmentManager(activity: Activity): FragmentManager {
        return (activity as AppCompatActivity).supportFragmentManager
    }

    @Provides
    @ActivityScoped
    fun provideNavigator(fragmentManager: FragmentManager): Navigator {
        return Navigator(fragmentManager)
    }
}