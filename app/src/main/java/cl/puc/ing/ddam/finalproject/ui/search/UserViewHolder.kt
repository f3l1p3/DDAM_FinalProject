package cl.puc.ing.ddam.finalproject.ui.search

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cl.puc.ing.ddam.finalproject.R

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val userNicknameTextVW: TextView
    val userAvatarImageVW: ImageView
    val followBtn: Button

    init {
        userNicknameTextVW = itemView.findViewById(R.id.user_nickname_txt)
        userAvatarImageVW = itemView.findViewById(R.id.user_avatar_img)
        followBtn=itemView.findViewById(R.id.followBtn)
    }

}
