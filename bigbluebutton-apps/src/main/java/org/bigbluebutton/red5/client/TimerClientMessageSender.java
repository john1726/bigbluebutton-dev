package org.bigbluebutton.red5.client;

import java.util.HashMap;
import java.util.Map;

import org.bigbluebutton.common.messages.GetTimerHistoryReplyMessage;
import org.bigbluebutton.common.messages.SendPublicTimerMessage;
import org.bigbluebutton.red5.client.messaging.BroadcastClientMessage;
import org.bigbluebutton.red5.client.messaging.ConnectionInvokerService;
import org.bigbluebutton.red5.client.messaging.DirectClientMessage;
import org.bigbluebutton.red5.service.TimerKeyUtil;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TimerClientMessageSender {
	private ConnectionInvokerService service;
	
	public TimerClientMessageSender(ConnectionInvokerService service) {
		this.service = service;
	}

	public void handleTimerMessage(String message) {

		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(message);

		if (obj.has("header") && obj.has("payload")) {
			JsonObject header = (JsonObject) obj.get("header");

			if (header.has("name")) {
				String messageName = header.get("name").getAsString();
				switch (messageName) {
					case SendPublicTimerMessage.SEND_PUBLIC_TIMER_MESSAGE:
						SendPublicTimerMessage spucm = SendPublicTimerMessage.fromJson(message);

						if (spucm != null) {
							processSendPublicTimerMessage(spucm);
						}
						break;
					case GetTimerHistoryReplyMessage.GET_TIMER_HISTORY_REPLY:
						GetTimerHistoryReplyMessage gch = GetTimerHistoryReplyMessage.fromJson(message);

						if (gch != null) {
							processGetTimerHistoryReply(gch);
						}
						break;
				}
			}
		}
	}


	private void processSendPublicTimerMessage(SendPublicTimerMessage msg) {
		Map<String, Object> messageInfo = new HashMap<String, Object>();
		messageInfo.put(TimerKeyUtil.TIMER_TYPE, msg.messageInfo.get(TimerKeyUtil.TIMER_TYPE));
		messageInfo.put(TimerKeyUtil.FROM_USERID, msg.messageInfo.get(TimerKeyUtil.FROM_USERID));
		messageInfo.put(TimerKeyUtil.FROM_USERNAME, msg.messageInfo.get(TimerKeyUtil.FROM_USERNAME));
		messageInfo.put(TimerKeyUtil.TO_USERID, msg.messageInfo.get(TimerKeyUtil.TO_USERID));
		messageInfo.put(TimerKeyUtil.TO_USERNAME, msg.messageInfo.get(TimerKeyUtil.TO_USERNAME));
		messageInfo.put(TimerKeyUtil.FROM_TIME, msg.messageInfo.get(TimerKeyUtil.FROM_TIME));
		messageInfo.put(TimerKeyUtil.FROM_TZ_OFFSET, msg.messageInfo.get(TimerKeyUtil.FROM_TZ_OFFSET));
		messageInfo.put(TimerKeyUtil.FROM_COLOR, msg.messageInfo.get(TimerKeyUtil.FROM_COLOR));
		messageInfo.put(TimerKeyUtil.MESSAGE, msg.messageInfo.get(TimerKeyUtil.MESSAGE));

		BroadcastClientMessage m = new BroadcastClientMessage(msg.meetingId, "TimerReceivePublicMessageCommand", messageInfo);
		service.sendMessage(m);
	}

	private void processGetTimerHistoryReply(GetTimerHistoryReplyMessage gch) {

		Map<String, Object> args = new HashMap<String, Object>();	
		args.put("meetingId", gch.meetingId);
		args.put("requester_id", gch.requesterId);
		args.put("timer_history", gch.timerHistory);

		Map<String, Object> message = new HashMap<String, Object>();
		Gson gson = new Gson();
		message.put("msg", gson.toJson(args.get("timer_history")));

		DirectClientMessage m = new DirectClientMessage(gch.meetingId, gch.requesterId, "TimerRequestMessageHistoryReply", message);
		service.sendMessage(m);
	}

}
