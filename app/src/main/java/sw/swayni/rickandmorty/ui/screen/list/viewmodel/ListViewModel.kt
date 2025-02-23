package sw.swayni.rickandmorty.ui.screen.list.viewmodel

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
class ListViewModel @Inject constructor(private val repository: IRepository, @IoDispatcher private val ioDispatcher: CoroutineDispatcher): ViewModel() {

    private val _uiState = MutableStateFlow(ListViewState())
    val uiState = _uiState.asStateFlow()

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    fun getAllCharacters(){
        viewModelScope.launch (ioDispatcher){
            _loadingState.update { true }
            repository.getAllCharacters().collect{ data->
                when(data){
                    is ResultData.Error -> {
                        _errorState.update { data.exception.message }
                        _loadingState.update { false }
                    }

                    is ResultData.Success -> {
                        _uiState.update { state -> state.copy(data = data.data.result) }
                        _loadingState.update { false }
                    }
                }
            }
        }
    }
}