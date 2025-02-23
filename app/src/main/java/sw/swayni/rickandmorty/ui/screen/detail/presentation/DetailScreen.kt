package sw.swayni.rickandmorty.ui.screen.detail.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import sw.swayni.rickandmorty.R
import sw.swayni.rickandmorty.data.model.Character
import sw.swayni.rickandmorty.ui.component.LoadingPopup
import sw.swayni.rickandmorty.ui.screen.detail.viewmodel.DetailViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    id : Int,
    viewModel: DetailViewModel = hiltViewModel(),
    navController: NavController
){
    viewModel.getCharacter(id)
    viewModel.getFavoriteCharacter(id)



    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                actions = {
                    IconButton(
                        modifier = Modifier.size(50.dp),
                        onClick = { navController.popBackStack() }
                    ){
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },

            )
        },
        modifier = Modifier.fillMaxSize(),
        content = {
            val uiCharacterState by viewModel.uiCharacterState.collectAsState()

            val loadingState by viewModel.loadingState.collectAsState()

            val errorState by viewModel.errorState.collectAsState()

            if (loadingState) { LoadingPopup() }

            if (errorState != null) { Text(text = errorState!!) }

            uiCharacterState?.let { character ->
                CharacterView(
                    character = character,
                    viewModel
                )
            }
        }
    )
}

@Composable
fun CharacterView(
    character: Character,
    viewModel: DetailViewModel
){
    viewModel.getEpisodeList(character)

    val uiIsFavoriteState by viewModel.uiIsFavoriteState.collectAsState()

    Box(modifier = Modifier.fillMaxSize().padding(top = 80.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)){
        Column (modifier = Modifier.fillMaxWidth()){
            Text(text = character.name, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, color = colorResource(id = R.color.app_default_color), modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp))

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Card(
                    modifier = Modifier.size(100.dp),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(character.image),
                        contentDescription = "Character Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Column (
                    modifier = Modifier.padding(start = 20.dp),
                    verticalArrangement = Arrangement.Center
                ){
                    Text(text = character.status, fontWeight = FontWeight.Normal, fontSize = 20.sp, color = colorResource(id = R.color.app_default_color), modifier = Modifier.padding(vertical = 5.dp))

                    Text(text = character.gender, fontWeight = FontWeight.Normal, fontSize = 20.sp, color = colorResource(id = R.color.app_default_color), modifier = Modifier.padding(vertical = 5.dp))
                }
            }

            EpisodeView(viewModel)
        }

        FavoriteView(uiIsFavoriteState, onFavoriteClicked = { if (uiIsFavoriteState) { viewModel.removeFavorite(character) } else { viewModel.addFavorite(character) } },
            modifier = Modifier.align(Alignment.BottomCenter)
                .background(
                    color = colorResource(R.color.app_default_color),
                    shape = RoundedCornerShape(15.dp)
                ).padding(horizontal = 20.dp, vertical = 10.dp)
        )
    }
}

@Composable
fun  EpisodeView(
    viewModel: DetailViewModel
){
    val uiState by viewModel.uiEpisodeListState.collectAsState()
    var isExpanded by remember { mutableStateOf(false) }

    uiState?.let { episodes->
        Column (
            modifier = Modifier
                .fillMaxWidth()
        ){
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = stringResource(R.string.episodes_title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = colorResource(R.color.app_default_color))

                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp, contentDescription = "Expand / Collapse icon")
                }
            }

            if (isExpanded) {
                LazyColumn (Modifier.height(200.dp).padding(horizontal = 20.dp)){
                    items(episodes){ episode ->
                        Text(
                            text = "${episode.name} - ${episodeStringToFormat(episode.episode)}",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteView(
    isFavorite: Boolean,
    onFavoriteClicked: () -> Unit,
    modifier: Modifier
){
    IconButton(
        onClick = onFavoriteClicked,
        modifier = modifier,
    ) {
        Icon(
            imageVector = if (isFavorite) {Icons.Default.Favorite} else {Icons.Default.FavoriteBorder},
            contentDescription = stringResource(R.string.favorite),
            tint = colorResource(R.color.white)
        )
    }
}


private fun episodeStringToFormat(episodeString: String):String{
    val resultStringBuilder = StringBuilder()

    var term = episodeString.substring(0,3)
    resultStringBuilder.append(
        if (term[1] == '0'){ "${term.replace("0", "")} - " } else{ "$term -" }
    )

    term = episodeString.substring(3)
    resultStringBuilder.append(
        if (term[1] == '0'){term.replace("0", "")} else{term}
    )

    return resultStringBuilder.toString()
}