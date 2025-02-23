package sw.swayni.rickandmorty.data.realm_model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class FavoriteCharacterModel() : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var status: String = ""
    var species: String = ""
    var type: String = ""
    var gender: String = ""
    var origin_name : String = ""
    var origin_url : String = ""
    var location_rm_name : String = ""
    var location_rm_url : String = ""
    var image: String = ""
    var episode : RealmList<String> = realmListOf()
    var url:String = ""
    var created:String = ""

    constructor(id: Int, name: String, status: String, species: String, type: String, gender: String, origin_name: String, origin_url: String, location_rm_name: String, location_rm_url: String, image: String, episode: RealmList<String>, url: String, created: String) : this() {
        this.id = id
        this.name = name
        this.status = status
        this.species = species
        this.type = type
        this.gender = gender
        this.origin_name = origin_name
        this.origin_url = origin_url
        this.location_rm_name = location_rm_name
        this.location_rm_url = location_rm_url
        this.image = image
        this.episode = episode
        this.url = url
        this.created = created
    }
}