package com.example.posttest

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.posttest.model.Item2
import com.example.posttest.repository.Repository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "REST App steps by Adam H"
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val buttonGetCoordinates: Button = findViewById(R.id.button_get_coordinates)
        val buttonGetDistance: Button = findViewById(R.id.button_get)

        val textView = findViewById<TextView>(R.id.textView)
        textView.text = "The distance will show up here."

        buttonGetCoordinates.setOnClickListener {

        fetchLocation()
        }




        buttonGetDistance.setOnClickListener {
            viewModel.getPost()
            viewModel.myResponse.observe(this, { response ->
                if (response.isSuccessful) {
                    textView.text =
                        "Distance you have passed equals: " + response.body()?.Distance!!.toString() + " km. "
                } else {
                    textView.text = response.errorBody().toString()
                }

            })

        }

    }

    private fun fetchLocation() {
        val task = fusedLocationClient.lastLocation
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }
        task.addOnSuccessListener {
            if (it != null) {
                Toast.makeText(
                    applicationContext,
                    "${it.latitude} ${it.longitude}",
                    Toast.LENGTH_SHORT
                ).show()
                latitude = it.latitude
                longitude = it.longitude
                val myPostedCoordinates = Item2(latitude, longitude)
                viewModel.pushPost(myPostedCoordinates)
                viewModel.myResponse.observe(this, Observer { response ->
                    if (response.isSuccessful) {
                        Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
                    } else {

                        Toast.makeText(this, "Failed to send data", Toast.LENGTH_SHORT).show()
                    }

                })

            }
        }
    }

}