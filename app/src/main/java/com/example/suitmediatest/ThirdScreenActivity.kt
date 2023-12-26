package com.example.suitmediatest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdScreenActivity : AppCompatActivity() {
    private val perPage = 10
    private var currentPage = 1
    private var isLoading = false

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var userAdapter: AdapterUser
    private var userList: MutableList<User> = mutableListOf()
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screen)

        layoutManager = LinearLayoutManager(this)

        recyclerView = findViewById(R.id.rvUser)
        progressBar = findViewById(R.id.progressBar)

        userAdapter = AdapterUser(userList)
        recyclerView.adapter = userAdapter
        recyclerView.layoutManager = layoutManager

        userAdapter.setOnItemClickListener(object : AdapterUser.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val clickedUser = userList[position]
                val fullName = "${clickedUser.first_name} ${clickedUser.last_name}"
                val intent = Intent(this@ThirdScreenActivity, SecondScreenActivity::class.java)
                intent.putExtra("SELECTUSER", fullName)
                startActivity(intent)
            }
        })

        getPage()

        var previousTotalItemCount = 0
        var totalItemCount = 0

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !isLoading) {
                    previousTotalItemCount = totalItemCount
                    totalItemCount = userAdapter.itemCount

                    val pastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
                    val pastVisibleItemm = layoutManager.findLastVisibleItemPosition()
                    Log.e("pre", previousTotalItemCount.toString())
                    Log.e("tot", totalItemCount.toString())
                    Log.e("past", pastVisibleItem.toString())
                    Log.e("pastm", pastVisibleItemm.toString())

                    if(pastVisibleItem >= totalItemCount-1){
                        if(previousTotalItemCount != totalItemCount){
                            currentPage++
                            getPage()
                        } else {
                            Toast.makeText(this@ThirdScreenActivity, "Tidak ada data baru", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
    }

    private fun getUser(page: Int, item: Int) {
        ApiClient.apiService.getUsers(page, item)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        apiResponse?.data?.let {
                            userList.addAll(it)
                            userAdapter.notifyDataSetChanged()
                        }
                    } else {
                        // Handle unsuccessful response
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    // Handle failure
                }
            })
    }

    fun getPage() {
        isLoading = true
        progressBar.visibility = View.VISIBLE

        Handler().postDelayed({
            isLoading = false
            progressBar.visibility = View.GONE
            getUser(currentPage, perPage)
        }, 3000)
    }

    fun intentBack(view: View){
        val intent = Intent(this, SecondScreenActivity::class.java)
        startActivity(intent)
    }
}