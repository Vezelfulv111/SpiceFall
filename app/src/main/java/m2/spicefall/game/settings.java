package m2.spicefall.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Stack;

public class settings extends AppCompatActivity implements View.OnClickListener{


    final String LOG_TAG = "myLogs";
    ListView main_loc;
    ListView СreativLoc;
    String[] LocationNames;
    String[] Dop_loc;
    String[] Checked_Names = new String[50];
    int Position;
    public Button save_main_loc; public Button load_dop_loc;
    boolean[] Checked_array;
    public TextView enterloc;
    public CheckBox Allow_edit;

    String MainLoc = "MainLoc.txt";
    File file1 = new File("/data/data/m2.spicefall.game/files/" + File.separator + MainLoc);

    String Main_checkbox = "Main_checkbox.txt";
    File file2 = new File("/data/data/m2.spicefall.game/files/" + File.separator + Main_checkbox);

    String DopLoc = "DopLoc.txt";
    File file3 = new File("/data/data/m2.spicefall.game/files/" + File.separator + DopLoc);

    String Dop_checkbox = "Dop_checkbox.txt";
    File file5 = new File("/data/data/m2.spicefall.game/files/" + File.separator + Dop_checkbox);


    String Alldop = "Alldop.txt";
    File file4 = new File("/data/data/m2.spicefall.game/files/" + File.separator + Alldop);

    int Language;
    String FileLan = "language.txt";
    File FileLanguage = new File("/data/data/m2.spicefall.game/files/" + File.separator + FileLan);
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //проверка языка
        if(FileLanguage.exists())
        { try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput((FileLan))));
            String str = "";
            while ((str = br.readLine()) != null) {
                Language =Integer.valueOf(str);
            } } catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}}


        toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //чтоб поднять вверх
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //ListView1
        LocationNames = getResources().getStringArray(R.array.LocationNames);
        Dop_loc = getResources().getStringArray(R.array.Dop_loc);

        //ЯЗЫК
        TextView Heading1; TextView Heading2;EditText Enterloc1;
        Heading1 =(TextView)findViewById(R.id.Heading1);
        Heading2 =(TextView)findViewById(R.id.Heading2);
        Enterloc1=(EditText)findViewById(R.id.enterlocText);
        Allow_edit=(CheckBox)findViewById(R.id.Allow_edit);
        if (Language==1){
            LocationNames = getResources().getStringArray(R.array.LocationNames_ENG);
            Dop_loc = getResources().getStringArray(R.array.Dop_loc_ENG);
            Enterloc1.setText("Location 1");
            Heading1.setText("Main Locations");
            Heading2.setText("Custom Locations");
            Allow_edit.setText("Use custom Locations");
        }


        main_loc = (ListView) findViewById(R.id.main_loc);// находим список
        main_loc.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item, LocationNames);
        main_loc.setAdapter(adapter); // присваиваем адаптер списку
        //ListView2
        СreativLoc = (ListView) findViewById(R.id.СreativLoc);// находим список
        СreativLoc.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.list_item, Dop_loc);
        СreativLoc.setAdapter(adapter2); // присваиваем адаптер списку

        //алгоритм на выделение ячеек - если есть файл, то выделяются выбранные ячейки
        if(file1.exists())
        {
            //первым делом выгрузили массив по которому мы будем ставить галки
            int i =0;
            try {
                // открываем поток для чтения
                BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput((Main_checkbox))));
                // BufferedReader br = new BufferedWriter(new FileWriter(FILENAME1)));
                String str = "";
                // читаем содержимое
                while ((str = br.readLine()) != null) {
                    Checked_Names[i] = str;
                    i = i + 1;
                    }
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //теперь нам нужно поставить галки по этому массиву
            {for (int i1 = 0; i1 <   main_loc.getAdapter().getCount(); i1++) {
                if (Checked_Names[i1].equals("null"))
                {main_loc.setItemChecked(i1, false);}
                else
                {main_loc.setItemChecked(i1, true);}
            }}
        }
        //если файла не существует то все галки выделены
        else
        {for (int i = 0; i <   main_loc.getAdapter().getCount(); i++) { //выбрали все локации
            main_loc.setItemChecked(i, true);
        }}
        //
        //выгрузили 2й список локаций
        if(file4.exists())
        {
            int i =0;
            try {BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput((Alldop))));
                String str = "";
                // читаем содержимое
                while ((str = br.readLine()) != null) {
                    Dop_loc[i] = str;
                    i = i + 1;}
             //Записали выгруженный список
                ArrayAdapter<String> adapterSmert = new ArrayAdapter<String>(this,
                        R.layout.list_item, Dop_loc);
                СreativLoc.setAdapter(adapterSmert);
            }
            catch (FileNotFoundException e) { e.printStackTrace(); }
            catch (IOException e) {e.printStackTrace();}
            //алгоритм на выделение доп локация- если есть файл, то выделяются выбранные ячейки
            if(file5.exists())
            { //первым делом выгрузили массив по которому мы будем ставить галки
                i =0;
                try {
                    // открываем поток для чтения
                    BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput((Dop_checkbox))));
                    // BufferedReader br = new BufferedWriter(new FileWriter(FILENAME1)));
                    String str = "";
                    while ((str = br.readLine()) != null) {
                        Checked_Names[i] = str;
                        i = i + 1; }
                }
                catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
                //теперь нам нужно поставить галки по этому массиву
                {for (int i1 = 0; i1 <   СreativLoc.getAdapter().getCount(); i1++) {
                    if (Checked_Names[i1].equals("null"))
                    {СreativLoc.setItemChecked(i1, false);}
                    else
                    {СreativLoc.setItemChecked(i1, true);}
                }}
            }
        }

        save_main_loc =(Button) findViewById(R.id.save_main_loc);
        save_main_loc.setOnClickListener(this);

        load_dop_loc =(Button)findViewById(R.id.load_dop_loc);
        load_dop_loc.setOnClickListener(this);

        enterloc = (TextView)findViewById(R.id.enterlocText);
        Allow_edit =(CheckBox)findViewById(R.id.Allow_edit);
        Allow_edit.setChecked(true);


        СreativLoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
                        + id);
                enterloc.setText(String.valueOf(Dop_loc[(int) id]));
                Position = position;
            }});

        if (Language==1)
        {   load_dop_loc.setText("Load");
            save_main_loc.setText("Save All");}
    }
                public void onCheckboxClicked(@NonNull View view) {
                    // Is the view now checked?
                    boolean checked = ((CheckBox) view).isChecked();
                    // Check which checkbox was clicked
                    switch(view.getId()) {
                        case R.id.Allow_edit:
                            if (checked)
                            {load_dop_loc.setVisibility(View.VISIBLE);
                             enterloc.setVisibility(View.VISIBLE);}
                            else
                            {load_dop_loc.setVisibility(View.INVISIBLE);
                             enterloc.setVisibility(View.INVISIBLE);}
                            break;
                    }
                }
    // создание шапки
    View createHeader(String text) {
        View view = getLayoutInflater().inflate(R.layout.header_for_listview, null);
        ((TextView)view.findViewById(R.id.textViewHeaderTitle)).setText(text);
        return view;
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_menu,menu);
        return true;}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.go_back) {
            Intent intent = new Intent (".steptwo");
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
       switch (arg0.getId()) {
           case R.id.save_main_loc:
               Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();

               MediaPlayer mediaPlayer_save = MediaPlayer.create(this, R.raw.cntrl);
               mediaPlayer_save.start();
               mediaPlayer_save.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                   @Override
                   public void onCompletion(MediaPlayer mp) {
                       mp.release();
                   }
               });
                SparseBooleanArray sbArray2 = main_loc.getCheckedItemPositions();
                    Checked_Names = null;
                    Checked_Names = new String[50];//зачем то очищаем
                Stack<String> HelpmyString = new Stack<String>();
                for (int z = 0; z < (sbArray2.size()); z++) {
                if (sbArray2.valueAt(z)) {
                    HelpmyString.add(LocationNames[sbArray2.keyAt(z)]+ " ");
                    Checked_Names[z] = (LocationNames[sbArray2.keyAt(z)]+ " ");}
                }

               //перенесли стак в массив, а массив в  строковый массив.
               Object[] src = HelpmyString.toArray();
               String[] dest = new String[src.length];
               System.arraycopy(src, 0, dest, 0, src.length);

               try {
                   // отрываем поток для записи
                   BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                           openFileOutput(MainLoc, MODE_PRIVATE)));
                   for (String value : dest) {bw.write(value + "\n");}
                   bw.close();
               } catch (FileNotFoundException e) { e.printStackTrace();
               } catch (IOException e) {e.printStackTrace();  }
               try {
                   // отрываем поток для записи
                   BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                           openFileOutput(Main_checkbox, MODE_PRIVATE)));
                  for (String value : Checked_Names) {bw.write(value + "\n");}
                   bw.close();

               } catch (FileNotFoundException e) {e.printStackTrace();}
                 catch (IOException e) {e.printStackTrace();}

               // ДОП ЛОКАЦИИ
               SparseBooleanArray sbArray4 = СreativLoc.getCheckedItemPositions();
               if  (sbArray4.size()!=0) {
                   Stack<String> HelpmySecondString = new Stack<String>();
                   //ЕЩЕ ДЕЛАЕМ
                   Checked_Names = null;
                   Checked_Names = new String[50];//зачем то очищаем
                   //
                   for (int f = 0; f < (sbArray4.size()); f++) {
                       if (sbArray4.valueAt(f)) {

                           HelpmySecondString.add(Dop_loc[sbArray4.keyAt(f)] + " ");
                           Checked_Names[sbArray4.keyAt(f)] = (Dop_loc[sbArray4.keyAt(f)] + " ");
                       }

                   }
                   //перенесли стак в массив, а массив в  строковый массив.
                   Object[] src2 = HelpmySecondString.toArray();
                   String[] dest2 = new String[src2.length];
                   System.arraycopy(src2, 0, dest2, 0, src2.length);

                   //Cохранили играемые локации
                   try {
                       // отрываем поток для записи
                       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                               openFileOutput(DopLoc, MODE_PRIVATE)));
                       for (String value : dest2) { bw.write(value + "\n");}
                       bw.close();
                   } catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e)
                   {e.printStackTrace();}



               }

               //НОВЫЙ КОД
               //Cохранили чекбоксы
               Checked_Names = null;
               Checked_Names = new String[50];//зачем то очищаем
               Stack<String> HelpmyString2 = new Stack<String>();
               for (int z = 0; z < (sbArray4.size()); z++) {
                   if (sbArray4.valueAt(z)) {
                       HelpmyString2.add(Dop_loc[sbArray4.keyAt(z)]+ " ");
                       Checked_Names[z] = (Dop_loc[sbArray4.keyAt(z)]+ " ");}
               }
               try {
                   // отрываем поток для записи
                   BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                           openFileOutput(Dop_checkbox, MODE_PRIVATE)));
                   for (String value : Checked_Names) { bw.write(value + "\n");}
                   bw.close();
               } catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e)
               {e.printStackTrace();}


         break;
           case R.id.load_dop_loc:
               MediaPlayer mediaPlayer_enter = MediaPlayer.create(this, R.raw.backspace);
               mediaPlayer_enter.start();
               mediaPlayer_enter.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                   @Override
                   public void onCompletion(MediaPlayer mp) {mp.release();}
               });
               String str = enterloc.getText().toString();
               Dop_loc[Position] = str;
               //переписали ArrayAdapter
               ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.list_item, Dop_loc);
               СreativLoc.setAdapter(adapter2);


               //cразу же перезаписали файл с локациями
               try {
                   BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(Alldop, MODE_PRIVATE)));
                   for (String value : Dop_loc) {
                       bw.write(value + "\n");
                   }
                   bw.close();
               }
               catch (FileNotFoundException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }


               //обновили галки
               SparseBooleanArray sbArray5 = СreativLoc.getCheckedItemPositions();
               for (int f = 0; f < (sbArray5.size()); f++) {
                   if (sbArray5.valueAt(f)) {
                       Checked_Names[sbArray5.keyAt(f)] = (Dop_loc[sbArray5.keyAt(f)] + " ");
                   }
               }
               for (int i1 = 0; i1 <   СreativLoc.getAdapter().getCount(); i1++) {
                   if (Checked_Names[i1].equals("null"))
                   {СreativLoc.setItemChecked(i1, false);}
                   else
                   {СreativLoc.setItemChecked(i1, true);}


               break;
    }
    }
}}


