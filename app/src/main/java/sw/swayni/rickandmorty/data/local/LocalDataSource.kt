package sw.swayni.rickandmorty.data.local

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import sw.swayni.rickandmorty.core.ResultData
import sw.swayni.rickandmorty.data.model.Character
import sw.swayni.rickandmorty.data.model.LocationRM
import sw.swayni.rickandmorty.data.model.Origin
import sw.swayni.rickandmorty.data.realm_model.FavoriteCharacterModel
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val realm: Realm): ILocalDataSource {

    override suspend fun getFavoriteCharacters(): Flow<ResultData<List<Character>>> = flow {
        try {
            val favoriteCharacters = realm.query<FavoriteCharacterModel>().find()
            val characterList = mutableListOf<Character>()
            favoriteCharacters.forEach { favoriteCharacterModel ->
                characterList.add(toCharacterModel(favoriteCharacterModel))
            }
            emit(ResultData.Success(characterList))
        }catch (e: Exception){
            emit(ResultData.Error(e))
        }
    }

    override suspend fun getFavoriteCharacterWithId(id: Int): Flow<ResultData<Boolean>> = flow {
        try {
            val favoriteCharacterModel = realm.query<FavoriteCharacterModel>("id == $id").find().first()
            favoriteCharacterModel.asFlow().collect{
                emit(ResultData.Success(it.obj != null))
            }
        }catch (e: Exception){
            emit(ResultData.Success(false))
        }
    }

    override suspend fun addFavoriteCharacter(character: Character): Flow<ResultData<Boolean>> = flow {
        try {
            realm.writeBlocking {
                val favoriteCharacterModel = toFavoriteCharacterModel(character)
                this.copyToRealm(favoriteCharacterModel)
            }
            emit(ResultData.Success(true))
        }catch (e : Exception){
            emit(ResultData.Success(false))
        }
    }

    override suspend fun removeFavoriteCharacter(character: Character) : Flow<ResultData<Boolean>> = flow {
        try {
            realm.writeBlocking {
                val favoriteCharacterModel = query<FavoriteCharacterModel>("id == ${character.id}").find().first()
                delete(favoriteCharacterModel)
            }
            emit(ResultData.Success(true))
        }catch (e : Exception){
            emit(ResultData.Success(false))
        }
    }

    private fun toCharacterModel(favoriteCharacterModel: FavoriteCharacterModel): Character{
        val id = favoriteCharacterModel.id
        val name = favoriteCharacterModel.name
        val status = favoriteCharacterModel.status
        val species = favoriteCharacterModel.species
        val type = favoriteCharacterModel.type
        val gender = favoriteCharacterModel.gender
        val origin = Origin(favoriteCharacterModel.origin_name, favoriteCharacterModel.origin_url)
        val location = LocationRM(favoriteCharacterModel.location_rm_name, favoriteCharacterModel.location_rm_url)
        val image = favoriteCharacterModel.image
        val episode = favoriteCharacterModel.episode
        val url = favoriteCharacterModel.url
        val created = favoriteCharacterModel.created
        return Character(id, name, status, species, type, gender, origin, location, image, episode, url, created)
    }

    private fun toFavoriteCharacterModel(character: Character): FavoriteCharacterModel{
        val episodeList = realmListOf<String>()
        character.episode.forEach{
            episodeList.add(it)
        }

        return FavoriteCharacterModel(
            id = character.id,
            name = character.name,
            status = character.status,
            species = character.species,
            type = character.type,
            gender = character.gender,
            origin_name = character.origin.name,
            origin_url = character.origin.url,
            location_rm_name = character.location.name,
            location_rm_url = character.location.url,
            image = character.image,
            episode = episodeList,
            url = character.url,
            created = character.created
        )
    }
}