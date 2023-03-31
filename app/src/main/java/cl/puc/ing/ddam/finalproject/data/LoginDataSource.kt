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
           
            val querySnapshot: QuerySnapshot=db.collection("logins")
                                                .whereEqualTo("email",username)
                                                .whereEqualTo("password",password)
                                                .get().await()

            if(!querySnapshot.isEmpty){
                val docs=querySnapshot.documents
                for (doc in docs) {
                    val data = doc.data
                    Log.d("Login", "User found. Data: $data ")
                    val email= data?.get("email").toString()
                    val userID=data?.get("user_id").toString()
                    val displayName=""
                    val user=LoggedInUser(userID, displayName, email)
                    return Result.Success(user)
                }

            }
            Log.d("Login", "User not found")
            throw Exception("User not Found")

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