package cl.puc.ing.ddam.finalproject.ui.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cl.puc.ing.ddam.finalproject.R
import cl.puc.ing.ddam.finalproject.data.UserRepository

class RegisterViewModel(private val userRepository: UserRepository): ViewModel() {


    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun registerDataChanged(nickName: String, name: String, lastName: String, email: String, password: String) {
        if (!isNickNameValid(nickName)) {
            _registerForm.value = RegisterFormState(nickNameError = R.string.invalid_nickname)
        } else if (!isUserNameValid(name)) {
            _registerForm.value = RegisterFormState(userNameError = R.string.invalid_username)
        } else if (!isLastNameValid(lastName)) {
            _registerForm.value = RegisterFormState(lastNameError = R.string.invalid_lastname)
        } else if (!isEmailValid(email)) {
            _registerForm.value = RegisterFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun isEmailValid(email: String): Boolean {
        return if (email.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isNotBlank()
        }
    }

    private fun isLastNameValid(lastName: String): Boolean {
        return lastName.isNotBlank() && lastName.length > 5
    }

    private fun isUserNameValid(name: String): Boolean {
        return name.isNotBlank() && name.length > 5
    }

    private fun isNickNameValid(nickName: String): Boolean {
        return nickName.isNotBlank() && nickName.length > 5
    }

    suspend fun registerUser(nickName: String, name: String, lastName: String, email: String, password: String) {
        val result= userRepository.createUser(nickName,name,lastName,email,password)

        if (result) {
            _registerResult.value = RegisterResult(success = true)
        } else {
            _registerResult.value = RegisterResult(error = R.string.login_failed)
        }
    }


}
