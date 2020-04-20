package br.com.fiap.notepad.api

import br.com.fiap.notepad.model.Nota
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NotaApi {

    @GET(value = "/nota")
    fun getNotas(): Call <List<Nota>>

    @POST(value = "/nota")
    fun salvar(@Body nota: Nota): Call<Nota>

}