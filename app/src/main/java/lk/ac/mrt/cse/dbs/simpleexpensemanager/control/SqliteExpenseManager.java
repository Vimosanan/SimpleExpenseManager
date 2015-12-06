package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HP on 12/6/2015.
 */
public class SqliteExpenseManager extends SQLiteOpenHelper{


    public static final String TABLE_ACCOUNT = "account";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ACCOUNT_NO = "account_no";
    public static final String COLUMN_BANK_NAME = "bank_name";
    public static final String COLUMN_ACCOUNT_HOLDER_NAME = "account_holder_name";
    public static final String COLUMN_BALANCE = "balance";


    public static final String TABLE_TRANSACTION = "expense_transaction";
    public static final String COLUMN_EXPENSE_TYPE = "expense_name";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DATE = "date";

    private static final String DATABASE_NAME = "expense_manager.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE_ACCOUNT = "create table "
            + TABLE_ACCOUNT + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_ACCOUNT_NO
            + " text not null, " +COLUMN_BANK_NAME
            + " text not null, " +COLUMN_ACCOUNT_HOLDER_NAME
            + " text not null, " +COLUMN_BALANCE
            + " text not null);";

    private static final String DATABASE_CREATE_TRANSACTION = "create table "
            + TABLE_TRANSACTION + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_ACCOUNT_NO
            + " text not null, " +COLUMN_EXPENSE_TYPE
            + " text not null, " +COLUMN_AMOUNT
            + " text not null, " +COLUMN_DATE
            + " text not null);";

    public SqliteExpenseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_ACCOUNT);
        db.execSQL(DATABASE_CREATE_TRANSACTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
