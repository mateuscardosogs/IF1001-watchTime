package if1001.watchtime.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import if1001.watchtime.DAO.ConfigFirebase;
import if1001.watchtime.Entities.Users;
import if1001.watchtime.Helper.Base64Custom;
import if1001.watchtime.Helper.Preferences;
import if1001.watchtime.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText editCadName;
    private EditText editCadLastname;
    private EditText editCadBirthday;
    private EditText editCadEmail;
    private EditText editCadPassword;
    private EditText editCadConfirmPassword;
    private RadioButton rbCadWoman;
    private RadioButton rbCadMale;
    private Button btnSubmit;
    private Users users;
    private FirebaseAuth authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editCadName = (EditText) findViewById(R.id.editCadName);
        editCadLastname = (EditText) findViewById(R.id.editCadLastname);
        editCadBirthday = (EditText) findViewById(R.id.editCadBirthday);
        editCadEmail = (EditText) findViewById(R.id.editCadEmail);
        editCadPassword = (EditText) findViewById(R.id.editCadPassword);
        editCadConfirmPassword = (EditText) findViewById(R.id.editCadConfirmPassword);
        rbCadWoman = (RadioButton) findViewById(R.id.rbCadWoman);
        rbCadMale = (RadioButton) findViewById(R.id.rbCadMale);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editCadPassword.getText().toString().equals(editCadConfirmPassword.getText().toString())) {
                    users = new Users();
                    users.setName(editCadName.getText().toString());
                    users.setLastname(editCadLastname.getText().toString());
                    users.setBirthday(editCadBirthday.getText().toString());
                    users.setEmail(editCadEmail.getText().toString());
                    users.setPassword(editCadPassword.getText().toString());

                    if (rbCadWoman.isChecked()) {
                        users.setGender("Feminino");
                    } else {
                        users.setGender("Masculino");
                    }

                    userRegister();

                } else {
                    Toast.makeText(RegisterActivity.this, "As senhas não são correspondentes", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private void userRegister() {

        authentication = ConfigFirebase.getFirebaseAuthentication();
        authentication.createUserWithEmailAndPassword(
                users.getEmail(),
                users.getPassword()
        ).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Usuário cadastrado com sucesso", Toast.LENGTH_LONG).show();

                    String userIdentifier = Base64Custom.base64Encode(users.getEmail());

                    FirebaseUser userFirebase = task.getResult().getUser();
                    users.setId(userIdentifier);
                    users.save();


                    Preferences preferences = new Preferences(RegisterActivity.this);
                    preferences.saveUserPreferences(userIdentifier, users.getName());

                    openUserLogin();
                } else {
                    String exceptionError = "";

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        exceptionError = " Digite uma senha mais forte, contendo no mínimo 8 caracteres de letras e números";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exceptionError = " O e-mail digitado é inválido, digite um novo e-mail";
                    } catch (FirebaseAuthUserCollisionException e) {
                        exceptionError = "Esse e-mail já está cadastrado no sistema";
                    } catch (Exception e) {
                        exceptionError = "Erro ao efetuar o cadastro!";
                        e.printStackTrace();
                    }
                    Toast.makeText(RegisterActivity.this, "Erro: " + exceptionError, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void openUserLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
