package com.example.testtackunisafe


import androidx.lifecycle.ViewModel

class KeyViewModel : ViewModel() {
    private var testKey: String = "" // Здесь храните тестовый ключ
    private var apiKey: String = ""  // Здесь храните ключ, полученный в других запросах

   /* // Метод для установки тестового ключа
    fun setTestKey(key: String) {
        testKey = key
    }

    // Метод для установки ключа, полученного в других запросах
    fun setApiKey(key: String) {
        apiKey = key
    }

    // Метод для вызова createShoppingList с сохраненными ключами
    suspend fun createShoppingList(name: String): ReceivedDataListId {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://cyberprot.ru/shopping/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val mainApi = retrofit.create(MainApi::class.java)

        return mainApi.createShoppingList(testKey, name)
    }*/
}


