package com.example.isyandra.parkit.utils;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by akbar on 11/05/17.
 */

public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved = null;

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    public ArrayList<String> retrieve(){

        final ArrayList<String> arrayList = new ArrayList<>();
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fecthData(dataSnapshot, arrayList);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fecthData(dataSnapshot, arrayList);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return arrayList;
    }

    private void fecthData(DataSnapshot snapshot, ArrayList<String> arrayList){
        arrayList.clear();
        for (DataSnapshot ds:snapshot.getChildren())
        {
        }
    }

}
