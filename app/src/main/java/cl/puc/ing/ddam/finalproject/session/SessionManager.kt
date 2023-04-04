package cl.puc.ing.ddam.finalproject.session

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("session", Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("logged_in", false)
    }

    fun setLoggedIn(loggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("logged_in", loggedIn).apply()
    }

    fun getUsername(): String? {
        return sharedPreferences.getString("username", null)
    }

    fun setUsername(username: String) {
        sharedPreferences.edit().putString("username", username).apply()
    }

    fun getUserId(): String {
        return sharedPreferences.getString("userId", null) ?: throw Exception("userid not found")
    }

    fun setUserId(userId: String) {
        sharedPreferences.edit().putString("userId", userId).apply()
    }

    fun clearSession() {
        sharedPreferences.edit().clear().apply()
    }
}
