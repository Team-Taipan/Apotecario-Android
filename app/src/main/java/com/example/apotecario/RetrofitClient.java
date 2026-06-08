package com.example.apotecario;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    
    // Se estiver usando EMULADOR, use: http://10.0.2.2:4000/
    // Se estiver usando CELULAR FÍSICO, use o IP da sua máquina: http://192.168.x.x:4000/
    private static final String BASE_URL = "http://10.0.2.2:4000/";

    public static ApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}