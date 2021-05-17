package com.example.finalinterngumi.ui.activity

import android.content.Intent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.finalinterngumi.R
import com.example.projectgumi.sns.SNSLoginActivity
import com.example.projectgumi.utils.SNSLoginType
import com.example.projectgumi.utils.Utils
import com.example.projectgumi.utils.Utils.SNS_LOGIN_TYPE
import com.example.projectgumi.utils.Utils.SNS_REQUEST_CODE
import com.example.projectgumi.utils.Utils.SNS_RESULT_CODE
import com.example.projectgumi.utils.Utils.USER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == SNS_REQUEST_CODE && resultCode == SNS_RESULT_CODE){
            val user = data?.extras?.get(Utils.SNS_RESULT_DATA) as FirebaseUser?
            updateUI(user)
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

    }
    private fun singInPhone() {
           val intent = Intent(this, SNSLoginActivity::class.java)
        intent.putExtra(SNS_LOGIN_TYPE, SNSLoginType.PhoneNumber)
        startActivityForResult(intent, SNS_REQUEST_CODE)
    }

    private fun signInFb() {
        val intent = Intent(this, SNSLoginActivity::class.java)
        intent.putExtra(Utils.SNS_LOGIN_TYPE, SNSLoginType.Facebook)
        startActivityForResult(intent, Utils.SNS_REQUEST_CODE)
    }

    private fun signInGg() {
        val intent = Intent(this, SNSLoginActivity::class.java)
        intent.putExtra(Utils.SNS_LOGIN_TYPE, SNSLoginType.Google)
        startActivityForResult(intent, Utils.SNS_REQUEST_CODE)
    }

//

    private fun updateUI(user: FirebaseUser?) {
       user?.let {
           startActivity(Intent(this,MainActivity::class.java).putExtra(USER,user))
       }
    }
}