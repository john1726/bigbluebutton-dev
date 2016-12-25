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
  
  import org.as3commons.logging.api.ILogger;
  import org.as3commons.logging.api.getClassLogger;
  import org.bigbluebutton.core.BBB;
  import org.bigbluebutton.core.managers.ConnectionManager;
  import org.bigbluebutton.modules.timer.vo.TimerMessageVO;

  public class MessageSender
  {
	private static const LOGGER:ILogger = getClassLogger(MessageSender);
    
    public var dispatcher:IEventDispatcher;
    
    public function getPublicTimerMessages():void
    {  
      LOGGER.debug("Sending [timer.getPublicTimerMessages] to server.");
      var _nc:ConnectionManager = BBB.initConnectionManager();
      _nc.sendMessage("timer.sendPublicTimerHistory", 
        function(result:String):void { // On successful result
          LOGGER.debug(result); 
        },	                   
        function(status:String):void { // status - On error occurred
		  LOGGER.error(status); 
        }
      );
    }
    
    public function sendPublicTimerMessage(message:TimerMessageVO):void
    {  
	  LOGGER.debug("Sending [timer.sendPublicTimerMessage] to server. [{0}]", [message.message]);
      var _nc:ConnectionManager = BBB.initConnectionManager();
      _nc.sendMessage("timer.sendPublicTimerMessage", 
        function(result:String):void { // On successful result
		  LOGGER.debug(result); 
        },	                   
        function(status:String):void { // status - On error occurred
		  LOGGER.error(status); 
        },
        message.toObj()
      );
    }
  }
}