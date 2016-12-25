package org.bigbluebutton.core.apps

import org.bigbluebutton.core.api._
import scala.collection.mutable.ArrayBuffer
import org.bigbluebutton.core.MeetingActor
import org.bigbluebutton.core.OutMessageGateway

trait TimerApp {
  this: MeetingActor =>

  val outGW: OutMessageGateway

  def handleGetTimerHistoryRequest(msg: GetTimerHistoryRequest) {
    val history = timerModel.getTimerHistory()
    outGW.send(new GetTimerHistoryReply(mProps.meetingID, mProps.recorded, msg.requesterID, msg.replyTo, history))
  }

  def handleSendPublicTimerMessageRequest(msg: SendPublicTimerMessageRequest) {
    timerModel.addNewTimerMessage(msg.message.toMap)
    val pubMsg = msg.message.toMap

    outGW.send(new SendPublicTimerMessageEvent(mProps.meetingID, mProps.recorded, msg.requesterID, pubMsg))
  }
}