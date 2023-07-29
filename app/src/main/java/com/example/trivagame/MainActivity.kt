package com.example.trivagame


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity()
{
    private var correct: Int = 0
    private var total: Int = 0

    private lateinit var questionview: TextView
    private lateinit var A1: Button
    private lateinit var A2: Button
    private lateinit var A3: Button
    private lateinit var A4: Button

    //find a way to import the view from gamesession
    private lateinit var GameView: View
    //lateinit var GameView: View = findViewById(gameSession)

    data class Question(val text: String, val answers: List<String>, val correctAnswer: Int) {}

    //private val correctAns: List<Int> = listOf(1,1,1)

    private val questions: MutableList<Question> = mutableListOf(
        Question(
            text = "What is Android Jetpack?",
            answers = listOf("All of these", "Tools", "Documentation", "Libraries"),
            correctAnswer = 1),
        Question(
            text = "What is the base class for layouts?",
            answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot"),
            correctAnswer = 1),
        Question(
            text = "What layout do you use for complex screens?",
            answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout"),
            correctAnswer = 1),
    )

    private var currentQuestionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.startmanu)

        val startButton = findViewById<Button>(R.id.startsession)
        startButton.setOnClickListener {
            setContentView(R.layout.gamesession)
            loadQuestion(questions[currentQuestionIndex])
            Gameplay(questions[currentQuestionIndex])
        }
    }

    fun loadQuestion(question: Question) {
        questionview.text = question.text
        A1.text = question.answers[0]
        A2.text = question.answers[1]
        A3.text = question.answers[2]
        A4.text = question.answers[3]
    }

    fun Gameplay(question: Question, selectedAnswerIndex: Int) {

        val correctAnswerIndex = question.correctAnswer
        //val selectedAnswer = question.answers[selectedAnswerIndex]

        val selectedAnswer = when (view.id) {
            R.id.A1B -> question.answers.random()
            R.id.A2B -> question.answers.random()
            R.id.A3B -> question.answers.random()
            R.id.A4B -> question.answers.random()
            else -> return
        }

        if (selectedAnswer == correctAnswerIndex) {
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

    fun loadResult() {
        setContentView(R.layout.resultsmenu)
        Toast.makeText(this, "Your score is $correct out of $total", Toast.LENGTH_SHORT).show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_startmanu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_about -> showAboutDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAboutDialog() {
        //We can change this. This toast is just for testing.
        showToast("This app is developed by Jordan Hamlett and Steven Sommer.")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
