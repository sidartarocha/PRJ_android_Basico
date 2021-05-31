package com.example.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fruta(val nome: String, var descricao: String, var imagemFruta: String, var ImagDraw: Int):Parcelable
