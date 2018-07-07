package if1001.watchtime.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import if1001.watchtime.DAO.ConfigFirebase;
import if1001.watchtime.Entities.Users;
import if1001.watchtime.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editPassword;
    private Button btnLogin;
    private TextView tvOpenRegister;
    private FirebaseAuth authentication;
    private Users users;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        tvOpenRegister = (TextView) findViewById(R.id.tvOpenRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        progress = new ProgressDialog(this);
        progress.setTitle("Aguarde");
        progress.setMessage("Efetuando Login...");
        progress.setCancelable(false);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.show();
                if (!editEmail.getText().toString().equals("") && !editPassword.getText().toString().equals("")) {

                    users = new Users();
                    users.setEmail(editEmail.getText().toString());
                    users.setPassword(editPassword.getText().toString());

                    loginValidade();

                } else {
                    Toast.makeText(LoginActivity.this, "Preencha os campos de email e senha", Toast.LENGTH_SHORT).show();
                }
                progress.dismiss();
            }
        });

        tvOpenRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserRegister();
            }
        });
    }

    private void loginValidade() {
        authentication = ConfigFirebase.getFirebaseAuthentication();
        authentication.signInWithEmailAndPassword(users.getEmail(), users.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    openMainScreen();
                    Toast.makeText(LoginActivity.this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(LoginActivity.this, "Usuário e/ou Senha inválidos", Toast.LENGTH_SHORT).show();
                }

                progress.dismiss();
            }
        });
    }

    public void openMainScreen() {
        Intent intentOpenMainScreen = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intentOpenMainScreen);
    }

    public void openUserRegister() {
        Intent intentOpenUserRegister = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intentOpenUserRegister);
    }
}
