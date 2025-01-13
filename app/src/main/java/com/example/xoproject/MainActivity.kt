package com.example.xoproject

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
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

        buttons = Array(3) { row ->
            Array(3) { column ->
                Button(this).apply {
                    textSize = 26f
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
                    width = 0
                    height = 0
                    rowSpec = GridLayout.spec(row, 1f)
                    columnSpec = GridLayout.spec(column, 1f)
                }

                button.layoutParams = params
                gridLayout.addView(button)
            }
        }


    }

    private fun handleMove(row: Int, col: Int) {
        Toast.makeText(this, "click", Toast.LENGTH_LONG).show()
    }
}