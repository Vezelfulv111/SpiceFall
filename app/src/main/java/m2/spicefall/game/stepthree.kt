package m2.spicefall.game

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.content.Intent
import android.media.MediaPlayer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.animation.doOnEnd
import org.intellij.lang.annotations.Language
import java.io.*
import java.util.*

class stepthree : AppCompatActivity() {
    private lateinit var last: Button
    private lateinit var first: Button


    private lateinit var Next_Game: Button
    private lateinit var textNum: TextView
    private lateinit var Name_of_player: TextView
    private lateinit var justtext: TextView

    var Condition = 1
    var Loc_number = 0
    var Loc_choise = 0
    var N1 = 0
    var N2 = 0
    var LocationNames: Array<String>? = null
    var Player_playing: Array<String>? = null
    var Rand_loc: IntArray? = null
    var for_Rand_loc = 0
    lateinit var nums: IntArray
    var MoreRandfor1Spy = 0
    private var toolbar: Toolbar? = null

    var FileLan = "language.txt"
    var FileLanguage = File("/data/data/m2.spicefall.game/files/" + File.separator + FileLan)
    var Language = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stepthree)

        //проверка языка

        //проверка языка
        if (FileLanguage.exists()) {
            try {
                // открываем поток для чтения
                val br = BufferedReader(InputStreamReader(openFileInput(FileLan)))
                var str: String? = ""
                while (br.readLine().also { str = it } != null) {
                    Language = Integer.valueOf(str)
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }


        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val arguments = intent.extras
        justtext=findViewById<View>(R.id.justtext) as TextView
        last = findViewById<View>(R.id.last) as Button
        LocationNames = arguments!!.getStringArray("LocationNames")


        Player_playing = arguments.getStringArray("Player_playing")
        Rand_loc = arguments.getIntArray("Rand_loc")
        for_Rand_loc = arguments.getInt("for_Rand_loc")
        N1 = arguments.getInt("Vse") //EE, передали всех людей
        N2 = arguments.getInt("SPY") //EE, передали шпионов
        MoreRandfor1Spy = arguments.getInt("MoreRandfor1Spy") //крутим рандом если 1н шпион
        Name_of_player = findViewById<View>(R.id.Name_of_player) as TextView
        Name_of_player!!.text = Player_playing!![0]
        Next_Game = findViewById<View>(R.id.Next_Game) as Button
        //ЯЗЫК
        if (Language==1){
            last.text = "Where am I?"
            justtext.text ="Player number"
            Next_Game.text ="New game" }

        val random = Random()
        nums = IntArray(N2)
        for (i in 0 until N2) {
            nums[i] = random.nextInt(N1) + 1
        }
        for (k in 0..9) {
            if (N2 == 2) {
                if (nums[0] == nums[1]) {
                    nums[1] = random.nextInt(N1) + 1
                }
            } else if (N2 == 3) {
                if (nums[0] == nums[1]) {
                    nums[1] = random.nextInt(N1) + 1
                } else if (nums[1] == nums[2]) {
                    nums[2] = random.nextInt(N1) + 1
                } else if (nums[0] == nums[2]) {
                    nums[2] = random.nextInt(N1) + 1
                } else {
                    break
                }
            } else {
                break
            }
        }
        if (N2 == 1) //крутим рандом если 1н шпион
        {
            if (MoreRandfor1Spy == nums[0]) {
                nums[0] = random.nextInt(N1) + 1
            }
        }
        Loc_number = LocationNames!!.size //записали колво элтов
        Loc_choise = random.nextInt(Loc_number) //выбрали 1н случайный
        addListenerOnButton()
    }

    //menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.back_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.go_back) {
            val intent = Intent(".steptwo")
            intent.putExtra("Language",  Language)
            startActivity(intent)
            val mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.shufflesound)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener { mp -> mp.release() }
        }
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun addListenerOnButton() {
        last = findViewById<View>(R.id.last) as Button
        first= findViewById<View>(R.id.first) as Button


        Next_Game = findViewById<View>(R.id.Next_Game) as Button
        textNum = findViewById<View>(R.id.textNum) as TextView
        Name_of_player = findViewById<View>(R.id.Name_of_player) as TextView


        Next_Game.setOnClickListener {
            val mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.shufflesound)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener { mp -> mp.release() }

            for_Rand_loc += 1
            val intent2 = Intent(".stepthree")
            intent2.putExtra("Player_playing", Player_playing)
            intent2.putExtra("Rand_loc", Rand_loc)
            intent2.putExtra("for_Rand_loc", for_Rand_loc)
            intent2.putExtra("LocationNames", LocationNames)
            intent2.putExtra("Massive", nums)
            intent2.putExtra("Vse", N1)
            intent2.putExtra("SPY", N2)
            intent2.putExtra("Language",  Language)



            if (N2 == 1) //крутим рандом если 1н шпион
            {
                MoreRandfor1Spy = nums[0]
                intent2.putExtra("MoreRandfor1Spy", MoreRandfor1Spy)
            }
            startActivity(intent2)
        }
        var PlayerNum=1;//cчетчик игроков
        var FlipLOck=1;//cчетчик игроков
        var FlipLOck_front=1;//cчетчик игроков
        var FlipLOck_back=0;//cчетчик игроков

        var front_animation = AnimatorInflater.loadAnimator(applicationContext, R.animator.front_animator) as AnimatorSet
        var back_animation =  AnimatorInflater.loadAnimator(applicationContext, R.animator.back_animator) as AnimatorSet
        var front_animation2 = AnimatorInflater.loadAnimator(applicationContext, R.animator.front_animator) as AnimatorSet
        val animE_frominvise = AnimationUtils.loadAnimation(this, R.anim.frominvise)


        last.setOnClickListener{

            if (FlipLOck_front==1 && FlipLOck==1){


                val mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.card_sound)
                mediaPlayer.start()
                mediaPlayer.setOnCompletionListener { mp -> mp.release() }

                front_animation.setTarget(last);
                back_animation.setTarget( first);

                front_animation.start()
                back_animation.start()


                front_animation.doOnEnd {
                    last.visibility = View.INVISIBLE
                    FlipLOck_back=1;
                }

                for (i in 0 until N2) {
                 if (PlayerNum == nums[i]) {
                     first.textSize = 30f
                     first.text = "ВЫ ШПИОН!"
                     if (Language==1){ first.text = "You are spy!"}
                     first.setBackgroundResource(R.drawable.button_3)

                Condition = 0
                break}
                else {
                     first.textSize = 22f
                     first.text = "ЛОКАЦИЯ: ${'\n'} ${LocationNames!![Rand_loc!![for_Rand_loc]]}".trimIndent()
                     if (Language==1)
                     { first.text = "Location: ${'\n'} ${LocationNames!![Rand_loc!![for_Rand_loc]]}".trimIndent()}
                     first.setBackgroundResource(R.drawable.button_1)

                }
                    first.setBackgroundResource(R.drawable.button_1)

                    }
                FlipLOck_front=0;


            }}


        first.setOnClickListener{
            if (FlipLOck_back==1 && FlipLOck==1){
                val mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.card_sound)
                mediaPlayer.start()
                mediaPlayer.setOnCompletionListener { mp -> mp.release() }
                last.visibility = View.VISIBLE
                back_animation.setTarget(last);
                front_animation2.setTarget(first);
                front_animation2 .start()
                back_animation.start()
                last.setText("Где я?")
                if (Language==1)
                { last.text = "Where am I?"}


                PlayerNum += 1;

                front_animation2.doOnEnd {
                    FlipLOck_front=1;
                    if (PlayerNum > N1) {
                        Next_Game.visibility = View.VISIBLE
                        Next_Game.startAnimation(animE_frominvise)
                    }
                }

                //проверка на колво игроков

                if (PlayerNum > N1) {
                    last.text = "Игра началась!"
                    if (Language==1)
                    { last.text = "Game has started!"}
                    last.setBackgroundResource(R.drawable.button_gamestarted)
                    FlipLOck=0;}
                else { textNum!!.text = PlayerNum.toString()}

                    FlipLOck_back=0;
                    Name_of_player!!.text = Player_playing!![PlayerNum-1]
            }}

        }




}