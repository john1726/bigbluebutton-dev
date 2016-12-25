package org.bigbluebutton.core.apps

import scala.collection.mutable.ArrayBuffer
import scala.collection.immutable.HashMap

class TimerModel {
  private val messages = new ArrayBuffer[Map[String, String]]()

  def getTimerHistory(): Array[Map[String, String]] = {
    val history = new Array[Map[String, String]](messages.size)
    messages.copyToArray(history)

    history
  }

  def addNewTimerMessage(msg: Map[String, String]) {
    messages append msg
  }
}