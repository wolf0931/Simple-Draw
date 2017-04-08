package com.simplemobiletools.draw

import android.graphics.Path
import com.simplemobiletools.draw.actions.Action
import com.simplemobiletools.draw.actions.Line
import com.simplemobiletools.draw.actions.Move
import com.simplemobiletools.draw.actions.Quad
import java.io.IOException
import java.io.ObjectInputStream
import java.io.Serializable
import java.security.InvalidParameterException
import java.util.*

// https://stackoverflow.com/a/8127953
class MyPath : Path(), Serializable {

    private val actions = LinkedList<Action>()

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(inputStream: ObjectInputStream) {
        inputStream.defaultReadObject()

        for (action in actions) {
            action.perform(this)
        }
    }

    @Throws(InvalidParameterException::class)
    fun readObject(pathData: String) {
        val tokens = pathData.split("\\s+".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()
        var i = 0
        while (i < tokens.size) {
            when (tokens[i][0]) {
                'M' -> addAction(Move(tokens[i]))
                'L' -> addAction(Line(tokens[i]))
                'Q' -> {
                    // Quad actions are of the following form:
                    // "Qx1,y1 x2,y2"
                    // Since we split the tokens by whitespace, we need to join them again
                    if (i + 1 >= tokens.size)
                        throw InvalidParameterException("Error parsing the data for a Quad.")

                    addAction(Quad(tokens[i] + " " + tokens[i + 1]))
                    ++i
                }
            }
            ++i
        }
    }

    override fun reset() {
        actions.clear()
        super.reset()
    }

    private fun addAction(action: Action) {
        if (action is Move) {
            moveTo(action.x, action.y)
        } else if (action is Line) {
            lineTo(action.x, action.y)
        } else if (action is Quad) {
            val q = action
            quadTo(q.x1, q.y1, q.x2, q.y2)
        }
    }

    override fun moveTo(x: Float, y: Float) {
        actions.add(Move(x, y))
        super.moveTo(x, y)
    }

    override fun lineTo(x: Float, y: Float) {
        actions.add(Line(x, y))
        super.lineTo(x, y)
    }

    override fun quadTo(x1: Float, y1: Float, x2: Float, y2: Float) {
        actions.add(Quad(x1, y1, x2, y2))
        super.quadTo(x1, y1, x2, y2)
    }

    fun getActions() = actions
}
