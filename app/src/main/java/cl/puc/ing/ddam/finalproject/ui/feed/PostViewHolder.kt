package cl.puc.ing.ddam.finalproject.ui.feed

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cl.puc.ing.ddam.finalproject.R

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val postTextVW: TextView
    val postImageVW: ImageView
    init {
        postTextVW = itemView.findViewById(R.id.post_text_txtvw)
        postImageVW = itemView.findViewById(R.id.post_img_imgvw)
    }
}
