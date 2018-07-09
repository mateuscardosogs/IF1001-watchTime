package if1001.watchtime.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import if1001.watchtime.Entities.Annotations;
import if1001.watchtime.Helper.CustomAdapter;
import if1001.watchtime.R;


public class ContentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextToSpeech t1;

    public static ArrayList<Annotations> annotations = new ArrayList<>();
    CustomAdapter myCustomAdapter;
    SwipeMenuListView listView;
    private List<ApplicationInfo> mAppList;
    TextView tv_without_annotation;
    private FirebaseAuth auth;
    public static boolean first=true;
    public static String userid;
    public static FirebaseUser user;
    public static FirebaseDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        db=FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();
        userid = user.getUid();

        tv_without_annotation = (TextView)findViewById(R.id.tv_without_annotation);

        mAppList = getPackageManager().getInstalledApplications(0);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(ContentActivity.this,CreateAnnotationActivity.class));
                CreateAnnotationActivity.ListSelected = false;
            }
        });
        listView = (SwipeMenuListView) findViewById(R.id.lv);
        if(!(annotations.size() > 0)) {
            listView.setVisibility(View.GONE);
            tv_without_annotation.setVisibility(View.VISIBLE);
        }
        else {
            listView.setVisibility(View.VISIBLE);
            tv_without_annotation.setVisibility(View.GONE );
        }

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                switch (menu.getViewType()) {
                    case 0:
                        createMenu1(menu);
                        break;
                }
            }
        };
        listView.setMenuCreator(creator);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    Locale locale = new Locale("pt_BR", "BR");
                    t1.setLanguage(locale);
                }
            }
        });

        myCustomAdapter= new CustomAdapter(this,R.layout.annotation_row,annotations);
        listView.setAdapter(myCustomAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                CreateAnnotationActivity.ListPosition = position;
                CreateAnnotationActivity.ListSelected = true;
                finish();
                startActivity(new Intent(ContentActivity.this, CreateAnnotationActivity.class));

            }

        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    t1.speak(annotations.get(pos).getContent().toString(),TextToSpeech.QUEUE_FLUSH,null,null);
                } else {
                    t1.speak(annotations.get(pos).getContent().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
                return true;

            }
        });

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                ApplicationInfo item = mAppList.get(position);
                switch (index) {
                    case 0:
                        CreateAnnotationActivity.ListPosition = position;
                        CreateAnnotationActivity.ListSelected = true;
                        finish();
                        startActivity(new Intent(ContentActivity.this, CreateAnnotationActivity.class));
                        break;
                    case 1:
                        Intent i = new Intent(android.content.Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Minhas Anotações");
                        i.putExtra(android.content.Intent.EXTRA_TEXT, "" + annotations.get(position).getTitle() + "\n" + annotations.get(position).getContent());
                        startActivity(Intent.createChooser(i, "Ações"));
                        break;

                    case 2:
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(ContentActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(ContentActivity.this);
                        }
                        builder.setTitle("Excluir")
                                .setMessage("Tem certeza de que deseja excluir a nota selecionada?? ")
                                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        mAppList.remove(position);
                                        annotations.remove(position);
                                        CreateAnnotationActivity object= new CreateAnnotationActivity();
                                        object.database_operations();
                                        myCustomAdapter.notifyDataSetChanged();

                                    }
                                })
                                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                        break;

                    case 3:
                        String s = annotations.get(position).getDeadline();
                        String[] split = s.split("/");
                        String day = split[0];
                        String month = split[1];
                        String year = split[2];
                        Calendar endTime = Calendar.getInstance();
                        endTime.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 17, 50);
                        long startMillis = 0;
                        long endMillis = 0;
                        endMillis = endTime.getTimeInMillis();
                        Calendar beginTime = Calendar.getInstance();
                        beginTime.set(2017, 12, 20, 6, 30);
                        startMillis = beginTime.getTimeInMillis();

                        Intent intent = new Intent(Intent.ACTION_EDIT);
                        intent.setType("vnd.android.cursor.item/event");
                        intent.putExtra("Título", annotations.get(position).getTitle());
                        intent.putExtra("Descrição", annotations.get(position).getContent());
                        intent.putExtra("Data de Criação", startMillis);
                        intent.putExtra("Deadline", endMillis);
                        startActivity(intent);


                }
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.action_sort_deadline) {
            Collections.sort(annotations, Sort_deadlinedate);

            myCustomAdapter.notifyDataSetChanged();

        }

        if (id == R.id.action_sort_priority) {
            Collections.sort(annotations, Sort_priority);

            myCustomAdapter.notifyDataSetChanged();

        }

        if (id == R.id.action_main) {
            annotations.clear();
            Intent intent = new Intent(ContentActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        if (id == R.id.action_sign_out) {

            auth.signOut();
            annotations.clear();
            Intent intent = new Intent(ContentActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void createMenu1(SwipeMenu menu) {
        SwipeMenuItem item1 = new SwipeMenuItem(
                getApplicationContext());


        item1.setBackground(new ColorDrawable(Color.parseColor("#EFEBE9")));
        item1.setWidth(dp2px(50));
        item1.setIcon(R.drawable.ic_edit);
        menu.addMenuItem(item1);
        SwipeMenuItem item2 = new SwipeMenuItem(
                getApplicationContext());


        item2.setWidth(dp2px(50));
        item2.setIcon(R.drawable.ic_share);
        menu.addMenuItem(item2);
        SwipeMenuItem item3 = new SwipeMenuItem(
                getApplicationContext());

        item3.setBackground(new ColorDrawable(Color.parseColor("#EEEEEE")));
        item3.setWidth(dp2px(50));
        item3.setIcon(R.drawable.ic_delete);
        menu.addMenuItem(item3);

        SwipeMenuItem item4 = new SwipeMenuItem(
                getApplicationContext());

        item4.setWidth(dp2px(50));
        item4.setIcon(R.drawable.ic_add);
        menu.addMenuItem(item4);

    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


    public Comparator<Annotations> Sort_deadlinedate = new Comparator<Annotations>() {

        @Override
        public int compare(Annotations o1, Annotations o2) {

            SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");

            try {
                Date d=dateFormat.parse(o1.getDeadline());
                Date d2=dateFormat.parse(o2.getDeadline());

                return d.compareTo(d2);
            }
            catch(Exception e) {

                System.out.println("Excep"+e);
                return 0;

            }
        }
    };


    public Comparator<Annotations> Sort_priority = new Comparator<Annotations>() {

        @Override
        public int compare(Annotations o1, Annotations o2) {

            Integer priorityo1=0,priorityo2=0;
            if(o1.getPriority().equalsIgnoreCase("Baixa")){
                priorityo1 = 3;
            }
            if(o1.getPriority().equalsIgnoreCase("Mediana")){
                priorityo1 = 2;
            }
            if(o1.getPriority().equalsIgnoreCase("Alta")){
                priorityo1 = 1;
            }
            if(o2.getPriority().equalsIgnoreCase("Baixa")){
                priorityo2 = 3;
            }
            if(o2.getPriority().equalsIgnoreCase("Mediana")){
                priorityo2 = 2;
            }
            if(o2.getPriority().equalsIgnoreCase("Alta")){
                priorityo2 = 1;
            }
            return priorityo1.compareTo(priorityo2);


        }
    };

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}