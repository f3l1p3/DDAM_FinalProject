package cl.puc.ing.ddam.finalproject.data

import cl.puc.ing.ddam.finalproject.data.model.UserItem

class UserRepository(private val userDataSource: UserDataSource,
                     private val loginDataSource: LoginDataSource) {



    suspend fun createUser(nickName: String, name: String, lastName: String, email: String, password: String): Boolean {

        val userId = userDataSource.createUser(nickName, name, lastName)

        return loginDataSource.createLogin(email,password,userId)

    }

    suspend fun followUser(userToFollow: String, userId: String) {
        userDataSource.followUser(userToFollow,userId)
    }

    suspend fun getUser(userId: String): UserItem? {
        return userDataSource.getUser(userId)
    }
}
