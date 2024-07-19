package ru.apstegor.retrofitexemple

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.apstegor.retrofitexemple.data.api.ApiExemple
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    val moshiBuilder = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val myCoroutineScope = CoroutineScope(Dispatchers.IO)

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .connectTimeout(CONNECTION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(DEV_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshiBuilder))
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        myCoroutineScope.launch {
            val response = retrofit.create(ApiExemple::class.java).getPost()
            Log.e("response",response.toString())
            withContext(Dispatchers.Main){
                findViewById<TextView>(R.id.tw1).setText(response.title)
                findViewById<TextView>(R.id.tw2).setText(response.body)
            }
        }
    }

    companion object {

        private const val READ_TIMEOUT_IN_SECONDS = 30L
        private const val CONNECTION_TIMEOUT_IN_SECONDS = 30L
        private const val DEV_BASE_URL = "https://jsonplaceholder.typicode.com"
    }
}