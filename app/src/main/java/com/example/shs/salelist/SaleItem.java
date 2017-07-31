package com.example.shs.salelist;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SHS on 2017-07-17.
 */

public class SaleItem implements Serializable{
    private String complex;
    private String dong;
    private String ho;
    private String phoneMale;
    private String phoneFemale;
    private String phone2Male;
    private String phone2Female;
    private String price;
    private String rmks;
    private String key;
    private boolean selected;

    public void setComplex(String complex){this.complex = complex;}
    public void setDong(String dong){this.dong = dong;}
    public void setHo(String ho){this.ho= ho;}
    public void setPhoneMale(String phoneMale){this.phoneMale= phoneMale;}
    public void setPhoneFemale(String phoneFemale){this.phoneFemale = phoneFemale;}
    public void setPhone2Male(String phone2Male){this.phone2Male = phone2Male;}
    public void setPhone2Female(String phone2Female){this.phone2Female = phone2Female;}
    public void setPrice(String price){this.price = price;}
    public void setRmks(String rmks){this.rmks = rmks;}
    public String getComplex(){return complex;}
    public String getDong(){return  dong;}
    public String getHo(){return ho;}
    public String getPhoneMale(){return phoneMale;}
    public String getPhoneFemale(){return phoneFemale;}
    public String getPhone2Male(){return phone2Male;}
    public String getPhone2Female(){return phone2Female;}
    public String getPrice(){return price;}
    public String getRmks(){return rmks;}
    public String getKey(){return key;}
    public void setKey(String key){this.key=key;};

    public SaleItem(){};

    public SaleItem(String conplex, String dong, String ho, String phoneMale,String phoneFemale,String phone2Male, String phone2Female, String price, String rmks, String key){
        this.complex=conplex;
        this.dong=dong;
        this.ho=ho;
        this.phoneMale=phoneMale;
        this.phoneFemale = phoneFemale;
        this.phone2Male=phone2Male;
        this.phone2Female = phone2Female;
        this.price=price;
        this.rmks=rmks;
        this.key = key;
        this.selected = false;
    }

    public SaleItem(String conplex, String dong, String ho, String phoneMale,String phoneFemale,String phone2Male, String phone2Female, String price, String rmks){
        this.complex=conplex;
        this.dong=dong;
        this.ho=ho;
        this.phoneMale=phoneMale;
        this.phoneFemale = phoneFemale;
        this.phone2Male=phone2Male;
        this.phone2Female = phone2Female;
        this.price=price;
        this.rmks=rmks;
        this.selected = false;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("complex",complex);
        result.put("dong",dong);
        result.put("ho",ho);
        result.put("phoneMale",phoneMale);
        result.put("phoneFemale",phoneFemale);
        result.put("phone2Male",phone2Male);
        result.put("phone2Female",phone2Female);
        result.put("price",price);
        result.put("rmks",rmks);
        return result;
    }

    @Override
    public boolean equals(Object object) {
        boolean sameSame = false;
        if (object != null && object instanceof SaleItem){
            sameSame = this.key == ((SaleItem) object).getKey();
        }
        return sameSame;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public boolean getSelected(){
        return  selected;
    }
}
