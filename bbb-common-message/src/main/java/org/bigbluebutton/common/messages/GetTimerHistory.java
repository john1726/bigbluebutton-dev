package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GetTimerHistory implements IBigBlueButtonMessage {
	public static final String GET_TIMER_HISTORY_REQUEST = "get_timer_history_request";
	public static final String VERSION = "0.0.1";

	public final String meetingId;
	public final String replyTo;
	public final String requesterId;


	public GetTimerHistory(String meetingId, String requesterId, String replyTo) {
		this.meetingId = meetingId;
		this.replyTo = replyTo;
		this.requesterId = requesterId;
	}

	public String toJson() {
		HashMap<String, Object> payload = new HashMap<String, Object>();
		payload.put(Constants.MEETING_ID, meetingId);
		payload.put(Constants.REPLY_TO, replyTo);
		payload.put(Constants.REQUESTER_ID, requesterId);

		java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(GET_TIMER_HISTORY_REQUEST, VERSION, null);
		return MessageBuilder.buildJson(header, payload);
	}

	public static GetTimerHistory fromJson(String message) {
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(message);
		
		if (obj.has("header") && obj.has("payload")) {
			JsonObject header = (JsonObject) obj.get("header");
			JsonObject payload = (JsonObject) obj.get("payload");
			
			if (header.has("name")) {
				String messageName = header.get("name").getAsString();
				if (GET_TIMER_HISTORY_REQUEST.equals(messageName)) {
					if (payload.has(Constants.MEETING_ID) 
							&& payload.has(Constants.REPLY_TO)
							&& payload.has(Constants.REQUESTER_ID)) {
						String meetingId = payload.get(Constants.MEETING_ID).getAsString();
						String replyTo = payload.get(Constants.REPLY_TO).getAsString();
						String requesterId = payload.get(Constants.REQUESTER_ID).getAsString();
						return new GetTimerHistory(meetingId, replyTo, requesterId);
					}
				} 
			}
		}
		return null;
	}
}
