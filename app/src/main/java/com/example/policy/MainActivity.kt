package com.example.policy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    /** Called when the user taps the Send button */
    fun renderSignupUser(view: View)
    {
        val intent: Intent = Intent(this,SignupUser::class.java)
        startActivity(intent)
    }
    fun renderSignupCompany(view: View)
    {
        val intent: Intent = Intent(this,SignupCompany::class.java)
        startActivity(intent)
    }
    fun renderLoginCompany(view: View)
    {
        val intent: Intent = Intent(this,LoginCompany::class.java)
        startActivity(intent)
    }
    fun renderLoginUser(view: View)
    {
        val intent: Intent = Intent(this,LoginUser::class.java)
        startActivity(intent)
    }

}