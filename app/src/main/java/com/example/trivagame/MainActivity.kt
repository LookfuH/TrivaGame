package com.example.trivagame


import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private var correct: Int = 0
    private var total: Int = 0

    private lateinit var questionview: TextView
    private lateinit var A1: Button
    private lateinit var A2: Button
    private lateinit var A3: Button
    private lateinit var A4: Button

    private lateinit var mediaPlayer: MediaPlayer

    data class Question(val text: String, val answers: List<String>, val correctAnswer: Int) {}

    //private val correctAns: List<Int> = listOf(1,1,1)

    private val questions: MutableList<Question> = mutableListOf(
        Question(
            text = "What is Android Jetpack?",
            answers = listOf("All of these", "Tools", "Documentation", "Libraries"),
            correctAnswer = 1
        ),
        Question(
            text = "What is the base class for layouts?",
            answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot"),
            correctAnswer = 1
        ),
        Question(
            text = "What layout do you use for complex screens?",
            answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout"),
            correctAnswer = 1
        ),
    )

    private var currentQuestionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.startmanu)

        val startButton = findViewById<Button>(R.id.startsession)
        startButton.setOnClickListener {
            setContentView(R.layout.gamesession)
            initializeGameSessionViews()
            loadQuestion(questions[currentQuestionIndex])
            Gameplay(it)
        }
    }

    private fun initializeGameSessionViews() {
        setContentView(R.layout.gamesession)
        questionview = findViewById(R.id.questionview)
        A1 = findViewById(R.id.A1B)
        A2 = findViewById(R.id.A2B)
        A3 = findViewById(R.id.A3B)
        A4 = findViewById(R.id.A4B)

        A1.setOnClickListener { view -> Gameplay(view) }
        A2.setOnClickListener { view -> Gameplay(view) }
        A3.setOnClickListener { view -> Gameplay(view) }
        A4.setOnClickListener { view -> Gameplay(view) }
    }

    fun loadQuestion(question: Question) {
        questionview.text = question.text
        A1.text = question.answers[0]
        A2.text = question.answers[1]
        A3.text = question.answers[2]
        A4.text = question.answers[3]
    }

    private fun Gameplay(view: View) {

        val question = questions[currentQuestionIndex]
        val correctAnswerIndex = question.correctAnswer - 1

        val selectedAnswerIndex = when (view.id) {
            R.id.A1B -> 0
            R.id.A2B -> 1
            R.id.A3B -> 2
            R.id.A4B -> 3
            else -> return
        }

        val selectedAnswer = question.answers[selectedAnswerIndex]

        if (selectedAnswerIndex == correctAnswerIndex) {
            mediaPlayer = MediaPlayer.create(this, R.raw.its_true)
            mediaPlayer.start()
            correct++
        }
        total++

        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            loadQuestion(questions[currentQuestionIndex])
        } else {
            loadResult()

        }
    }

    // Cleaned up to preventing memory leaks and improving resource management.
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }


    private fun loadResult() {
        setContentView(R.layout.resultsmenu)
        Toast.makeText(this, "Your score is $correct out of $total", Toast.LENGTH_SHORT).show()

        mediaPlayer = MediaPlayer.create(this, R.raw.how_did_that_make_you_feel)

        // Play the sound after a delay
        val delayMillis = 1500L
        CoroutineScope(Dispatchers.Main).launch {
            delay(delayMillis)
            mediaPlayer.start()
        }

        val retrybutton = findViewById<Button>(R.id.retrybutton)
        retrybutton.setOnClickListener {
            correct = 0
            total = 0
            currentQuestionIndex = 0


            Toast.makeText(this, "Game is being reset. Good luck!", Toast.LENGTH_SHORT).show()

            // Start of a new game
            setContentView(R.layout.startmanu)

            val startButton = findViewById<Button>(R.id.startsession)
            startButton.setOnClickListener {
                setContentView(R.layout.gamesession)
                initializeGameSessionViews()
                loadQuestion(questions[currentQuestionIndex])
                Gameplay(it)
            }
        }


        //For menu_startmanu.xml

        //override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //    menuInflater.inflate(R.menu.menu_startmanu, menu)
        //     return true
        //  }

        //   override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //     when (item.itemId) {
        //        R.id.menu_about -> showAboutDialog()
        //     }
        //     return super.onOptionsItemSelected(item)
        // }

        // private fun showAboutDialog() {
        //We can change this. This toast is just for testing.
        //     showToast("This app is developed by Jordan Hamlett and Steven Sommer.")
        // }

        // private fun showToast(message: String) {
        //    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        // }
    }
}
