package com.example.dogappv2.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class DogBreedResponse(
    val message: Map<String, List<String>>,
    val status: String
)


data class DogImagesResponse(
    val message: List<String>,
    val status: String
)

interface DogApiService {
    @GET("breeds/list/all")
    suspend fun getDogBreeds(): DogBreedResponse

    @GET("breed/{breedName}/images")
    suspend fun getBreedImages(@Path("breedName") breedName: String): DogImagesResponse
}

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: DogApiService by lazy {
        retrofit.create(DogApiService::class.java)
    }
}
