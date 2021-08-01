package com.example.policy

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun renderMainScreen(view: View)
    {
        setContentView(R.layout.activity_main)
    }
    fun renderSignupUser(view: View)
    {
        setContentView(R.layout.signup_user)
    }
    fun renderSignupCompany(view: View)
    {
        setContentView(R.layout.signup_company)
    }
    fun renderLoginCompany(view: View)
    {
        setContentView(R.layout.login_company)
    }
    fun renderLoginUser(view: View)
    {
        setContentView(R.layout.login_user)
    }
    //Routes

    //Create JSONS

    fun createUserJsonSignup(): JSONObject
    {
        val email: String = findViewById<EditText>(R.id.signupEmail).text.toString()
        val password: String = findViewById<EditText>(R.id.signupPassword).text.toString()
        val name: String = findViewById<EditText>(R.id.signupName).text.toString()
        val phone: String = findViewById<EditText>(R.id.signupPhone).text.toString()
        val userDetails = JSONObject()
        userDetails.put("email", email)
        userDetails.put("password", password)
        userDetails.put("name", name)
        userDetails.put("phoneno", phone)
        return userDetails
    }
    fun createUserJsonLogin(): JSONObject
    {
        val email: String = findViewById<EditText>(R.id.loginEmail).text.toString()
        val password: String = findViewById<EditText>(R.id.loginPassword).text.toString()
        val userDetails = JSONObject()
        userDetails.put("email", email)
        userDetails.put("password", password)
        return userDetails
    }
    fun createCompanyJsonSignup(): JSONObject
    {
        val email: String = findViewById<EditText>(R.id.signupEmail).text.toString()
        val password: String = findViewById<EditText>(R.id.signupPassword).text.toString()
        val name: String = findViewById<EditText>(R.id.signupName).text.toString()
        val userDetails = JSONObject()
        userDetails.put("email", email)
        userDetails.put("password", password)
        userDetails.put("name", name)
        return userDetails
    }
    fun createCompanyJsonLogin(): JSONObject
    {
        val email: String = findViewById<EditText>(R.id.loginEmail).text.toString()
        val password: String = findViewById<EditText>(R.id.loginPassword).text.toString()
        val userDetails = JSONObject()
        userDetails.put("email", email)
        userDetails.put("password", password)
        return userDetails
    }
    //Create JSON done

    //parsing errors
    fun parseVolleyError(error: VolleyError) :String
    {
        try {
            if (error.networkResponse.data == null)
                return error.toString()
            return String(error.networkResponse.data, Charsets.UTF_8)
        } catch (e: Exception) {
            return "Error Parsing" + e.toString()
        }
    }
    //parsing errors done
    var res = "E"
    //controllers start
    fun sendPostRequest(url:String, reqObject:JSONObject, view: Int)
    {
        val queue = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(Request.Method.POST, url, reqObject,
            { response ->
                res = response.toString()
                setContentView(view)
            },
            { error ->
                res = "Error"+parseVolleyError(error)
            }
        )
        queue.add(req)
    }
    fun processResponse(displayInfo: TextView, view: Int)
    {
        GlobalScope.launch{
            delay(1000)
            displayInfo.text = res
        }
    }
    //main screen controllers
    fun createUser(view: View)
    {
        val userDetails = createUserJsonSignup()
        val url = "http://localhost:3000/signup_user"
        val displayInfo = findViewById<TextView>(R.id.displayInfo)
        sendPostRequest(url, userDetails, R.layout.activity_main)
        processResponse(displayInfo, R.layout.activity_main)
    }

    fun loginUser(view: View)
    {
        val userDetails = createUserJsonLogin()
        val displayInfo = findViewById<TextView>(R.id.displayInfo)
        val url = "http://localhost:3000/login_user"
        sendPostRequest(url, userDetails, R.layout.activity_main)
        processResponse(displayInfo, R.layout.activity_main)
    }
    fun createCompany()
    {
        val userDetails = createCompanyJsonSignup()
        val displayInfo = findViewById<TextView>(R.id.displayInfo)
        val url = "http://localhost:3000/signup_company"
        sendPostRequest(url, userDetails, R.layout.activity_main)
        processResponse(displayInfo, R.layout.activity_main)
    }
    fun loginCompany()
    {
        val userDetails = createCompanyJsonLogin()
        val displayInfo = findViewById<TextView>(R.id.displayInfo)
        val url = "http://localhost:3000/login_user"
        sendPostRequest(url, userDetails, R.layout.activity_main)
        processResponse(displayInfo, R.layout.activity_main)
    }
    //main screen controllers done
}
