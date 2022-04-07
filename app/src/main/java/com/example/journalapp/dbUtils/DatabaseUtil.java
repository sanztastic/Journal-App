package com.example.journalapp.dbUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.journalapp.model.Journal;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil extends SQLiteOpenHelper {

    private static final String DB_NAME = "journal.db";
    private SQLiteDatabase database;

    public DatabaseUtil(Context context) {
        super(context, DB_NAME, null, 1);
        database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table user (fullName TEXT, username TEXT primary key, address TEXT, password TEXT)");
        sqLiteDatabase.execSQL("create table journal (id INTEGER primary key, title TEXT, description TEXT, image blob, user_name TEXT, CONSTRAINT fk_user" +
                " foreign key (user_name)" +
                " REFERENCES user(username))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists user");
        sqLiteDatabase.execSQL("drop Table if exists journal");
    }

    public boolean insertRegisterData(String fullName, String username, String address, String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullName", fullName);
        contentValues.put("username", username);
        contentValues.put("address", address);
        contentValues.put("password", password);
        long insert = database.insert("user", null, contentValues);
        return insert!=1;
    }

    public Boolean checkUsernameExist(String username){
        Cursor cursor = database.rawQuery("select * from user where username = ?", new String[] {username});
        return cursor.getCount()>0;
    }

    public boolean loginUser(String userName, String password){
        Cursor cursor = database.rawQuery("select * from user where username=? and password=?", new String[]{userName, password});
        return cursor.getCount()>0;
    }

    public boolean insertJournalData(String title, String description, byte[] image, String user_name){
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("image", image);
        contentValues.put("user_name", user_name);
        long insert = database.insert("journal", null, contentValues);
        return insert!=-1;
    }

    public boolean updateJournalData(String title, String description, byte[] image, String user_name, int id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("image", image);
        contentValues.put("user_name", user_name);
        long insert = database.update("journal",contentValues,"id=?",new String[]{String.valueOf(id)});
        return insert!=-1;
    }

    public List<Journal> getJournalFromUser(String user_name){
        List<Journal> journalList = new ArrayList<Journal>();
        Cursor cursor = database.rawQuery("select * from journal where user_name=?", new String[]{user_name});
        while(cursor.moveToNext()){
            Journal journal = new Journal();

            journal.setId(cursor.getInt(0));

            journal.setTitle(cursor.getString(1));

            journal.setDescription(cursor.getString(2));

            journal.setImage(cursor.getBlob(3));

            journal.setUser_name(cursor.getString(4));
            journalList.add(journal);
        }
        return journalList;
    }

    public Journal getJournalFromId(int id){
        Journal journal = new Journal();
        Cursor cursor = database.rawQuery("select * from journal where id=?",new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        journal.setId(cursor.getInt(0));
        journal.setTitle(cursor.getString(1));
        journal.setDescription(cursor.getString(2));
        journal.setImage(cursor.getBlob(3));
        journal.setUser_name(cursor.getString(4));
        return journal;
    }

    public boolean deleteJournalById(int id){
        int result = database.delete("journal","id=?",new String[]{String.valueOf(id)});
        return result>0;
    }

    public void close(){
        database.close();
    }
}
