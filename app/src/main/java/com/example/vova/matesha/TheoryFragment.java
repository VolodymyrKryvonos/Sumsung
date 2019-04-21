package com.example.vova.matesha;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import io.github.kexanie.library.MathView;


public class TheoryFragment extends Fragment {

    MathView textView;
    ImageView imageView;
    ImageButton button;
    boolean visibl = false;
    private RequestOptions glideRequestOptions;

    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.theory_fragment, container, false);
        textView = v.findViewById(R.id.theory_there);
        imageView = v.findViewById(R.id.img);
        button = v.findViewById(R.id.hide_img);
        Intent intent = getActivity().getIntent();
        DBHelper helper = new DBHelper(getContext());
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT theory, IMG_URL FROM chapters WHERE _id=?", new String[]{intent.getExtras().getInt("ID") + ""});

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visibl) {

                    imageView.setVisibility(View.GONE);
                    button.setImageResource(R.drawable.arrow_down_float);
                    visibl = !visibl;
                } else {
                    imageView.setVisibility(View.VISIBLE);
                    button.setImageResource(R.drawable.arrow_up_float);
                    visibl = !visibl;
                }
            }
        });
        if (cursor.moveToFirst() && (cursor.getString(0) != null)) {
            textView.setText("<font size=4>" + cursor.getString(0) + "</font>" + "<br>");


        }
        if (cursor.getString(1) != null) {
            visibl = true;
            int resourceId =getResources().getIdentifier(cursor.getString(1) , "drawable", getContext().getPackageName());

            Log.e("LOOOOOOOOOOG",resourceId+"");
            Log.e("CORECT",R.drawable.square1+"");
            Uri uri = Uri.parse("android.resource://"  + getContext().getPackageName() + "/" + resourceId);
            Glide.with(imageView.getContext())
                    .load(uri)
                    .into(imageView);
            imageView.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
        return v;
    }
}
