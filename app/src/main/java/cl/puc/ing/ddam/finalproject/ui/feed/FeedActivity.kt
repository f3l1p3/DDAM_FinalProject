package cl.puc.ing.ddam.finalproject.ui.feed

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cl.puc.ing.ddam.finalproject.databinding.ActivityFeedBinding
import cl.puc.ing.ddam.finalproject.ui.ViewModelFactory
import cl.puc.ing.ddam.finalproject.ui.post.AddPostActivity

class FeedActivity : AppCompatActivity() {


    private lateinit var viewModel: FeedViewModel
    private lateinit var binding: ActivityFeedBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)



        viewModel = ViewModelProvider(this, ViewModelFactory())[FeedViewModel::class.java]


        adapter = PostsAdapter(viewModel.options)

        recyclerView = binding.listView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val addPostBtn=binding.addPostBtn
        addPostBtn.setOnClickListener {
            val intent = Intent(it.context, AddPostActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}

