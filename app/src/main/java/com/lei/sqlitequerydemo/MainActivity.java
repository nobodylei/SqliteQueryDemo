package com.lei.sqlitequerydemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lei.bean.Person;
import com.lei.utils.Constant;
import com.lei.utils.DbManager;
import com.lei.utils.MySqliteHelper;

import java.util.List;

/**
 * 演示Sqlite数据库中的查询操作
 */
public class MainActivity extends AppCompatActivity {

    private MySqliteHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        helper = DbManager.getIntance(this);
    }

    /*
    点击按钮创建数据库，并插入测试数据
     */
    public void createDB(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        for(int i = 1;i <= 50;i ++) {
            String sql = "insert into " + Constant.TABLE_NAME + " values(" +
                    i+ ",'zhangsan"+ i +"',20)";
            db.execSQL(sql);
        }
        db.close();
    }

    /*
    点击按钮查询表中的数据
     */
    public void onClick(View view) {
        SQLiteDatabase db;
        Cursor cursor;
        List<Person> list;
        switch(view.getId()) {
            case R.id.btn_query:
                db = helper.getWritableDatabase();
                String sql = "select * from " + Constant.TABLE_NAME;
                cursor = DbManager.selectDataBySql(db,sql,null);
                list = DbManager.cursorToList(cursor);
                for(Person person:list) {
                    Log.i("tag",person.toString());
                }
                db.close();
                break;
            case R.id.btn_queryApi:
                db = helper.getWritableDatabase();
                /*
                query(String table,String[] columns,String selection,String[] selectionArgs,
                String groupBy,String having,String orderBy)
                String table 表示查询的表名
                String[] ccolumns 表示查询表中的字段名称，null 表示查询所有
                String selection 表示查询条件 Where字句
                String[] selectionArgs 表示查询条件占位符的取值
                String groupBy 表示分组条件 group by子句
                String having 表示筛选条件 having子句
                String orderBy 表示排序条件 order by子句
                 */
                cursor = db.query(Constant.TABLE_NAME,null,Constant._ID + ">?",new String[]{"10"},
                        null,null,Constant._ID + " desc");
                list = DbManager.cursorToList(cursor);
                for(Person person:list) {
                    Log.i("tag",person.toString());
                }
                db.close();
                break;
        }
    }
}









