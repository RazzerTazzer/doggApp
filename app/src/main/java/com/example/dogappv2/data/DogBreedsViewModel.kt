import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogappv2.data.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DogBreedsViewModel : ViewModel() {
    private val _dogBreeds = MutableStateFlow<Map<String, List<String>>>(emptyMap())
    val dogBreeds: StateFlow<Map<String, List<String>>> = _dogBreeds

    private val _breedImages = MutableStateFlow<List<String>>(emptyList())
    val breedImages: StateFlow<List<String>> = _breedImages

    private val _favoriteImages = MutableStateFlow<Map<String, List<String>>>(emptyMap())
    val favoriteImages: MutableStateFlow<Map<String, List<String>>> = _favoriteImages

    init {
        fetchDogBreeds()
    }

    private fun fetchDogBreeds() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getDogBreeds()
                if (response.status == "success") {
                    _dogBreeds.value = response.message
                }
            } catch (e: Exception) {
                // Handle the error appropriately
                e.printStackTrace()
            }
        }
    }


    fun fetchBreedImages(breedName: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getBreedImages(breedName)
                if (response.status == "success") {
                    _breedImages.value = response.message
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addFavoriteImage(imageUrl: String, breed: String) {
        val currentFavorites = _favoriteImages.value.toMutableMap()
        val breedFavorites = currentFavorites[breed]?.toMutableList() ?: mutableListOf()
        breedFavorites.add(imageUrl)
        currentFavorites[breed] = breedFavorites
        _favoriteImages.value = currentFavorites
    }

    fun removeFromFavorite(imageUrl: String, breed: String) {
        val currentFavorites = _favoriteImages.value.toMutableMap()
        val breedFavorites = currentFavorites[breed]?.toMutableList() ?: mutableListOf()
        breedFavorites.remove(imageUrl)
        currentFavorites[breed] = breedFavorites
        _favoriteImages.value = currentFavorites
    }

    fun isFavorited(imageUrl: String, breed: String) : Boolean {
        val currentFavorites = _favoriteImages.value.toMutableMap()
        val breedFavorites = currentFavorites[breed]?.toMutableList() ?: mutableListOf()

        return breedFavorites.contains(imageUrl)
    }
}
