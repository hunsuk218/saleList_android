package com.example.shs.salelist;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by SHS on 2017-07-16.
 */

public class ListViewAdapter extends BaseAdapter implements Filterable{

    private ArrayList<SaleItem> listViewItemList = new ArrayList<SaleItem>();
    private ArrayList<SaleItem> filteredItemList = listViewItemList;
    private boolean checkClick = false; //삭제버튼 Click여부 확인
    Filter listFilter;


    public interface ListBtnClickListener{
        void onListBtnClick(int position);
    }

    public ListViewAdapter(){};

    @Override
    public int getCount() {
        return filteredItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredItemList.get(position);
    }

    public String getTitle(int position){
        SaleItem saleItem = filteredItemList.get(position);
        return saleItem.getComplex();
    }
    public String getKey(int position){
        SaleItem saleItem = filteredItemList.get(position);
        return saleItem.getKey();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item,parent,false);
        }
         CheckBox itemChk = (CheckBox)convertView.findViewById(R.id.itemChk);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView2);

        final SaleItem saleItem = filteredItemList.get(position);

        itemChk.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                saleItem.setSelected(!saleItem.getSelected());
            }
        });

        itemChk.setChecked(((ListView)parent).isItemChecked(position));

        if(checkClick) {
            itemChk.setVisibility(View.VISIBLE);
        }
        else{
            itemChk.setVisibility(View.INVISIBLE);
        }
        titleTextView.setText(saleItem.getComplex() +"단지 "+ saleItem.getDong()+"동 "+ saleItem.getHo()+"호 ");
        descTextView.setText(saleItem.getPhoneMale() + " & " +saleItem.getPhoneFemale() + "\n" +
                saleItem.getPhone2Male() + " & " + saleItem.getPhone2Female());
        return convertView;
    }

    public void clickBtn(){
        checkClick = !checkClick;
    }

    public void delSaleItem(String key){
        synchronized (listViewItemList){
            for(Iterator<SaleItem> it = listViewItemList.iterator(); it.hasNext();)
            {
                String value = it.next().getKey();
                if(value.equals(key))
                {
                    it.remove();
                }
            }}
    }

    @Override
    public Filter getFilter() {
        if(listFilter == null){
            listFilter = new ListFilter();
        }
        return listFilter;
    }

    public void addItem(SaleItem saleItem){
        listViewItemList.add(saleItem);
    }

    private class ListFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constranit) {
            FilterResults results = new FilterResults();

            if(constranit == null || constranit.length() == 0)
            {
                results.values=listViewItemList;
                results.count=listViewItemList.size();
            }else{
                ArrayList<SaleItem> itemList = new ArrayList<SaleItem>();

                for(SaleItem item : listViewItemList){
                    String title = item.getComplex() +"단지 "+ item.getDong()+"동 "+ item.getHo()+"호";
                    String desc = item.getPhoneMale() + " & " +item.getPhoneFemale() + "\n" +
                            item.getPhone2Male() + " & " + item.getPhone2Female();
                    if(title.toUpperCase().contains(constranit.toString().toUpperCase()) || desc.toUpperCase().contains(constranit.toString().toUpperCase()))
                    {
                        itemList.add(item);
                    }
                }
                results.values = itemList;
                results.count = itemList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            filteredItemList = (ArrayList<SaleItem>) results.values;

            if(results.count> 0){
                notifyDataSetChanged();
            }else{
                notifyDataSetInvalidated();
            }

        }
    }

    public ArrayList<SaleItem> getSaleList(){
        return filteredItemList;
    }
}
