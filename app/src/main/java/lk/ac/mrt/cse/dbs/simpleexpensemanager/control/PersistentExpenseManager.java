package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;

/**
 * Created by HP on 12/6/2015.
 */
public class PersistentExpenseManager extends ExpenseManager{

    private SqliteExpenseManager sqliteExpenseManager;
    private Context context;

    public PersistentExpenseManager(Context context){
        this.context = context;
        try {
            this.setup();
        } catch (ExpenseManagerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setup() throws ExpenseManagerException {

        sqliteExpenseManager = new SqliteExpenseManager(context);

        TransactionDAO inMemoryTransactionDAO = new PersistentTransactionDAO(context);
        setTransactionsDAO(inMemoryTransactionDAO);

        AccountDAO inMemoryAccountDAO = new PersistentAccountDAO(context);
        setAccountsDAO(inMemoryAccountDAO);
    }
}
