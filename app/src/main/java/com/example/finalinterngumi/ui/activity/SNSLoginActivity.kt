package com.example.projectgumi.sns

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.projectgumi.R
import com.example.projectgumi.utils.SNSLoginType
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.projectgumi.utils.Utils
import com.example.projectgumi.utils.Utils.SNS_LOGIN_TYPE
import com.example.projectgumi.utils.Utils.SNS_REQUEST_CODE
import com.example.projectgumi.utils.Utils.SNS_RESULT_CODE
import com.example.projectgumi.utils.Utils.SNS_RESULT_DATA
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class SNSLoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        val resultData = intent.extras?.get(SNS_LOGIN_TYPE) as SNSLoginType
        when(resultData){
            SNSLoginType.Google -> signInGoogle()
        }
    }

    private fun init() {
        auth = Firebase.auth
        instanceGoogleSignIn()
    }

    private fun instanceGoogleSignIn() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, SNS_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == SNS_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(Utils.TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(Utils.TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Utils.TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    handleCallbackLoginActivity(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Utils.TAG, "signInWithCredential:failure", task.exception)
                    handleCallbackLoginActivity(null)
                }
            }
    }

    private fun handleCallbackLoginActivity(user: FirebaseUser?) {
        val intent = Intent()
        intent.putExtra(SNS_RESULT_DATA, user)
        setResult(SNS_RESULT_CODE, intent)
        finish()
    }

}