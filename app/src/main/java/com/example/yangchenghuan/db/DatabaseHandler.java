package com.example.yangchenghuan.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.yangchenghuan.entity.Attend;
import com.example.yangchenghuan.entity.Grade;
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
    private static final String TABLE_NAME_GRADE = "grade";
    private static final int VERSION=1;
    private static final String KEY_ID="id";
    private static final String KEY_NAME="name";
    private static final String FACE_ID= "faceid";
    private static final String KEY_GRADE="grade";
    private static final String CREATE_DATE="createdate";
    private static final String IMAGE_PATH = "imagepath";
    private static final String CROWD_ID = "crowdid";

    //建表语句
    private static final String CREATE_TABLE_STUDENT="create table "+TABLE_NAME_STUDENT+"("+
     KEY_ID+ " integer primary key autoincrement,"+
     KEY_NAME+ " text not null,"+
     FACE_ID+" text not null,"    +
     IMAGE_PATH+" text not null,"+
     KEY_GRADE+ " text not null," +
     CREATE_DATE  +" TIMESTAMP NOT NULL DEFAULT current_timestamp  "+
    ")";

    private static final String CREATE_TABLE_ATTEND="create table "+TABLE_NAME_ATTEND+"("+
            KEY_ID+ " integer primary key autoincrement,"+
                    KEY_NAME+ " text not null, "+
                    FACE_ID+" text not null, "    +
                    IMAGE_PATH+" text not null, "+
                    KEY_GRADE+ " text not null, "+
                    CREATE_DATE  +" TIMESTAMP DEFAULT current_timestamp "+
    ")";

    private static final String CREATE_TABLE_GARDE ="create table "+TABLE_NAME_GRADE+"("+
            KEY_ID+ " integer primary key autoincrement, "+
            KEY_NAME+ " text not null, "+
            CROWD_ID+" text not null" +
            ")";

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
        sqLiteDatabase.execSQL(CREATE_TABLE_GARDE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_STUDENT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ATTEND);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_GRADE);
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
        values.put(FACE_ID,attend.getFaceid());
        values.put(IMAGE_PATH,attend.getImagepath());
        db.insert(TABLE_NAME_ATTEND, null, values);
        db.close();
    }
    public Attend getAttend(String name){
        SQLiteDatabase db=this.getWritableDatabase();

        //Cursor对象返回查询结果
        Cursor cursor=db.query(TABLE_NAME_ATTEND,new String[]{KEY_ID,KEY_NAME,FACE_ID,IMAGE_PATH,KEY_GRADE,CREATE_DATE},
                KEY_NAME+"=?",new String[]{name},null,null,null,null);


        Attend attend=null;
        //注意返回结果有可能为空
        if(cursor.moveToFirst()){
            attend=new Attend(cursor.getInt(0),cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
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
                attend.setFaceid(cursor.getString(2));
                attend.setImagepath(cursor.getString(3));
                attend.setGrade(cursor.getString(4));
                attend.setCreatetime(cursor.getString(5));
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
        values.put(FACE_ID,attend.getFaceid());
        values.put(IMAGE_PATH,attend.getImagepath());

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
        values.put(FACE_ID,student.getFaceid());
        values.put(IMAGE_PATH,student.getImagepath());

        db.insert(TABLE_NAME_STUDENT, null, values);
        db.close();
    }
    public Student getStudent(String name){
        SQLiteDatabase db=this.getWritableDatabase();

        //Cursor对象返回查询结果
        Cursor cursor=db.query(TABLE_NAME_STUDENT,new String[]{KEY_ID,KEY_NAME,FACE_ID,IMAGE_PATH,KEY_GRADE,CREATE_DATE},
                KEY_NAME+"=?",new String[]{name},null,null,null,null);


        Student student=null;
        //注意返回结果有可能为空
        if(cursor.moveToFirst()){
            student=new Student(cursor.getInt(0),cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
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
                student.setFaceid(cursor.getString(2));
                student.setImagepath(cursor.getString(3));
                student.setGrade(cursor.getString(4));
                student.setCreatetime(cursor.getString(5));

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
        values.put(FACE_ID,student.getFaceid());
        values.put(IMAGE_PATH,student.getImagepath());

        return db.update(TABLE_NAME_STUDENT,values,KEY_ID+"=?",new String[]{String.valueOf(student.getId())});
    }
    public void deleteStudent(Student student){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME_STUDENT,KEY_ID+"=?",new String[]{String.valueOf(student.getId())});
        db.close();
    }
/**
 * 下面操作grade
 */

public void addGrade(Grade grade){
    SQLiteDatabase db=this.getWritableDatabase();

    //使用ContentValues添加数据
    ContentValues values=new ContentValues();
    values.put(KEY_NAME,grade.getName());
    values.put(CROWD_ID,grade.getCrowdid());

    db.insert(TABLE_NAME_GRADE, null, values);
    db.close();
}
    public Grade getGrade(String name){
        SQLiteDatabase db=this.getWritableDatabase();

        //Cursor对象返回查询结果
        Cursor cursor=db.query(TABLE_NAME_GRADE,new String[]{KEY_ID,KEY_NAME,CROWD_ID},
                KEY_NAME+"=?",new String[]{name},null,null,null,null);


        Grade grade=null;
        //注意返回结果有可能为空
        if(cursor.moveToFirst()){
            grade=new Grade(cursor.getInt(0),cursor.getString(1), cursor.getString(2));
        }
        return grade;
    }
    public int getGradeCounts(){
        String selectQuery="SELECT * FROM "+TABLE_NAME_GRADE;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        cursor.close();

        return cursor.getCount();
    }

    //查找所有Grade
    public List<Grade> getALllGrade(){
        List<Grade> gradeList=new ArrayList<Grade>();

        String selectQuery="SELECT * FROM "+TABLE_NAME_GRADE;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Grade student=new Grade();
                student.setId(Integer.parseInt(cursor.getString(0)));
                student.setName(cursor.getString(1));
                student.setCrowdid(cursor.getString(2));

                gradeList.add(student);
            }while(cursor.moveToNext());
        }
        return gradeList;
    }

    //更新student
    public int updateGrade(Grade grade){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,grade.getName());
        values.put(CROWD_ID,grade.getCrowdid());

        return db.update(TABLE_NAME_GRADE,values,KEY_ID+"=?",new String[]{String.valueOf(grade.getId())});
    }
    public void deleteGrade(Grade grade){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME_GRADE,KEY_ID+"=?",new String[]{String.valueOf(grade.getId())});
        db.close();
    }
}
