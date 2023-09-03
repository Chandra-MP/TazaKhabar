package com.example.tazakhabar

import android.app.PendingIntent.getActivity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tazakhabar.View.MainMenu
import com.google.firebase.auth.FirebaseAuth
import com.example.tazakhabar.databinding.LoginSignupBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginSignupActivity : AppCompatActivity() {


    private var auth: FirebaseAuth =  Firebase.auth
    private var isLoginLayoutVisible = true

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        Log.d(TAG, "inside onStart()")
        if (currentUser != null) {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContentView(R.layout.login_signup)


        Log.d(TAG, "Inside onCreate")
        val binding = LoginSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Firebase.firestore

        val name = binding.name
        val gender = binding.gender
        val email = binding.emailEditText

        val toggleTextView = binding.toggleTextView
        val toggleTextView2 = binding.toggleTextView2

        val loginButton = binding.loginButton
        val signupButton = binding.signupButton
//        val loginButton = findViewById<Button>(R.id.loginButton)

        val loginLayout = binding.loginLayout
        val signupLayout = binding.signupLayout

        toggleTextView.setOnClickListener {
            Log.d(TAG, "ToggleTextView clicked")
            // Toggle between login and signup layouts
            isLoginLayoutVisible = !isLoginLayoutVisible
            Log.d(TAG, "isLoginLayoutVisible: $isLoginLayoutVisible")
            loginLayout.visibility = if (isLoginLayoutVisible) View.VISIBLE else View.GONE
            signupLayout.visibility = if (isLoginLayoutVisible) View.GONE else View.VISIBLE
        }
        toggleTextView2.setOnClickListener {
            Log.d(TAG, "ToggleTextView2 clicked")
            // Toggle between login and signup layouts
            isLoginLayoutVisible = !isLoginLayoutVisible
            loginLayout.visibility = if (isLoginLayoutVisible) View.VISIBLE else View.GONE
            signupLayout.visibility = if (isLoginLayoutVisible) View.GONE else View.VISIBLE
        }

        loginButton.setOnClickListener {
            // Perform login using Firebase Authentication
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            Log.d(TAG, "Clicked loginButton and inside Login button onCLickListener")

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    Log.d(TAG, "Inside signInWithEmailAndPassword")
                    if (task.isSuccessful) {
                        // Login successful
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser

                        val intent = Intent(this, MainMenu::class.java)
                        startActivity(intent)
                        finish()

                        // You can navigate to the main activity or perform other actions
                    } else {
                        // Login failed
                        // Handle the error, e.g., display an error message
                        Log.d(TAG, "signInWithEmail:Failed")
                        val intent = Intent(this, LoginSignupActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
        }

        signupButton.setOnClickListener {
            // Perform signup using Firebase Authentication
            val email = binding.signupEmailEditText.text.toString()
            val password = binding.signupPasswordEditText.text.toString()
            val name = binding.name.text.toString()
            val gender = binding.gender.text.toString()

            Log.d(TAG, "Inside onClickListener of signUpButton onClickListener")

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    Log.d(TAG, "Inside createUserWithEmailAndPassword")
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = hashMapOf(
                            "name" to name,
                            "email" to email,
                            "gender" to gender,
                        )

                        //Add user details to the database
                        if (user != null) {
                            db.collection("users")
                                .add(user)
                                .addOnSuccessListener { documentReference ->
                                    Log.d(TAG, "DocumentSnapShot added with ID: ${documentReference.id}")
                                }
                                .addOnFailureListener{e ->
                                    Log.w(TAG, "Error adding document", e)
                                }
                        }

                        // Toggle to the login layout
                        isLoginLayoutVisible = true
                        loginLayout.visibility = View.VISIBLE
                        signupLayout.visibility = View.GONE

                        // Clear signup form fields (optional)
                        binding.signupEmailEditText.text.clear()
                        binding.signupPasswordEditText.text.clear()

                        // Display a message to the user (optional)
                        Toast.makeText(
                            baseContext,
                            "Sign up successful. Please log in.",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        // Signup failed
                        // Handle the error, e.g., display an error message
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }
}
