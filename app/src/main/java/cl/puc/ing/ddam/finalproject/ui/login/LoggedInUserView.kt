package cl.puc.ing.ddam.finalproject.ui.login

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String,
    val userId: String
)