package cl.puc.ing.ddam.finalproject.ui.search

import androidx.lifecycle.ViewModel
import cl.puc.ing.ddam.finalproject.data.model.FollowerItem
import cl.puc.ing.ddam.finalproject.data.model.UserItem
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.SnapshotParser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class SearchViewModel: ViewModel() {

    private val query = FirebaseFirestore.getInstance()
        .collection("users")


    val options = FirestoreRecyclerOptions.Builder<UserItem>()
                    .setQuery(query, SnapshotParser { snapshot ->
                        // Extract the document ID and map it to a new PostItem object
                        val user = snapshot.toObject(UserItem::class.java)
                        user?.userId = snapshot.id
                        // Fetch the followers subcollection for this user
                        val followersQuery = snapshot.reference.collection("followers")

                        // Fetch the followers subcollection for this user
                        val followersList:List<FollowerItem>  =  runBlocking {
                            getFollowersForUser(snapshot.reference)
                        }

                        // Set the followers list to the user object
                        user?.followers = followersList

                        user!!
                    })
                    .build()

    private suspend fun getFollowersForUser(userDocumentRef: DocumentReference): List<FollowerItem> {
        return try {
            val querySnapshot = userDocumentRef.collection("followers").get().await()
            querySnapshot.toObjects(FollowerItem::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

}
