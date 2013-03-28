package com.dmidroid.ingress.excubitor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class RegisterActivity extends Activity {

    private Spinner mAccount;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAccount = (Spinner) findViewById(R.id.ingress_account_spinner);
        
        // set up spinner with accounts on the device
        List<String> emails = new ArrayList<String>();
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                emails.add(account.name);
            }
        }
        ArrayAdapter<String> accountAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, emails);
        mAccount.setAdapter(accountAdapter);
        
        ((Button) findViewById(R.id.register_button)).setOnClickListener(
                new OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences("excubitor prefs", MODE_PRIVATE)
                        .edit()
                        .putString("pref account", (String) mAccount.getSelectedItem())
                        .commit();
                // TODO: GCMRegistrar.register(this, "sender id");
            }
        });
    }
}
