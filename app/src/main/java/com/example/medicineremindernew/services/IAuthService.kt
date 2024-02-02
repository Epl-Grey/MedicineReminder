package com.example.medicineremindernew.services

import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.gotrue.user.UserSession

interface IAuthService {
    suspend fun createUser(userEmail: String, userPassword: String, userName: String)
    suspend fun signIn(userEmail: String, userPassword: String)
    suspend fun signOut()
    suspend fun isSignedIn(): Boolean
    fun getUser(): UserInfo
}