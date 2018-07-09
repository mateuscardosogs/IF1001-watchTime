package if1001.watchtime.Activity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import if1001.watchtime.Entities.Annotations;
import if1001.watchtime.Helper.Notifications;
import if1001.watchtime.R;

public class CreateAnnotationActivity extends AppCompatActivity {
    FirebaseDatabase db;


    Button save;
    ImageButton btn_speech_content, btn_speech_title;
    EditText tv_content, tv_title;
    Spinner spinner_category;
    Spinner spinner_priority;
    String[] items_priority = {"Baixa", "Mediana", "Alta"};
    String[] items_category = {"Filme", "Serie", "Anime"};
    DatePicker datePicker;
    public static Boolean ListSelected;
    public static int ListPosition;
    public static Annotations myannotations = new Annotations();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_create_annotation);
        init();
        ContentActivity.first = false;

        ArrayAdapter<String> adapter_priority = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items_priority);

        ArrayAdapter<String> adapter_category = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items_category);

        spinner_priority.setAdapter(adapter_priority);
        spinner_category.setAdapter(adapter_category);

        if (ListSelected) {

            for (int i = 0; i < 3; i++) {
                if (spinner_category.getItemAtPosition(i).toString().equalsIgnoreCase( ContentActivity.annotations.get(ListPosition).getCategory()))
                    spinner_category.setSelection(i);
                if (spinner_priority.getItemAtPosition(i).toString().equalsIgnoreCase( ContentActivity.annotations.get(ListPosition).getPriority()))
                    spinner_priority.setSelection(i);
            }
            tv_title.setText("" + ContentActivity.annotations.get(ListPosition).getTitle());
            tv_content.setText("" + ContentActivity.annotations.get(ListPosition).getContent());

            String[] parts = ContentActivity.annotations.get(ListPosition).getDeadline().split("/");
            String day = parts[0];
            String month = parts[1];
            String year = parts[2];
            datePicker.init(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day), null);

        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ListSelected) {
                    Annotations new_note = new Annotations();
                    new_note.setContent(tv_title.getText().toString());
                    new_note.setTitle(tv_content.getText().toString());

                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth() + 1;
                    int year = datePicker.getYear();
                    new_note.setDeadline(Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year));

                    String formattedDate = java.text.DateFormat.getDateTimeInstance().format(new Date());
                    new_note.setDatetime(formattedDate);

                    new_note.setCategory(spinner_category.getSelectedItem().toString());
                    new_note.setPriority(spinner_priority.getSelectedItem().toString());
                    ContentActivity.annotations.add(new_note);


                    int delay_year = year - Calendar.getInstance().get(Calendar.YEAR);
                    int delay_month = month - (Calendar.getInstance().get(Calendar.MONTH)+1);
                    int delay_day = day - Calendar.getInstance().get(Calendar.DATE);

                    double delay_year_mili = 3155760000.0 * delay_year;
                    double delay_month_mili = TimeUnit.DAYS.toMillis(delay_month*30);
                    double delay_day_mili = TimeUnit.DAYS.toMillis(delay_day);

                    long delay = (long) ( delay_day_mili  + delay_month_mili + delay_year_mili);

                    scheduleNotification(getNotification("5 second delay"), delay);


                }
                else {

                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth() + 1;
                    int year = datePicker.getYear();

                    String formattedDate = java.text.DateFormat.getDateTimeInstance().format(new Date());

                    ContentActivity.annotations.get(ListPosition).setPriority(spinner_priority.getSelectedItem().toString());
                    ContentActivity.annotations.get(ListPosition).setCategory(spinner_category.getSelectedItem().toString());
                    ContentActivity.annotations.get(ListPosition).setDatetime(formattedDate);
                    ContentActivity.annotations.get(ListPosition).setDeadline(Integer.toString(day) + "/" +
                            Integer.toString(month) + "/" + Integer.toString(year));
                    ContentActivity.annotations.get(ListPosition).setContent(tv_content.getText().toString());
                    ContentActivity.annotations.get(ListPosition).setTitle(tv_title.getText().toString());


                    int delay_year = year - Calendar.getInstance().get(Calendar.YEAR);
                    int delay_month = month - (Calendar.getInstance().get(Calendar.MONTH)+1);
                    int delay_day = day - Calendar.getInstance().get(Calendar.DATE);

                    double delay_year_mili = 3155760000.0 * delay_year;
                    double delay_month_mili = TimeUnit.DAYS.toMillis(delay_month*30);
                    double delay_day_mili = TimeUnit.DAYS.toMillis(delay_day);

                    long delay = (long) ( delay_day_mili  + delay_month_mili + delay_year_mili);


                    scheduleNotification(getNotification("5 second delay"), delay);


                }
                database_operations();
                finish();
                startActivity(new Intent(CreateAnnotationActivity.this, MainActivity.class));

            }
        });

        btn_speech_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        getString(R.string.speech_get_content));
                try {

                    startActivityForResult(intent, 1 );

                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.speech_help),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_speech_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        getString(R.string.speech_get_title));
                try {

                    startActivityForResult(intent, 2 );

                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.speech_help),
                            Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> myannotations = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tv_content.setText(myannotations.get(0));
                }
                break;
            }

            case 2: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> not_basligi = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tv_title.setText(not_basligi.get(0));

                }
                break;
            }
        }
    }

    private void init(){
        btn_speech_title = (ImageButton)findViewById(R.id.btn_speech_title);

        db=FirebaseDatabase.getInstance();
        btn_speech_content = (ImageButton)findViewById(R.id.btn_speech_content);
        datePicker = (DatePicker) findViewById(R.id.datepicker);
        tv_title = (EditText) findViewById(R.id.tv_title);
        tv_content = (EditText) findViewById(R.id.tv_content);
        datePicker = (DatePicker) findViewById(R.id.datepicker);
        spinner_category=(Spinner)findViewById(R.id.spinner_category);
        spinner_priority=(Spinner)findViewById(R.id.spinner_priority);
        save=(Button)findViewById(R.id.btn_save);

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(CreateAnnotationActivity.this, MainActivity.class));
    }


    private void scheduleNotification(Notification notification, long delay) {

        Intent notificationIntent = new Intent(this, Notifications.class);
        notificationIntent.putExtra(Notifications.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(Notifications.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Lembre-se do que fazer!");
        builder.setContentText("Verifique suas anotações");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        return builder.build();
    }

    public void database_operations() {

        DatabaseReference dbrefuser = ContentActivity.db.getReference(ContentActivity.userid);
        dbrefuser.removeValue();

        DatabaseReference dbRef = ContentActivity.db.getReference(ContentActivity.userid + "/Annotations/");

        for (int i = 0; i < ContentActivity.annotations.size(); i++) {
            if (ContentActivity.annotations.get(i).getTitle().length() > 0) {

                String key = dbRef.push().getKey();
                DatabaseReference dbRefkeyli = ContentActivity.db.getReference(ContentActivity.userid + "/Annotations/" + key);
                dbRefkeyli.setValue(ContentActivity.annotations.get(i));

            }
            else {
                String key = dbRef.push().getKey();
                DatabaseReference dbRefkeyli = ContentActivity.db.getReference(ContentActivity.userid + "/Annotations/" + key);
                dbRefkeyli.removeValue();
            }
        }
    }
}
