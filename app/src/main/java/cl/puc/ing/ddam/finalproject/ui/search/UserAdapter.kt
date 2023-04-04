package cl.puc.ing.ddam.finalproject.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import cl.puc.ing.ddam.finalproject.R
import cl.puc.ing.ddam.finalproject.data.LoginDataSource
import cl.puc.ing.ddam.finalproject.data.UserDataSource
import cl.puc.ing.ddam.finalproject.data.UserRepository
import cl.puc.ing.ddam.finalproject.data.model.UserItem
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserAdapter(options: FirestoreRecyclerOptions<UserItem>, val userId:String) : FirestoreRecyclerAdapter<UserItem, UserViewHolder>(options) {
    val userRepo = UserRepository(UserDataSource(), LoginDataSource())


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.activity_search_item,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: UserItem) {
        holder.userNicknameTextVW.text=model.nickName

        val user: UserItem = runBlocking { userRepo.getUser(userId)!!}
        holder.followBtn.isEnabled= user.followers?.stream()!!.noneMatch { it.user_id==model.userId }

        holder.followBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                followUser(holder.itemView.context,model)
            }
        }
    }

    private suspend fun followUser(context: Context?, model: UserItem) {
        userRepo.followUser(model.userId, userId)
        Toast.makeText(context,"following user ${model.nickName}",Toast.LENGTH_LONG
        ).show()
    }

}
