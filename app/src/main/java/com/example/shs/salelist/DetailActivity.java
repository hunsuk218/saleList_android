package com.example.shs.salelist;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by SHS on 2017-07-18.
 */

public class DetailActivity extends AppCompatActivity {

    private EditText editTextComplex;
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
    private SaleItem saleItem;
    private boolean isClicked;

    private Button btnUpdate;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("상세 보기");

        Intent intent = new Intent(this.getIntent());
        saleItem = (SaleItem) intent.getSerializableExtra("saleItem");

        editTextComplex = (EditText) findViewById(R.id.editTextComplex);
        editTextDong = (EditText) findViewById(R.id.editTextDong);
        editTextHo= (EditText) findViewById(R.id.editTextHo);
        editTextPhoneMale= (EditText) findViewById(R.id.editTextPhoneMale);
        editTextPhoneFemale = (EditText) findViewById(R.id.editTextPhoneFemale);
        editTextPhone2Male =(EditText) findViewById(R.id.editTextPhone2Male);
        editTextPhone2Female =(EditText) findViewById(R.id.editTextPhone2Female);
        editTextPrice= (EditText) findViewById(R.id.editTextPrice);
        editTextRmks = (EditText) findViewById(R.id.editTextRmks);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        editTextComplex.setText(saleItem.getComplex());
        editTextDong.setText(saleItem.getDong());
        editTextHo.setText(saleItem.getHo());
        editTextPhoneMale.setText(saleItem.getPhoneMale());
        editTextPhoneFemale.setText(saleItem.getPhoneFemale());
        editTextPhone2Male.setText(saleItem.getPhone2Male());
        editTextPhone2Female.setText(saleItem.getPhone2Female());
        editTextPrice.setText(saleItem.getPrice());
        editTextRmks.setText(saleItem.getRmks());

        isClicked = false;

    }

    public void btnUpdate(View v){

        if(!isClicked){

            editTextComplex.setFocusableInTouchMode(true);
            editTextDong.setFocusableInTouchMode(true);
            editTextHo.setFocusableInTouchMode(true);
            editTextPhoneMale.setFocusableInTouchMode(true);
            editTextPhoneFemale.setFocusableInTouchMode(true);
            editTextPhone2Male.setFocusableInTouchMode(true);
            editTextPhone2Female.setFocusableInTouchMode(true);
            editTextPrice.setFocusableInTouchMode(true);
            editTextRmks.setFocusableInTouchMode(true);

            editTextComplex.setFocusable(true);
            editTextDong.setFocusable(true);
            editTextHo.setFocusable(true);
            editTextPhoneMale.setFocusable(true);
            editTextPhoneFemale.setFocusable(true);
            editTextPhone2Male.setFocusable(true);
            editTextPhone2Female.setFocusable(true);
            editTextPrice.setFocusable(true);
            editTextRmks.setFocusable(true);

            isClicked = !isClicked;

            btnUpdate.setText("완료");}
        else{

            databaseReference = FirebaseDatabase.getInstance().getReference();

            String conplex = editTextComplex.getText().toString();
            String dong = editTextDong.getText().toString();
            String ho = editTextHo.getText().toString();
            String phoneMale = editTextPhoneMale.getText().toString();
            String phoneFemale = editTextPhoneFemale.getText().toString();
            String phone2Male = editTextPhone2Male.getText().toString();
            String phone2Female = editTextPhone2Female.getText().toString();
            String price = editTextPrice.getText().toString();
            String rmks = editTextRmks.getText().toString();

            SaleItem new_saleItem = new SaleItem(conplex, dong, ho, phoneMale,phoneFemale,phone2Male, phone2Female, price, rmks);

            Map<String, Object> saleItemValues = new_saleItem.toMap();
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/sale-list/" + saleItem.getKey(), saleItemValues);

            databaseReference.updateChildren(childUpdates);

            editTextComplex.setFocusable(false);
            editTextDong.setFocusable(false);
            editTextHo.setFocusable(false);
            editTextPhoneMale.setFocusable(false);
            editTextPhoneFemale.setFocusable(false);
            editTextPhone2Male.setFocusable(false);
            editTextPhone2Female.setFocusable(false);
            editTextPrice.setFocusable(false);
            editTextRmks.setFocusable(false);

            isClicked = !isClicked;

            btnUpdate.setText("수정");
        }
    }

    public void phoneMaleClick(View v){
        if(!isClicked) {
            String tel = "tel:" + editTextPhoneMale.getText().toString();
            if(!tel.equals("tel:"))
            {
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
            }
        }
    }

    public void phoneFemaleClick(View v){
        if(!isClicked) {
            String tel = "tel:" + editTextPhoneFemale.getText().toString();
            if(!tel.equals("tel:"))
            {
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
            }
        }
    }

    public void phone2MaleClick(View v){
        if(!isClicked) {
            String tel = "tel:" + editTextPhone2Male.getText().toString();
            if (!tel.equals("tel:")) {
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
            }
        }
    }

    public void phone2FemaleClick(View v){
        if(!isClicked) {
            String tel = "tel:" + editTextPhone2Female.getText().toString();
            if (!tel.equals("tel:")) {
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
            }
        }
    }


    public void btnDel(View v){
        String key = saleItem.getKey();
        databaseReference = FirebaseDatabase.getInstance().getReference("/sale-list/" + key);

        databaseReference.removeValue();
        finish();
    }
}
