package cl.puc.ing.ddam.finalproject.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cl.puc.ing.ddam.finalproject.R
import cl.puc.ing.ddam.finalproject.databinding.ActivityLoginBinding
import cl.puc.ing.ddam.finalproject.session.SessionManager
import cl.puc.ing.ddam.finalproject.ui.ViewModelFactory
import cl.puc.ing.ddam.finalproject.ui.feed.FeedActivity
import cl.puc.ing.ddam.finalproject.ui.register.RegisterActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        if (sessionManager.isLoggedIn()) {
            // Redirect the user to the main activity
            val intent = Intent(this, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading
        val registerBtn = binding.registerBtn

        loginViewModel = ViewModelProvider(this, ViewModelFactory())[LoginViewModel::class.java]

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            //finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        CoroutineScope(Dispatchers.Main).launch {
                            loginViewModel.login(
                                username.text.toString(),
                                password.text.toString()
                            )
                        }

                }
                false
            }
        }

        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.Main).launch {
                loginViewModel.login(
                    username.text.toString(),
                    password.text.toString()
                )
            }
        }

        registerBtn?.setOnClickListener {
            val intent = Intent(it.context, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName

        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
        // Store the login information
        sessionManager.setLoggedIn(true)
        sessionManager.setUsername(displayName)
        sessionManager.setUserId(model.userId)

        val intent = Intent(this, FeedActivity::class.java)

        startActivity(intent)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}