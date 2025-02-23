package sw.swayni.rickandmorty.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sw.swayni.rickandmorty.R


@Composable
fun LoadingPopup(){
    Box(
        modifier = Modifier
            .fillMaxWidth().fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.width(250.dp).wrapContentHeight(),
            shape = RoundedCornerShape(20.dp),
            colors = CardColors(
                containerColor = colorResource(R.color.status_bar_color),
                contentColor = colorResource(R.color.white),
                disabledContentColor = colorResource(R.color.status_bar_color),
                disabledContainerColor = colorResource(R.color.status_bar_color)
            )
        ){
            Column (modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 20.dp, bottom = 30.dp), horizontalAlignment = Alignment.CenterHorizontally){
                Row (modifier = Modifier.padding(bottom = 10.dp)) {
                    Text(text = stringResource(R.string.loading), fontSize = 18.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.SemiBold, color = colorResource(R.color.app_default_color))
                }
                Row (){
                    CircularProgressIndicator(
                        modifier = Modifier.wrapContentSize(),
                        color = colorResource(R.color.app_default_color),
                        strokeWidth = 5.dp,
                        trackColor = colorResource(R.color.background_progress_bar),
                        strokeCap = StrokeCap.Round
                    )
                }
            }
        }
    }
}