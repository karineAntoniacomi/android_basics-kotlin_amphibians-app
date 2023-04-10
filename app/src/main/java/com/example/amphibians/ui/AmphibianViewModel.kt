/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.amphibians.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amphibians.network.Amphibian
import com.example.amphibians.network.AmphibianApi
import kotlinx.coroutines.launch

enum class AmphibianApiStatus {LOADING, ERROR, DONE}

class AmphibianViewModel : ViewModel() {

    // Properties to represent MutableLiveData and LiveData for the API status
    private val _status = MutableLiveData<AmphibianApiStatus>()
    val status: LiveData<AmphibianApiStatus> = _status

    // Properties to represent MutableLiveData and LiveData for a list of amphibian objects
    private val _amphibians = MutableLiveData<List<Amphibian>>()
    val amphibians : LiveData<List<Amphibian>> = _amphibians

    // Properties to represent MutableLiveData and LiveData for a single amphibian object.
    // This will be used to display the details of an amphibian when a list item is clicked
    private val _amphibian = MutableLiveData<Amphibian>()
    val amphibian : LiveData<Amphibian> = _amphibian

    // Function that gets a sst of amphibians from the api service and sets the status via a Coroutine
    fun getAmphibiansList() {
        // status inicial da corrotina
        _status.value = AmphibianApiStatus.LOADING

        // inicia corrotina para executar solicitação GET e fazer download dos dados
        // de anfíbios chamando o método getAmphibians() do serviço da Retrofit.
        viewModelScope.launch {
            // verifica se ocorre excessão ao enviar a solicitação GET
            try {
                _amphibians.value = AmphibianApi.retrofitService.getAmphibians()
                _status.value = AmphibianApiStatus.DONE

            // trata excessão
            } catch (e: Exception) {
                _amphibians.value = emptyList()
                _status.value = AmphibianApiStatus.ERROR
            }
        }
    }

    // Configura a propriedade _amphibian definida para o argumento do anfíbio transmitido à função.
    // Esse método é chamado ao selecionar um anfíbio, para que seja exibido na tela de detalhes.
    fun onAmphibianClicked(amphibian: Amphibian) {
        // Set the amphibian object
        _amphibian.value = amphibian
    }
}
