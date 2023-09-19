package com.example.watchflixmovie.activity

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.watchflixmovie.R
import com.example.watchflixmovie.adapter.MovieAdapter
import com.example.watchflixmovie.domain.ApiResponse
import com.example.watchflixmovie.domain.MovieList
import com.example.watchflixmovie.domain.Movies
import com.google.gson.Gson
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    lateinit  var rvNowPlaying:RecyclerView
    lateinit var rvUpcoming:RecyclerView
    lateinit var  mRequestQueue:RequestQueue
    lateinit var mStringRequest1:StringRequest
    lateinit var mStringRequest2:StringRequest
    lateinit var loading1:ProgressBar
    lateinit var loading2:ProgressBar
    lateinit var movieList:ArrayList<Movies>
    lateinit var  movieAdapter:MovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loading1 = findViewById(R.id.loading1)
        loading2 = findViewById(R.id.loading2)
        initView()
        sendRequest1()
        sendRequest2()

    }

    private fun sendRequest1() {
       mRequestQueue = Volley.newRequestQueue(this)
        loading1.visibility = View.VISIBLE
        var requestBody=""
        movieList = ArrayList()
        mStringRequest1 = object: StringRequest(
            Request.Method.GET,"https://api.themoviedb.org/3/movie/now_playing",
            Response.Listener {
                println("tes")
               val gson = Gson()
                loading1.visibility = View.GONE
                movieAdapter = MovieAdapter(gson.fromJson(it,ApiResponse::class.java).results)
                rvNowPlaying.adapter = movieAdapter

                Log.d("it",it)
            },
            Response.ErrorListener {error->
                println("$error")
                Log.d("error",error.toString())
            }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String,String>()
                headers["Authorization"] = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzMGRmNDA5NmQ2MTNiNDNlM2Y0NjhiYzUzZjI1YmYyYyIsInN1YiI6IjYzZDQ5OWExMjBlNmE1MDBkNTQzMGIxNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.P-lTVsmLOeKvI9CuNFeRcuqL1-YUnbdxp_ak94jhmlU"
                return headers
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray(Charset.defaultCharset())
            }
        }
        mRequestQueue.add(mStringRequest1)
    }

    private fun sendRequest2(){
        mRequestQueue = Volley.newRequestQueue(this)
        loading2.visibility = View.VISIBLE
        var requestBody = ""
        movieList = ArrayList()
        mStringRequest2 = object :StringRequest(
            Request.Method.GET,"https://api.themoviedb.org/3/movie/upcoming",Response.Listener {
                loading2.visibility = View.GONE
                val gson2 = Gson()
                movieAdapter = MovieAdapter(gson2.fromJson(it,ApiResponse::class.java).results)
                rvUpcoming.adapter = movieAdapter
            },
            Response.ErrorListener {
                Log.d("error", "${it}")
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String,String>()
                headers["Authorization"]="Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzMGRmNDA5NmQ2MTNiNDNlM2Y0NjhiYzUzZjI1YmYyYyIsInN1YiI6IjYzZDQ5OWExMjBlNmE1MDBkNTQzMGIxNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.P-lTVsmLOeKvI9CuNFeRcuqL1-YUnbdxp_ak94jhmlU"
                return headers
            }
        }
        mRequestQueue.add(mStringRequest2)

    }


    private fun initView() {
        rvNowPlaying = findViewById(R.id.newMovies)
        rvNowPlaying.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rvUpcoming = findViewById(R.id.upcoming)
        rvUpcoming.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
    }


}