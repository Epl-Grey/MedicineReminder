package com.example.medicineremindernew.di

import android.content.Context
import com.example.medicineremindernew.models.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    fun provideSupabaseClient(): SupabaseClient{
        return createSupabaseClient(
            supabaseUrl = ApiConfig.supabaseUrl,
            supabaseKey = ApiConfig.supabaseKey
        ) {
            install(Auth)
            install(Postgrest)
        }
    }
}