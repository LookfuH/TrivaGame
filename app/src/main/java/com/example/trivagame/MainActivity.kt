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
    lateinit var A1B: Button
    lateinit var A2B: Button
    lateinit var A3B: Button
    lateinit var A4B: Button
    //find a way to import the view from gamesession
    lateinit var GameView: View = findViewById(gameSession)
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
    // inits the variables might cause it to crash? might need to replace this with an actual view...


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.startmanu)

        //inits varaibles, might be the cause of the crashing find a way to properly import
        questionview = GameView.findViewById(R.id.questionview)
        A1B = GameView.findViewById(R.id.A1B)
        A2B = GameView.findViewById(R.id.A2B)
        A3B = GameView.findViewById(R.id.A3B)
        A4B = GameView.findViewById(R.id.A4B)

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
    fun ButtonOne(){
        Gameplay(1)
    }
    fun ButtonTwo(){
        Gameplay(2)
    }
    fun ButtonThree(){
        Gameplay(3)
    }
    fun ButtonFour(){
        Gameplay(4)
    }

   fun loadQuestion(question: Question) {
        questionview.text = questions[1].text
        A1B.text = questions[currentQuestionIndex].answers[0]
        A2B.text = questions[currentQuestionIndex].answers[1]
        A3B.text = questions[currentQuestionIndex].answers[2]
        A4B.text = questions[currentQuestionIndex].answers[3]
    }
    //look into swapping it from a view import and make it so you pass in a int?
    fun Gameplay(selectedAnswer: Int){
        /*
        val selectedAnswer = when (View.id) {
            R.id.A1B -> 1
            R.id.A2B -> 2
            R.id.A3B -> 3
            R.id.A4B -> 4
            else -> return
        }
*/
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


