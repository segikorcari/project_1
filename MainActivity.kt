package com.example.rockpaperscissors

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var playerChoiceImage: ImageView
    private lateinit var computerChoiceImage: ImageView
    private lateinit var resultText: TextView
    private lateinit var playerScoreText: TextView
    private lateinit var computerScoreText: TextView
    private lateinit var playAgainButton: Button
    
    private var playerScore = 0
    private var computerScore = 0
    
    private val choices = arrayOf("Rock", "Paper", "Scissors")
    private val images = mapOf(
        "Rock" to R.drawable.rock,
        "Paper" to R.drawable.paper,
        "Scissors" to R.drawable.scissors
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerChoiceImage = findViewById(R.id.playerChoiceImage)
        computerChoiceImage = findViewById(R.id.computerChoiceImage)
        resultText = findViewById(R.id.resultText)
        playerScoreText = findViewById(R.id.playerScore)
        computerScoreText = findViewById(R.id.computerScore)
        playAgainButton = findViewById(R.id.playAgainButton)

        findViewById<Button>(R.id.rockButton).setOnClickListener { playGame("Rock") }
        findViewById<Button>(R.id.paperButton).setOnClickListener { playGame("Paper") }
        findViewById<Button>(R.id.scissorsButton).setOnClickListener { playGame("Scissors") }
        playAgainButton.setOnClickListener { resetGame() }
    }

    private fun playGame(playerChoice: String) {
        val computerChoice = choices[Random.nextInt(choices.size)]
        
        playerChoiceImage.setImageResource(images[playerChoice]!!)
        computerChoiceImage.setImageResource(images[computerChoice]!!)

        val result = determineWinner(playerChoice, computerChoice)
        resultText.text = result
        updateScore(result)
    }

    private fun determineWinner(playerChoice: String, computerChoice: String): String {
        return when {
            playerChoice == computerChoice -> "It's a Tie!"
            (playerChoice == "Rock" && computerChoice == "Scissors") ||
            (playerChoice == "Scissors" && computerChoice == "Paper") ||
            (playerChoice == "Paper" && computerChoice == "Rock") -> {
                playerScore++
                "You Win!"
            }
            else -> {
                computerScore++
                "Computer Wins!"
            }
        }
    }

    private fun updateScore(result: String) {
        playerScoreText.text = "Player: $playerScore"
        computerScoreText.text = "Computer: $computerScore"
        
        if (playerScore == 10 || computerScore == 10) {
            resultText.text = "${if (playerScore == 10) "You Win!" else "Computer Wins!"} Game Over."
            disableButtons()
            playAgainButton.visibility = Button.VISIBLE
        }
    }

    private fun disableButtons() {
        findViewById<Button>(R.id.rockButton).isEnabled = false
        findViewById<Button>(R.id.paperButton).isEnabled = false
        findViewById<Button>(R.id.scissorsButton).isEnabled = false
    }

    private fun resetGame() {
        playerScore = 0
        computerScore = 0
        playerScoreText.text = "Player: 0"
        computerScoreText.text = "Computer: 0"
        resultText.text = "Make your move!"
        
        findViewById<Button>(R.id.rockButton).isEnabled = true
        findViewById<Button>(R.id.paperButton).isEnabled = true
        findViewById<Button>(R.id.scissorsButton).isEnabled = true
        playAgainButton.visibility = Button.GONE
    }
}
