package cl.puc.ing.ddam.finalproject.ui.feed

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import cl.puc.ing.ddam.finalproject.R
import cl.puc.ing.ddam.finalproject.ui.feed.post.PostDetailActivity
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class PostsAdapter(options: FirestoreRecyclerOptions<PostItem>) : FirestoreRecyclerAdapter<PostItem, PostViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.listview_post,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: PostItem) {
        holder.postTextVW.text=model.text

        Glide.with(holder.itemView.context).load(model.image_url).into(holder.postImageVW)

        holder.itemView.setOnClickListener { goToPostDetailActivity(holder.itemView.context,model) }
    }

    private fun goToPostDetailActivity(context: Context?, model: PostItem) {
        val intent = Intent(context, PostDetailActivity::class.java)

        intent.putExtra("POST_DATA", model)

        context?.startActivity(intent)
    }

}
