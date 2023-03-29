package cl.puc.ing.ddam.finalproject.ui.post

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import cl.puc.ing.ddam.finalproject.databinding.ActivityAddPostBinding
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class AddPostActivity : AppCompatActivity() {

    private var storageRef = Firebase.storage("gs://ddam-instapp.appspot.com").reference
    private lateinit var binding: ActivityAddPostBinding
    private lateinit var addPostViewModel: AddPostViewModel
    private lateinit var imageview: ImageView

    private var imagePickerActivityResult: ActivityResultLauncher<Intent> =
    // lambda expression to receive a result back, here we
        // receive single item(photo) on selection
        registerForActivityResult( ActivityResultContracts.StartActivityForResult()) { result ->
            if (result != null) {
                // getting URI of selected Image
                val imageUri: Uri? = result.data?.data

                // val fileName = imageUri?.pathSegments?.last()

                // extract the file name with extension
                val sd = getFileName(applicationContext, imageUri!!)

                // Upload Task with upload to directory 'file'
                // and name of the file remains same
                val uploadTask = storageRef.child("file/$sd").putFile(imageUri)

                // On success, download the file URL and display it
                uploadTask.addOnSuccessListener {
                    // using glide library to display the image
                    storageRef.child("file/$sd").downloadUrl.addOnSuccessListener {
                        Glide.with(this@AddPostActivity)
                            .load(it)
                            .into(imageview)

                        Log.e("Firebase", "download passed")
                    }.addOnFailureListener {
                        Log.e("Firebase", "Failed in downloading")
                    }
                }.addOnFailureListener {
                    Log.e("Firebase", "Image Upload fail")
                }
            }
        }

    private fun getFileName(context: Context, uri: Uri): String? {
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (cursor != null) {
                    if(cursor.moveToFirst()) {
                        val columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        return cursor.getString(columnIndex)
                    }
                }
            }
        }
        return uri.path?.lastIndexOf('/')?.let { uri.path?.substring(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uploadImageBtn=binding.uploadImageButton
        imageview=binding.imagePreview

        uploadImageBtn.setOnClickListener {
            // PICK INTENT picks item from data
            // and returned selected item
            val galleryIntent = Intent(Intent.ACTION_PICK)
            // here item is type of image
            galleryIntent.type = "image/*"
            // ActivityResultLauncher callback
            imagePickerActivityResult.launch(galleryIntent)
        }

    }
}
