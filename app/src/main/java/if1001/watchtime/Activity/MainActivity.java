package if1001.watchtime.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import if1001.watchtime.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView movieCard, serieCard, animeCard, historyCard, addCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieCard = (CardView) findViewById(R.id.moviesId);
        serieCard = (CardView) findViewById(R.id.seriesId);
        animeCard = (CardView) findViewById(R.id.animesId);
        historyCard = (CardView) findViewById(R.id.historyId);
        addCard = (CardView) findViewById(R.id.addId);

        movieCard.setOnClickListener(this);
        serieCard.setOnClickListener(this);
        animeCard.setOnClickListener(this);
        historyCard.setOnClickListener(this);
        addCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()) {
            case R.id.moviesId : i = new Intent(this, MainActivity.class); startActivity(i); break;
            case R.id.seriesId : i = new Intent(this, MainActivity.class); startActivity(i); break;
            case R.id.animesId : i = new Intent(this, MainActivity.class); startActivity(i); break;
            case R.id.historyId : i = new Intent(this, MainActivity.class); startActivity(i); break;
            case R.id.addId : i = new Intent(this, MainActivity.class); startActivity(i); break;
            default:break;
        }

    }
}
