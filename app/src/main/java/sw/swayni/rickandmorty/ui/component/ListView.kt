package sw.swayni.rickandmorty.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sw.swayni.rickandmorty.R
import sw.swayni.rickandmorty.data.model.Character

@Composable
fun ListView(
    characters: List<Character>,
    onClick: (Character) -> Unit = {}
){
    Column {
        Spacer(modifier = Modifier.height(50.dp))
        Row {
            Spacer(modifier = Modifier.width(30.dp))
            Text(text = stringResource(R.string.characters_title), color = colorResource(R.color.app_default_color), fontSize = 23.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.height(15.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(25.dp),
            horizontalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            this.items(characters) { character ->
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight().clickable { onClick(character) }){
                    DefaultListItem(image = character.image, title = character.name)
                }
            }
        }
    }
}