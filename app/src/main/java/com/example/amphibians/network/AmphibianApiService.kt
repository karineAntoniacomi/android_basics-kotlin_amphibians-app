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
package com.example.amphibians.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// Variável para armazenar o URL base da API
private val BASE_URL = "https://developer.android.com/courses/" +
        "pathways/android-basics-kotlin-unit-4-pathway-2/" +
        "android-basics-kotlin-unit-4-pathway-2/"

// Cria objeto Moshi com a fábrica do adaptador Kotlin
//  que a Retrofit vai usar para analisar o JSON
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Cria instância/objeto da Retrofit usando o conversor da Moshi
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    // Adiciona o URi base do serviço da web
    .baseUrl(BASE_URL)
    // Cria o objeto Retrofit
    .build()

// interface pública AmphibianApiService que expoe a função suspend para cada método
// de API. Neste app, há apenas um método de solicitação GET da lista de anfíbios.
interface AmphibianApiService {
    // Declara função suspensa que obtem uma lista de amphibians.
    // Quando este método é invocado, a Retrofit anexa o endpoint amphibian ao URL base.
    // Anotação @GET indica que o endpoint amphibians será chamado com o método HTTP GET.
    // suspend: Permite chamar o método em corrotinas.
    @GET("amphibians")
    suspend fun getAmphibians(): List<Amphibian>
}

// Cria objeto AmphibianApi para expor um serviço de inicialização
// lenta da Retrofit, que usa a interface AmphibianApiService.
object AmphibianApi {
    // lazy garante que o objeto seja inicializado no primeiro uso do objeto
    val retrofitService : AmphibianApiService by lazy {
        retrofit.create(AmphibianApiService::class.java) }
}
