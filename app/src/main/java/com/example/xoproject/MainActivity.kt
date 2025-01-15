package com.example.xoproject

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.xoproject.logic.EMPTY_CELL
import com.example.xoproject.logic.Result
import com.example.xoproject.logic.XOGame
import com.example.xoproject.logic.X_SYMBOL

class MainActivity : AppCompatActivity() {

    private lateinit var game: XOGame
    private lateinit var buttons: Array<Array<Button>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        game = XOGame()
        buttons = Array(3) { row ->
            Array(3) { column ->
                val b = Button(this)
                b.apply {
                    textSize = 60f

                    setOnClickListener { handleMove(row, column) }
                }
            }
        }

        val gridLayout = findViewById<GridLayout>(R.id.activity_main_grid_layout)
        gridLayout.rowCount = 3
        gridLayout.columnCount = 3

        for (row in 0..2) {
            for (column in 0..2) {
                val button = buttons[row][column]

                val params = GridLayout.LayoutParams().apply {
                    rowSpec = GridLayout.spec(row, 1)
                    columnSpec = GridLayout.spec(column, 1)
                }

                button.layoutParams = params
                gridLayout.addView(button)
            }
        }

        gridLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.CENTER
        }

        findViewById<Button>(R.id.activity_main_reset_button).setOnClickListener {
            game.reset()
            updateBoard()
        }

        updateBoard()

    }

    private fun handleMove(row: Int, col: Int) {
        if(game.validateTurn(row, col)) {
            game.move(row, col)
            updateBoard()
            val winner = game.checkForWinner()
            if (winner != null) {
                val message = when (winner) {
                    Result.X_SYMBOL -> "X is the Winner!!"
                    Result.O_SYMBOL -> "O is the Winner!"
                    Result.DRAW -> "It's a draw!"
                }
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                game.reset()
            }
        }
    }

    private fun playerColor(player: String) : Int {
        return if (player == X_SYMBOL.toString()) Color.RED else Color.BLUE
    }

    private fun updateBoard() {
        val board = game.board
        for (row in board.indices) {
            for (column in board[row].indices) {
                val currCell = board[row][column].toString()
                buttons[row][column].text = currCell
                buttons[row][column].setTextColor(playerColor(currCell))
                buttons[row][column].isEnabled = board[row][column] == EMPTY_CELL
            }
        }
    }


}