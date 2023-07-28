package com.example.trivagame

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity()
{
    var correct: Int = 0
    var total: Int = 0

    lateinit var questionview: TextView
    lateinit var A1: Button
    lateinit var A2: Button
    lateinit var A3: Button
    lateinit var A4: Button

    data class Question(
        val text: String,
        val answers: List<String>) {

    }

    private val correctAns: List<Int> = listOf(1,1,1)

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

        // Initialize views
        questionview = findViewById(R.id.questionview)
        A1 = findViewById(R.id.A1B)
        A2 = findViewById(R.id.A2B)
        A3 = findViewById(R.id.A3B)
        A4 = findViewById(R.id.A4B)

        // Start the game
        loadGame()

    }

    // If I am understanding the code correctly it should use the list of correctAns and the location of the correct answer for the button found in gameplay() which is compared by the int provided
    fun isCorrect(selectedAnswer: Int): Boolean {

        if(correctAns[total] == selectedAnswer){
            return true
        }
        return false
    }
    fun loadGame() {
        setContentView(R.layout.gamesession)
        loadQuestion(questions[currentQuestionIndex])
    }

   fun loadQuestion(question: Question) {
        questionview.text = questions[1].text
        A1.text = questions[1].answers[1]
        A2.text = questions[1].answers[2]
        A3.text = questions[1].answers[3]
        A4.text = questions[1].answers[4]
    }

    fun Gameplay(v: View){
        val selectedAnswer = when (View.id) {
            R.id.A1B -> 1
            R.id.A2B -> 2
            R.id.A3B -> 3
            R.id.A4B -> 4
            else -> return
        }

        val currentQuestion = questions[currentQuestionIndex]
        if (isCorrect(selectedAnswer)) {
            correct++
        }
        total++

        // Move to the next question or show results
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            loadQuestion(questions[currentQuestionIndex])
        } else {
            loadResult()
        }

    }

    fun loadResult()  {
        setContentView(R.layout.resultsmenu)
}
}