package sw.swayni.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

data class Origin(
    @SerializedName("name") val name:String,
    @SerializedName("url") val url:String
)
