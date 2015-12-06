package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.SqliteExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * Created by HP on 12/6/2015.
 */
public class PersistentAccountDAO implements AccountDAO{

    private SqliteExpenseManager sqliteExpenseManager;
   // private SQLiteDatabase db;

    private String[] allColumns = { SqliteExpenseManager.COLUMN_ID,
            SqliteExpenseManager.COLUMN_ACCOUNT_NO, SqliteExpenseManager.COLUMN_ACCOUNT_HOLDER_NAME, SqliteExpenseManager.COLUMN_BALANCE };


    public PersistentAccountDAO(Context context){
        sqliteExpenseManager = new SqliteExpenseManager(context);
        //db = sqliteExpenseManager.getWritableDatabase();
    }

    @Override
    public List<String> getAccountNumbersList() {
        List<String> numbers = new ArrayList<String>();

        Cursor cursor = sqliteExpenseManager.getReadableDatabase().query(SqliteExpenseManager.TABLE_ACCOUNT,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Account account = cursorToAccount(cursor);
            numbers.add(account.getAccountNo());
            cursor.moveToNext();
        }
        cursor.close();
        return numbers;
    }

    @Override
    public List<Account> getAccountsList() {
        List<Account> accounts = new ArrayList<Account>();

        Cursor cursor = sqliteExpenseManager.getReadableDatabase().query(SqliteExpenseManager.TABLE_ACCOUNT,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Account account = cursorToAccount(cursor);
            accounts.add(account);
            cursor.moveToNext();
        }
        cursor.close();
        return accounts;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        Account account = null;
        String whereClause = "account_no = ?";
        String[] whereArgs = new String[] {
                accountNo
        };
        Cursor cursor = sqliteExpenseManager.getReadableDatabase().query(SqliteExpenseManager.TABLE_ACCOUNT, allColumns, whereClause, whereArgs, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            account = cursorToAccount(cursor);
            cursor.moveToNext();
        }
        return account;
    }

    @Override
    public void addAccount(Account account) {
        ContentValues values = new ContentValues();
        values.put(SqliteExpenseManager.COLUMN_ACCOUNT_NO, account.getAccountNo());
        values.put(SqliteExpenseManager.COLUMN_ACCOUNT_HOLDER_NAME, account.getAccountHolderName());
        values.put(SqliteExpenseManager.COLUMN_BANK_NAME, account.getBankName());
        values.put(SqliteExpenseManager.COLUMN_BALANCE, account.getBalance());
        long insertId = sqliteExpenseManager.getWritableDatabase().insert(SqliteExpenseManager.TABLE_ACCOUNT, null, values);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        sqliteExpenseManager.getWritableDatabase().delete(SqliteExpenseManager.TABLE_ACCOUNT, SqliteExpenseManager.COLUMN_ACCOUNT_NO + " = " + accountNo, null);
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

    }


    private Account cursorToAccount(Cursor cursor) {
        Account account = new Account();
        account.setAccountNo(cursor.getString(cursor.getColumnIndex(SqliteExpenseManager.COLUMN_ACCOUNT_NO)));
        account.setAccountHolderName(cursor.getString(cursor.getColumnIndex(SqliteExpenseManager.COLUMN_ACCOUNT_HOLDER_NAME)));
        //account.setBankName(cursor.getString(cursor.getColumnIndex(SqliteExpenseManager.COLUMN_BANK_NAME)));
        account.setBalance(Double.parseDouble(cursor.getString(cursor.getColumnIndex(SqliteExpenseManager.COLUMN_BALANCE))));
        return account;
    }
}
