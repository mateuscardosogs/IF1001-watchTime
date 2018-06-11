package if1001.watchtime.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import if1001.watchtime.R;

public class InitialActivity extends AppCompatActivity {

    private Button btnOpenActivityLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        btnOpenActivityLogin = (Button) findViewById(R.id.btnSignin);
        btnOpenActivityLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOpenLoginScreen = new Intent(InitialActivity.this, LoginActivity.class);
                startActivity(intentOpenLoginScreen);
            }
        });
    }
}
