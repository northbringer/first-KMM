package ru.northbringer.firstkmm.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import ru.northbringer.firstkmm.Greeting
import android.widget.TextView
import android.widget.Toast
import ru.northbringer.firstkmm.Account
import ru.northbringer.firstkmm.AccountDao
import ru.northbringer.firstkmm.DatabaseDriverFactory

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {

    private lateinit var loginEditText: TextView
    private lateinit var passwordEditText: TextView
    private lateinit var signInButton: Button
    private lateinit var createAccountButton: Button
    private lateinit var logAllAccountsButton: Button

    private val sharedDb = AccountDao(DatabaseDriverFactory(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initListeners()

    }

    fun initViews() {
        loginEditText = findViewById(R.id.etLogin)
        passwordEditText = findViewById(R.id.etPassword)
        signInButton = findViewById(R.id.buttonSignIn)
        createAccountButton = findViewById(R.id.buttonCreateAccount)
        logAllAccountsButton = findViewById(R.id.buttonLogAllAccounts)
    }

    fun initListeners() {

        createAccountButton.setOnClickListener {
            if (!isFieldsValidated()) return@setOnClickListener

            val inputLogin = loginEditText.text.toString()
            val inputPassword = passwordEditText.text.toString()

            val requireAccount: Account? = sharedDb.getAccountByLogin(inputLogin)
            if (requireAccount != null) {
                showToast("There is already account with login: ${requireAccount.login}")
            } else {
                sharedDb.createNewAccount(inputLogin, inputPassword)
                showToast("Account created")
            }
        }

        signInButton.setOnClickListener {
            if (!isFieldsValidated()) return@setOnClickListener

            val inputLogin = loginEditText.text.toString()
            val inputPassword = passwordEditText.text.toString()

            val requireAccount: Account? = sharedDb.getAccountByLogin(inputLogin)
            if (requireAccount != null) {
                if (requireAccount.password == inputPassword) {
                    showToast("Success! You`re logged.")
                } else {
                    showToast("Incorrect password for account login: ${requireAccount.login}")
                }
            } else {
                showToast("There is not account with that login")
            }
        }

        logAllAccountsButton.setOnClickListener {
            Log.d("ACCOUNT", sharedDb.getAllAccounts().toString())
        }
    }

    fun isFieldsValidated(): Boolean {
        when {
            loginEditText.text.isEmpty() -> {
                showToast("Enter login")
                return false
            }
            passwordEditText.text.isEmpty() -> {
                showToast("Enter password")
                return false
            }
        }
        return true
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
