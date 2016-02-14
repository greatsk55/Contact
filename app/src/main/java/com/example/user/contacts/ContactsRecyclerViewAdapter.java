package com.example.user.contacts;


import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 15. 7. 12.
 */
public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    public ArrayList<ContactsList.Member> list;
    private LinearLayoutManager linearLayoutManager;

    public ArrayList<ContactsList.Member> getContactsList(){
        return list;
    }

    public ContactsRecyclerViewAdapter(Context context, ArrayList<ContactsList.Member> _dataSet, LinearLayoutManager linearLayoutManager) {
        mContext = context;
        list = _dataSet;
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.contact_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
        //Uri sAlbumArtUri = ContentUris.withAppendedId(sArtworkUri, list.get(position).albumId);

        //Picasso.with(mContext).load(sAlbumArtUri).placeholder(R.drawable.ic_no_album_sm).error(R.drawable.ic_no_album_sm).into(holder.albumArt);

        holder.tvName.setText(list.get(position).name);
        holder.tvNumber.setText(list.get(position).mobilePhone);
    }

    @Override
    public int getItemCount() {
        if( list == null ) return 0;
        return list.size();
    }

    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void add(ContactsList.Member song, int position) {
        list.add(position, song);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //public ImageView albumArt;
        public TextView tvName;
        public TextView tvNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            //albumArt = (ImageView) itemView.findViewById(R.id.album_art1);
            tvName = (TextView) itemView.findViewById(R.id.name);
            tvNumber = (TextView) itemView.findViewById(R.id.number);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // song is selected
                    return true;
                }
            });
        }


        @Override
        public void onClick(View v) {
            //TODO 전화걸기
        }

    }

}


