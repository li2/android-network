package me.li2.android.network.auth

import android.content.SharedPreferences

class AuthPreferences(private val sp: SharedPreferences) {

    fun saveToken(authToken: String) {
        sp.edit().putString(SP_KEY_AUTH_TOKEN, authToken).apply()
    }
    
    fun getToken() = sp.getString(SP_KEY_AUTH_TOKEN, "").orEmpty()

    fun deleteToken() = sp.edit().clear().apply()

    companion object {
        private const val SP_KEY_AUTH_TOKEN = "key_auth_token"
    }
}
