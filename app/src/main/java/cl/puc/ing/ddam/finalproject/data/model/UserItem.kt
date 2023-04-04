package cl.puc.ing.ddam.finalproject.data.model

data class UserItem (
    val nickName: String="",
    var userId: String="",
    var followers: List<FollowerItem>? = null
)



