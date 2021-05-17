package com.example.finalinterngumi.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.example.finalinterngumi.R
import com.example.projectgumi.utils.Utils.USER
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        instanceGoogleSignIn()
        val user = intent.extras?.get(USER) as FirebaseUser?
        user?.let {
            image_user.load(it.phoneNumber) {
                placeholder(R.drawable.com_facebook_button_icon)
            }
            text_email.text = it.displayName
            it.photoUrl?.let {
                text_phone.text = it.toString()
            }
        }
        button_logout.setOnClickListener {
            signOut()
        }
    }

    private fun instanceGoogleSignIn() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signOut() {
        Firebase.auth.signOut()
        googleSignInClient.signOut()
        backLogin()
    }

    private fun backLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}