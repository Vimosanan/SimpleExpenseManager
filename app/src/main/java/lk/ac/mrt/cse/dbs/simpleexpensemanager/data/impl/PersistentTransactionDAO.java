package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.SqliteExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by HP on 12/6/2015.
 */
public class PersistentTransactionDAO implements TransactionDAO {


    private SqliteExpenseManager sqliteExpenseManager;
    //private SQLiteDatabase db;

    private String[] allColumns = { SqliteExpenseManager.COLUMN_ID,
            SqliteExpenseManager.COLUMN_ACCOUNT_NO, SqliteExpenseManager.COLUMN_EXPENSE_TYPE, SqliteExpenseManager.COLUMN_AMOUNT, SqliteExpenseManager.COLUMN_DATE };


    public PersistentTransactionDAO(Context context){
        sqliteExpenseManager = new SqliteExpenseManager(context);
        //db = sqliteExpenseManager.getWritableDatabase();
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        ContentValues values = new ContentValues();
        values.put(SqliteExpenseManager.COLUMN_ACCOUNT_NO, accountNo);
        values.put(SqliteExpenseManager.COLUMN_EXPENSE_TYPE, expenseType.toString());
        values.put(SqliteExpenseManager.COLUMN_AMOUNT, String.valueOf(amount));

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = format.format(date);

        values.put(SqliteExpenseManager.COLUMN_DATE, formattedDate);
        long insertId = sqliteExpenseManager.getWritableDatabase().insert(SqliteExpenseManager.TABLE_TRANSACTION, null, values);
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> transactions = new ArrayList<Transaction>();

        Cursor cursor = sqliteExpenseManager.getReadableDatabase().query(SqliteExpenseManager.TABLE_TRANSACTION,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Transaction transaction = cursorToTransaction(cursor);
            transactions.add(transaction);
            cursor.moveToNext();
        }
        cursor.close();
        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> transactions = new ArrayList<Transaction>();

        Cursor cursor = sqliteExpenseManager.getReadableDatabase().query(SqliteExpenseManager.TABLE_TRANSACTION,
                allColumns, null, null, null, null, null, "" + limit);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Transaction transaction = cursorToTransaction(cursor);
            transactions.add(transaction);
            cursor.moveToNext();
        }
        cursor.close();
        return transactions;
    }

    private Transaction cursorToTransaction(Cursor cursor) {
        Transaction transaction = new Transaction();
        transaction.setAccountNo(cursor.getString(cursor.getColumnIndex(SqliteExpenseManager.COLUMN_ACCOUNT_NO)));
        transaction.setAmount(Double.parseDouble(cursor.getString(cursor.getColumnIndex(SqliteExpenseManager.COLUMN_AMOUNT))));
        transaction.setExpenseType(ExpenseType.valueOf(cursor.getString(cursor.getColumnIndex(SqliteExpenseManager.COLUMN_EXPENSE_TYPE))));

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = format.parse(cursor.getString(cursor.getColumnIndex(SqliteExpenseManager.COLUMN_DATE)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        transaction.setDate(date);
        return transaction;
    }
}
