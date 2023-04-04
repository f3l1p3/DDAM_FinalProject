package cl.puc.ing.ddam.finalproject.data

import android.util.Log
import cl.puc.ing.ddam.finalproject.data.model.FollowerItem
import cl.puc.ing.ddam.finalproject.data.model.UserItem
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

    suspend fun getUser(user_id: String) : UserItem? {

        val userDocRef = db.collection("users").document(user_id)
        val followersCollectionRef = userDocRef.collection("followers")

        return try {
            val userSnapshot = userDocRef.get().await()
            val user = userSnapshot.toObject(UserItem::class.java)?.apply { userId = userSnapshot.id }

            val followersSnapshot = followersCollectionRef.get().await()
            val followers = followersSnapshot.toObjects(FollowerItem::class.java)
            user?.copy(followers = followers)
        } catch (e: Exception) {
            Log.e("UserDataSource","error while getting user", e)
            null
        }
    }


}
