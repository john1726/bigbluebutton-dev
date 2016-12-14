package org.bigbluebutton.core.pubsub.receivers;

import org.bigbluebutton.common.messages.GetTimerHistoryRequestMessage;
import org.bigbluebutton.common.messages.MessagingConstants;
import org.bigbluebutton.common.messages.SendPublicTimerMessage;

import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

import org.bigbluebutton.core.api.IBigBlueButtonInGW;

public class TimerMessageReceiver implements MessageHandler{

	private IBigBlueButtonInGW bbbGW;
	
	public TimerMessageReceiver(IBigBlueButtonInGW bbbGW) {
		this.bbbGW = bbbGW;
	}

	@Override
	public void handleMessage(String pattern, String channel, String message) {
		if (channel.equalsIgnoreCase(MessagingConstants.TO_TIMER_CHANNEL)) {
			JsonParser parser = new JsonParser();
			JsonObject obj = (JsonObject) parser.parse(message);
			if (obj.has("header") && obj.has("payload")) {
				JsonObject header = (JsonObject) obj.get("header");
				if (header.has("name")) {
					String messageName = header.get("name").getAsString();
					if (GetTimerHistoryRequestMessage.GET_TIMER_HISTORY_REQUEST.equals(messageName)) {
						GetTimerHistoryRequestMessage msg = GetTimerHistoryRequestMessage.fromJson(message);
						bbbGW.getTimerHistory(msg.meetingId, msg.requesterId, msg.replyTo);
					} else if (SendPublicTimerMessage.SEND_PUBLIC_TIMER_MESSAGE.equals(messageName)){
						SendPublicTimerMessage msg = SendPublicTimerMessage.fromJson(message);
						bbbGW.sendPublicMessage(msg.meetingId, msg.requesterId, msg.messageInfo);
					} 
				}
			}
		}
	}
}
