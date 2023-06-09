package cl.puc.ing.ddam.finalproject.ui.post

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cl.puc.ing.ddam.finalproject.databinding.ActivityAddPostBinding
import cl.puc.ing.ddam.finalproject.session.SessionManager
import cl.puc.ing.ddam.finalproject.ui.ViewModelFactory
import cl.puc.ing.ddam.finalproject.ui.feed.FeedActivity
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddPostActivity : AppCompatActivity() {

    private var storageRef = Firebase.storage("gs://ddam-instapp.appspot.com").reference
    private lateinit var binding: ActivityAddPostBinding
    private lateinit var viewModel: AddPostViewModel
    private lateinit var imageview: ImageView
    private lateinit var sessionManager: SessionManager

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { imageBitmap: Bitmap? ->
        imageBitmap?.let {
            imageview.setImageBitmap(it)
        }
    }
    private var imagePickerActivityResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result != null) {
                // getting URI of selected Image
                val imageUri: Uri? = result.data?.data

                // load the selected image into the ImageView using Glide library
                Glide.with(this@AddPostActivity)
                    .load(imageUri)
                    .into(imageview)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        val uploadImageBtn=binding.uploadImageButton
        val postButton=binding.postButton
        val postText = binding.commentEdittext
        imageview=binding.imagePreview

        viewModel = ViewModelProvider(this, ViewModelFactory())[AddPostViewModel::class.java]

        viewModel.addPostFormState.observe(this@AddPostActivity, Observer {
            val formState = it ?: return@Observer

            // disable postButton button unless form is valid
            postButton.isEnabled = formState.isDataValid



        })

        viewModel.addPostResult.observe(this@AddPostActivity, Observer {
            val addPostResult = it ?: return@Observer

            //loading.visibility = View.GONE
            if (addPostResult.error != null) {
                showPostFailed(addPostResult.error)
            }
            if (addPostResult.success != null) {
                Toast.makeText(
                    applicationContext,
                    "Post added successfully",
                    Toast.LENGTH_LONG
                ).show()
                goToFeedScreen()
            }

            setResult(Activity.RESULT_OK)

        })

        uploadImageBtn.setOnClickListener {
            // PICK INTENT picks item from data
            // and returned selected item
            val galleryIntent = Intent(Intent.ACTION_PICK)
            // here item is type of image
            galleryIntent.type = "image/*"
            // ActivityResultLauncher callback


            val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Select an option")
            builder.setItems(options) { dialog, item ->
                when {
                    options[item] == "Take Photo" -> {
                        takePictureLauncher.launch(null)
                    }
                    options[item] == "Choose from Gallery" -> {
                        //pickImageLauncher.launch("image/*")
                        imagePickerActivityResult.launch(galleryIntent)
                    }
                    options[item] == "Cancel" -> dialog.dismiss()
                }
            }
            builder.show()

        }

        postButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.addPost(imageview,postText.text.toString(),sessionManager.getUserId())
            }
        }

    }

    private fun goToFeedScreen() {
        val intent = Intent(this, FeedActivity::class.java)

        startActivity(intent)
    }

    private fun showPostFailed(errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}
