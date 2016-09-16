package com.example.yangchenghuan.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.yangchenghuan.entity.Attend;
import com.example.yangchenghuan.entity.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杨城欢 on 2016/9/11.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

   private static final String DATABASE_NAMES="Test";
    private static final String TABLE_NAME_STUDENT="student";
    private static final String TABLE_NAME_ATTEND="attend";
    private static final int VERSION=1;
    private static final String KEY_ID="id";
    private static final String KEY_NAME="name";
    private static final String FACE_ID= "faceid";
    private static final String KEY_GRADE="grade";
    private static final String CREATE_DATE="createdate";

    //建表语句
    private static final String CREATE_TABLE_STUDENT="create table "+TABLE_NAME_STUDENT+(
     KEY_ID+ " integer primary key autoincrement,"+
     KEY_NAME+ " text not null,"+
     FACE_ID+" text not null,"    +
     KEY_GRADE+ " text not null," +
     CREATE_DATE  +" TIMESTAMP NOT NULL DEFAULT current_timestamp  "

    );

    private static final String CREATE_TABLE_ATTEND="create table "+TABLE_NAME_ATTEND+(
            KEY_ID+ " integer primary key autoincrement,"+
                    KEY_NAME+ " text not null,"+
                    FACE_ID+" text not null,"    +
                    KEY_GRADE+ " text not null"+
                    CREATE_DATE  +" TIMESTAMP NOT NULL DEFAULT current_timestamp "
    );

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, VERSION);
    }
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAMES, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_STUDENT);
        sqLiteDatabase.execSQL(CREATE_TABLE_ATTEND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_STUDENT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ATTEND);
        onCreate(sqLiteDatabase);
    }
////////////////////////////////////////////////

    /**
     *
     * 下面操作Attend
     */
    public void addAttend(Attend attend){
        SQLiteDatabase db=this.getWritableDatabase();

        //使用ContentValues添加数据
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,attend.getName());
        values.put(KEY_GRADE,attend.getGrade());
        db.insert(TABLE_NAME_ATTEND, null, values);
        db.close();
    }
    public Attend getAttend(String name){
        SQLiteDatabase db=this.getWritableDatabase();

        //Cursor对象返回查询结果
        Cursor cursor=db.query(TABLE_NAME_ATTEND,new String[]{KEY_ID,KEY_NAME,KEY_GRADE},
                KEY_NAME+"=?",new String[]{name},null,null,null,null);


        Attend attend=null;
        //注意返回结果有可能为空
        if(cursor.moveToFirst()){
            attend=new Attend(cursor.getInt(0),cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4));
        }
        return attend;
    }
    public int getAttendCounts(){
        String selectQuery="SELECT * FROM "+TABLE_NAME_ATTEND;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        cursor.close();

        return cursor.getCount();
    }

    //查找所有Attend
    public List<Attend> getALllAttend(){
        List<Attend> attendList=new ArrayList<Attend>();

        String selectQuery="SELECT * FROM "+TABLE_NAME_ATTEND;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Attend attend=new Attend();
                attend.setId(Integer.parseInt(cursor.getString(0)));
                attend.setName(cursor.getString(1));
                attend.setGrade(cursor.getString(2));
                attendList.add(attend);
            }while(cursor.moveToNext());
        }
        return attendList;
    }

    //更新Attend
    public int updateAttend(Attend attend){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,attend.getName());
        values.put(KEY_GRADE,attend.getGrade());

        return db.update(TABLE_NAME_ATTEND,values,KEY_ID+"=?",new String[]{String.valueOf(attend.getId())});
    }
    public void deleteAttend(Attend attend){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME_ATTEND,KEY_ID+"=?",new String[]{String.valueOf(attend.getId())});
        db.close();
    }

    /**
     * 下面操作Student
     */

    public void addStudent(Student student){
        SQLiteDatabase db=this.getWritableDatabase();

        //使用ContentValues添加数据
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,student.getName());
        values.put(KEY_GRADE,student.getGrade());
        db.insert(TABLE_NAME_STUDENT, null, values);
        db.close();
    }
    public Student getStudent(String name){
        SQLiteDatabase db=this.getWritableDatabase();

        //Cursor对象返回查询结果
        Cursor cursor=db.query(TABLE_NAME_STUDENT,new String[]{KEY_ID,KEY_NAME,KEY_GRADE},
                KEY_NAME+"=?",new String[]{name},null,null,null,null);


        Student student=null;
        //注意返回结果有可能为空
        if(cursor.moveToFirst()){
            student=new Student(cursor.getInt(0),cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4));
        }
        return student;
    }
    public int getStudentCounts(){
        String selectQuery="SELECT * FROM "+TABLE_NAME_STUDENT;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        cursor.close();

        return cursor.getCount();
    }

    //查找所有student
    public List<Student> getALllStudent(){
        List<Student> studentList=new ArrayList<Student>();

        String selectQuery="SELECT * FROM "+TABLE_NAME_STUDENT;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Student student=new Student();
                student.setId(Integer.parseInt(cursor.getString(0)));
                student.setName(cursor.getString(1));
                student.setGrade(cursor.getString(2));
                studentList.add(student);
            }while(cursor.moveToNext());
        }
        return studentList;
    }

    //更新student
    public int updateStudent(Student student){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,student.getName());
        values.put(KEY_GRADE,student.getGrade());

        return db.update(TABLE_NAME_STUDENT,values,KEY_ID+"=?",new String[]{String.valueOf(student.getId())});
    }
    public void deleteStudent(Student student){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME_STUDENT,KEY_ID+"=?",new String[]{String.valueOf(student.getId())});
        db.close();
    }

}
