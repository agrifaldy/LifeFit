package com.example.lifefit.TekananDarah;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifefit.TekananDarah.TekananDarah;
import com.example.lifefit.TekananDarah.TekananDarahAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.AccessControlContext;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private TekananDarahAdapter adapter; // this will be your recycler adapter

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();

    public SwipeToDeleteCallback(int dragDirs, int swipeDirs, TekananDarahAdapter adapter) {
        super(dragDirs, swipeDirs);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition(); // this is how you can get the position
        TekananDarah object = adapter.getItemCount(position); // You will have your own class ofcourse.

        // then you can delete the object
        root.child("TekananDarah").child(object.getId()).setValue(null);// setting the value to null will just delete it from the database.
    }
}
