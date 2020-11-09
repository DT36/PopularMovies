package android.example.popularmovies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.example.popularmovies.Utils.RetrofitClient;
import android.example.popularmovies.adapter.MAdapter;
import android.example.popularmovies.model.MData;
import android.example.popularmovies.model.MResult;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private TextView noConnectionTv;
    private Call<MResult> mResultCall;
    private int Sort = 1;
    //TODO add your apiKey
    private final String apiKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noConnectionTv = findViewById(R.id.no_connectionTv);
        recyclerView = findViewById(R.id.movieRv);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        if (checkNetwork()){
            showRecyclerView();
        }else {
            showNoConnection();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                Sort = 1;
                setTitle(this.getString(R.string.app_name));
                break;
            case R.id.most_rated:
                Sort = 2;
                setTitle(this.getString(R.string.top_rated));
                break;
        }
        loadPage();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void showNoConnection(){
        noConnectionTv.setVisibility(View.VISIBLE);
    }

    public void showRecyclerView(){
        noConnectionTv.setVisibility(View.INVISIBLE);
        loadPage();
    }

    private void loadPage() {
        switch (Sort) {
            case 1:
                mResultCall = RetrofitClient.getRetrofitClient().getPopularMovies(apiKey);
                break;

            case 2:
                mResultCall = RetrofitClient.getRetrofitClient().getTopRatedMovies(apiKey);
                break;
        }

        mResultCall.enqueue(new Callback<MResult>() {
            @Override
            public void onResponse(Call<MResult> call, Response<MResult> response) {
                if (response.isSuccessful()){
                        if (response.body() != null) {
                            generateMovieData(response.body().getmData());
                        }
                    }
            }

            @Override
            public void onFailure(Call<MResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public boolean checkNetwork(){
        ConnectivityManager cManager =(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = null;

        if (cManager != null) {
            networkInfo = cManager.getActiveNetworkInfo();
        }

        return networkInfo != null && networkInfo.isConnected();
    }

    private void generateMovieData(List<MData> mData){
        MAdapter mAdapter = new MAdapter(mData, mData1 -> {

            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("mData", mData1);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        recyclerView.setAdapter(mAdapter);
    }
}
