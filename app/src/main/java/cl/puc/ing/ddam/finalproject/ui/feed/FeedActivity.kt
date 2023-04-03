package cl.puc.ing.ddam.finalproject.ui.feed

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cl.puc.ing.ddam.finalproject.R
import cl.puc.ing.ddam.finalproject.databinding.ActivityFeedBinding
import cl.puc.ing.ddam.finalproject.session.SessionManager
import cl.puc.ing.ddam.finalproject.ui.ViewModelFactory
import cl.puc.ing.ddam.finalproject.ui.login.LoginActivity
import cl.puc.ing.ddam.finalproject.ui.menu.BottomNavigationHandler

class FeedActivity : AppCompatActivity() {


    private lateinit var viewModel: FeedViewModel
    private lateinit var binding: ActivityFeedBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostsAdapter
    private lateinit var sessionManager: SessionManager
    private lateinit var navigationHandler: BottomNavigationHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        viewModel = ViewModelProvider(this, ViewModelFactory())[FeedViewModel::class.java]

        val navigationView = binding.bottomNavigationMenu
        navigationHandler = BottomNavigationHandler(this, navigationView)
        navigationHandler.setup()

        adapter = PostsAdapter(viewModel.options)
        recyclerView = binding.listView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

//        val addPostBtn=binding.addPostBtn
//        addPostBtn.setOnClickListener {
//            val intent = Intent(it.context, AddPostActivity::class.java)
//            startActivity(intent)
//        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_logout -> {
                sessionManager.setLoggedIn(false)
                sessionManager.setUsername("")
                sessionManager.setUserId("")
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
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

