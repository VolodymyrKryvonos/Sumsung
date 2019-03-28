package com.example.vova.matesha;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AlgebraChapter extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.algebra_chapters_fragment,container,false);

        recyclerView = view.findViewById(R.id.algebra_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        return view;
    }
}
