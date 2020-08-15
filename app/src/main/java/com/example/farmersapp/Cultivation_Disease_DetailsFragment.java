package com.example.farmersapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farmersapp.model.CustomListItem_Diseases;

public class Cultivation_Disease_DetailsFragment extends Fragment {

    //Widgets
    private TextView diseaseTitle_details, diseaseArticle_details;
    private ImageView diseaseImage_details;
    CustomListItem_Diseases mData;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public Cultivation_Disease_DetailsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Cultivation_Disease_DetailsFragment newInstance(String param1, String param2) {
        Cultivation_Disease_DetailsFragment fragment = new Cultivation_Disease_DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

         mData =    getArguments().getParcelable(DataLoadActivity.DATA_DISEASE);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_cultivation_disease_details, container, false);

        //Invoking widgets
        diseaseTitle_details = convertView.findViewById(R.id.diseaseTitle_details);
        diseaseArticle_details = convertView.findViewById(R.id.diseaseArticle_details);
        diseaseImage_details = convertView.findViewById(R.id.diseaseImage_details);

        byte[] decodedByte = Base64.decode(mData.getDiseasePhoto(), 0);
        Bitmap bmp = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
      diseaseImage_details.setImageBitmap(bmp);

        return convertView;
    }
}