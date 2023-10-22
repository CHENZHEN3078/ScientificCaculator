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

   /* private static final int EQUATIONS = 1;
    private static final int EQUATION = 2;*/

    /* static {
         // 往Uri匹配器中添加指定的数据路径
         URI_MATCHER.addURI(EquationInfoContent.AUTHORITIES, "/equation", EQUATIONS);
         URI_MATCHER.addURI(EquationInfoContent.AUTHORITIES, "/equation/#", EQUATION);
     }
 */
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
                /*// 如果添加成功，就利用新记录的行号生成新的地址
                Uri newUri = ContentUris.withAppendedId(UserInfoContent.CONTENT_URI, rowId);
                // 通知监听器，数据已经改变
                getContext().getContentResolver().notifyChange(newUri, null);*/
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
       /* int count = 0;
        switch (URI_MATCHER.match(uri)) {
            //content://com.dongnaoedu.chapter07_server.provider.UserInfoProvider/user
            // 删除多行
            case EQUATIONS:
                SQLiteDatabase db1 = dbHelper.getWritableDatabase();
                count = db1.delete(EquationDBHelper.TABLE_NAME, selection, selectionArgs);
                db1.close();
                break;

            //content://com.dongnaoedu.chapter07_server.provider.UserInfoProvider/user/2
            //删除单行
            case EQUATION:
                String id = uri.getLastPathSegment();
                SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                count = db2.delete(EquationDBHelper.TABLE_NAME, "_id=?", new String[]{id});
                db2.close();
                break;
        }
        return count;*/
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