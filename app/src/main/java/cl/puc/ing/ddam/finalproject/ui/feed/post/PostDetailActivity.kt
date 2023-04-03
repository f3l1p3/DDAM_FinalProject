package cl.puc.ing.ddam.finalproject.ui.feed.post

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cl.puc.ing.ddam.finalproject.databinding.ActivityPostDetailBinding
import cl.puc.ing.ddam.finalproject.ui.feed.PostItem
import com.bumptech.glide.Glide

class PostDetailActivity: AppCompatActivity() {

    private lateinit var item: PostItem
    private lateinit var binding: ActivityPostDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postImageView : ImageView = binding.postDetailImgvw
        val postNameTextView : TextView =  binding.postDetailTxtvw

        item = intent.getSerializableExtra("POST_DATA") as PostItem

        postNameTextView.text = item.text
        Glide.with(this).load(item.image_url).into(postImageView)
    }
}
