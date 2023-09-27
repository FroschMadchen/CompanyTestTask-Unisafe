package com.example.testtackunisafe

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testtackunisafe.databinding.ActivityMainBinding
import com.example.testtackunisafe.`interface`.MainApi
import com.example.testtackunisafe.recevied_data.ReceivedData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://cyberprot.ru/shopping/v1/")
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val mainApi = retrofit.create(MainApi::class.java)


        binding.startBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val keyValue: String = createTestKey(mainApi)
                Log.i("Key value", ""+keyValue)
                val successfulLogin:Boolean = authentication(mainApi,keyValue)
                    runOnUiThread { //возвращаемся на ui поток
                        val intent =
                            Intent(this@MainActivity, SecondActivity::class.java)
                        intent.putExtra("keyV", keyValue)
                        startActivity(intent)
                    }

            }
        }


    }
    private suspend fun createTestKey(mainApi: MainApi): String {
        val key = mainApi.createTestKey()
        return if (key.isSuccessful) {
            val keyData = key.body().toString() // Get the response body as a string
            Log.i("String value", (keyData ?: "key").toString())
            return keyData
        }else{ "null"
    }
}
    private suspend  fun authentication(mainApi: MainApi,key:String):Boolean{
        val success = mainApi.authentication(keyValue = key)
        return success.success
    }




    /*  binding.startBtn.setOnClickListener {
          CoroutineScope(Dispatchers.IO).launch {
              try {
                  val response = mainApi.createTestKey()
                  if (response.isSuccessful) {
                      val receivedData = response.body()
                      if (receivedData != null) { // Получаем ключ из объекта ReceivedData
                          val keyValue = receivedData.key
                          val loginOccurred = mainApi.getProductById(keyValue)
                          if (loginOccurred.success) { // Проверяем значение, если ключ подходит для входа, то loginOccurred будет true
                              Log.i("loginOccurred", "true")
                              runOnUiThread { //возвращаемся на ui поток
                                  val intent =
                                      Intent(this@MainActivity, SecondActivity::class.java)
                                  intent.putExtra("keyV", keyValue)
                                  startActivity(intent)
                              }
                          } else {
                              Log.i("loginOccurred", "false")
                              // Обработка случая, когда ключ не подходит для входа
                          }
                      } else {
                          Log.i("Empty object received", "!empty object")
                          // Обработка случая, когда получен пустой объект ReceivedData
                      }
                  } else {
                      Log.i("Failed request", "failed request!")
                      // Обработка случая, когда запрос завершился неуспешно
                  }
              } catch (e: Exception) {
                  // Обработка исключения, если произошла ошибка во время выполнения запроса
                  e.printStackTrace()
                  e.message?.let { it1 -> Log.i("Exception", it1) }
              }


          }
      }*/





    /*
    val viewModel = ViewModelProvider(this).get(KeyViewModel::class.java)

// Устанавливаем тестовый ключ
    viewModel.setTestKey("Ваш тестовый ключ")

// Устанавливаем ключ, полученный в других запросах
    viewModel.setApiKey("Ваш ключ из другого запроса")

// Вызываем метод createShoppingList с именем списка
    val response = viewModel.createShoppingList("Shopping with bestie")

    if (response.success) {
        // Обработка успешного ответа, например, получение list_id
        val listId = response.list_id
    } else {
        // Обработка ошибки, если success = false
    }


      val loginOccurred:ReceivedData = mainApi.getProductById(keyValue)
                if (loginOccurred.success) { // Проверяем значение, если ключ подходит для входа, то loginOccurred будет true
                    Log.i("loginOccurred", "true")
                    runOnUiThread { //возвращаемся на ui поток
                        val intent =
                            Intent(this@MainActivity, SecondActivity::class.java)
                        intent.putExtra("keyV", keyValue)
                        startActivity(intent)
                    }
                } else {
                    Log.i("loginOccurred", "false")
                    // Обработка случая, когда ключ не подходит для входа
                }
}*/
}

