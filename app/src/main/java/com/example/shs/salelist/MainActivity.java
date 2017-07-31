package com.example.shs.salelist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{
    private ListView listView = null ;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ListViewAdapter adapter;
    private EditText editTextFilter;
    private ChildEventListener mChildEventListener;
    private Button delBtn;
    private boolean delBtnClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("sale-list");

        setTitle("Sale List");

        // Adapter 생성
        adapter = new ListViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listView = (ListView) findViewById(R.id.listview1);
        editTextFilter = (EditText) findViewById(R.id.editTextFilter);
        delBtn = (Button)findViewById(R.id.delBtn);

        delBtnClicked = false;

        mChildEventListener = new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SaleItem saleItemData = dataSnapshot.getValue(SaleItem.class);  // chatData를 가져오고
                saleItemData.setKey(dataSnapshot.getKey());
                adapter.addItem(saleItemData);  // adapter에 추가합니다.
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                SaleItem saleItemData = dataSnapshot.getValue(SaleItem.class);  // chatData를 가져오고
                saleItemData.setKey(dataSnapshot.getKey());
                adapter.delSaleItem(dataSnapshot.getKey());
                adapter.addItem(saleItemData);  // adapter에 추가합니다.
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                adapter.delSaleItem(key);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }

        };
        databaseReference.addChildEventListener(mChildEventListener);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView parent,View v,int position, long id){
            }
        });
    }

    public void btnInsert(View v){
        Intent intent=new Intent(MainActivity.this,InsertDB.class);
        startActivity(intent);
    }

    public void onStart(){
        super.onStart();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(MainActivity.this,DetailActivity.class);

                SaleItem selectedItem = (SaleItem) adapter.getItem(position);

                intent.putExtra("saleItem", selectedItem);
                intent.putExtra("conplex",selectedItem.getComplex());
                intent.putExtra("dong",selectedItem.getDong());
                intent.putExtra("ho",selectedItem.getHo());
                intent.putExtra("phoneMale",selectedItem.getPhoneMale());
                intent.putExtra("phoneFemale",selectedItem.getPhoneFemale());
                intent.putExtra("phone2Male",selectedItem.getPhone2Male());
                intent.putExtra("phone2Female",selectedItem.getPhone2Female());
                intent.putExtra("price",selectedItem.getPrice());
                intent.putExtra("rmks",selectedItem.getRmks());

                startActivity(intent);
            }
        });

        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString() ;
                ((ListViewAdapter)listView.getAdapter()).getFilter().filter(filterText) ;
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        }) ;

        listView.setAdapter(adapter);
    }

    protected void onDestroy(){
        super.onDestroy();
        databaseReference.removeEventListener(mChildEventListener);
    }

    public void btnDel(View v) {
        adapter.clickBtn();
        if (!delBtnClicked) {
            delBtn.setText("삭제 완료");
            delBtnClicked = !delBtnClicked;
        } else{
            ArrayList<SaleItem> saleItems = adapter.getSaleList();

            for(int i =0;i<saleItems.size();i++){
                SaleItem saleItem = saleItems.get(i);
                if(saleItem.getSelected()){
                    String key = saleItem.getKey();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/sale-list/" + key);

                    ref.removeValue();
                    saleItem.setSelected(false);
                }
            }

            delBtn.setText("삭제");
            delBtnClicked = !delBtnClicked;
        }
        adapter.notifyDataSetChanged();
    };

}
