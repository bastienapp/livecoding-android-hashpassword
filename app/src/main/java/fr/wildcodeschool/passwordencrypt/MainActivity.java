package fr.wildcodeschool.passwordencrypt;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    public static final String CACHE_PASSWORD = "CACHE_PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bSavePassword = findViewById(R.id.button_save_password);
        bSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                EditText etNewPassword = findViewById(R.id.edit_new_password);
                HashCode hashCode = Hashing.sha256().hashString(etNewPassword.getText().toString(), Charset.defaultCharset());

                // save hash password into preferences
                editor.putString(CACHE_PASSWORD, hashCode.toString());
                editor.commit();
            }
        });

        Button bCheckPassword = findViewById(R.id.button_check_password);
        bCheckPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                String password = sharedPref.getString(CACHE_PASSWORD, null);

                EditText etCheckPassword = findViewById(R.id.edit_check_password);
                HashCode hashCode = Hashing.sha256().hashString(etCheckPassword.getText().toString(), Charset.defaultCharset());

                // check if password are the same
                if (password != null && password.equals(hashCode.toString())) {
                    Toast.makeText(MainActivity.this, R.string.password_ok, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, R.string.password_nok, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
