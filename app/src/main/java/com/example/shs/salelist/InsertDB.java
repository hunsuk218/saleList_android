package com.example.shs.salelist;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by SHS on 2017-07-18.
 */

public class InsertDB extends AppCompatActivity {

    private EditText editTextConplex;
    private EditText editTextDong;
    private EditText editTextHo;
    private EditText editTextPhoneMale;
    private EditText editTextPhoneFemale;
    private EditText editTextPhone2Male;
    private EditText editTextPhone2Female;
    private EditText editTextPrice;
    private EditText editTextRmks;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        setTitle("삽입");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        editTextConplex = (EditText) findViewById(R.id.editTextConplex);
        editTextDong = (EditText) findViewById(R.id.editTextDong);
        editTextHo= (EditText) findViewById(R.id.editTextHo);
        editTextPhoneMale= (EditText) findViewById(R.id.editTextPhoneMale);
        editTextPhoneFemale = (EditText) findViewById(R.id.editTextPhoneFemale);
        editTextPhone2Male =(EditText) findViewById(R.id.editTextPhone2Male);
        editTextPhone2Female =(EditText) findViewById(R.id.editTextPhone2Female);
        editTextPrice= (EditText) findViewById(R.id.editTextPrice);
        editTextRmks = (EditText) findViewById(R.id.editTextRmks);

    }
    public void btnInsert(View v){

        String conplex = editTextConplex.getText().toString();
        String dong = editTextDong.getText().toString();
        String ho = editTextHo.getText().toString();
        String phoneMale = editTextPhoneMale.getText().toString();
        String phoneFemale = editTextPhoneFemale.getText().toString();
        String phone2Male = editTextPhone2Male.getText().toString();
        String phone2Female = editTextPhone2Female.getText().toString();
        String price = editTextPrice.getText().toString();
        String rmks = editTextRmks.getText().toString();

        SaleItem saleItem = new SaleItem(conplex, dong, ho, phoneMale,phoneFemale,phone2Male, phone2Female, price, rmks);

        Map<String, Object> saleItemValues = saleItem.toMap();

        databaseReference.child("sale-list").push().setValue(saleItemValues);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();     //닫기
                finish();
            }
        });
        alert.setMessage("DB저장 완료");
        alert.show();

    }
}

