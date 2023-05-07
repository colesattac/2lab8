package kz.talipovsn.retrofit_demo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static kz.talipovsn.retrofit_demo.UnsafeOkHttpClient.getUnsafeOkHttpClient;

// АДАПТЕР ЧТЕНИЯ С САЙТА ЧЕРЕЗ ТЕХНОЛОГИЮ RETROFIT 2
class RestAdapter {
    private static final String URL = "https://data.egov.kz";
    private static final String API = "/api/v4/valutalar_bagamdary4/v708?apiKey=93b06eeb104541";

    private static Retrofit retrofit = null;
    private static ApiInterface apiInterface;

    // Наш интерфес передачи параметров сайту
    interface ApiInterface {
        @GET(API)
        Call<List<DataModel>> getData(@Query("pretty") String param);
    }

    static ApiInterface getApiClient() {
        if (apiInterface == null) {
            try {
                retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .client(getUnsafeOkHttpClient().build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

            } catch (Exception ignore) {
            }
            apiInterface = retrofit.create(ApiInterface.class);
        }
        return apiInterface;
    }

}
