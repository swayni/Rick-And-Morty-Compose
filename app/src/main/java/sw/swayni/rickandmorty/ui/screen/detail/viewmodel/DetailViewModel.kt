package sw.swayni.rickandmorty.ui.screen.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sw.swayni.rickandmorty.core.ResultData
import sw.swayni.rickandmorty.data.model.Character
import sw.swayni.rickandmorty.data.model.Episode
import sw.swayni.rickandmorty.data.repository.IRepository
import sw.swayni.rickandmorty.di.quality.IoDispatcher
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: IRepository, @IoDispatcher private val dispatcher: CoroutineDispatcher) : ViewModel()  {

    private val _uiCharacterState = MutableStateFlow<Character?>(null)
    val uiCharacterState = _uiCharacterState.asStateFlow()

    private val _uiEpisodeListState = MutableStateFlow<List<Episode>?>(null)
    val uiEpisodeListState = _uiEpisodeListState.asStateFlow()

    private val _uiIsFavoriteState = MutableStateFlow<Boolean>(false)
    val uiIsFavoriteState = _uiIsFavoriteState.asStateFlow()

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()


    fun getCharacter(id: Int){
        viewModelScope.launch(dispatcher) {
            _loadingState.update { true }
            repository.getCharacter(id).collect{ data ->
                when(data){
                    is ResultData.Error -> {
                        _errorState.update { data.exception.message }
                    }
                    is ResultData.Success -> {
                        _uiCharacterState.update { data.data }
                    }
                }
            }
        }
    }

    fun getEpisodeList(character: Character){
        viewModelScope.launch(dispatcher) {
            var idsString = ""
            character.episode.forEach { url->
                idsString = "${idsString},${url.substring(40)}"
            }
            val ids = idsString.substring(1)

            repository.getEpisodeList(ids).collect{ data ->
                when(data){
                    is ResultData.Error -> {
                        _errorState.update { data.exception.message }
                    }
                    is ResultData.Success -> {
                        _uiEpisodeListState.update { data.data }
                    }
                }
            }
        }
    }

    fun addFavorite(character: Character){
        viewModelScope.launch(dispatcher) {
            _loadingState.update { true }
            repository.addFavoriteCharacter(character).collect{ data ->
                when(data){
                    is ResultData.Error -> {
                        _uiIsFavoriteState.update { false }
                        _loadingState.update { false }
                    }
                    is ResultData.Success -> {
                        _uiIsFavoriteState.update { data.data }
                        _loadingState.update { false }
                    }
                }
            }
        }
    }

    fun removeFavorite(character: Character){
        viewModelScope.launch(dispatcher) {
            _loadingState.update { true }
            repository.removeFavoriteCharacter(character).collect { data ->
                when (data) {
                    is ResultData.Error -> {
                        _uiIsFavoriteState.update { false }
                        _loadingState.update { false }
                    }
                    is ResultData.Success -> {
                        _uiIsFavoriteState.update { data.data }
                        _loadingState.update { false }
                    }
                }
            }
        }
    }

    fun getFavoriteCharacter(id : Int){
        viewModelScope.launch(dispatcher) {
            _loadingState.update { true }
            repository.getFavoriteCharacterWithId(id).collect { data ->
                when(data){
                    is ResultData.Error -> {
                        _uiIsFavoriteState.update { false }
                        _loadingState.update { false }
                    }
                    is ResultData.Success -> {
                        _uiIsFavoriteState.update { data.data }
                        _loadingState.update { false }
                    }
                }
            }
        }
    }
}