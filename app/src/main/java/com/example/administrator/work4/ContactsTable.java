package com.example.administrator.work4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.Vector;

/**
 * Created by Administrator on 2015/11/3.
 */
public class ContactsTable {
    private final static String TABLENAME="contactsTable";
    private MyDB db;

    public ContactsTable(Context context) {
        db=new MyDB(context);
        if (!db.isTableExists(TABLENAME)){
            String createTableSql="create table if not exists "+TABLENAME+"(id_DB integer primary key autoincrement,"+User.NAME+" varchar,"+User.MOBILE+" varchar,"+User.DANWEI+" varchar,"+User.QQ+" varchar,"+User.ADDERSS+" varchar)";
            db.createTable(createTableSql);
        }
    }

    public boolean addData(User user){
        ContentValues values=new ContentValues();
        values.put(User.NAME,user.getName());
        values.put(User.MOBILE,user.getMobile());
        values.put(User.DANWEI,user.getDanwei());
        values.put(User.QQ,user.getQq());
        values.put(User.ADDERSS,user.getAddress());
        return db.save(TABLENAME,values);
    }

    public User[] getAllUser(){
        Vector<User> v=new Vector<User>();
        Cursor cursor=null;
        try{
            cursor=db.find("select * from "+TABLENAME,null);
            while (cursor.moveToNext()){
                User temp=new User();
                temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
                temp.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
                temp.setQq(cursor.getString(cursor.getColumnIndex(User.QQ)));
                temp.setDanwei(cursor.getString(cursor.getColumnIndex(User.DANWEI)));
                temp.setAddress(cursor.getString(cursor.getColumnIndex(User.ADDERSS)));
                temp.setMobile(cursor.getString(cursor.getColumnIndex(User.MOBILE)));
                v.add(temp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            db.closeConnection();
        }
        if (v.size()>0){
            return v.toArray(new User[]{});
        }else{
            User[] users=new User[1];
            User user=new User();
            user.setName("无结果");
            users[0]=user;
            return users;
        }
    }

    public User getUserByID(int id){
        Cursor cursor=null;
        try {
            cursor=db.find("select * from "+TABLENAME+" where id_DB=?",new String[]{id+""});
            User temp=new User();
            cursor.moveToNext();
            temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
            temp.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
            temp.setQq(cursor.getString(cursor.getColumnIndex(User.QQ)));
            temp.setDanwei(cursor.getString(cursor.getColumnIndex(User.DANWEI)));
            temp.setAddress(cursor.getString(cursor.getColumnIndex(User.ADDERSS)));
            temp.setMobile(cursor.getString(cursor.getColumnIndex(User.MOBILE)));
            return temp;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor!=null){
                cursor.close();
            }
            db.closeConnection();
        }
        return null;
    }

    public boolean updateUser(User user){
        ContentValues values=new ContentValues();
        values.put(User.NAME,user.getName());
        values.put(User.MOBILE,user.getMobile());
        values.put(User.DANWEI,user.getDanwei());
        values.put(User.QQ,user.getQq());
        values.put(User.ADDERSS,user.getAddress());
        return db.update(TABLENAME,values," id_DB=? ",new String[]{user.getId_DB()+""});
    }

    public User[] findUserByKey(String key){
        Vector<User> v=new Vector<User>();
        Cursor cursor=null;
        try {
            cursor=db.find("select * from "+TABLENAME+" where "+User.NAME+" like '%"+key+"%' "+" or "+User.MOBILE+" like '%"+key+"%' "+" or "+User.QQ+" like '%"+key+"%' ",null);
            while (cursor.moveToNext()) {
                User temp = new User();
                temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
                temp.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
                temp.setQq(cursor.getString(cursor.getColumnIndex(User.QQ)));
                temp.setDanwei(cursor.getString(cursor.getColumnIndex(User.DANWEI)));
                temp.setAddress(cursor.getString(cursor.getColumnIndex(User.ADDERSS)));
                temp.setMobile(cursor.getString(cursor.getColumnIndex(User.MOBILE)));
                v.add(temp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            db.closeConnection();
        }
        if (v.size()>0){
            return v.toArray(new User[] {});
        }else {
            User[] users=new User[1];
            User user=new User();
            user.setName("无结果");
            users[0]=user;
            return users;
        }
    }
    public boolean deleteByUser(User user){
        return db.delete(TABLENAME," id_DB=?",new String[]{user.getId_DB()+""});
    }
}
