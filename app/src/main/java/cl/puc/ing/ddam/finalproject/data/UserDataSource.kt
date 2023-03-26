package cl.puc.ing.ddam.finalproject.data

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
}
