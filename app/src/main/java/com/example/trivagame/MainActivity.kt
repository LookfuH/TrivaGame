package com.example.trivagame


import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
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

    private val questions: MutableList<Question> = mutableListOf(

        Question(
            text = "Which file extension is used to save Kotlin files?",
            answers = listOf(".java", ".kot", ".kt or .kts", ".android"),
            correctAnswer = 3
        ),
        Question(
            text = "What is the default return type of any functions defined in Kotlin?",
            answers = listOf("Int", "DoubleCatfish", "Void", "Unit"),
            correctAnswer = 4
        ),
        Question(
            text = "Which keyword is used to indicate that a class can be subclassed?",
            answers = listOf("public", "open", "internal", "protected"),
            correctAnswer = 2
        ),
        Question(
            text = "Which Gradle configuration indicates the most recent API level your app has been tested with?",
            answers = listOf("minSdkVersion", "compileSdkVersion", "targetSdkVersion", "testSdkVersion"),
            correctAnswer = 3
        ),
        Question(
            text = "What is EditText a subclass of?",
            answers = listOf("TextField", "LinearLayout", "TextView", "Button"),
            correctAnswer = 3
        ),
        Question(
            text = "Which lifecycle method is called to give an activity focus?",
            answers = listOf("onResume()", "onVisible()", "Catfish", "onFocus()"),
            correctAnswer = 1
        ),
        Question(
            text = "What was Collin's minor as an undergrad?",
            answers = listOf("Catfish", "French", "German", "Math"),
            correctAnswer = 2
        ),
        Question(
            text = "When did Collin started Teaching at WSU",
            answers = listOf("2012", "1974", "2017", "2021"),
            correctAnswer = 4
        ),
    )

    private var currentQuestionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.startmanu)

        // .mp3 player
        mediaPlayer = MediaPlayer.create(this, R.raw.know_the_answer)
        mediaPlayer.start()

        //Start Button
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

        // Verify the layout and view IDs
        questionview = findViewById(R.id.questionview)
        A1 = findViewById(R.id.A1B)
        A2 = findViewById(R.id.A2B)
        A3 = findViewById(R.id.A3B)
        A4 = findViewById(R.id.A4B)

        // Verify the click listeners
        A1.setOnClickListener { view -> Gameplay(view) }
        A2.setOnClickListener { view -> Gameplay(view) }
        A3.setOnClickListener { view -> Gameplay(view) }
        A4.setOnClickListener { view -> Gameplay(view) }
    }

    private fun loadQuestion(question: Question) {

        // Load Question and Answers
        questionview.text = question.text
        A1.text = question.answers[0]
        A2.text = question.answers[1]
        A3.text = question.answers[2]
        A4.text = question.answers[3]
    }

    private fun Gameplay(view: View) {
        val question = questions[currentQuestionIndex]
        val correctAnswerIndex = question.correctAnswer - 1

        // Assign Button to answer
        val selectedAnswerIndex = when (view.id) {
            R.id.A1B -> 0
            R.id.A2B -> 1
            R.id.A3B -> 2
            R.id.A4B -> 3
            else -> return
        }

        //If answered correctly Collin says it's true and correct is added
        if (selectedAnswerIndex == correctAnswerIndex) {
            mediaPlayer = MediaPlayer.create(this, R.raw.its_true)
            mediaPlayer.start()
            correct++
        }
        total++

        //If all 8 questions are answered then loadResult() will run
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

        // Result text view
        val result = "Your score is $correct out of $total"
        val textView = findViewById<TextView>(R.id.result)
        textView.text = result

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


            Toast.makeText(this, "Game is reset. Good luck!", Toast.LENGTH_SHORT).show()

            // Start of a new game
            setContentView(R.layout.startmanu)
            mediaPlayer = MediaPlayer.create(this, R.raw.know_the_answer)
            mediaPlayer.start()
            val startButton = findViewById<Button>(R.id.startsession)
            startButton.setOnClickListener {
                setContentView(R.layout.gamesession)
                initializeGameSessionViews()
                loadQuestion(questions[currentQuestionIndex])
                Gameplay(it)
            }
        }
    }
}
