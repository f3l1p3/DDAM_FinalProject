package cl.puc.ing.ddam.finalproject.ui.search

import androidx.lifecycle.ViewModel
import cl.puc.ing.ddam.finalproject.ui.feed.PostItem
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.SnapshotParser
import com.google.firebase.firestore.FirebaseFirestore

class SearchViewModel: ViewModel() {

    private val query = FirebaseFirestore.getInstance()
        .collection("users")


    val options = FirestoreRecyclerOptions.Builder<UserItem>()
                    .setQuery(query, SnapshotParser { snapshot ->
                        // Extract the document ID and map it to a new PostItem object
                        val user = snapshot.toObject(UserItem::class.java)
                        user?.userId = snapshot.id
                        user!!
                    })
                    .build()

}
