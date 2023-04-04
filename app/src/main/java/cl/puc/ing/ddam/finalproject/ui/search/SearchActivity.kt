package cl.puc.ing.ddam.finalproject.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cl.puc.ing.ddam.finalproject.databinding.ActivityFeedBinding
import cl.puc.ing.ddam.finalproject.databinding.ActivitySearchBinding
import cl.puc.ing.ddam.finalproject.session.SessionManager
import cl.puc.ing.ddam.finalproject.ui.ViewModelFactory
import cl.puc.ing.ddam.finalproject.ui.feed.FeedViewModel
import cl.puc.ing.ddam.finalproject.ui.feed.PostsAdapter
import cl.puc.ing.ddam.finalproject.ui.menu.BottomNavigationHandler

class SearchActivity : AppCompatActivity(){
    private lateinit var binding: ActivitySearchBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: SearchViewModel
    private lateinit var sessionManager: SessionManager
    private lateinit var navigationHandler: BottomNavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        viewModel = ViewModelProvider(this, ViewModelFactory())[SearchViewModel::class.java]

        val navigationView = binding.bottomNavigationMenu
        navigationHandler = BottomNavigationHandler(this, navigationView)
        navigationHandler.setup()

        adapter = UserAdapter(viewModel.options,sessionManager.getUserId())

        recyclerView = binding.listView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

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
