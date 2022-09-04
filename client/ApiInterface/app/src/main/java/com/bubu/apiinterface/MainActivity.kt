package com.bubu.apiinterface

import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bubu.apiinterface.databinding.ActivityMainBinding
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.EOFException
import java.net.SocketTimeoutException


lateinit var userInformation: UserData

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        userInformation = UserData

        /**
         * get Address Module Usage
         * */
        CoroutineScope(Dispatchers.Default).launch {
            var testObject = UserGetAddressModule(null)
            val result = testObject.getApiData()
            if(result is List<*>) {
                result.forEach {
                    it as JsonObject
                    Log.d("name",it.get("name").toString())
                    Log.d("code",it.get("cd").toString())
                }
            } else if(result is UserError) {
                //..
            } else if(result is Exception) {
                //..
            }

            val result2 = testObject.getDetailCity("11")
            if(result2 is List<*>) {
                result2.forEach {
                    it as JsonObject
                    Log.d("name",it.get("name").toString())
                    Log.d("code",it.get("cd").toString())
                }
            } else if(result2 is UserError) {
                //..
            } else if(result2 is Exception) {
                //..
            }
            val result3 = testObject.getDetailCity("11020")
            if(result3 is List<*>) {
                result3.forEach {
                    it as JsonObject
                    Log.d("name",it.get("name").toString())
                    Log.d("code",it.get("cd").toString())
                }
            } else if(result3 is UserError) {
                //..
            } else if(result3 is Exception) {
                //..
            }
        }





        super.onCreate(savedInstanceState)
        setContentView(binding.root)




        /**
         * Any operation that requires authentication must run this procedure
         * */
        binding.checkButton.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                var auth = UserAuthModule(null)
                when (auth.getApiData()) {
                    true -> {
                        //Do Any Operation or Jobs..
                        CoroutineScope(Dispatchers.Main).launch {
                            binding.accessToken.text = userInformation.accessToken
                        }
                    }
                    is UserError -> {
                        //Error Handling
                    }
                    is UninitializedPropertyAccessException -> {
                        //Error Handling
                    }
                    is SocketTimeoutException -> {
                        //Error Handling
                    }
                    is EOFException -> {
                        //Error Handling
                    }
                    is Exception -> {
                        //Error Handling
                    }
                }
            }
        }



        binding.button.setOnClickListener {
            /**
             * {"username":"gbdngb12", "email":"gbdngb12@Naver.com", "password":"1919tkd2@"}
             *
             * UserLogin Usage
             * */
            CoroutineScope(Dispatchers.Default).launch {
                var searchUserData = JsonObject()
                searchUserData.addProperty("username", binding.username.text.toString())
                searchUserData.addProperty("email", binding.email.text.toString())
                searchUserData.addProperty("password", binding.password.text.toString())
                val testObject = UserLoginModule(searchUserData)
                when (val result = testObject.getApiData()) {
                    is UserLoginResponseData -> { // Successful
                        Log.d("result!", result.toString())
                        CoroutineScope(Dispatchers.Main).launch {
                            //  Staring the UI
                            binding.accessToken.text = result.accessToken
                            binding.refreshToken.text = result.refreshToken
                        }
                    }
                    is UserError -> { //if Error
                        Log.d("result", "I AM HERE!")
                        result.message.forEach { //Print Error Message
                            Log.d("result Error", it)
                        }
                    }
                    is SocketTimeoutException -> {
                        Log.d("result Exception", result.toString())
                    }
                    is EOFException -> {
                        Log.d("result Exception", result.toString())
                    }
                    is Exception -> {
                        Log.d("result Exception", result.toString())
                    }
                }
            }

        }

    }
}