# ScientificCaculator
|The Link Your Class  |[https://bbs.csdn.net/forums/ssynkqtd-04](https://bbs.csdn.net/forums/ssynkqtd-04) |
|--|--|
| The Link of Requirement of This Assignment  |  [https://bbs.csdn.net/topics/617378696](https://bbs.csdn.net/topics/617378696) |
|The Aim of This Assignment|Android Application - Calculator|
| MU STU ID and FZU STU ID|<21124825-832102119>|
| The Link of Code of this assignment of GitHub|[https://github.com/CHENZHEN3078/ScientificCaculator](https://github.com/CHENZHEN3078/ScientificCaculator) |


@[TOC](Catalog)

---

# PSP form
| **Personal Software Process Stages**    | Estimated Time（minutes） | Actual Time（minutes） |
| :-------------------------------------- | :------------------------ | :--------------------- |
| Planning                                |                           |                        |
| • Estimate                              |       30                |           60          |
| Development                             |                           |                        |
| • Analysis                              |          10                 |        10                |
| • Design Spec                           |       30                    |          30              |
| • Design Review                         |          10                 |         10              |
| • Coding Standard                       |       20                    |        20                |
| • Design                                |       60                    |      60                  |
| • Coding                                |      400                    |       600                 |
| • Code Review                           |       60                    |        90               |
| • Test                                  |            60               |           60             |
| Reporting                               |                          |                     |
| • Test Repor                            |         60                  |           60             |
| • Size Measurement                      |          20                 |         10               |
| • Postmortem & Process Improvement Plan |          20                 |         60               |
| **Sum**                                 |            780              |       1090                 |
---

# Design and implementation process
1. Basic functions
Function 1: 
Basic operations and the correct order of operations
Store the input string and the result in a back-end database
Function 2: 
View the history
Obtain historical calculation results from the back-end database
2. Extended function:
Scientific calculator
Better looking interface

## Front end (caculator)
**Main interface**
Continues the page design and configuration from the previous assignment
Made some adjustments, added some buttons (index, factorial, parentheses, etc.), and text for reporting errors
Jump button for history

**History interface**
Refresh button for reading historical data
Ten clickable texts, click on the text to obtain the formula on the text and save it in ANS, then return to the main interface

**Scientific caculation**
Improve scientific computing functions
Solved complex operations that were previously were not completed using inverse Polish notation
Implement error reporting function

**Accessing the backend**
URI for accessing content providers
Call the backend to insert data after completing correct calculation on the main interface every time
call the backend to query data after clicking the refresh button on the new page every time

![在这里插入图片描述](https://img-blog.csdnimg.cn/8206aea2f18e4b84b9c310954f34281c.png#pic_center =600x)

##  Back end (sever)
**Interacting with the front-end**
Using a database helper to implement insertion and query methods, providing data for the front-end

**Database**
Create and manage databases
Obtain a unique instance of the database helper

---
# Code description

## Front-end code
The front-end calculation code and page are similar to before, with only some improvements made. Here, only the implementation code of the history interface is shown.

`HistoryActivity.java`

```java
package com.example.caculator02;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.caculator02.entity.equation;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.text1).setOnClickListener(this);
        findViewById(R.id.text2).setOnClickListener(this);
        findViewById(R.id.text3).setOnClickListener(this);
        findViewById(R.id.text4).setOnClickListener(this);
        findViewById(R.id.text5).setOnClickListener(this);
        findViewById(R.id.text6).setOnClickListener(this);
        findViewById(R.id.text7).setOnClickListener(this);
        findViewById(R.id.text8).setOnClickListener(this);
        findViewById(R.id.text9).setOnClickListener(this);
        findViewById(R.id.text10).setOnClickListener(this);
    }

    private int num=0;
    public static String memory="";
    @SuppressLint("Range")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn1) {
            Cursor cursor = getContentResolver().query(EquationInfoContent.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    equation info = new equation();
                    //info.id = cursor.getInt(cursor.getColumnIndex(EquationInfoContent._ID));
                    info.str = cursor.getString(cursor.getColumnIndex(EquationInfoContent.EQUATION_STR));
                    info.ans = cursor.getDouble(cursor.getColumnIndex(EquationInfoContent.EQUATION_ANS));
                    Log.d("ning", info.toString());
                    TextView t;
                    if (num==0) t=(TextView)findViewById(R.id.text1);
                    else if (num==1)t=(TextView)findViewById(R.id.text2);
                    else if (num==2)t=(TextView)findViewById(R.id.text3);
                    else if (num==3)t=(TextView)findViewById(R.id.text4);
                    else if (num==4)t=(TextView)findViewById(R.id.text5);
                    else if (num==5)t=(TextView)findViewById(R.id.text6);
                    else if (num==6)t=(TextView)findViewById(R.id.text7);
                    else if (num==7)t=(TextView)findViewById(R.id.text8);
                    else if (num==8)t=(TextView)findViewById(R.id.text9);
                    else t=(TextView)findViewById(R.id.text10);
                    num++;
                    t.setText(info.toString());
                }
                num=0;
            }
        }else if (id== R.id.text1) {
            TextView a=(TextView)findViewById(R.id.text1);
            memory= (String) a.getText();
            finish();
        }else if (id== R.id.text2) {
            TextView a=(TextView)findViewById(R.id.text2);
            memory= (String) a.getText();
            finish();
        }else if (id== R.id.text3) {
            TextView a=(TextView)findViewById(R.id.text3);
            memory= (String) a.getText();
            finish();
        }else if (id== R.id.text4) {
            TextView a=(TextView)findViewById(R.id.text4);
            memory= (String) a.getText();
            finish();
        }else if (id== R.id.text5) {
            TextView a=(TextView)findViewById(R.id.text5);
            memory= (String) a.getText();
            finish();
        }else if (id== R.id.text6) {
            TextView a=(TextView)findViewById(R.id.text6);
            memory= (String) a.getText();
            finish();
        }else if (id== R.id.text7) {
            TextView a=(TextView)findViewById(R.id.text7);
            memory= (String) a.getText();
            finish();
        }else if (id== R.id.text8) {
            TextView a=(TextView)findViewById(R.id.text8);
            memory= (String) a.getText();
            finish();
        }else if (id== R.id.text9) {
            TextView a=(TextView)findViewById(R.id.text9);
            memory= (String) a.getText();
            finish();
        }else if (id== R.id.text10) {
            TextView a=(TextView)findViewById(R.id.text10);
            memory= (String) a.getText();
            finish();
        }
        }
    }

```
`activity_history.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:background="@drawable/bg3"
android:orientation="vertical"
android:padding="5dp">

<ScrollView
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="#156200EE"
            android:gravity="center"
            android:text="@string/simple_calculator"
            android:textColor="#000000"
            android:textSize="25sp"
            android:textStyle="bold|italic" />

        <Button
            android:id="@+id/btn1"
            android:layout_width="match_parent"
            android:layout_height="@dimen/button_height"
            android:layout_columnWeight="1"
            android:background="@color/white"
            android:gravity="center"
            android:onClick="delete"
            android:text="Refresh"
            android:textColor="@color/black"
            android:textSize="20sp
" />


        <TextView
            android:id="@+id/text1"
            android:layout_width="match_parent"
            android:layout_height="@dimen/button_height"
            android:background="@color/white"
            android:clickable="true"
            android:gravity="center"
            android:text="equation1"
            android:textColor="@color/black"
            android:textSize="20dp" />

        <TextView
            android:id="@+id/text2"
            android:layout_width="match_parent"
            android:layout_height="@dimen/button_height"
            android:clickable="true"
            android:gravity="center"
            android:text="equation2"
            android:textColor="@color/black"
            android:background="@color/white"
            android:textSize="20dp" />

        <TextView
            android:id="@+id/text3"
            android:layout_width="match_parent"
            android:layout_height="@dimen/button_height"
            android:clickable="true"
            android:gravity="center"
            android:text="equation3"
            android:textColor="@color/black"
            android:background="@color/white"
            android:textSize="20dp" />

        <TextView
            android:id="@+id/text4"
            android:layout_width="match_parent"
            android:layout_height="@dimen/button_height"
            android:clickable="true"
            android:gravity="center"
            android:text="equation4"
            android:textColor="@color/black"
            android:background="@color/white"
            android:textSize="20dp" />

        <TextView
            android:id="@+id/text5"
            android:layout_width="match_parent"
            android:layout_height="@dimen/button_height"
            android:clickable="true"
            android:gravity="center"
            android:text="equation5"
            android:textColor="@color/black"
            android:background="@color/white"
            android:textSize="20dp" />

        <TextView
            android:id="@+id/text6"
            android:layout_width="match_parent"
            android:layout_height="@dimen/button_height"
            android:clickable="true"
            android:gravity="center"
            android:text="equation6"
            android:textColor="@color/black"
            android:background="@color/white"
            android:textSize="20dp" />

        <TextView
            android:id="@+id/text7"
            android:layout_width="match_parent"
            android:layout_height="@dimen/button_height"
            android:clickable="true"
            android:gravity="center"
            android:text="equation7"
            android:textColor="@color/black"
            android:background="@color/white"
            android:textSize="20dp" />
        <TextView
            android:id="@+id/text8"
            android:layout_width="match_parent"
            android:layout_height="@dimen/button_height"
            android:clickable="true"
            android:gravity="center"
            android:text="equation8"
            android:textColor="@color/black"
            android:background="@color/white"
            android:textSize="20dp" />

        <TextView
            android:id="@+id/text9"
            android:layout_width="match_parent"
            android:layout_height="@dimen/button_height"
            android:clickable="true"
            android:gravity="center"
            android:text="equation9"
            android:textColor="@color/black"
            android:background="@color/white"
            android:textSize="20dp" />

        <TextView
            android:id="@+id/text10"
            android:layout_width="match_parent"
            android:layout_height="@dimen/button_height"
            android:clickable="true"
            android:gravity="center"
            android:text="equation10"
            android:textColor="@color/black"
            android:background="@color/white"
            android:textSize="20dp" />

       <!-- <TextView
            android:id="@+id/textView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true"
            android:layout_centerHorizontal="true"
            android:layout_marginBottom="10dp"
            android:textColor="#FF0000"
            android:text="11111111111"
            android:textSize="20sp" />-->

    </LinearLayout>

</ScrollView>

</LinearLayout>


```
## Back-end code
The backend is responsible for creating and managing databases, providing data for the front-end.

`EquationDBHelper.java`
```java
package com.example.sever01.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EquationDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "equation.db";
    public static final String TABLE_NAME = "equation_info";
    private static final int DB_VERSION = 1;
    private static EquationDBHelper mHelper = null;

    private EquationDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static EquationDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new EquationDBHelper(context);
        }
        return mHelper;
    }

    // 创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " str VARCHAR NOT NULL," +
                " ans DOUBLE NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
```

`EquationInfoProvider.java`
```java
package com.example.sever01.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.sever01.database.EquationDBHelper;

public class EquationInfoProvider extends ContentProvider {

    private EquationDBHelper dbHelper;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    
    @Override
    public boolean onCreate() {
        Log.d("ning", "EquationInfoProvider onCreate");
        dbHelper = EquationDBHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //if (URI_MATCHER.match(uri) == EQUATIONS) {
        Log.d("chen", "EquationInfoProvider insert" + values);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String deleteQuery = "DELETE FROM equation_info WHERE _id NOT IN (SELECT _id FROM equation_info ORDER BY _id DESC LIMIT 10)";
        db.execSQL(deleteQuery);

        long rowId = db.insert(EquationDBHelper.TABLE_NAME, null, values);
        if (rowId > 0) { // 判断插入是否执行成功
            Log.d("chen", "EquationInfoProvider insert " + values);
        }
        return uri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d("chen", "EquationInfoProvider query");
        //if (URI_MATCHER.match(uri) == EQUATIONS) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(EquationDBHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        //}
        //return null;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
```
---
# Result display
## Screenshot
**Main Page**
![在这里插入图片描述](https://img-blog.csdnimg.cn/1fec5fdef1f04afcbfc2fdaffa594f21.png#pic_center =250x)

**History Page**
![在这里插入图片描述](https://img-blog.csdnimg.cn/2c83b4d818a54e39b9706ee1baca72e5.png#pic_center =250x)
**Partial error display**
Incorrect input
![在这里插入图片描述](https://img-blog.csdnimg.cn/629fe930122f47e19af7653de0d5b0ce.png#pic_center =250x)
Divided by 0
![在这里插入图片描述](https://img-blog.csdnimg.cn/2f2da46b513641e1ae459d36faf4b35c.png#pic_center =250x)



## Screen recording


[video(video-znJiF44U-1697958892987)(type-csdn)(url-https://live.csdn.net/v/embed/337447)(image-https://video-community.csdnimg.cn/vod-84deb4/03d0a26070aa71ee9f7f0675b3ed0102/snapshots/755bf59f342849b7aa1360757e10415c-00004.jpg?auth_key=4851558613-0-0-b3504613efd759b2bdb83ca1d63f3989)(title-Basic function)]
Calculators can perform scientific and complex calculations

[video(video-Lf3JNGGx-1697958900348)(type-csdn)(url-https://live.csdn.net/v/embed/337446)(image-https://video-community.csdnimg.cn/vod-84deb4/f0cced9070a971ee839b4531958d0102/snapshots/e90763ebd959431b84e2778128afb8e1-00004.jpg?auth_key=4851558575-0-0-419e4fbd8463f94ba328f794ad57e427)(title-Save)]
After proper calculation, the calculation results and formulas will be saved to the database (only ten records will be kept), which can be viewed in the history interface.

[video(video-RNwxoz8E-1697958906637)(type-csdn)(url-https://live.csdn.net/v/embed/337445)(image-https://video-community.csdnimg.cn/vod-84deb4/c16af42070a971ee92cf5107e0c90102/snapshots/f6cfe7a7a582403a8abf25394cbafb24-00004.jpg?auth_key=4851558501-0-0-300b4894e18fdb304d870ca75b0d57b3)(title-Read)]
After clicking on a history record in the history interface, it will return to the main interface. Clicking on ANS can calculate this history record again.


---
# Summarize
## Remaining issues
1.The function of brackets is abnormal (I'm still checking).
2.Can meet scientific calculations, but can still be improved.
## Learning summar
Due to a lack of understanding of front-end and back-end separation, both learning and practice took more time than expected, but the final result was quite satisfactory and fully implemented all functions. In practical operation, I complete some functions by querying, imitating, and combining (mainly referring to the shopping cart code of [Android 基础教程](https://www.bilibili.com/video/BV19U4y1R7zV/?spm_id_from=333.337.search-card.all.click&vd_source=095be17af1e00a1a80fc1b087af57581)) and has solved many code issues. Through this assignment, I have gained a deeper understanding of the method of separating the front and rear ends, and I believe I can become more proficient and smooth next time.

**Thank you for browsing！**
