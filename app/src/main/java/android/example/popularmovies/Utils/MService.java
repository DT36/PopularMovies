package android.example.popularmovies.Utils;

import android.example.popularmovies.model.MResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MService {

    //Top Rated MData
    @GET("movie/top_rated")
    Call<MResult> getTopRatedMovies(@Query("api_key") String apiKey);


    //Most popular movies
    @GET("movie/popular")
    Call<MResult> getPopularMovies(@Query("api_key") String apiKey);

}

