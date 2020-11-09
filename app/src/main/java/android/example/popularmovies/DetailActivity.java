package android.example.popularmovies;

import android.example.popularmovies.model.MData;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private ImageView moviePoster;
    private TextView userRating;
    private TextView releaseDate;
    private TextView movieSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        moviePoster = findViewById(R.id.movie_thumbnail);
        userRating = findViewById(R.id.user_rating_value);
        releaseDate = findViewById(R.id.release_date_value);
        movieSynopsis = findViewById(R.id.movie_summary);

        Bundle bundle = getIntent().getExtras();
        MData mData = (MData) bundle.getSerializable("mData");
        getData(mData);
        setTitle(mData.getTitle());


    }

    private void getData(MData mData) {
        Picasso.get().load("https://image.tmdb.org/t/p/" + "w185" + mData.getPosterPath())
                .into(moviePoster);

        userRating.setText(String.format("%s/10", mData.getVoteAverage()));
        releaseDate.setText(mData.getReleaseDate());
        movieSynopsis.setText(mData.getOverview());
    }

}
