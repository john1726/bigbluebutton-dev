/**
* BigBlueButton open source conferencing system - http://www.bigbluebutton.org/
* 
* Copyright (c) 2012 BigBlueButton Inc. and by respective authors (see below).
*
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License as published by the Free Software
* Foundation; either version 3.0 of the License, or (at your option) any later
* version.
* 
* BigBlueButton is distributed in the hope that it will be useful, but WITHOUT ANY
* WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
* PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License along
* with BigBlueButton; if not, see <http://www.gnu.org/licenses/>.
*
*/
package org.bigbluebutton.red5.service;

import java.util.HashMap;
import java.util.Map;

import org.bigbluebutton.red5.BigBlueButtonSession;
import org.bigbluebutton.red5.Constants;
import org.bigbluebutton.red5.pubsub.MessagePublisher;
import org.red5.logging.Red5LoggerFactory;
import org.red5.server.api.Red5;
import org.slf4j.Logger;

public class TimerService {	
	private static Logger log = Red5LoggerFactory.getLogger( TimerService.class, "bigbluebutton" );
	
	private MessagePublisher red5BBBInGw;
	private int maxMessageLength;

	public void sendPublicTimerHistory() {
		String meetingID = Red5.getConnectionLocal().getScope().getName();
		String requesterID = getBbbSession().getInternalUserID();
		// Just hardcode as we don't really need it for flash client. (ralam may 7, 2014)
		String replyTo = meetingID + "/" + requesterID; 
		
		red5BBBInGw.getTimerHistory(meetingID, requesterID, replyTo);
	}
	
	private BigBlueButtonSession getBbbSession() {
		return (BigBlueButtonSession) Red5.getConnectionLocal().getAttribute(Constants.SESSION);
	}
	
	public void sendPublicTimerMessage(Map<String, Object> msg) {
		
		String timerType = msg.get(TimerKeyUtil.TIMER_TYPE).toString(); 
		String fromUserID = msg.get(TimerKeyUtil.FROM_USERID).toString();
		String fromUsername = msg.get(TimerKeyUtil.FROM_USERNAME ).toString();
		String fromColor = msg.get(TimerKeyUtil.FROM_COLOR).toString();
		String fromTime = msg.get(TimerKeyUtil.FROM_TIME).toString();   
		String fromTimezoneOffset = msg.get(TimerKeyUtil.FROM_TZ_OFFSET).toString();
		String toUserID = msg.get(TimerKeyUtil.TO_USERID).toString();
		String toUsername = msg.get(TimerKeyUtil.TO_USERNAME).toString();
		String timerText = msg.get(TimerKeyUtil.MESSAGE).toString();
        log.warn("sendPublicTimerMessage timerText: " + timerText);		

		Map<String, String> message = new HashMap<String, String>();
		message.put(TimerKeyUtil.TIMER_TYPE, timerType); 
		message.put(TimerKeyUtil.FROM_USERID, fromUserID);
		message.put(TimerKeyUtil.FROM_USERNAME, fromUsername);
		message.put(TimerKeyUtil.FROM_COLOR, fromColor);
		message.put(TimerKeyUtil.FROM_TIME, fromTime);   
		message.put(TimerKeyUtil.FROM_TZ_OFFSET, fromTimezoneOffset);
		message.put(TimerKeyUtil.TO_USERID, toUserID);
		message.put(TimerKeyUtil.TO_USERNAME, toUsername);
		message.put(TimerKeyUtil.MESSAGE, timerText);
	
		String meetingID = Red5.getConnectionLocal().getScope().getName();
		String requesterID = getBbbSession().getInternalUserID();

		// The message is being ignored in the red5 application to avoid copying it to any another application which that may cause a memory issue
		if (timerText.length() <= maxMessageLength) {
			red5BBBInGw.sendPublicTimerMessage(meetingID, requesterID, message);
		}
		else {
			log.warn("sendPublicTimerMessage maximum allowed message length exceeded (length: [" + timerText.length() + "], message: [" + timerText + "])");
		}
	}
	
	public void setRed5Publisher(MessagePublisher inGW) {
		red5BBBInGw = inGW;
	}
	
	public void setMaxMessageLength(int maxLength) {
		maxMessageLength = maxLength;
    }
}
