package sw.swayni.rickandmorty.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import sw.swayni.rickandmorty.R

@Composable
fun DefaultListItem(image: String, title: String){
    Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start){
        Card (shape = RoundedCornerShape(10.dp)) {
            Image(modifier = Modifier.fillMaxWidth().height(200.dp), painter = rememberImagePainter(data = image), contentDescription = "", contentScale = ContentScale.Crop)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = title, fontSize = 20.sp, color = colorResource(R.color.app_default_color), fontWeight = FontWeight.SemiBold)
    }
}