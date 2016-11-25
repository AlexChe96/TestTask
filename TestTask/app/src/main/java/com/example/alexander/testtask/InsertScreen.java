package com.example.alexander.testtask;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class InsertScreen extends Fragment implements View.OnClickListener {

    Button insertButton;
    EditText numberEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.insert_screen, container,
                false);

        insertButton = (Button) rootView.findViewById(R.id.insertButton);
        insertButton.setOnClickListener(this);

        numberEditText = (EditText) rootView.findViewById(R.id.numberEditText);

        return rootView;
    }


    @Override
    public void onClick(View view) {
        Context context = getActivity();
        CharSequence toastText;
        if(numberEditText.getText().toString().isEmpty()){
            toastText = "Error!\nEmpty number";
        } else if (insertNumber()) {
            toastText = "Successfully inserted";
        } else {
            toastText = "Error!\nToo big number";
        }
        Toast toast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT);
        toast.show();
        numberEditText.setText("");

        View focusedView = this.getActivity().getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private boolean insertNumber () {

        int enteredNumber = 0;
        try {
            enteredNumber = Integer.parseInt(numberEditText.getText().toString());
        } catch (NumberFormatException e) {
            return false;
        }

        SQLiteDatabase sqLiteDatabase = MainActivity.dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_NUMBER, enteredNumber);

        sqLiteDatabase.insert(DBHelper.TABLE_NUMBERS, null, contentValues);
        return true;

    }
}
