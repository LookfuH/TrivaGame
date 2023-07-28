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

    data class Question(
        val text: String,
        val answers: List<String>)

    private val questions: MutableList<Question> = mutableListOf(
        Question(text = "What is Android Jetpack?",
            answers = listOf("All of these", "Tools", "Documentation", "Libraries")),
        Question(text = "What is the base class for layouts?",
            answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")),
        Question(text = "What layout do you use for complex screens?",
            answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")),
    )

    private var currentQuestionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.startmanu)

    }

    fun loadGame() {
        setContentView(R.layout.gamesession)
       // loadQuestion(questions[currentQuestionIndex])
    }

    fun loadQuestion(question: Question) {
        questionview.text = question.text
        A1.text = question.answers[0]
        A2.text = question.answers[1]
        A3.text = question.answers[2]
        A4.text = question.answers[3]
    }

    fun Gameplay(view: View){
        val selectedAnswer = when (view.id) {
            R.id.A1B -> 1
            R.id.A2B -> 2
            R.id.A3B -> 3
            R.id.A4B -> 4
            else -> return
        }

        val currentQuestion = questions[currentQuestionIndex]
        //if (currentQuestion.isCorrect(selectedAnswer)) {
        //    correct++
       // }
       // total++

        // Move to the next question or show results
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            loadQuestion(questions[currentQuestionIndex])
        } else {
            loadResult(view)
        }

    }

    fun loadResult(v: View)  {
        setContentView(R.layout.resultsmenu)
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