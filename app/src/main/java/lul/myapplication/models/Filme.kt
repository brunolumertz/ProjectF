package lul.myapplication.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "Filme")
data class Filme (
    @PrimaryKey(autoGenerate = false)

    @SerializedName("id")
    val id : String,

    @SerializedName("title")
    val tittle : String ?,

    @SerializedName("poster_path")
    val poster : String ?,

    @SerializedName("release_date")
    val release : String ?


) : Parcelable{
    constructor() : this("","","","")
}