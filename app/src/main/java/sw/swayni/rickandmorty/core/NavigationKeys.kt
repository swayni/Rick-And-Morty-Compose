package sw.swayni.rickandmorty.core

import sw.swayni.rickandmorty.core.NavigationKeys.Arg.CHARACTER_ID

object NavigationKeys {
    object Arg {
        const val CHARACTER_ID = "characterId"
    }

    object Route {
        const val LIST = "list"
        const val FAVORITE = "favorite"
        const val DETAIL = "detail/{$CHARACTER_ID}"
    }
}