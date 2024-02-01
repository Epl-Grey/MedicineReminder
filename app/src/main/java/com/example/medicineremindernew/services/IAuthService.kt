package com.example.medicineremindernew.services

import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.gotrue.user.UserSession

interface IAuthService {
    suspend fun createUser(userEmail: String, userPassword: String, userName: String, diabetes: Boolean)
    suspend fun signIn(userEmail: String, userPassword: String)
    suspend fun signOut()
    fun getUser(): UserInfo
}