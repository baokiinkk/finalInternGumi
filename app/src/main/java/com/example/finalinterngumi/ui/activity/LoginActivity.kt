package com.example.finalinterngumi.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.finalinterngumi.R
import com.example.projectgumi.sns.SNSLoginActivity
import com.example.projectgumi.utils.SNSLoginType
import com.example.projectgumi.utils.Utils
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    override fun onResume() {
        super.onResume()
        Firebase.auth.currentUser.let {
            it?.let {
                updateUI(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {
        //Instance firebase auth
        auth = Firebase.auth

        button_loginGg.setOnClickListener {
            signInGg()
        }
        button_loginFb.setOnClickListener {
            signInFb()
        }
        button_loginPhone.setOnClickListener {
            singInPhone()
        }
        button_logout.setOnClickListener {
            signOut()
        }

    }
    private fun singInPhone() {
//        val intent = Intent(this, PhoneLoginActivity::class.java)
//        startActivity(intent)
    }

    private fun signInFb() {
        LoginManager.getInstance()
            .logInWithReadPermissions(this, arrayListOf("public_profile", "user_friends"))
    }

    private fun signInGg() {
        val intent = Intent(this, SNSLoginActivity::class.java)
        intent.putExtra(Utils.SNS_LOGIN_TYPE, SNSLoginType.Google)
        startActivityForResult(intent, Utils.SNS_REQUEST_CODE)
    }

    private fun signOut() {
        auth.signOut()
        updateUI(null)
    }

    private fun updateUI(user: FirebaseUser?) {
        layout_logout.visibility = View.GONE
        layout_login.visibility = View.VISIBLE
        user?.let {
            layout_logout.visibility = View.VISIBLE
            layout_login.visibility = View.GONE
            image_user.load(it.photoUrl)
            text_email.text = it.displayName
            text_phone.text = it.phoneNumber
        }
    }
}