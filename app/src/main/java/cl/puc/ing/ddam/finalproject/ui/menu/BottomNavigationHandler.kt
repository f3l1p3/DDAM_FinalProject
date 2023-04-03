package cl.puc.ing.ddam.finalproject.ui.menu

import android.content.Context
import android.content.Intent
import cl.puc.ing.ddam.finalproject.R
import cl.puc.ing.ddam.finalproject.ui.feed.FeedActivity
import cl.puc.ing.ddam.finalproject.ui.post.AddPostActivity
import cl.puc.ing.ddam.finalproject.ui.profile.ProfileActivity
import cl.puc.ing.ddam.finalproject.ui.search.SearchActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationHandler(private val context: Context, private val navigationView: BottomNavigationView) {

    fun setup() {
        navigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    goToMainActivity()
                    true
                }
                R.id.menu_search -> {
                    goToSearchActivity()
                    true
                }
                R.id.menu_profile -> {
                    goToProfileActivity()
                    true
                }
                R.id.menu_add_post -> {
                    goToAddPostActivity()
                    true
                }
                else -> false
            }
        }
    }

    private fun goToAddPostActivity() {
        val intent = Intent(context, AddPostActivity::class.java)
        context.startActivity(intent)
    }

    private fun goToMainActivity() {
        val intent = Intent(context, FeedActivity::class.java)
        context.startActivity(intent)
    }

    private fun goToSearchActivity() {
        val intent = Intent(context, SearchActivity::class.java)
        context.startActivity(intent)
    }

    private fun goToProfileActivity() {
        val intent = Intent(context, ProfileActivity::class.java)
        context.startActivity(intent)
    }
}
