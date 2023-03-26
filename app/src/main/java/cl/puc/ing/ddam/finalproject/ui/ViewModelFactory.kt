package cl.puc.ing.ddam.finalproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.puc.ing.ddam.finalproject.data.LoginDataSource
import cl.puc.ing.ddam.finalproject.data.LoginRepository
import cl.puc.ing.ddam.finalproject.data.UserDataSource
import cl.puc.ing.ddam.finalproject.data.UserRepository
import cl.puc.ing.ddam.finalproject.ui.login.LoginViewModel
import cl.puc.ing.ddam.finalproject.ui.register.RegisterViewModel

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource()
                )
            ) as T
        }

        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(
                userRepository = UserRepository(userDataSource=UserDataSource()
                                                ,loginDataSource = LoginDataSource())
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}