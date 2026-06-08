package com.example.apotecario;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // --- Medicamentos ---
    
    // Busca medicamentos na base da ANVISA
    @GET("medicamento/anvisa")
    Call<List<Medicamento>> getMedicamentosAnvisa(
            @Query("nome") String nome,
            @Query("pagina") Integer pagina
    );

    // Cria um medicamento personalizado
    @POST("medicamento")
    Call<Medicamento> criarMedicamento(@Body Medicamento medicamento);

    // Edita um medicamento personalizado
    @PATCH("medicamento/{id}")
    Call<Medicamento> atualizarMedicamento(@Path("id") String id, @Body Map<String, Object> updates);

    // Remove um medicamento personalizado
    @DELETE("medicamento/{id}")
    Call<Void> deletarMedicamento(@Path("id") String id);


    // --- Contas e Usuários ---

    // Cadastro de nova conta
    @POST("usuario")
    Call<Void> cadastrarUsuario(@Body Map<String, String> dados);

    // Login e retorno de Token JWT
    @POST("usuario/login")
    Call<Map<String, String>> login(@Body Map<String, String> credenciais);

    // Criação do perfil inicial vinculado à conta
    @POST("usuario/perfil")
    Call<Perfil> criarPerfilInicial(@Body Perfil perfil);

    // Listagem de contas (Admin)
    @GET("usuario/all")
    Call<List<Object>> getTodosUsuarios();

    // Detalhes de uma conta específica
    @GET("usuario/{id}")
    Call<Object> getUsuarioPorId(@Path("id") String id);

    // Atualização de conta
    @PATCH("usuario/{id}")
    Call<Object> atualizarUsuario(@Path("id") String id, @Body Map<String, Object> updates);

    // Remoção de conta
    @DELETE("usuario/{id}")
    Call<Void> deletarUsuario(@Path("id") String id);


    // --- Gerenciamento de Perfis ---

    // Criação de perfil autenticada
    @POST("perfil")
    Call<Perfil> cadastrarPerfil(@Body Perfil perfil);

    // Listagem de perfis vinculados ao usuário logado
    @GET("perfil/me")
    Call<List<Perfil>> getMeusPerfis();

    // Detalhes de um perfil específico
    @GET("perfil/{id}")
    Call<Perfil> getPerfilPorId(@Path("id") String id);

    // Edição de informações de um perfil
    @PATCH("perfil/{id}")
    Call<Perfil> atualizarPerfil(@Path("id") String id, @Body Perfil perfil);

    // Remoção de um perfil
    @DELETE("perfil/{id}")
    Call<Void> deletarPerfil(@Path("id") String id);
}