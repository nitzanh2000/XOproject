package com.example.xoproject.logic

const val X_SYMBOL = 'x'
const val O_SYMBOL = 'o'
const val EMPTY_CELL = ' '

enum class Result {
    X_SYMBOL, O_SYMBOL, DRAW
}

class XOGame {
    val board =  Array(3) { CharArray(3) { EMPTY_CELL} }
    private var player : Char = X_SYMBOL

    fun reset() {
        for (row in board.indices) {
            for (column in board[row].indices) {
                board[row][column] = EMPTY_CELL
            }
        }

        player = X_SYMBOL
    }

    fun validateTurn(row: Int, column: Int) : Boolean {
        return !(row !in board.indices || column !in board.indices|| board[row][column] != EMPTY_CELL)
    }

    fun move(row: Int, column: Int) {
        board[row][column] = player
        player = if (player == X_SYMBOL) O_SYMBOL else X_SYMBOL
    }

    private fun returnResult(winner: Char) : Result {
        return if(winner == X_SYMBOL) {
            Result.X_SYMBOL
        } else {
            Result.O_SYMBOL
        }
    }

    fun checkForWinner(): Result? {
        for (i in board.indices) {
            if (board[i][0] != EMPTY_CELL && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                return returnResult(board[i][0])
            }
            if (board[0][i] != EMPTY_CELL && board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
                return returnResult(board[0][i])
            }
        }

        if (board[0][0] != EMPTY_CELL && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return returnResult(board[0][0])
        }
        if (board[0][2] != EMPTY_CELL && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            return returnResult(board[0][2])
        }

        if (board.all { row -> row.all { cell -> cell != EMPTY_CELL } }) {
           return Result.DRAW
        }

        return null
    }
}