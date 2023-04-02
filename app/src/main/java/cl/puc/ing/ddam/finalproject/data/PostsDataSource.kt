package cl.puc.ing.ddam.finalproject.data

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.UUID

class PostsDataSource {

    private var storageRef = Firebase.storage("gs://ddam-instapp.appspot.com").reference
    private val db = Firebase.firestore
    suspend fun addPost(uploadImageView: ImageView, postText: String, userId: String?) {
        // get the bitmap from the ImageView
        val bitmap = (uploadImageView.drawable as BitmapDrawable).bitmap

        // create a byte array output stream and compress the bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        // create a byte array from the output stream
        val data = baos.toByteArray()

        // create a reference to the image file in the storage
        val imageName = "posts/${UUID.randomUUID()}.jpg"
        val imageRef = storageRef.child(imageName)

        // upload the byte array to the image file reference
        val uploadTask = imageRef.putBytes(data).await()
        val imageURL = storageRef.child(imageName).downloadUrl.await().toString()

        val docRef = db.collection("posts").document()

        val post = hashMapOf(
            "image_url" to imageURL,
            "text" to postText,
            "updated_dt" to FieldValue.serverTimestamp(),
            "user_id" to userId
        )
        docRef.set(post).await()

    }

}
