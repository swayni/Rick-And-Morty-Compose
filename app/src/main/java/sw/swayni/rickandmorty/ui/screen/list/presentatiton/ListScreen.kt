package sw.swayni.rickandmorty.ui.screen.list.presentatiton

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import sw.swayni.rickandmorty.data.model.Character
import sw.swayni.rickandmorty.ui.component.ListView
import sw.swayni.rickandmorty.ui.component.LoadingPopup
import sw.swayni.rickandmorty.ui.screen.list.viewmodel.ListViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(
    viewModel: ListViewModel = hiltViewModel(),
    onNavigateToDetail: (Character) -> Unit = {}
){
    viewModel.getAllCharacters()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ){
        val uiState by viewModel.uiState.collectAsState()
        val loadingState by viewModel.loadingState.collectAsState()
        val errorState by viewModel.errorState.collectAsState()

        uiState.data?.let {
            ListView(it, onNavigateToDetail)
        }

        if(loadingState && uiState.data == null) LoadingPopup()

        if(errorState != null) Text(text = errorState!!)

    }
}



