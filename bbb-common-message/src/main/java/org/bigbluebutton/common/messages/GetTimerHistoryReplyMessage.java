package org.bigbluebutton.common.messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GetTimerHistoryReplyMessage implements ISubscribedMessage {
	public static final String GET_TIMER_HISTORY_REPLY = "get_timer_history_reply";
	public static final String VERSION = "0.0.1";

	public final String meetingId;
	public final String requesterId;
	public final ArrayList<Map<String, Object>> timerHistory;


	public GetTimerHistoryReplyMessage(String meetingId, String requesterId, ArrayList<Map<String, Object>> timerHistory) {
		this.meetingId = meetingId;
		this.timerHistory = timerHistory;
		this.requesterId = requesterId;
	}

	public String toJson() {
		HashMap<String, Object> payload = new HashMap<String, Object>();
		payload.put(Constants.MEETING_ID, meetingId);
		payload.put(Constants.TIMER_HISTORY, timerHistory);
		payload.put(Constants.REQUESTER_ID, requesterId);

		java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(GET_TIMER_HISTORY_REPLY, VERSION, null);
		return MessageBuilder.buildJson(header, payload);
	}

	public static GetTimerHistoryReplyMessage fromJson(String message) {
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(message);
		if (obj.has("header") && obj.has("payload")) {
			JsonObject header = (JsonObject) obj.get("header");
			JsonObject payload = (JsonObject) obj.get("payload");

			if (header.has("name")) {
				String messageName = header.get("name").getAsString();
				if (GET_TIMER_HISTORY_REPLY.equals(messageName)) {
					if (payload.has(Constants.MEETING_ID) 
							&& payload.has(Constants.TIMER_HISTORY)
							&& payload.has(Constants.REQUESTER_ID)) {
						String meetingId = payload.get(Constants.MEETING_ID).getAsString();
						String requesterId = payload.get(Constants.REQUESTER_ID).getAsString();

						JsonArray history = (JsonArray) payload.get(Constants.TIMER_HISTORY);

						Util util = new Util();

						ArrayList<Map<String, Object>> timerHistory = util.extractTimerHistory(history);

						return new GetTimerHistoryReplyMessage(meetingId, requesterId, timerHistory);
					}
				} 
			}
		}
		return null;
	}
}
