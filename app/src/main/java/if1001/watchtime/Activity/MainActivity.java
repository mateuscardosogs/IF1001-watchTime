package if1001.watchtime.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import if1001.watchtime.Entities.Annotations;
import if1001.watchtime.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView movieCard, serieCard, animeCard, everythingCard, addCard;
    public static FirebaseUser user;
    public static String userid;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieCard = (CardView) findViewById(R.id.moviesId);
        serieCard = (CardView) findViewById(R.id.seriesId);
        animeCard = (CardView) findViewById(R.id.animesId);
        everythingCard = (CardView) findViewById(R.id.everythingId);
        addCard = (CardView) findViewById(R.id.addId);

        movieCard.setOnClickListener(this);
        serieCard.setOnClickListener(this);
        animeCard.setOnClickListener(this);
        everythingCard.setOnClickListener(this);
        addCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()) {
            case R.id.moviesId : getMovies(); break;
            case R.id.seriesId : getSeries(); break;
            case R.id.animesId : getAnimes(); break;
            case R.id.everythingId : getAll(); break;
            case R.id.addId :
                i = new Intent(this, CreateAnnotationActivity.class);
                startActivity(i);
                CreateAnnotationActivity.ListSelected = false;
                break;
            default:break;
        }

    }


    private void getMovies(){

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        userid = user.getUid();
        ContentActivity.annotations.clear();

        DatabaseReference read = db.getInstance().getInstance().getReference(userid+"/Annotations");
        read.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> keys = dataSnapshot.getChildren();
                for(DataSnapshot key : keys){
                    Annotations note = key.getValue(Annotations.class);
                    if (note.getCategory().toString().equalsIgnoreCase("Filme")) {
                        ContentActivity.annotations.add(note);
                    }
                }
                openContentScreen();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void getSeries(){

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        userid = user.getUid();
        ContentActivity.annotations.clear();

        DatabaseReference read = db.getInstance().getInstance().getReference(userid+"/Annotations");
        read.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> keys = dataSnapshot.getChildren();
                for(DataSnapshot key : keys){
                    Annotations note = key.getValue(Annotations.class);
                    if (note.getCategory().toString().equalsIgnoreCase("Serie")) {
                        ContentActivity.annotations.add(note);
                    }
                }
                openContentScreen();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void getAnimes(){

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        userid = user.getUid();
        ContentActivity.annotations.clear();

        DatabaseReference read = db.getInstance().getInstance().getReference(userid+"/Annotations");
        read.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> keys = dataSnapshot.getChildren();
                for(DataSnapshot key : keys){
                    Annotations note = key.getValue(Annotations.class);
                    if (note.getCategory().toString().equalsIgnoreCase("Anime")) {
                        ContentActivity.annotations.add(note);
                    }
                }
                openContentScreen();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void getAll(){

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        userid = user.getUid();
        ContentActivity.annotations.clear();

        DatabaseReference read = db.getInstance().getInstance().getReference(userid+"/Annotations");
        read.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> keys = dataSnapshot.getChildren();
                for(DataSnapshot key : keys){
                    Annotations note = key.getValue(Annotations.class);
                    ContentActivity.annotations.add(note);
                }
                openContentScreen();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void openContentScreen() {
        Intent intentOpenMainScreen = new Intent(MainActivity.this, ContentActivity.class);
        startActivity(intentOpenMainScreen);
    }
}
