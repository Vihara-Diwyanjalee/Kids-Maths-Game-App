package com.example.kidsmathsgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var TimeTextView : TextView? = null
    private var QuestionText : TextView? = null
    private var ScoreTextView : TextView? = null
    private var AlertTextView : TextView? = null
    private var FinalScoreTextView : TextView? = null
    private  var btn0 : Button? = null
    private var btn1 : Button? = null
    private var btn2 : Button? = null
    private var btn3 : Button? = null

    private var countDownTimer : CountDownTimer? = null
    private var random : Random = Random
    private var a = 0
    private var b = 0
    private var indexOfCorrectAnswer = 0
    private var answers = ArrayList<Int>()
    private var points = 0
    private var totalQuestions = 0
    private var cals = ""

    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//var
        val calInt = intent.getStringExtra("cals")
        cals = calInt!!
        TimeTextView = findViewById(R.id.TimeTextView)
        QuestionText = findViewById(R.id.QuestionText)
        ScoreTextView = findViewById(R.id.ScoreTextView)
        AlertTextView = findViewById(R.id.AlertTextView)

        btn0 = findViewById(R.id.button3)
        btn1 = findViewById(R.id.button2)
        btn2 = findViewById(R.id.button)
        btn3 = findViewById(R.id.button4)

        btn0?.tag = "0"
        btn1?.tag = "1"
        btn2?.tag = "2"
        btn3?.tag = "3"

        btn0?.setOnClickListener { optionSelect(it) }
        btn1?.setOnClickListener { optionSelect(it) }
        btn2?.setOnClickListener { optionSelect(it) }
        btn3?.setOnClickListener { optionSelect(it) }

        start()
    }

    override fun onPause() {
        super.onPause()
        // Dismiss any open dialogs
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog?.dismiss()
    }

    fun NextQuestion(cal:String){
        a = random.nextInt(10)
        b = random.nextInt(10)
        QuestionText!!.text = "$a $cal $b"
        indexOfCorrectAnswer = random.nextInt(4)

        answers.clear()

        for (i in 0..3){
            if (indexOfCorrectAnswer == i){
                when(cal){
                    "+"->{answers.add(a+b)}
                    "-"->{answers.add(a-b)}
                    "*"->{answers.add(a*b)}
                    "/"-> {
                        try {
                            answers.add(a/b)
                        }
                        catch (e:Exception){
                            e.printStackTrace()
                        }
                    }
                }
            }
            else{
                var wrongAnswer = random.nextInt(20)
                try{
                    while(
                        wrongAnswer == a+b
                        || wrongAnswer == a-b
                        || wrongAnswer == a*b
                        || wrongAnswer == a/b
                    ){
                        wrongAnswer = random.nextInt(20)
                    }
                    answers.add(wrongAnswer)
                }
                catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
        try {
            btn0!!.text = "${answers[0]}"
            btn1!!.text = "${answers[1]}"
            btn2!!.text = "${answers[2]}"
            btn3!!.text = "${answers[3]}"
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun optionSelect(view: View?) {
        if (view != null && view.tag != null) {
            val clickedTag = view.tag.toString().toInt()
            totalQuestions++
            if (clickedTag == indexOfCorrectAnswer) {
                points++
                AlertTextView!!.text = "Correct"
            } else {
                AlertTextView!!.text = "Wrong"
            }
            ScoreTextView!!.text = "$points/$totalQuestions"
            NextQuestion(cals)
        } else {
            // Handle the case where view or view.tag is null
        }
    }


    fun PlayAgain(view: View?){
        points = 0
        totalQuestions = 0
        ScoreTextView!!.text = "$points/$totalQuestions"
        countDownTimer!!.start()
    }

    private fun start(){
        NextQuestion(cals)
        countDownTimer = object :CountDownTimer(30000,1000){
            override fun onTick(p0: Long) {
                TimeTextView!!.text = (p0 / 1000).toString() + "s"
            }

            override fun onFinish() {
                TimeTextView!!.text = "Time Up ! "
                openDialog()
            }

        }.start()
    }

    private fun openDialog() {
        if (!isFinishing && window != null) {
            val inflate = LayoutInflater.from(this)
            val winDialog = inflate.inflate(R.layout.win_layout, null)
            FinalScoreTextView = winDialog.findViewById(R.id.FinalScoreTextView)
            val btnPlayAgain = winDialog.findViewById<Button>(R.id.buttonPlayAgain)
            val btnBack = winDialog.findViewById<Button>(R.id.btnBack)

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setCancelable(false)
            dialogBuilder.setView(winDialog)
            FinalScoreTextView!!.text = "$points/$totalQuestions"

            btnPlayAgain.setOnClickListener {
                startActivity(Intent(this,PlayActivity::class.java))
            }
            btnBack.setOnClickListener { onBackPressed() }

            dialog = dialogBuilder.create()

            if (!isFinishing && window != null) {
                dialog?.show()
            }
        }
    }

}
