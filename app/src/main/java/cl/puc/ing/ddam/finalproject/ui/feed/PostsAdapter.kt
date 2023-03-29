package cl.puc.ing.ddam.finalproject.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import cl.puc.ing.ddam.finalproject.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class PostsAdapter(options: FirestoreRecyclerOptions<PostItem>) : FirestoreRecyclerAdapter<PostItem, PostViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.listview_post,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: PostItem) {
        holder.postTextVW.text=model.text
    }

}
