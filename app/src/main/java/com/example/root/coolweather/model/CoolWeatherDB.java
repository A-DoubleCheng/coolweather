package com.example.root.coolweather.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.root.coolweather.db.CoolWeatherOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-2-25.
 */
public class CoolWeatherDB {
    //DBname
    public static final String DB_NAME = "cool_weather";
    //DBversion
    public static final int VERSION = 1;
    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;
    //make method private
    private CoolWeatherDB(Context context){
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }
    //get CoolWeatherDB example
    public synchronized static CoolWeatherDB getInstance(Context context){
        if(coolWeatherDB == null){
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }
    //make Province example store to DB
    public void saveProvince(Province province){
        if(province != null){
            ContentValues values = new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("Province",null,values);
        }
    }
    //read from DB for Province data
    public List<Province> loadProvince(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db
                .query("Province",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while(cursor.moveToNext());
        }
        return list;
    }
    //make City example store to DB
    public void saveCity(City city){
        if(city!=null){
            ContentValues values = new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
            db.insert("City",null,values);
        }
    }
    //read from DB for City data
    public List<City> loadCities(int provinceId){
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City",null,"province_id = ?",
                new String[] {String.valueOf(provinceId)},null,null,null);
        if (cursor.moveToFirst()){
            do{
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor
                .getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor
                .getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);
            }while (cursor.moveToNext());
        }
        return list;
    }
    //make County example store to DB
    public void saveCounty(County county){
        if(county!=null){
            ContentValues values = new ContentValues();
            values.put("county_name",county.getCountyName());
            values.put("county_code",county.getCountyCode());
            values.put("city_id",county.getCityId());
            db.insert("County",null,values);
        }
    }
    //read from DB for County data
    public List<County> loadCounties(int cityId){
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County",null,"city_id = ?",
                new String[] {String.valueOf(cityId)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor
                .getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor
                .getColumnIndex("county_code")));
                county.setCityId(cityId);
                list.add(county);
            }while(cursor.moveToNext());
        }
        return list;
    }
}
