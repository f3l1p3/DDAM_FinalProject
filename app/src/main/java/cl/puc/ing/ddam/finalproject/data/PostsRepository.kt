package cl.puc.ing.ddam.finalproject.data

import android.widget.ImageView

class PostsRepository(private val postsDataSource: PostsDataSource) {

    suspend fun addPost(imageview: ImageView, postText: String, userId: String?) {
        postsDataSource.addPost(imageview,postText,userId)
    }
}
