package com.example.farmersapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlternateListAdapter_Market extends RecyclerView.Adapter<AlternateListAdapter_Market.AlternateListHolder_Market> implements Filterable {

     List<productsListOfMarketFirestore>filterList;
     List<productsListOfMarketFirestore>mainList;
     Context mContext;

    @NonNull
    @Override
    public AlternateListHolder_Market onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;

        layout = LayoutInflater.from(mContext).inflate(R.layout.customlist_item_market, parent, false);
        return new AlternateListHolder_Market(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final AlternateListHolder_Market holder, int position) {

        productsListOfMarketFirestore curretItem = filterList.get(position);
        holder.imageView.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
        holder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));

        holder.textView_productId.setText(curretItem.getProductId());
        holder.textView_price.setText(curretItem.getProductPrice());
        holder.textView_title.setText(curretItem.getProductTitle());
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://farmersapp-31e06.appspot.com/user_image1/").child(curretItem.getProductId() + ".jpg");
        try {
            final File file = File.createTempFile("image", "jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    holder.imageView.setImageBitmap(bitmap);

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    @Override
    public Filter getFilter() {

        return filteredData;
    }
    private   Filter filteredData= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<productsListOfMarketFirestore> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList = mainList;
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (productsListOfMarketFirestore item : mainList) {
                    if (item.getProductTitle().toLowerCase().contains(filterPattern)) {
                        Log.d("checked","productTitle check "+item.getProductTitle());
                        filteredList.add(item);
                    }
                }
                filterList = filteredList;

            }

            FilterResults results = new FilterResults();
            results.values = filterList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            filterList =(List<productsListOfMarketFirestore>)results.values;
            notifyDataSetChanged();
        }
    };
    class AlternateListHolder_Market extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView_title;
        TextView textView_price,textView_productId;
        RelativeLayout container;

        public AlternateListHolder_Market(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView_item_market);
            textView_title = itemView.findViewById(R.id.textView_item_title_market);
            textView_price = itemView.findViewById(R.id.textView_price);
            textView_productId = itemView.findViewById(R.id.textView_productId);
            container = itemView.findViewById(R.id.item_container_market);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Fragment itemFragment = MarketItemDetails.newInstance("", "");
                    if (itemFragment != null) {

                        FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle args = new Bundle();
                        args.putString("productId", textView_productId.getText().toString());
                        itemFragment.setArguments(args);
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                        fragmentTransaction.replace(R.id.container, itemFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    } else {
                        Log.d("error", "null exception");
                    }
                }
            });


        }
    }

    AlternateListAdapter_Market(List<productsListOfMarketFirestore > exampleList,Context Context) {
        this.filterList = exampleList;
        this.mainList = exampleList;
        this.mContext = Context;
    }


public void setFilterList(List<productsListOfMarketFirestore> mList)
{
    this.filterList = mList;
    notifyDataSetChanged();
}


}
