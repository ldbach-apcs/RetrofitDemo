package com.example.cpu02351_local.retrofitdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val baseUrl = "https://api.github.com/"

        val httpClient = OkHttpClient.Builder()

        val builder = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(
                        GsonConverterFactory.create()
                )

        val retrofit = builder
                .client(
                        httpClient.build()
                )
                .build()

        val client = retrofit.create(GithubClient::class.java)


        val start = System.currentTimeMillis()

        val call = client.getRepos("ldbach-apcs")
        call.enqueue(object : retrofit2.Callback<List<GithubRepo>> {
            override fun onResponse(call: Call<List<GithubRepo>>, response: Response<List<GithubRepo>>) {
                Log.d("RETROFIT_SINGLE", "${System.currentTimeMillis() - start}")
            }

            override fun onFailure(call: Call<List<GithubRepo>>, t: Throwable) {
                // the network call was a failure
            }
        })
    }
}
