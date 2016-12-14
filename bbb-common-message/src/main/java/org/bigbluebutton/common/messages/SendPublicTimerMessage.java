package org.bigbluebutton.common.messages;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SendPublicTimerMessage implements IBigBlueButtonMessage {
	public static final String SEND_PUBLIC_TIMER_MESSAGE = "send_public_timer_message";
	public static final String VERSION = "0.0.1";

	public final String meetingId;
	public final String requesterId;
	public final Map<String, String> messageInfo;

	public SendPublicTimerMessage(String meetingId, String requesterId,
			Map<String, String> message) {
		this.meetingId = meetingId;
		this.requesterId = requesterId;
		this.messageInfo = message;
	}

	public String toJson() {
		HashMap<String, Object> payload = new HashMap<String, Object>();

		Map<String, String> message = new HashMap<String, String>();

		message.put(TimerKeyUtil.TIMER_TYPE, messageInfo.get(TimerKeyUtil.TIMER_TYPE));
		message.put(TimerKeyUtil.MESSAGE, messageInfo.get(TimerKeyUtil.MESSAGE));
		message.put(TimerKeyUtil.TO_USERNAME, messageInfo.get(TimerKeyUtil.TO_USERNAME));
		message.put(TimerKeyUtil.FROM_TZ_OFFSET, messageInfo.get(TimerKeyUtil.FROM_TZ_OFFSET));
		message.put(TimerKeyUtil.FROM_COLOR, messageInfo.get(TimerKeyUtil.FROM_COLOR));
		message.put(TimerKeyUtil.TO_USERID, messageInfo.get(TimerKeyUtil.TO_USERID));
		message.put(TimerKeyUtil.FROM_USERID, messageInfo.get(TimerKeyUtil.FROM_USERID));
		message.put(TimerKeyUtil.FROM_TIME, messageInfo.get(TimerKeyUtil.FROM_TIME));
		message.put(TimerKeyUtil.FROM_USERNAME, messageInfo.get(TimerKeyUtil.FROM_USERNAME));

		payload.put(Constants.MESSAGE, message);
		payload.put(Constants.MEETING_ID, meetingId);

		java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(SEND_PUBLIC_TIMER_MESSAGE, VERSION, null);

		return MessageBuilder.buildJson(header, payload);
	}

	public static SendPublicTimerMessage fromJson(String message) {
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(message);

		if (obj.has("header") && obj.has("payload")) {
			JsonObject header = (JsonObject) obj.get("header");
			JsonObject payload = (JsonObject) obj.get("payload");

			if (header.has("name")) {
				String messageName = header.get("name").getAsString();
				if (SEND_PUBLIC_TIMER_MESSAGE.equals(messageName)) {
					if (payload.has(Constants.MEETING_ID) 
							&& payload.has(Constants.MESSAGE)) {
						String meetingId = payload.get(Constants.MEETING_ID).getAsString();

						JsonObject msgObj = (JsonObject) payload.get(Constants.MESSAGE).getAsJsonObject();
						Map<String, String> messageInfo = new HashMap<String, String>();

						if (msgObj.has(TimerKeyUtil.TIMER_TYPE) 
								&& msgObj.has(TimerKeyUtil.MESSAGE)
								&& msgObj.has(TimerKeyUtil.TO_USERNAME)
								&& msgObj.has(TimerKeyUtil.FROM_TZ_OFFSET)
								&& msgObj.has(TimerKeyUtil.FROM_COLOR)
								&& msgObj.has(TimerKeyUtil.TO_USERID)
								&& msgObj.has(TimerKeyUtil.FROM_USERID)
								&& msgObj.has(TimerKeyUtil.FROM_TIME)
								&& msgObj.has(TimerKeyUtil.FROM_USERNAME)){
							messageInfo.put(TimerKeyUtil.TIMER_TYPE, msgObj.get(TimerKeyUtil.TIMER_TYPE).getAsString());
							messageInfo.put(TimerKeyUtil.MESSAGE, msgObj.get(TimerKeyUtil.MESSAGE).getAsString());
							messageInfo.put(TimerKeyUtil.TO_USERNAME, msgObj.get(TimerKeyUtil.TO_USERNAME).getAsString());
							messageInfo.put(TimerKeyUtil.FROM_TZ_OFFSET, msgObj.get(TimerKeyUtil.FROM_TZ_OFFSET).getAsString());
							messageInfo.put(TimerKeyUtil.FROM_COLOR, msgObj.get(TimerKeyUtil.FROM_COLOR).getAsString());
							messageInfo.put(TimerKeyUtil.TO_USERID, msgObj.get(TimerKeyUtil.TO_USERID).getAsString());
							messageInfo.put(TimerKeyUtil.FROM_USERID, msgObj.get(TimerKeyUtil.FROM_USERID).getAsString());
							messageInfo.put(TimerKeyUtil.FROM_TIME, msgObj.get(TimerKeyUtil.FROM_TIME).getAsString());
							messageInfo.put(TimerKeyUtil.FROM_USERNAME, msgObj.get(TimerKeyUtil.FROM_USERNAME).getAsString());

							String requesterId = messageInfo.get(TimerKeyUtil.FROM_USERID);

							return new SendPublicTimerMessage(meetingId, requesterId, messageInfo);
						} else if (msgObj.has(Constants.TIMER_TYPE) 
								&& msgObj.has(Constants.MESSAGE)
								&& msgObj.has(Constants.TO_USERNAME)
								&& msgObj.has(Constants.FROM_TZ_OFFSET)
								&& msgObj.has(Constants.FROM_COLOR)
								&& msgObj.has(Constants.TO_USERID)
								&& msgObj.has(Constants.FROM_USERID)
								&& msgObj.has(Constants.FROM_TIME)
								&& msgObj.has(Constants.FROM_USERNAME)){
							messageInfo.put(TimerKeyUtil.TIMER_TYPE, msgObj.get(Constants.TIMER_TYPE).getAsString());
							messageInfo.put(TimerKeyUtil.MESSAGE, msgObj.get(Constants.MESSAGE).getAsString());
							messageInfo.put(TimerKeyUtil.TO_USERNAME, msgObj.get(Constants.TO_USERNAME).getAsString());
							messageInfo.put(TimerKeyUtil.FROM_TZ_OFFSET, msgObj.get(Constants.FROM_TZ_OFFSET).getAsString());
							messageInfo.put(TimerKeyUtil.FROM_COLOR, msgObj.get(Constants.FROM_COLOR).getAsString());
							messageInfo.put(TimerKeyUtil.TO_USERID, msgObj.get(Constants.TO_USERID).getAsString());
							messageInfo.put(TimerKeyUtil.FROM_USERID, msgObj.get(Constants.FROM_USERID).getAsString());
							messageInfo.put(TimerKeyUtil.FROM_TIME, msgObj.get(Constants.FROM_TIME).getAsString());
							messageInfo.put(TimerKeyUtil.FROM_USERNAME, msgObj.get(Constants.FROM_USERNAME).getAsString());

							String requesterId = messageInfo.get(TimerKeyUtil.FROM_USERID);

							return new SendPublicTimerMessage(meetingId, requesterId, messageInfo);
						}
					}
				} 
			}
		}
		return null;
	}
}
