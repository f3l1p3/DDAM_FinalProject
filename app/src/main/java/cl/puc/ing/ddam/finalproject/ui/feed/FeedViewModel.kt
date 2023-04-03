package cl.puc.ing.ddam.finalproject.ui.feed

import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.SnapshotParser
import com.google.firebase.firestore.FirebaseFirestore

class FeedViewModel : ViewModel() {

    private val query = FirebaseFirestore.getInstance()
                        .collection("posts")
                        .orderBy("updated_dt")

    val options = FirestoreRecyclerOptions.Builder<PostItem>()
                        .setQuery(query, SnapshotParser { snapshot ->
                            // Extract the document ID and map it to a new PostItem object
                            val post = snapshot.toObject(PostItem::class.java)
                            post?.postId = snapshot.id
                            post!!
                        })
                        .build()


}
