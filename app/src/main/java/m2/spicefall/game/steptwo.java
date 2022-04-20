package m2.spicefall.game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;


public class steptwo extends AppCompatActivity implements View.OnClickListener {
    //для игрков файл
    String FILENAME1 = "file.txt";
    File file = new File("/data/data/m2.spicefall.game/files/" + File.separator + FILENAME1);

    //для основных локаций файл
    String MainLoc = "MainLoc.txt";
    File file_mainLoc = new File("/data/data/m2.spicefall.game/files/" + File.separator + MainLoc);

    //Для доп локаций файл
    String DopLoc = "DopLoc.txt";
    File file_DopLoc = new File("/data/data/m2.spicefall.game/files/" + File.separator + DopLoc);

    final String LOG_TAG = "myLogs";
    ListView lvMain;
    public Button Bigbutton;
    public Button EnterName;
    public Button left_1 ;public Button left_2; public Button right_1;public Button right_2;
    public TextView TextPlayer; public TextView TextSpy;
    private Toolbar toolbar;
    private Toolbar toolbar_list;
    public EditText EnterToTable;
    int N1;    int N2; int Rand_loc[];
    int for_Rand_loc=0;

    String[] Player_names;
    String[] Player_playing= new String[20];
    String[] LocationNames;
    String[] Dop_LocationNames;
    private CheckBox сheckDopLoc;

    int Position; int Language;

    String FileLan = "language.txt";
    File FileLanguage = new File("/data/data/m2.spicefall.game/files/" + File.separator + FileLan);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //проверка языка
        if(FileLanguage.exists())
        { try {
                // открываем поток для чтения
                BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput((FileLan))));
                String str = "";
                while ((str = br.readLine()) != null) {
                    Language =Integer.valueOf(str);
                } } catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}}


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steptwo);
        toolbar =(Toolbar)findViewById(R.id.toolbar_main);setSupportActionBar(toolbar);
        toolbar_list =(Toolbar)findViewById(R.id.toolbarnames);
        if (Language==1){toolbar_list.setTitle("Players");}
        toolbar_list.inflateMenu(R.menu.menu_list);//changed
        toolbar_list.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.s2ndSave:
                        try {
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(FILENAME1, MODE_PRIVATE)));
                            for (String value : Player_names) {
                                bw.write(value + "\n");}
                            bw.close();
                            Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
                        }
                        catch (FileNotFoundException e) {e.printStackTrace();}
                        catch (IOException e) { e.printStackTrace();}
                        break;}
                return false;
            }
        });




        Player_names = getResources().getStringArray(R.array.Player_names);
        if (Language==1){
            Player_names = getResources().getStringArray(R.array.Player_names_ENG);
        }

        //ListView
        lvMain = (ListView) findViewById(R.id.lvMain);// находим список
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, Player_names);
        lvMain.setAdapter(adapter); // присваиваем адаптер списку

        Bigbutton =(Button)findViewById(R.id.Bigbutton);
        EnterName =(Button)findViewById(R.id.EnterName);

        сheckDopLoc=(CheckBox)findViewById(R.id.сheckDopLoc);
        TextPlayer = (TextView) findViewById(R.id.TextPlayer);
        TextSpy = (TextView)findViewById(R.id.TextSpy);

        Bigbutton.setOnClickListener(this);EnterName.setOnClickListener(this);

        left_1 = (Button) findViewById(R.id.left_1);left_2 =(Button)findViewById(R.id.left_2);
        right_1 =(Button)findViewById(R.id.right_1);right_2 = (Button)findViewById(R.id.right_2);
        left_1.setOnClickListener(this);left_2.setOnClickListener(this);
        right_1.setOnClickListener(this);right_2.setOnClickListener(this);


        //работа с таблицей
        EnterToTable =(EditText)findViewById(R.id.Enter_name);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
                        + id);
                EnterToTable.setText(String.valueOf(Player_names[(int) id]));
                Position = position;
            }});

        //работа с файлом
        if(file.exists())
        {int i = 0;
            try {
                // открываем поток для чтения
                BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput((FILENAME1))));
                // BufferedReader br = new BufferedWriter(new FileWriter(FILENAME1)));
                String str = "";
                // читаем содержимое
                while ((str = br.readLine()) != null) {
                    Log.d(LOG_TAG, str);
                    Player_names[i] = str;
                    i = i + 1;
                    //ListView
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                            R.layout.list_item, Player_names);
                    lvMain.setAdapter(adapter2); // присваиваем адаптер списку
                }
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        TextView textVPlayer;TextView textVspy;
        textVPlayer=findViewById(R.id.textVPlayer);
        textVspy=findViewById(R.id.textVspy);



        if (Language==1)
        {

            textVPlayer.setText("Number of players");
            textVspy.setText("Number of spy");
            EnterName.setText("Enter name");
            Bigbutton.setText("Next Screen");
            сheckDopLoc.setText("Use custom locations");
        }



    }
    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_info_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.s2ndSave) {

            try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(FILENAME1, MODE_PRIVATE)));
                    for (String value : Player_names) {
                    bw.write(value + "\n");
                }
                bw.close();

                }
            catch (FileNotFoundException e) {
              e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if(item.getItemId() == R.id.go_back) {
            Intent intent = new Intent (".settings");
            startActivity(intent);
            MediaPlayer mediaPlayer;
            mediaPlayer = MediaPlayer.create(this, R.raw.grab_cards);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }

        return true;
    }
    @Override
    public void onClick(View arg0) {
        //звук
        MediaPlayer mediaPlayer_schelchok;
        MediaPlayer mediaPlayer_enter;
        mediaPlayer_schelchok = MediaPlayer.create(this, R.raw.schelchok);
        mediaPlayer_enter = MediaPlayer.create(this, R.raw.backspace);

        switch (arg0.getId()) {
            case R.id.EnterName:
                mediaPlayer_enter.start();
                mediaPlayer_enter.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                String str = EnterToTable.getText().toString();
                Player_names[Position] = str;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                            R.layout.list_item, Player_names);
                    lvMain.setAdapter(adapter); // присваиваем адаптер списку
                    //ListView
                 break;
            case R.id.Bigbutton:
                MediaPlayer mediaPlayer;
                mediaPlayer = MediaPlayer.create(this, R.raw.grab_cards);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                //Злая магия //Если изменено кол во основных локаций
                if (file_mainLoc.exists()) {
                    int i = 0;
                    Stack<String> HelpmyString = new Stack<String>();
                    try {
                        // открываем поток для чтения
                        BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput((MainLoc))));
                        String str2 = "";
                        // читаем содержимое
                        while ((str2 = br.readLine()) != null) {
                            Log.d(LOG_TAG, str2);
                            HelpmyString.add(str2);
                            i = i + 1;
                        }
                        Object[] src = HelpmyString.toArray();
                        LocationNames = new String[src.length];
                        System.arraycopy(src, 0, LocationNames, 0, src.length);
                    }
                    catch (FileNotFoundException e) {e.printStackTrace();}
                    catch (IOException e) {e.printStackTrace();}
                }
                else
                {LocationNames = getResources().getStringArray(R.array.LocationNames);
                if (Language==1){LocationNames = getResources().getStringArray(R.array.LocationNames_ENG);}}


                    //проверка нажат ли флажок
                if (сheckDopLoc.isChecked()) {
                    //Проверка на файл с доп локациями
                    if (file_DopLoc.exists()) {
                        int i = 0;
                        Stack<String> HelpmyString = new Stack<String>();
                        try {
                            // открываем поток для чтения
                            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput((DopLoc))));
                            // BufferedReader br = new BufferedWriter(new FileWriter(FILENAME1)));
                            String str2 = "";
                            // читаем содержимое
                            while ((str2 = br.readLine()) != null) {
                                Log.d(LOG_TAG, str2);
                                HelpmyString.add(str2);
                                i = i + 1;
                            }
                            Object[] src = HelpmyString.toArray();
                            Dop_LocationNames = new String[src.length];
                            System.arraycopy(src, 0, Dop_LocationNames, 0, src.length);
                            // Initialize an empty list
                            List<String> both = new ArrayList<>();
                            // Add first array elements to list
                            Collections.addAll(both, LocationNames);
                            // Add another array elements to list
                            Collections.addAll(both, Dop_LocationNames);
                            // Convert list to array
                            LocationNames = both.toArray(new String[both.size()]);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Rand_loc = new int[LocationNames.length]; // Делаем массив с номерами элементов локаций
                for (int i = 0; i<LocationNames.length; i++) {
                    Rand_loc[i]=i; }
                Random rnd = new Random();
                for(int i = 0; i < Rand_loc.length; i++) {
                    int index = rnd.nextInt(i + 1);
                    int a = Rand_loc[index];
                    Rand_loc[index] = Rand_loc[i];
                    Rand_loc[i] = a;
                }
                //Злая магия
                N1 = Integer.parseInt(TextPlayer.getText().toString());
                N2 = Integer.parseInt(TextSpy.getText().toString());
                SparseBooleanArray sbArray2 = lvMain.getCheckedItemPositions();
                for (int i = 0; i < sbArray2.size(); i++) {
                    if (sbArray2.valueAt(i)) {
                       Player_playing[i]= (Player_names[sbArray2.keyAt(i)]+ " "); }}

                Intent intent = new Intent (".stepthree");
                intent.putExtra("Player_playing", Player_playing);
                intent.putExtra("Rand_loc", Rand_loc);
                intent.putExtra("for_Rand_loc", for_Rand_loc);
                intent.putExtra("LocationNames", LocationNames);
                intent.putExtra("Vse", N1);
                intent.putExtra("SPY", N2);
                intent.putExtra("Language", Language);
                startActivity(intent);
                break;

           case R.id.left_1:

               mediaPlayer_schelchok.start();
               mediaPlayer_schelchok.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                   @Override
                   public void onCompletion(MediaPlayer mp) {
                       mp.release();
                    }
                 });

                N1 = Integer.parseInt(TextPlayer.getText().toString());
                N1=N1-1;
                if (N1<3){N1=3;}
                String t_1 = Integer.toString(N1);
                TextPlayer.setText(t_1);
                break;

           case R.id.right_1:

               mediaPlayer_schelchok.start();
               mediaPlayer_schelchok.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                   @Override
                   public void onCompletion(MediaPlayer mp) {
                       mp.release();
                   }
               });
               N1 = Integer.parseInt(TextPlayer.getText().toString());
               N1=N1+1;
               if (N1>12){N1=12;}
               String t_2 = Integer.toString(N1);
               TextPlayer.setText(t_2);
               break;

            case R.id.left_2:
                mediaPlayer_schelchok.start();
                mediaPlayer_schelchok.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {mp.release();}});

                N2 = Integer.parseInt(TextSpy.getText().toString());
                N2=N2-1;
                if (N2<1){N2=3;}
                String t_3 = Integer.toString(N2);
                TextSpy.setText(t_3);
                break;

            case R.id.right_2:
                mediaPlayer_schelchok.start();
                mediaPlayer_schelchok.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });

                N2 = Integer.parseInt(TextSpy.getText().toString());
                N2=N2+1;
                if (N2>3){N2=1;}
                String t_4 = Integer.toString(N2);
                TextSpy.setText(t_4);
                break;
        }
    }
}