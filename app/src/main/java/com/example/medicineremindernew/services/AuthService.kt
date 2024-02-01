package com.example.medicineremindernew.services

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.OtpType
import io.github.jan.supabase.gotrue.SignOutScope
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class AuthService @Inject constructor(
    private val supabase: SupabaseClient
) : IAuthService {

    override suspend fun createUser(userEmail: String, userPassword: String, userName: String, diabetes: Boolean){
        val user = supabase.auth.signUpWith(Email) {
            email = userEmail
            password = userPassword
            data = buildJsonObject {
                put("name", userName)
                put("diabetes", diabetes)
            }
        }
    }

    override suspend fun signIn(userEmail: String, userPassword: String) {
        supabase.auth.signInWith(Email) {
            email = userEmail
            password = userPassword
        }
    }

    override suspend fun signOut() {
        supabase.auth.signOut(scope = SignOutScope.LOCAL)
    }

    override fun getUser(): UserInfo {
        return supabase.auth.currentSessionOrNull()!!.user!!
    }
}