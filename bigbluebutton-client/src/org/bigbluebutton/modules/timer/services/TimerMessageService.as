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
package org.bigbluebutton.modules.timer.services
{
  import flash.events.IEventDispatcher;
  import flash.external.ExternalInterface;
  
  import org.as3commons.logging.api.ILogger;
  import org.as3commons.logging.api.getClassLogger;
  import org.bigbluebutton.core.BBB;
  import org.bigbluebutton.core.UsersUtil;
  import org.bigbluebutton.core.model.MeetingModel;
  import org.bigbluebutton.modules.timer.TimerConstants;
  import org.bigbluebutton.modules.timer.events.StartTimerSyncEvent;
  import org.bigbluebutton.modules.timer.vo.TimerMessageVO;
  import org.bigbluebutton.util.i18n.ResourceUtil;

  public class TimerMessageService
  {
	private static const LOGGER:ILogger = getClassLogger(TimerMessageService);      
    
    public var sender:MessageSender;
    public var receiver:MessageReceiver;
    public var dispatcher:IEventDispatcher;
    
    public function sendPublicMessageFromApi(message:Object):void
    {
      LOGGER.debug("sendPublicMessageFromApi");
      var msgVO:TimerMessageVO = new TimerMessageVO();
      msgVO.timerType = TimerConstants.PUBLIC_TIMER;
      msgVO.fromUserID = message.fromUserID;
      msgVO.fromUsername = message.fromUsername;
      msgVO.fromColor = message.fromColor;
      msgVO.fromTime = message.fromTime;
      msgVO.fromTimezoneOffset = message.fromTimezoneOffset;

      msgVO.message = message.message;
      
      sendPublicMessage(msgVO);
    }    
    
    public function sendPrivateMessageFromApi(message:Object):void
    {
	  LOGGER.debug("sendPrivateMessageFromApi");
      var msgVO:TimerMessageVO = new TimerMessageVO();
      msgVO.timerType = TimerConstants.PUBLIC_TIMER;
      msgVO.fromUserID = message.fromUserID;
      msgVO.fromUsername = message.fromUsername;
      msgVO.fromColor = message.fromColor;
      msgVO.fromTime = message.fromTime;
      msgVO.fromTimezoneOffset = message.fromTimezoneOffset;
      
      msgVO.toUserID = message.toUserID;
      msgVO.toUsername = message.toUsername;
      
      msgVO.message = message.message;
      
      sendPrivateMessage(msgVO);

    }
    
    public function sendPublicMessage(message:TimerMessageVO):void {
      sender.sendPublicMessage(message);
    }
    
    public function sendPrivateMessage(message:TimerMessageVO):void {
      sender.sendPrivateMessage(message);
    }
    
    public function getPublicTimerMessages():void {
      sender.getPublicTimerMessages();
    }
    
    private static const SPACE:String = " ";
    
    public function sendWelcomeMessage():void {
	  LOGGER.debug("sendWelcomeMessage");
      var welcome:String = BBB.initUserConfigManager().getWelcomeMessage();
      if (welcome != "") {
        var welcomeMsg:TimerMessageVO = new TimerMessageVO();
        welcomeMsg.timerType = TimerConstants.PUBLIC_TIMER;
        welcomeMsg.fromUserID = SPACE;
        welcomeMsg.fromUsername = SPACE;
        welcomeMsg.fromColor = "86187";
        welcomeMsg.fromTime = new Date().getTime();
        welcomeMsg.fromTimezoneOffset = new Date().getTimezoneOffset();
        welcomeMsg.toUserID = SPACE;
        welcomeMsg.toUsername = SPACE;
        welcomeMsg.message = welcome;
        
        var welcomeMsgEvent:StartTimerSyncEvent = new StartTimerSyncEvent(StartTimerSyncEvent.SYNC_START_TIMER);
        welcomeMsgEvent.message = welcomeMsg;
        welcomeMsgEvent.history = false;
        dispatcher.dispatchEvent(welcomeMsgEvent);
        
        //Say that client is ready when sending the welcome message
        ExternalInterface.call("clientReady", ResourceUtil.getInstance().getString('bbb.accessibility.clientReady'));
      }	
      
      if (UsersUtil.amIModerator()) {
        if (MeetingModel.getInstance().modOnlyMessage != null) {
          var moderatorOnlyMsg:TimerMessageVO = new TimerMessageVO();
          moderatorOnlyMsg.timerType = TimerConstants.PUBLIC_TIMER;
          moderatorOnlyMsg.fromUserID = SPACE;
          moderatorOnlyMsg.fromUsername = SPACE;
          moderatorOnlyMsg.fromColor = "86187";
          moderatorOnlyMsg.fromTime = new Date().getTime();
          moderatorOnlyMsg.fromTimezoneOffset = new Date().getTimezoneOffset();
          moderatorOnlyMsg.toUserID = SPACE;
          moderatorOnlyMsg.toUsername = SPACE;
          moderatorOnlyMsg.message = MeetingModel.getInstance().modOnlyMessage;
          
          var moderatorOnlyMsgEvent:StartTimerSyncEvent = new StartTimerSyncEvent(StartTimerSyncEvent.SYNC_START_TIMER);
          moderatorOnlyMsgEvent.message = moderatorOnlyMsg;
          moderatorOnlyMsgEvent.history = false;
          dispatcher.dispatchEvent(moderatorOnlyMsgEvent);
        }
      }
    }
  }
}