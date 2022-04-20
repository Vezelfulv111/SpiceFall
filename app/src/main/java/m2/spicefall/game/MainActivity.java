package m2.spicefall.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class    MainActivity extends AppCompatActivity  {
    private Button imp;
    private Button Rules;
    Spinner Spinner;
    spinadapter adapter;
    int[] images = {R.drawable.ru,R.drawable.uk};
    String[] ButText = {"Начать игру","Start Game"};
    String[] RulesButText = {"Правила","Rules"};

    int Current_language = 0;

    //для типа локалицации файл
    String FileLan = "language.txt";
    File FileLan1 = new File("/data/data/m2.spicefall.game/files/" + File.separator + FileLan);


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
        Spinner = findViewById(R.id.spinner_l);
        imp = findViewById(R.id.imp);
        Rules = findViewById(R.id.rules);


        Resources res = getResources();
        String[] Languages = res.getStringArray(R.array.Language);



        adapter = new spinadapter (this,Languages,images);
        Spinner.setAdapter(adapter);

        Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                imp.setText(ButText[i]);
                Rules.setText(RulesButText[i]);
                Current_language=i;
                try {
                   // отрываем поток для записи
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(FileLan, MODE_PRIVATE)));
                    bw.write(Current_language + "\n");
                    bw.close();
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    public void addListenerOnButton () {
        imp = findViewById(R.id.imp);
        Rules = findViewById(R.id.rules);
        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(this, R.raw.grab_cards);



        imp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // imp.setBackgroundColor(Color.RED);
                        Intent intent = new Intent (".steptwo");
                        startActivity(intent);

                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();
                            }
                        });

                    }
                });


        Rules.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // imp.setBackgroundColor(Color.RED);
                        Intent intent2 = new Intent (".Rules");
                        startActivity(intent2);

                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();
                            }
                        });

                    }
                });






    }
}