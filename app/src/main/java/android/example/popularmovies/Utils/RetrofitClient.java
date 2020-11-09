package android.example.popularmovies.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

private final static String URL = "https://api.themoviedb.org/3/";

    public static MService getRetrofitClient(){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit =new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        return retrofit.create(MService.class);
    }
}
