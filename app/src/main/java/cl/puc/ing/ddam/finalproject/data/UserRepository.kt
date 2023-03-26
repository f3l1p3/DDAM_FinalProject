package cl.puc.ing.ddam.finalproject.data

class UserRepository(private val userDataSource: UserDataSource,
                     private val loginDataSource: LoginDataSource) {



    suspend fun createUser(nickName: String, name: String, lastName: String, email: String, password: String): Boolean {

        val userId = userDataSource.createUser(nickName, name, lastName)

        return loginDataSource.createLogin(email,password,userId)

    }
}
