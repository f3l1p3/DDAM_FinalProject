package cl.puc.ing.ddam.finalproject.ui.post

import android.text.Editable
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cl.puc.ing.ddam.finalproject.data.PostsRepository

class AddPostViewModel(private val postsRepository: PostsRepository) : ViewModel() {

    private val _addPostForm = MutableLiveData<AddPostFormState>()
    val addPostFormState: LiveData<AddPostFormState> = _addPostForm

    private val _addPostResult = MutableLiveData<AddPostResult>()
    val addPostResult: LiveData<AddPostResult> = _addPostResult

    suspend fun addPost(imageview: ImageView, postText: String) {
        postsRepository.addPost(imageview,postText)
        _addPostResult.value = AddPostResult(success = true)
    }

}
