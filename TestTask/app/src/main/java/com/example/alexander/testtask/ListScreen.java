package com.example.alexander.testtask;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListScreen extends Fragment {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_screen, container,
                false);

        SQLiteDatabase sqLiteDatabase = MainActivity.dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_NUMBERS, null, null, null, null, null, null);

        listView = (ListView)rootView.findViewById(R.id.listView);

        String[] numbers = null;
        if (cursor.moveToFirst()) {
            numbers = new String[cursor.getCount()];
            int numberIndex = cursor.getColumnIndex(DBHelper.KEY_NUMBER);
            int index = 0;
            do {
                numbers[index++] = String.valueOf(cursor.getInt(numberIndex));
            } while (cursor.moveToNext());
        } else {
            numbers = new String[]{"Empty database"};
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(listView.getContext() ,R.layout.custom_list_item, numbers);

        listView.setAdapter(adapter);

        return rootView;
    }
}
