package com.example.medicineremindernew.services

interface IAuthService {
    suspend fun createUser(userEmail: String, userPassword: String, userName: String, diabetes: Boolean)
    suspend fun signIn(userEmail: String, userPassword: String)
    suspend fun signOut()
}