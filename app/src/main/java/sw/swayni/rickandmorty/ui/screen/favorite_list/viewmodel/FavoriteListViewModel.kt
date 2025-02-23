package sw.swayni.rickandmorty.ui.screen.favorite_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sw.swayni.rickandmorty.core.ResultData
import sw.swayni.rickandmorty.data.repository.IRepository
import sw.swayni.rickandmorty.di.quality.IoDispatcher
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(private val repository: IRepository, @IoDispatcher private val dispatcher: CoroutineDispatcher) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteListViewState())
    val uiState = _uiState.asStateFlow()

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    fun getFavoriteList(){
        viewModelScope.launch(dispatcher) {
            _loadingState.update { true }
            repository.getFavoriteCharacters().collect{ data ->
                when(data){
                    is ResultData.Error -> {
                        _errorState.update { data.exception.message }
                        _loadingState.update { false }
                    }
                    is ResultData.Success -> {
                        _uiState.update { state -> state.copy(data = data.data) }
                        _loadingState.update { false }
                    }
                }
            }
        }
    }
}