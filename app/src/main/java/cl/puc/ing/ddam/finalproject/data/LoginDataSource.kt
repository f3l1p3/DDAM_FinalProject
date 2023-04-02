package cl.puc.ing.ddam.finalproject.data

import android.util.Log
import cl.puc.ing.ddam.finalproject.data.model.LoggedInUser
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    private val db = Firebase.firestore
    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        try {
           
            val loginQuerySnapshot: QuerySnapshot=db.collection("logins")
                                                .whereEqualTo("email",username.lowercase())
                                                .whereEqualTo("password",password)
                                                .get().await()

            if(loginQuerySnapshot.isEmpty){
                Log.d("Login", "Login not found")
                throw Exception("Login not Found")
            }

            val login = loginQuerySnapshot.documents[0].data
            Log.d("Login", "Login found. Data: $login ")
            val email= login?.get("email").toString()
            val userID=login?.get("user_id").toString()

            val docSnapshot=db.collection("users").document(userID).get().await()

            if(!docSnapshot.exists()){
                Log.d("Login", "User not found")
                throw Exception("User not Found")
            }

            val user = docSnapshot.data
            Log.d("Login", "User found. Data: $user ")
            val displayName= user?.get("nickname").toString()
            return Result.Success(LoggedInUser(userID, displayName, email))

        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }

    suspend fun createLogin(email: String, password: String, userId: String): Boolean {
        val login= hashMapOf("email" to email,
                                                "password" to password,
                                                "user_id" to userId)
        val docRef = db.collection("logins").add(login).await()
        return docRef.id!=null
    }
}