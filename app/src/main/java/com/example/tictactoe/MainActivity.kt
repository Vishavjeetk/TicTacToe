package com.example.tictactoe


import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    lateinit var textToSpeach: TextToSpeech
    private lateinit var btn1: ImageView
    private lateinit var btn2: ImageView
    private lateinit var btn3: ImageView
    private lateinit var btn4: ImageView
    private lateinit var btn5: ImageView
    private lateinit var btn6: ImageView
    private lateinit var btn7: ImageView
    private lateinit var btn8: ImageView
    private lateinit var btn9: ImageView
    private var gameOver by Delegates.notNull<Boolean>()
    private lateinit var lineImageView: ImageView
    private lateinit var playerOneScore: TextView
    private lateinit var playerTwoScore: TextView
    private lateinit var restart_btn: ImageView
    private lateinit var playWithComputer: TextView
    private var playWithComputerValue by Delegates.notNull<Boolean>()
    private var playerOneWin = 0
    private var playerTwoWin = 0
    private lateinit var idList: MutableList<ImageView>
    var player = 1
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameOver = false

        textToSpeach = TextToSpeech(this,this)
        playerOneScore = findViewById(R.id.player_one_score)
        playerTwoScore = findViewById(R.id.player_two_score)

        playWithComputerValue = false


        lineImageView = findViewById(R.id.line)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        btn8 = findViewById(R.id.btn8)
        btn9 = findViewById(R.id.btn9)
        playWithComputer = findViewById(R.id.play_with_computer)
        restart_btn = findViewById(R.id.restart_btn)

        idList = mutableListOf(btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9)

        supportActionBar?.title = "Tic Tac Toe"

        restart_btn.visibility = View.INVISIBLE
        btn1.setOnClickListener {
            selectedButton(btn1)
        }
        btn2.setOnClickListener {
            selectedButton(btn2)
        }
        btn3.setOnClickListener {
            selectedButton(btn3)
        }
        btn4.setOnClickListener {
            selectedButton(btn4)
        }
        btn5.setOnClickListener {
            selectedButton(btn5)
        }
        btn6.setOnClickListener {
            selectedButton(btn6)
        }
        btn7.setOnClickListener {
            selectedButton(btn7)
        }
        btn8.setOnClickListener {
            selectedButton(btn8)
        }
        btn9.setOnClickListener {
            selectedButton(btn9)
        }
        restart_btn.setOnClickListener {
            restart()

        }
        playWithComputer.setOnClickListener {
            if (!playWithComputerValue) {
                playWithComputerValue = true
                playWithComputer.text = "Play With Human"
            }
            else {
                playWithComputerValue = false
                playWithComputer.text = "Play With Computer"
            }
        }

    }
    /*if (playWithComputerValue) {
        if (player == 1) {
            idList.remove(view)
            selectedBtn = view
        }
        else {
            val randomIndex = Random().nextInt(idList.size)
            val randomElement = idList[randomIndex]
            selectedBtn = randomElement
            idList.remove(randomElement)
        }
    }*/
    private fun selectedButton(view:ImageView){
        var selectedBtn = view
        var  btnNum = 0
        when(selectedBtn.id){
            R.id.btn1 -> btnNum = 1
            R.id.btn2 -> btnNum = 2
            R.id.btn3 -> btnNum = 3
            R.id.btn4 -> btnNum = 4
            R.id.btn5 -> btnNum = 5
            R.id.btn6 -> btnNum = 6
            R.id.btn7 -> btnNum = 7
            R.id.btn8 -> btnNum = 8
            R.id.btn9 -> btnNum = 9
        }
        if (playWithComputerValue) {
            gameWithComputer(btnNum,selectedBtn)
        }
        else {
            game(btnNum,selectedBtn)
        }
    }


    private fun gameWithComputer(cell: Int, selectedBtn: ImageView) {
        restart_btn.visibility = View.INVISIBLE
        if (player == 1){
            selectedBtn.setImageResource(R.drawable.blockwithzero)
            player1.add(cell)
            idList.remove(selectedBtn)
            player =4
            checkIfGameOver()
            if (!gameOver) {
                val randomIndex = Random().nextInt(idList.size)
                val randomElement = idList[randomIndex]
                idList.remove(randomElement)
                selectedButton(randomElement)
            }

        }else{
            disableButton()
                val handler = Handler()
                handler.postDelayed(
                    {
                        enableButton()
                        selectedBtn.setImageResource(R.drawable.blockwithcross)
                    },
                    500
                )
                player = 1
                player2.add(cell)
            checkIfGameOver()
        }
        selectedBtn.isEnabled = false
        neutral()
        //row1

    }

    fun checkIfGameOver() {
        if (player1.contains(1)&& player1.contains(2) && player1.contains(3)){
            textToSpeachFunction("player1 wins")
            playerOneWin++
            playerOneScore.text = "Player 1: $playerOneWin"
            lineImageView.setImageResource(R.drawable.topmost)
            gameOver = true
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        if (player2.contains(1) && player2.contains(2) && player2.contains(3)){
            textToSpeachFunction("player2 wins")
            playerTwoWin++
            lineImageView.setImageResource(R.drawable.topmost)
            gameOver = true
            playerTwoScore.text = "$playerTwoWin :Player 2"
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        //row2
        if (player1.contains(4) && player1.contains(5) && player1.contains(6)){
            textToSpeachFunction("player1 wins")
            playerOneWin++
            lineImageView.setImageResource(R.drawable.horizontal)
            gameOver = true
            playerOneScore.text = "Player 1: $playerOneWin"
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        if (player2.contains(4) && player2.contains(5) && player2.contains(6)){
            textToSpeachFunction("player2 wins")
            playerTwoWin++
            lineImageView.setImageResource(R.drawable.horizontal)
            gameOver = true
            playerTwoScore.text = "$playerTwoWin :Player 2"
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        //row3
        if (player1.contains(7) && player1.contains(8) && player1.contains(9)){
            textToSpeachFunction("player1 wins")
            playerOneWin++
            lineImageView.setImageResource(R.drawable.bottommost)
            gameOver = true
            playerOneScore.text = "Player 1: $playerOneWin"
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        if (player2.contains(7) && player2.contains(8) && player2.contains(9)){
            textToSpeachFunction("player2 wins")
            playerTwoWin++
            lineImageView.setImageResource(R.drawable.bottommost)
            gameOver = true
            playerTwoScore.text = "$playerTwoWin :Player 2"
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        //clmn1
        if (player1.contains(1)&& player1.contains(4) && player1.contains(7)){
            textToSpeachFunction("player1 wins")
            playerOneWin++
            lineImageView.setImageResource(R.drawable.leftmost)
            gameOver = true
            playerOneScore.text = "Player 1: $playerOneWin"
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        if (player2.contains(1) && player2.contains(4) && player2.contains(7)){
            textToSpeachFunction("player2 wins")
            playerTwoWin++
            lineImageView.setImageResource(R.drawable.leftmost)
            gameOver = true
            playerTwoScore.text = "$playerTwoWin :Player 2"
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        //clmn2
        if (player1.contains(2) && player1.contains(5) && player1.contains(8)){
            textToSpeachFunction("player1 wins")
            playerOneWin++
            lineImageView.setImageResource(R.drawable.vertical)
            gameOver = true
            playerOneScore.text = "Player 1: $playerOneWin"
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        if (player2.contains(2) && player2.contains(5) && player2.contains(8)){
            textToSpeachFunction("player2 wins")
            playerTwoWin++
            lineImageView.setImageResource(R.drawable.vertical)
            gameOver = true
            playerTwoScore.text = "$playerTwoWin :Player 2"
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        //clmn3
        if (player1.contains(3) && player1.contains(6) && player1.contains(9)){
            textToSpeachFunction("player1 wins")
            playerOneWin++
            lineImageView.setImageResource(R.drawable.rightmost)
            gameOver = true
            playerOneScore.text = "Player 1: $playerOneWin"
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        if (player2.contains(3) && player2.contains(6) && player2.contains(9)){
            textToSpeachFunction("player2 wins")
            playerTwoWin++
            lineImageView.setImageResource(R.drawable.rightmost)
            gameOver = true
            playerTwoScore.text = "$playerTwoWin :Player 2"
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        //
        //
        if (player1.contains(3) && player1.contains(5) && player1.contains(7)){
            textToSpeachFunction("player1 wins")
            playerOneWin++
            lineImageView.setImageResource(R.drawable.topright)
            gameOver = true
            playerOneScore.text = "Player 1: $playerOneWin"
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        if (player2.contains(3) && player2.contains(5) && player2.contains(7)){
            textToSpeachFunction("player2 wins")
            playerTwoWin++
            lineImageView.setImageResource(R.drawable.topright)
            playerTwoScore.text = "$playerTwoWin :Player 2"
            gameOver = true
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        //
        //
        if (player1.contains(1) && player1.contains(5) && player1.contains(9)){
            textToSpeachFunction("player1 wins")
            playerOneWin++
            lineImageView.setImageResource(R.drawable.topleft)
            gameOver = true
            playerOneScore.text = "Player 1: $playerOneWin"
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        if (player2.contains(1) && player2.contains(5) && player2.contains(9)){
            textToSpeachFunction("player2 wins")
            playerTwoWin++
            lineImageView.setImageResource(R.drawable.topleft)
            gameOver = true
            playerTwoScore.text = "$playerTwoWin :Player 2"
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
    }

    private fun game(cell:Int, selectedBtn:ImageView){
        restart_btn.visibility = View.INVISIBLE
            if (player == 1){
                selectedBtn.setImageResource(R.drawable.blockwithzero)
                player1.add(cell)
                player =4
            }else{
                selectedBtn.setImageResource(R.drawable.blockwithcross)
                player = 1
                player2.add(cell)
            }
        selectedBtn.isEnabled = false
        neutral()
        //row1
        if (player1.contains(1)&& player1.contains(2) && player1.contains(3)){
            textToSpeachFunction("player1 wins")
            playerOneWin++
            playerOneScore.text = "Player 1: $playerOneWin"
            lineImageView.setImageResource(R.drawable.topmost)
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        if (player2.contains(1) && player2.contains(2) && player2.contains(3)){
            textToSpeachFunction("player2 wins")
            playerTwoWin++
            lineImageView.setImageResource(R.drawable.topmost)
            playerTwoScore.text = "$playerTwoWin :Player 2"
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        //row2
        if (player1.contains(4) && player1.contains(5) && player1.contains(6)){
            textToSpeachFunction("player1 wins")
            playerOneWin++
            lineImageView.setImageResource(R.drawable.horizontal)
            playerOneScore.text = "Player 1: $playerOneWin"
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        if (player2.contains(4) && player2.contains(5) && player2.contains(6)){
            textToSpeachFunction("player2 wins")
            playerTwoWin++
            lineImageView.setImageResource(R.drawable.horizontal)
            playerTwoScore.text = "$playerTwoWin :Player 2"
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        //row3
        if (player1.contains(7) && player1.contains(8) && player1.contains(9)){
            textToSpeachFunction("player1 wins")
            playerOneWin++
            lineImageView.setImageResource(R.drawable.bottommost)
            playerOneScore.text = "Player 1: $playerOneWin"
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        if (player2.contains(7) && player2.contains(8) && player2.contains(9)){
            textToSpeachFunction("player2 wins")
            playerTwoWin++
            lineImageView.setImageResource(R.drawable.bottommost)
            playerTwoScore.text = "$playerTwoWin :Player 2"
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        //clmn1
        if (player1.contains(1)&& player1.contains(4) && player1.contains(7)){
            textToSpeachFunction("player1 wins")
            playerOneWin++
            lineImageView.setImageResource(R.drawable.leftmost)
            playerOneScore.text = "Player 1: $playerOneWin"
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        if (player2.contains(1) && player2.contains(4) && player2.contains(7)){
            textToSpeachFunction("player2 wins")
            playerTwoWin++
            lineImageView.setImageResource(R.drawable.leftmost)
            playerTwoScore.text = "$playerTwoWin :Player 2"
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        //clmn2
        if (player1.contains(2) && player1.contains(5) && player1.contains(8)){
            textToSpeachFunction("player1 wins")
            playerOneWin++
            lineImageView.setImageResource(R.drawable.vertical)
            playerOneScore.text = "Player 1: $playerOneWin"
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        if (player2.contains(2) && player2.contains(5) && player2.contains(8)){
            textToSpeachFunction("player2 wins")
            playerTwoWin++
            lineImageView.setImageResource(R.drawable.vertical)
            playerTwoScore.text = "$playerTwoWin :Player 2"
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        //clmn3
        if (player1.contains(3) && player1.contains(6) && player1.contains(9)){
            textToSpeachFunction("player1 wins")
            playerOneWin++
            lineImageView.setImageResource(R.drawable.rightmost)
            playerOneScore.text = "Player 1: $playerOneWin"
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        if (player2.contains(3) && player2.contains(6) && player2.contains(9)){
            textToSpeachFunction("player2 wins")
            playerTwoWin++
            lineImageView.setImageResource(R.drawable.rightmost)
            playerTwoScore.text = "$playerTwoWin :Player 2"
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        //
        //
        if (player1.contains(3) && player1.contains(5) && player1.contains(7)){
            textToSpeachFunction("player1 wins")
            playerOneWin++
            lineImageView.setImageResource(R.drawable.topright)
            playerOneScore.text = "Player 1: $playerOneWin"
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        if (player2.contains(3) && player2.contains(5) && player2.contains(7)){
            textToSpeachFunction("player2 wins")
            playerTwoWin++
            lineImageView.setImageResource(R.drawable.topright)
            playerTwoScore.text = "$playerTwoWin :Player 2"
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        //
        //
        if (player1.contains(1) && player1.contains(5) && player1.contains(9)){
            textToSpeachFunction("player1 wins")
            playerOneWin++
            lineImageView.setImageResource(R.drawable.topleft)
            playerOneScore.text = "Player 1: $playerOneWin"
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }
        if (player2.contains(1) && player2.contains(5) && player2.contains(9)){
            textToSpeachFunction("player2 wins")
            playerTwoWin++
            lineImageView.setImageResource(R.drawable.topleft)
            playerTwoScore.text = "$playerTwoWin :Player 2"
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
        }

    }


    fun restart(){
        gameOver = false
        player = 1
        player1.clear()
        player2.clear()
        idList = mutableListOf(btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9)

        lineImageView.setImageResource(R.drawable.blank)
        btn1.setImageResource(R.drawable.bluesquare)
        btn2.setImageResource(R.drawable.bluesquare)
        btn3.setImageResource(R.drawable.bluesquare)
        btn4.setImageResource(R.drawable.bluesquare)
        btn5.setImageResource(R.drawable.bluesquare)
        btn6.setImageResource(R.drawable.bluesquare)
        btn7.setImageResource(R.drawable.bluesquare)
        btn8.setImageResource(R.drawable.bluesquare)
        btn9.setImageResource(R.drawable.bluesquare)
        btn1.isEnabled = true
        btn2.isEnabled = true
        btn3.isEnabled = true
        btn4.isEnabled = true
        btn5.isEnabled = true
        btn6.isEnabled = true
        btn7.isEnabled = true
        btn8.isEnabled = true
        btn9.isEnabled = true
    }

    fun neutral(){
        if (player1.size ==4 && player2.size ==4 ){
            disableButton()
        }else if (player1.size >4 || player2.size >4){

        }
    }

    fun disableButton(){
        btn1.isEnabled = false
        btn2.isEnabled = false
        btn3.isEnabled = false
        btn4.isEnabled = false
        btn5.isEnabled = false
        btn6.isEnabled = false
        btn7.isEnabled = false
        btn8.isEnabled = false
        btn9.isEnabled = false
        restart_btn.visibility = View.VISIBLE
    }
    fun enableButton(){
        btn1.isEnabled = true
        btn2.isEnabled = true
        btn3.isEnabled = true
        btn4.isEnabled = true
        btn5.isEnabled = true
        btn6.isEnabled = true
        btn7.isEnabled = true
        btn8.isEnabled = true
        btn9.isEnabled = true
        restart_btn.visibility = View.VISIBLE
    }

    override fun onInit(status: Int) {
        if(status== TextToSpeech.SUCCESS)
        {
            val res:Int=textToSpeach.setLanguage(Locale.US)
            if(res== TextToSpeech.LANG_MISSING_DATA || res== TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Toast.makeText(applicationContext,"Not supported",Toast.LENGTH_LONG).show()
            }
            else
            {
                //btn.isEnabled=true
                //textToSpeachFunction("Player1 is 0")
            }
        }
        else
        {
            Toast.makeText(applicationContext,"Failed to initialze",Toast.LENGTH_LONG).show()
        }
    }

    private fun textToSpeachFunction(strText: String)
    {
        textToSpeach.speak(strText,TextToSpeech.QUEUE_FLUSH,null)
        Toast.makeText(applicationContext,strText,Toast.LENGTH_LONG).show()
    }

    override fun onDestroy()
    {

        if(textToSpeach!=null)
        {
            textToSpeach.stop()
            textToSpeach.shutdown()
        }
        super.onDestroy()
    }
}