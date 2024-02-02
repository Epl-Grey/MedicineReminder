package com.example.medicineremindernew.services

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SignOutScope
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject


class AuthService @Inject constructor(
    val supabase: SupabaseClient
) : IAuthService {

    override suspend fun createUser(userEmail: String, userPassword: String, userName: String){
        val user = supabase.auth.signUpWith(Email) {
            email = userEmail
            password = userPassword
            data = buildJsonObject {
                put("name", userName)
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

    override suspend fun isSignedIn(): Boolean {
//        val session = supabase.auth.refreshCurrentSession()
        return supabase.auth.currentSessionOrNull() != null
    }

    override fun getUser(): UserInfo {
        return supabase.auth.currentSessionOrNull()!!.user!!
    }
}