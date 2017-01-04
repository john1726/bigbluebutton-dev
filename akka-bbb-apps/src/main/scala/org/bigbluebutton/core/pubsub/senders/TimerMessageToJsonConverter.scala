package org.bigbluebutton.core.pubsub.senders

import scala.collection.mutable.HashMap
import org.bigbluebutton.core.api._
import com.google.gson.Gson
import scala.collection.mutable.HashMap
import collection.JavaConverters._
import scala.collection.JavaConversions._
import java.util.ArrayList
import org.bigbluebutton.common.messages.MessagingConstants
import org.bigbluebutton.core.messaging.Util
import org.bigbluebutton.core.service.timer.TimerKeyUtil

object TimerMessageToJsonConverter {

  val UNKNOWN = "unknown"

  private def timerMessageToMap(msg: Map[String, String]): HashMap[String, String] = {
    val res = new HashMap[String, String]
    res += "timer_type" -> msg.get(TimerKeyUtil.TIMER_TYPE).getOrElse(UNKNOWN)
    res += "from_userid" -> msg.get(TimerKeyUtil.FROM_USERID).getOrElse(UNKNOWN)
    res += "from_username" -> msg.get(TimerKeyUtil.FROM_USERNAME).getOrElse(UNKNOWN)
    res += "from_time" -> msg.get(TimerKeyUtil.FROM_TIME).getOrElse(UNKNOWN)
    res += "from_tz_offset" -> msg.get(TimerKeyUtil.FROM_TZ_OFFSET).getOrElse(UNKNOWN)
    res += "to_userid" -> msg.get(TimerKeyUtil.TO_USERID).getOrElse(UNKNOWN)
    res += "to_username" -> msg.get(TimerKeyUtil.TO_USERNAME).getOrElse(UNKNOWN)
    res += "message" -> msg.get(TimerKeyUtil.MESSAGE).getOrElse(UNKNOWN)

    res
  }

  def getTimerHistoryReplyToJson(msg: GetTimerHistoryReply): String = {
    val payload = new java.util.HashMap[String, Any]()
    payload.put(Constants.MEETING_ID, msg.meetingID)
    payload.put(Constants.REQUESTER_ID, msg.requesterID)

    val collection = new ArrayList[java.util.Map[String, String]]();

    msg.history.foreach(p => {
      collection.add(mapAsJavaMap(TimerMessageToJsonConverter.timerMessageToMap(p)))
    })

    payload.put(Constants.TIMER_HISTORY, collection)

    val header = Util.buildHeader(MessageNames.GET_TIMER_HISTORY_REPLY, Some(msg.replyTo))
    Util.buildJson(header, payload)
  }

  def sendPublicTimerMessageEventToJson(msg: SendPublicTimerMessageEvent): String = {
    val payload = new java.util.HashMap[String, Any]()
    payload.put(Constants.MEETING_ID, msg.meetingID)
    payload.put(Constants.MESSAGE, mapAsJavaMap(TimerMessageToJsonConverter.timerMessageToMap(msg.message)))

    val header = Util.buildHeader(MessageNames.SEND_PUBLIC_TIMER_MESSAGE, None)
    Util.buildJson(header, payload)
  }
}