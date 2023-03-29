package cl.puc.ing.ddam.finalproject.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddPostViewModel : ViewModel() {

    private val _addPostForm = MutableLiveData<AddPostFormState>()
    val addPostFormState: LiveData<AddPostFormState> = _addPostForm

    private val _addPostResult = MutableLiveData<AddPostResult>()
    val addPostResult: LiveData<AddPostResult> = _addPostResult



}
