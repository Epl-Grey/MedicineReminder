package com.example.medicineremindernew.di

import com.example.medicineremindernew.models.ApiKey
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
            supabaseUrl = ApiKey.supabaseUrl,
            supabaseKey = ApiKey.supabaseKey
        ) {
            install(Auth)
            install(Postgrest)
        }
    }
}