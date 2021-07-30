package com.example.policy

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class SignupUser : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_user)
    }
    fun createUser(view: View)
    {
        val email: String = findViewById<EditText>(R.id.signupEmail).getText().toString();
        val password: String = findViewById<EditText>(R.id.signupPassword).getText().toString();
        val name: String = findViewById<EditText>(R.id.signupName).getText().toString();
        val phone: String = findViewById<EditText>(R.id.signupPhone).getText().toString();
        val userDetails = JSONObject();
        userDetails.put("email", email);
        userDetails.put("password", password);
        userDetails.put("name", name);
        userDetails.put("phoneno", phone);
        val displayInfo = findViewById<TextView>(R.id.displayInfo);
        displayInfo.text = userDetails.toString();
        val queue = Volley.newRequestQueue(this)
        var url = "http://localhost:3000/signup_user";
        val req = JsonObjectRequest(Request.Method.POST, url, userDetails,
            { response ->

                displayInfo.text = response.toString();
            },
            { error ->
                displayInfo.text = error.toString();
            }
        );
        queue.add(req);
    }
}