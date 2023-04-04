package cl.puc.ing.ddam.finalproject.data

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserDataSource {


    private val db = Firebase.firestore

    suspend fun createUser(nickName: String, name: String, lastName: String):String{

        val user= hashMapOf("name" to name,
            "lastName" to lastName,
            "nickName" to nickName)
        val docRef = db.collection("users").add(user).await()
        return docRef.id
    }

    suspend fun followUser(userToFollow: String, userId: String) {

        // Create a new follower document with an ID
        val followerData = hashMapOf(
            "user_id" to userToFollow,
            "updated_dt" to FieldValue.serverTimestamp()
        )

        val docSnapshot=db.collection("users").document(userId)
            .collection("followers")
            .add(followerData)
            .await()
    }
}
