package br.com.fiap.notepad.view.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.fiap.notepad.model.Nota
import br.com.fiap.notepad.repository.NotaRepository

class MainViewModel: ViewModel() {

    val notas : MutableLiveData<List<Nota>> = MutableLiveData()
    val menmsagemErro : MutableLiveData<String> = MutableLiveData()
    val isLoading : MutableLiveData<Boolean> = MutableLiveData()

    fun buscarNotas(){
        isLoading.value = true
        val notaRepository  = NotaRepository()
        notaRepository.getNotas({
            notas.value = it
            isLoading.value = false
        }, {
            menmsagemErro.value = it?.message
            isLoading.value = false
        })
    }

}
