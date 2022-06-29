package com.example.header

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    fun api(view: View) {
        val textView = findViewById<TextView>(R.id.respuesta)
        val textView2 = findViewById<TextView>(R.id.respuesta2)
        val PublicMerchantId="84e1d0de1fbf437e9779fd6a52a9ca18"
        val q = Volley.newRequestQueue(this)
        var cad = ""
        var j = object : JsonArrayRequest(
            Method.GET, "https://api-uat.kushkipagos.com/transfer/v1/bankList",null,
            { response ->

                for (i in 0 until response.length()) {
                    val jobj: JSONObject = response.getJSONObject(i)
                    cad = cad +
                         "\n"+"code: "+jobj.getString("code")+"\n"+
                        "\n"+"; name: "+jobj.getString("name")+"\n"
                }
                textView.text = cad
                if(textView.text!="")
                {
                    textView2.text = "CONSULTA REALIZADA :)"
                }
            },
            {   Response.ErrorListener {
                    error->error.printStackTrace()
            }
            }
        ) {  @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json; utf-8")
                headers.put("Public-Merchant-Id", PublicMerchantId)
                headers.put("Accept", "**")
                return headers
            }
        }
        q.add(j);
    }
}

