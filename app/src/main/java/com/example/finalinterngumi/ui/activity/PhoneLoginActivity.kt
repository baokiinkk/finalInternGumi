package com.example.finalinterngumi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.projectgumi.utils.Utils.SNS_RESULT_CODE
import com.example.projectgumi.utils.Utils.SNS_RESULT_DATA
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_phone_login.*
import java.util.concurrent.TimeUnit

class PhoneLoginActivity : AppCompatActivity() {
    private lateinit var phoneCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var forceResend: PhoneAuthProvider.ForceResendingToken
    private lateinit var mVerficationId: String
    private lateinit var auth: FirebaseAuth
    private val TAG = "MAIN_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_login)
        init()
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()

        instancePhoneSignIn()

        buttonGetCode.setOnClickListener {
            val phone = edit_phoneNumber.text.toString()
            if (phone.isEmpty() || phone.length != 9) {
                Toast.makeText(
                    this@PhoneLoginActivity,
                    "Please enter phone number without 0",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                startPhoneNumberVerification("+84$phone")
            }
        }
        buttonOK.setOnClickListener {
            val code = edit_code.text.toString()
            verifyPhoneNumberWithCode(mVerficationId, code)
        }
        buttonResend.setOnClickListener {
            val phone = edit_phoneNumber.text.toString()
            resendVerificationCode(phone, forceResend)
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(phoneCallBack)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(phoneCallBack)          // OnVerificationStateChangedCallbacks
            .setForceResendingToken(token)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent()
                    intent.putExtra(SNS_RESULT_DATA, auth.currentUser)
                    setResult(SNS_RESULT_CODE, intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed by code error.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun instancePhoneSignIn() {
        phoneCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                //signInWithPhoneAuthCredential(p0)
                Toast.makeText(this@PhoneLoginActivity, p0.smsCode, Toast.LENGTH_SHORT).show()
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(this@PhoneLoginActivity, p0.message, Toast.LENGTH_SHORT).show()
            }

            @SuppressLint("SetTextI18n")
            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                mVerficationId = p0
                forceResend = p1
                Toast.makeText(
                    this@PhoneLoginActivity,
                    "Verification code sent",
                    Toast.LENGTH_SHORT
                ).show()
                //hide phone show code
                layout_code.visibility = View.VISIBLE
                layout_phone.visibility = View.GONE

            }
        }
    }
}