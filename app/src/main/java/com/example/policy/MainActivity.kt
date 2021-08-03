package com.example.policy

import MyAdapter
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.net.CookieHandler
import java.net.CookieManager


class MainActivity : AppCompatActivity()
{
    fun createListView(mCompanies:Array<String>)
    {
        //var mCompanies = arrayOf("Algorithms", "Data Structures", "Languages", "Interview Corner")
        var mSumInsured = Array<String>(mCompanies.size){"SI"}
        var mPremium = Array<String>(mCompanies.size){"PR"}
        val listView = findViewById<ListView>(R.id.listView)
        val adapter = MyAdapter(this, mCompanies, mSumInsured, mPremium)
        listView.setAdapter(adapter)
        listView.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            Toast.makeText(this@MainActivity, position.toString(), Toast.LENGTH_SHORT).show()
        })
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        CookieHandler.setDefault(CookieManager())
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
    //company routes
    fun renderCreatePolicy(view: View)
    {
        setContentView(R.layout.create_policy)
    }
    fun renderViewPoliciesCompany(view: View)
    {
        setContentView(R.layout.view_policies_of_company)
        viewList("http://localhost:3000/view_policies_of_my_company",view);
    }
    fun renderViewClaimsCompany(view: View)
    {
        setContentView(R.layout.view_claims_of_company)
        viewList("http://localhost:3000/view_claims_of_my_company",view);
    }
    //user routes
    fun renderViewPolicies(view: View)
    {
        setContentView(R.layout.view_policies)
        viewList("http://localhost:3000/policies",view);
    }
    fun renderViewMyBonds(view: View)
    {
        setContentView(R.layout.view_my_bonds)
        viewList("http://localhost:3000/viewmypolicies",view);
    }
    fun renderViewMyClaims(view: View)
    {
        setContentView(R.layout.view_my_claims)
        viewList("http://localhost:3000/viewmyclaims",view);
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
        val companyDetails = JSONObject()
        companyDetails.put("email", email)
        companyDetails.put("password", password)
        companyDetails.put("company_name", name)
        return companyDetails
    }
    fun createCompanyJsonLogin(): JSONObject
    {
        val email: String = findViewById<EditText>(R.id.loginEmail).text.toString()
        val password: String = findViewById<EditText>(R.id.loginPassword).text.toString()
        val companyDetails = JSONObject()
        companyDetails.put("email", email)
        companyDetails.put("password", password)
        return companyDetails
    }
    fun createPolicyJson(): JSONObject
    {
        val policyWording = findViewById<EditText>(R.id.policyWording).text.toString()
        val roomRentCap = findViewById<EditText>(R.id.roomRentCap).text.toString()
        val sumInsured = findViewById<EditText>(R.id.sumInsured).text.toString()
        val exemptions = findViewById<EditText>(R.id.exemptions).text.toString()
        val csr = findViewById<EditText>(R.id.csr).text.toString()
        val policyDetails = JSONObject()
        policyDetails.put("policywording", policyWording)
        policyDetails.put("roomrentcap", roomRentCap)
        policyDetails.put("suminsured", sumInsured)
        policyDetails.put("exemptions", exemptions)
        policyDetails.put("claim_settlement_ratio", csr)
        return policyDetails
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
    var res = "E";
    var resJson= JSONArray()
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
    fun sendGetRequest(url:String, view: Int?)
    {
        val queue = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                res = response.toString()
                if(view!=null)
                    setContentView(view)
            },
            { error ->
                res = "Error"+parseVolleyError(error)
            }
        )
        queue.add(req)
    }
    fun sendGetRequest2(url:String, view: Int?)
    {
        val queue = Volley.newRequestQueue(this)
        val req = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                res = response.toString()
                resJson = response
                if(view!=null)
                    setContentView(view)
            },
            { error ->
                res = "Error"+parseVolleyError(error)
            }
        )
        queue.add(req)
    }
    fun processResponse(displayInfo: TextView)
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
        processResponse(displayInfo)
    }

    fun loginUser(view: View)
    {
        val userDetails = createUserJsonLogin()
        val displayInfo = findViewById<TextView>(R.id.displayInfo)
        val url = "http://localhost:3000/login_user"
        sendPostRequest(url, userDetails,R.layout.user_screen)
        processResponse(displayInfo)
    }
    fun createCompany(view: View)
    {
        val companyDetails = createCompanyJsonSignup()
        val displayInfo = findViewById<TextView>(R.id.displayInfo)
        val url = "http://localhost:3000/signup_company"
        sendPostRequest(url, companyDetails, R.layout.activity_main)
        processResponse(displayInfo)
    }
    fun loginCompany(view: View)
    {
        val companyDetails = createCompanyJsonLogin()
        val displayInfo = findViewById<TextView>(R.id.displayInfo)
        val url = "http://localhost:3000/login_company"
        sendPostRequest(url, companyDetails, R.layout.company_screen)
        processResponse(displayInfo)
    }
    //main screen controllers done

    //company controllers
    fun createPolicy(view: View)
    {
        val policyDetails = createPolicyJson()
        val displayInfo = findViewById<TextView>(R.id.displayInfo)
        val url = "http://localhost:3000/createpolicy"
        sendPostRequest(url, policyDetails, R.layout.company_screen)
        processResponse(displayInfo)
    }
    fun viewList(url: String,view: View)
    {
        sendGetRequest2(url, null)
        var length = 1
        if(resJson.length()> length)
            length = resJson.length()
        var companies = Array<String>(length){"comp"}
        for(i in 0 until resJson.length() )
            companies[i] = resJson[i].toString()

        createListView(companies)
    }
    fun logoutCompany(view: View)
    {
        val url = "http://localhost:3000/logout_company"
        sendGetRequest(url, R.layout.activity_main)
        Toast.makeText(this, res,Toast.LENGTH_LONG).show()
    }

    //user controllers
    fun logoutUser(view: View)
    {
        val url = "http://localhost:3000/logout_user"
        sendGetRequest(url, R.layout.activity_main)
        Toast.makeText(this, res,Toast.LENGTH_LONG).show()
    }
}