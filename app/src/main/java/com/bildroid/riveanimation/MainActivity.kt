package com.bildroid.riveanimation

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.startup.AppInitializer
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.RiveInitializer
import app.rive.runtime.kotlin.core.Rive


class MainActivity : AppCompatActivity() {

    lateinit var edt_email: EditText
    lateinit var edt_password: EditText
    lateinit var btn_login: androidx.appcompat.widget.AppCompatButton
    lateinit var login_animation_view: RiveAnimationView
    lateinit var progressBar: ProgressBar
    val stateMachineName = "Login Machine"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edt_email = findViewById(R.id.edt_email)
        edt_password = findViewById(R.id.edt_Password)
        btn_login = findViewById(R.id.btn_login)
        progressBar = findViewById(R.id.progress)
        login_animation_view = findViewById(R.id.login_animation_view)

        Rive.init(this)

        edt_email.setOnFocusChangeListener { view, b ->
            if (b) {
                login_animation_view.controller.setBooleanState(
                    stateMachineName,
                    "isChecking",
                    true
                )
            } else {
                login_animation_view.controller.setBooleanState(
                    stateMachineName,
                    "isChecking",
                    false
                )
            }
        }
        edt_password.setOnFocusChangeListener { view, b ->
            if (b) {
                login_animation_view.controller.setBooleanState(stateMachineName, "isHandsUp", true)
            } else {
                login_animation_view.controller.setBooleanState(
                    stateMachineName,
                    "isHandsUp",
                    false
                )
            }
        }

        edt_email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                try {
                    login_animation_view.controller.setNumberState(
                        stateMachineName,
                        "numLook",
                        s!!.length.toFloat()
                    )
                } catch (e: Exception) {
                    e.printStackTrace()

                }

            }

        })

        btn_login.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            // Disable the button temporarily to prevent multiple clicks


            // Clear focus from the password field
            edt_password.clearFocus()

            // Check email and password


            Handler(mainLooper).postDelayed({
                // Immediately trigger the animation based on the credentials
                if (edt_email.text!!.isNotEmpty() && edt_password.text!!.isNotEmpty() && edt_email.text.toString() == "bilalrana2003@gmail.com" && edt_password.text.toString() == "admin") {
                    // Trigger success animation state
                    login_animation_view.controller.fireState(stateMachineName, "trigSuccess")
                    progressBar.visibility = View.GONE
                } else {
                    // Trigger failure animation state
                    login_animation_view.controller.fireState(stateMachineName, "trigFail")
                    progressBar.visibility = View.GONE
                }
            },2000)


        }
    }
}