package com.example.apotecario;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    // Rota para buscar os perfis
    @GET("perfis")
    Call<List<Perfil>> getPerfis();

    // Rota para buscar os medicamentos ativos de um perfil
    @GET("medicamentos/ativos")
    Call<List<MedicamentoAtivo>> getMedicamentosAtivos();

    // Rota para cadastrar um novo perfil
    @POST("perfis")
    Call<Perfil> cadastrarPerfil(@Body Perfil perfil);
}