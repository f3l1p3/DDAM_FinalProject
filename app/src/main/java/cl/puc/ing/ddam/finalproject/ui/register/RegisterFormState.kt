package cl.puc.ing.ddam.finalproject.ui.register

data class RegisterFormState (
    val userNameError: Int? = null,
    val passwordError: Int? = null,
    val nickNameError: Int? = null,
    val emailError: Int? = null,
    val lastNameError: Int? = null,
    val isDataValid: Boolean = false
)
