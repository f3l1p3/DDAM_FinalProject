package cl.puc.ing.ddam.finalproject.ui.feed

import java.io.Serializable
import java.util.Date

data class  PostItem(
    var postId: String="",
    val text: String="",
    val nickname: String="",
    val user_id: String="",
    val image_url: String="",
    val updated_dt: Date = Date()
) : Serializable
