package cl.puc.ing.ddam.finalproject.ui.feed

import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore

class FeedViewModel : ViewModel() {

    private val query = FirebaseFirestore.getInstance()
                        .collection("posts")
                        .orderBy("updated_dt")

    val options = FirestoreRecyclerOptions.Builder<PostItem>()
                        .setQuery(query, PostItem::class.java)
                        .build()


}
