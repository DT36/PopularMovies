package android.example.popularmovies.adapter;

import android.example.popularmovies.R;
import android.example.popularmovies.model.MData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MAdapter extends RecyclerView.Adapter<MAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(MData mData);
    }

    List<MData> mData;
    OnItemClickListener listener;

    public MAdapter(List<MData> mData, OnItemClickListener listener) {
        this.mData = mData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.movie_poster, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mData.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.iv_movie_poster);
        }

        public void onBind(final MData mData, final OnItemClickListener listener) {

            Picasso.get().load("https://image.tmdb.org/t/p/" + "w185" + mData.getPosterPath())
                    .resize(540, 800)
                    .into(poster);

            itemView.setOnClickListener(v -> listener.onItemClick(mData));
        }
    }


}

