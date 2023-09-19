package com.example.watchflixmovie.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.watchflixmovie.R
import com.example.watchflixmovie.domain.Movies
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import java.nio.charset.Charset

class DetailActivity : AppCompatActivity() {
    lateinit var posterBigImg:ImageView
    lateinit var posterNormalImg:ShapeableImageView
    lateinit var imageView9:ImageView
    lateinit var imageFavourite:ImageView
    lateinit var movieTitleText:TextView
    lateinit var mRequestQueue:RequestQueue
    lateinit var mStringRequest: StringRequest
    lateinit var movieRateText:TextView
    lateinit var timeFilm:TextView
    lateinit var movieDateText:TextView
    lateinit var textView11:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        posterBigImg = findViewById(R.id.posterBigImg)
        posterNormalImg = findViewById(R.id.posterNormalImg)
        imageView9 = findViewById(R.id.imageView9)
        imageFavourite = findViewById(R.id.imageFavourite)
        movieTitleText = findViewById(R.id.movieTitleText)
        movieRateText = findViewById(R.id.movieRateText)
        timeFilm = findViewById(R.id.movieTimeText)
        movieDateText = findViewById(R.id.movieDateText)
        textView11 = findViewById(R.id.textView11)

         initDetailView()
        back()

    }

    private fun back() {
        imageView9.setOnClickListener {
            val intent= Intent(this@DetailActivity,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initDetailView() {
        val id = intent.extras?.get("id")
        var body = ""
        val URL_IMAGE = "https://image.tmdb.org/t/p/w500/"

        mRequestQueue = Volley.newRequestQueue(this)
        mStringRequest = object :StringRequest(
            Request.Method.GET,"https://api.themoviedb.org/3/movie/$id",
            Response.Listener {
                val gson = Gson()
                val data = gson.fromJson(it,Movies::class.java)
                Glide.with(this@DetailActivity)
                    .load(URL_IMAGE+data.backdrop_path)
                    .into(posterBigImg)
                Glide.with(this@DetailActivity)
                    .load(URL_IMAGE+data.poster_path)
                    .into(posterNormalImg)
                movieTitleText.text = data.original_title
                movieRateText.text = data.vote_average.toString()
                timeFilm.text = data.popularity.toString()
                movieDateText.text = data.release_date
                textView11.text = data.overview
            }, Response.ErrorListener {
                Log.d("error", "error: $it ")
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String,String>()
                headers["Authorization"] = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzMGRmNDA5NmQ2MTNiNDNlM2Y0NjhiYzUzZjI1YmYyYyIsInN1YiI6IjYzZDQ5OWExMjBlNmE1MDBkNTQzMGIxNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.P-lTVsmLOeKvI9CuNFeRcuqL1-YUnbdxp_ak94jhmlU"

                return headers
            }

            override fun getBody(): ByteArray {

                return body.toByteArray(Charset.defaultCharset())
            }
        }
        mRequestQueue.add(mStringRequest)
    }
}