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
    ListView –°reativLoc;
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

        //–Ņ—Ä–ĺ–≤–Ķ—Ä–ļ–į —Ź–∑—č–ļ–į
        if(FileLanguage.exists())
        { try {
            // –ĺ—ā–ļ—Ä—č–≤–į–Ķ–ľ –Ņ–ĺ—ā–ĺ–ļ –ī–Ľ—Ź —á—ā–Ķ–Ĺ–ł—Ź
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput((FileLan))));
            String str = "";
            while ((str = br.readLine()) != null) {
                Language =Integer.valueOf(str);
            } } catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}}


        toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //—á—ā–ĺ–Ī –Ņ–ĺ–ī–Ĺ—Ź—ā—Ć –≤–≤–Ķ—Ä—Ö
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //ListView1
        LocationNames = getResources().getStringArray(R.array.LocationNames);
        Dop_loc = getResources().getStringArray(R.array.Dop_loc);

        //–Į–ó–ę–ö
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


        main_loc = (ListView) findViewById(R.id.main_loc);// –Ĺ–į—Ö–ĺ–ī–ł–ľ —Ā–Ņ–ł—Ā–ĺ–ļ
        main_loc.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item, LocationNames);
        main_loc.setAdapter(adapter); // –Ņ—Ä–ł—Ā–≤–į–ł–≤–į–Ķ–ľ –į–ī–į–Ņ—ā–Ķ—Ä —Ā–Ņ–ł—Ā–ļ—É
        //ListView2
        –°reativLoc = (ListView) findViewById(R.id.–°reativLoc);// –Ĺ–į—Ö–ĺ–ī–ł–ľ —Ā–Ņ–ł—Ā–ĺ–ļ
        –°reativLoc.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.list_item, Dop_loc);
        –°reativLoc.setAdapter(adapter2); // –Ņ—Ä–ł—Ā–≤–į–ł–≤–į–Ķ–ľ –į–ī–į–Ņ—ā–Ķ—Ä —Ā–Ņ–ł—Ā–ļ—É

        //–į–Ľ–≥–ĺ—Ä–ł—ā–ľ –Ĺ–į –≤—č–ī–Ķ–Ľ–Ķ–Ĺ–ł–Ķ —Ź—á–Ķ–Ķ–ļ - –Ķ—Ā–Ľ–ł –Ķ—Ā—ā—Ć —Ą–į–Ļ–Ľ, —ā–ĺ –≤—č–ī–Ķ–Ľ—Ź—é—ā—Ā—Ź –≤—č–Ī—Ä–į–Ĺ–Ĺ—č–Ķ —Ź—á–Ķ–Ļ–ļ–ł
        if(file1.exists())
        {
            //–Ņ–Ķ—Ä–≤—č–ľ –ī–Ķ–Ľ–ĺ–ľ –≤—č–≥—Ä—É–∑–ł–Ľ–ł –ľ–į—Ā—Ā–ł–≤ –Ņ–ĺ –ļ–ĺ—ā–ĺ—Ä–ĺ–ľ—É –ľ—č –Ī—É–ī–Ķ–ľ —Ā—ā–į–≤–ł—ā—Ć –≥–į–Ľ–ļ–ł
            int i =0;
            try {
                // –ĺ—ā–ļ—Ä—č–≤–į–Ķ–ľ –Ņ–ĺ—ā–ĺ–ļ –ī–Ľ—Ź —á—ā–Ķ–Ĺ–ł—Ź
                BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput((Main_checkbox))));
                // BufferedReader br = new BufferedWriter(new FileWriter(FILENAME1)));
                String str = "";
                // —á–ł—ā–į–Ķ–ľ —Ā–ĺ–ī–Ķ—Ä–∂–ł–ľ–ĺ–Ķ
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

            //—ā–Ķ–Ņ–Ķ—Ä—Ć –Ĺ–į–ľ –Ĺ—É–∂–Ĺ–ĺ –Ņ–ĺ—Ā—ā–į–≤–ł—ā—Ć –≥–į–Ľ–ļ–ł –Ņ–ĺ —ć—ā–ĺ–ľ—É –ľ–į—Ā—Ā–ł–≤—É
            {for (int i1 = 0; i1 <   main_loc.getAdapter().getCount(); i1++) {
                if (Checked_Names[i1].equals("null"))
                {main_loc.setItemChecked(i1, false);}
                else
                {main_loc.setItemChecked(i1, true);}
            }}
        }
        //–Ķ—Ā–Ľ–ł —Ą–į–Ļ–Ľ–į –Ĺ–Ķ —Ā—É—Č–Ķ—Ā—ā–≤—É–Ķ—ā —ā–ĺ –≤—Ā–Ķ –≥–į–Ľ–ļ–ł –≤—č–ī–Ķ–Ľ–Ķ–Ĺ—č
        else
        {for (int i = 0; i <   main_loc.getAdapter().getCount(); i++) { //–≤—č–Ī—Ä–į–Ľ–ł –≤—Ā–Ķ –Ľ–ĺ–ļ–į—Ü–ł–ł
            main_loc.setItemChecked(i, true);
        }}
        //
        //–≤—č–≥—Ä—É–∑–ł–Ľ–ł 2–Ļ —Ā–Ņ–ł—Ā–ĺ–ļ –Ľ–ĺ–ļ–į—Ü–ł–Ļ
        if(file4.exists())
        {
            int i =0;
            try {BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput((Alldop))));
                String str = "";
                // —á–ł—ā–į–Ķ–ľ —Ā–ĺ–ī–Ķ—Ä–∂–ł–ľ–ĺ–Ķ
                while ((str = br.readLine()) != null) {
                    Dop_loc[i] = str;
                    i = i + 1;}
             //–ó–į–Ņ–ł—Ā–į–Ľ–ł –≤—č–≥—Ä—É–∂–Ķ–Ĺ–Ĺ—č–Ļ —Ā–Ņ–ł—Ā–ĺ–ļ
                ArrayAdapter<String> adapterSmert = new ArrayAdapter<String>(this,
                        R.layout.list_item, Dop_loc);
                –°reativLoc.setAdapter(adapterSmert);
            }
            catch (FileNotFoundException e) { e.printStackTrace(); }
            catch (IOException e) {e.printStackTrace();}
            //–į–Ľ–≥–ĺ—Ä–ł—ā–ľ –Ĺ–į –≤—č–ī–Ķ–Ľ–Ķ–Ĺ–ł–Ķ –ī–ĺ–Ņ –Ľ–ĺ–ļ–į—Ü–ł—Ź- –Ķ—Ā–Ľ–ł –Ķ—Ā—ā—Ć —Ą–į–Ļ–Ľ, —ā–ĺ –≤—č–ī–Ķ–Ľ—Ź—é—ā—Ā—Ź –≤—č–Ī—Ä–į–Ĺ–Ĺ—č–Ķ —Ź—á–Ķ–Ļ–ļ–ł
            if(file5.exists())
            { //–Ņ–Ķ—Ä–≤—č–ľ –ī–Ķ–Ľ–ĺ–ľ –≤—č–≥—Ä—É–∑–ł–Ľ–ł –ľ–į—Ā—Ā–ł–≤ –Ņ–ĺ –ļ–ĺ—ā–ĺ—Ä–ĺ–ľ—É –ľ—č –Ī—É–ī–Ķ–ľ —Ā—ā–į–≤–ł—ā—Ć –≥–į–Ľ–ļ–ł
                i =0;
                try {
                    // –ĺ—ā–ļ—Ä—č–≤–į–Ķ–ľ –Ņ–ĺ—ā–ĺ–ļ –ī–Ľ—Ź —á—ā–Ķ–Ĺ–ł—Ź
                    BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput((Dop_checkbox))));
                    // BufferedReader br = new BufferedWriter(new FileWriter(FILENAME1)));
                    String str = "";
                    while ((str = br.readLine()) != null) {
                        Checked_Names[i] = str;
                        i = i + 1; }
                }
                catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
                //—ā–Ķ–Ņ–Ķ—Ä—Ć –Ĺ–į–ľ –Ĺ—É–∂–Ĺ–ĺ –Ņ–ĺ—Ā—ā–į–≤–ł—ā—Ć –≥–į–Ľ–ļ–ł –Ņ–ĺ —ć—ā–ĺ–ľ—É –ľ–į—Ā—Ā–ł–≤—É
                {for (int i1 = 0; i1 <   –°reativLoc.getAdapter().getCount(); i1++) {
                    if (Checked_Names[i1].equals("null"))
                    {–°reativLoc.setItemChecked(i1, false);}
                    else
                    {–°reativLoc.setItemChecked(i1, true);}
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


        –°reativLoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
    // —Ā–ĺ–∑–ī–į–Ĺ–ł–Ķ —ą–į–Ņ–ļ–ł
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
                    Checked_Names = new String[50];//–∑–į—á–Ķ–ľ —ā–ĺ –ĺ—á–ł—Č–į–Ķ–ľ
                Stack<String> HelpmyString = new Stack<String>();
                for (int z = 0; z < (sbArray2.size()); z++) {
                if (sbArray2.valueAt(z)) {
                    HelpmyString.add(LocationNames[sbArray2.keyAt(z)]+ " ");
                    Checked_Names[z] = (LocationNames[sbArray2.keyAt(z)]+ " ");}
                }

               //–Ņ–Ķ—Ä–Ķ–Ĺ–Ķ—Ā–Ľ–ł —Ā—ā–į–ļ –≤ –ľ–į—Ā—Ā–ł–≤, –į –ľ–į—Ā—Ā–ł–≤ –≤  —Ā—ā—Ä–ĺ–ļ–ĺ–≤—č–Ļ –ľ–į—Ā—Ā–ł–≤.
               Object[] src = HelpmyString.toArray();
               String[] dest = new String[src.length];
               System.arraycopy(src, 0, dest, 0, src.length);

               try {
                   // –ĺ—ā—Ä—č–≤–į–Ķ–ľ –Ņ–ĺ—ā–ĺ–ļ –ī–Ľ—Ź –∑–į–Ņ–ł—Ā–ł
                   BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                           openFileOutput(MainLoc, MODE_PRIVATE)));
                   for (String value : dest) {bw.write(value + "\n");}
                   bw.close();
               } catch (FileNotFoundException e) { e.printStackTrace();
               } catch (IOException e) {e.printStackTrace();  }
               try {
                   // –ĺ—ā—Ä—č–≤–į–Ķ–ľ –Ņ–ĺ—ā–ĺ–ļ –ī–Ľ—Ź –∑–į–Ņ–ł—Ā–ł
                   BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                           openFileOutput(Main_checkbox, MODE_PRIVATE)));
                  for (String value : Checked_Names) {bw.write(value + "\n");}
                   bw.close();

               } catch (FileNotFoundException e) {e.printStackTrace();}
                 catch (IOException e) {e.printStackTrace();}

               // –Ē–ě–ü –õ–ě–ö–ź–¶–ė–ė
               SparseBooleanArray sbArray4 = –°reativLoc.getCheckedItemPositions();
               if  (sbArray4.size()!=0) {
                   Stack<String> HelpmySecondString = new Stack<String>();
                   //–ē–©–ē –Ē–ē–õ–ź–ē–ú
                   Checked_Names = null;
                   Checked_Names = new String[50];//–∑–į—á–Ķ–ľ —ā–ĺ –ĺ—á–ł—Č–į–Ķ–ľ
                   //
                   for (int f = 0; f < (sbArray4.size()); f++) {
                       if (sbArray4.valueAt(f)) {

                           HelpmySecondString.add(Dop_loc[sbArray4.keyAt(f)] + " ");
                           Checked_Names[sbArray4.keyAt(f)] = (Dop_loc[sbArray4.keyAt(f)] + " ");
                       }

                   }
                   //–Ņ–Ķ—Ä–Ķ–Ĺ–Ķ—Ā–Ľ–ł —Ā—ā–į–ļ –≤ –ľ–į—Ā—Ā–ł–≤, –į –ľ–į—Ā—Ā–ł–≤ –≤  —Ā—ā—Ä–ĺ–ļ–ĺ–≤—č–Ļ –ľ–į—Ā—Ā–ł–≤.
                   Object[] src2 = HelpmySecondString.toArray();
                   String[] dest2 = new String[src2.length];
                   System.arraycopy(src2, 0, dest2, 0, src2.length);

                   //C–ĺ—Ö—Ä–į–Ĺ–ł–Ľ–ł –ł–≥—Ä–į–Ķ–ľ—č–Ķ –Ľ–ĺ–ļ–į—Ü–ł–ł
                   try {
                       // –ĺ—ā—Ä—č–≤–į–Ķ–ľ –Ņ–ĺ—ā–ĺ–ļ –ī–Ľ—Ź –∑–į–Ņ–ł—Ā–ł
                       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                               openFileOutput(DopLoc, MODE_PRIVATE)));
                       for (String value : dest2) { bw.write(value + "\n");}
                       bw.close();
                   } catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e)
                   {e.printStackTrace();}



               }

               //–Ě–ě–í–ę–ô –ö–ě–Ē
               //C–ĺ—Ö—Ä–į–Ĺ–ł–Ľ–ł —á–Ķ–ļ–Ī–ĺ–ļ—Ā—č
               Checked_Names = null;
               Checked_Names = new String[50];//–∑–į—á–Ķ–ľ —ā–ĺ –ĺ—á–ł—Č–į–Ķ–ľ
               Stack<String> HelpmyString2 = new Stack<String>();
               for (int z = 0; z < (sbArray4.size()); z++) {
                   if (sbArray4.valueAt(z)) {
                       HelpmyString2.add(Dop_loc[sbArray4.keyAt(z)]+ " ");
                       Checked_Names[z] = (Dop_loc[sbArray4.keyAt(z)]+ " ");}
               }
               try {
                   // –ĺ—ā—Ä—č–≤–į–Ķ–ľ –Ņ–ĺ—ā–ĺ–ļ –ī–Ľ—Ź –∑–į–Ņ–ł—Ā–ł
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
               //–Ņ–Ķ—Ä–Ķ–Ņ–ł—Ā–į–Ľ–ł ArrayAdapter
               ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.list_item, Dop_loc);
               –°reativLoc.setAdapter(adapter2);


               //c—Ä–į–∑—É –∂–Ķ –Ņ–Ķ—Ä–Ķ–∑–į–Ņ–ł—Ā–į–Ľ–ł —Ą–į–Ļ–Ľ —Ā –Ľ–ĺ–ļ–į—Ü–ł—Ź–ľ–ł
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


               //–ĺ–Ī–Ĺ–ĺ–≤–ł–Ľ–ł –≥–į–Ľ–ļ–ł
               SparseBooleanArray sbArray5 = –°reativLoc.getCheckedItemPositions();
               for (int f = 0; f < (sbArray5.size()); f++) {
                   if (sbArray5.valueAt(f)) {
                       Checked_Names[sbArray5.keyAt(f)] = (Dop_loc[sbArray5.keyAt(f)] + " ");
                   }
               }
               for (int i1 = 0; i1 <   –°reativLoc.getAdapter().getCount(); i1++) {
                   if (Checked_Names[i1].equals("null"))
                   {–°reativLoc.setItemChecked(i1, false);}
                   else
                   {–°reativLoc.setItemChecked(i1, true);}


               break;
    }
    }
}}


