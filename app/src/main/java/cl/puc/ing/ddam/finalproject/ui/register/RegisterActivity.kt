package cl.puc.ing.ddam.finalproject.ui.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cl.puc.ing.ddam.finalproject.databinding.ActivityRegisterBinding
import cl.puc.ing.ddam.finalproject.ui.ViewModelFactory
import cl.puc.ing.ddam.finalproject.ui.login.LoginActivity
import cl.puc.ing.ddam.finalproject.ui.login.afterTextChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nameTxt = binding.nameTxt
        val lastNameTxt = binding.lastNameTxt
        val emailTxt = binding.emailTxt
        val passwordTxt = binding.passwordTxt
        val nickNameTxt = binding.nickNameTxt
        val submitBtn = binding.submitBtn


        registerViewModel = ViewModelProvider(this, ViewModelFactory())[RegisterViewModel::class.java]

        registerViewModel.registerFormState.observe(this@RegisterActivity, Observer {
            val registerState = it ?: return@Observer

            // disable login button unless both username / password is valid
            submitBtn.isEnabled = registerState.isDataValid

            if (registerState.nickNameError != null) {
                nickNameTxt.error = getString(registerState.nickNameError)
            }
            if (registerState.passwordError != null) {
                passwordTxt.error = getString(registerState.passwordError)
            }
            if (registerState.userNameError != null) {
                nameTxt.error = getString(registerState.userNameError)
            }
            if (registerState.lastNameError != null) {
                lastNameTxt.error = getString(registerState.lastNameError)
            }
            if (registerState.emailError != null) {
                emailTxt.error = getString(registerState.emailError)
            }
        })

        registerViewModel.registerResult.observe(this@RegisterActivity, Observer {
            val registerResult = it ?: return@Observer

            //loading.visibility = View.GONE
            if (registerResult.error != null) {
                showRegisterFailed(registerResult.error)
            }
            if (registerResult.success != null) {
                Toast.makeText(
                    applicationContext,
                    "Register successful",
                    Toast.LENGTH_LONG
                ).show()
                goToLoginScreen()
            }

            setResult(Activity.RESULT_OK)

        })

        nickNameTxt.afterTextChanged {
            registerViewModel.registerDataChanged(
                nickNameTxt.text.toString(),
                nameTxt.text.toString(),
                lastNameTxt.text.toString(),
                emailTxt.text.toString(),
                passwordTxt.text.toString()

            )
        }

        nameTxt.afterTextChanged {
            registerViewModel.registerDataChanged(
                nickNameTxt.text.toString(),
                nameTxt.text.toString(),
                lastNameTxt.text.toString(),
                emailTxt.text.toString(),
                passwordTxt.text.toString()

            )
        }

        lastNameTxt.afterTextChanged {
            registerViewModel.registerDataChanged(
                nickNameTxt.text.toString(),
                nameTxt.text.toString(),
                lastNameTxt.text.toString(),
                emailTxt.text.toString(),
                passwordTxt.text.toString()

            )
        }

        emailTxt.afterTextChanged {
            registerViewModel.registerDataChanged(
                nickNameTxt.text.toString(),
                nameTxt.text.toString(),
                lastNameTxt.text.toString(),
                emailTxt.text.toString(),
                passwordTxt.text.toString()

            )
        }

        passwordTxt.afterTextChanged {
            registerViewModel.registerDataChanged(
                nickNameTxt.text.toString(),
                nameTxt.text.toString(),
                lastNameTxt.text.toString(),
                emailTxt.text.toString(),
                passwordTxt.text.toString()

            )
        }

        submitBtn.setOnClickListener {
            //loading.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.Main).launch {
                registerViewModel.registerUser(
                    nickNameTxt.text.toString(),
                    nameTxt.text.toString(),
                    lastNameTxt.text.toString(),
                    emailTxt.text.toString(),
                    passwordTxt.text.toString()
                )
            }
        }
    }

    private fun goToLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)

        startActivity(intent)
    }

    private fun showRegisterFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}


