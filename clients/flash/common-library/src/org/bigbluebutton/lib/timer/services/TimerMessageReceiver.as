package org.bigbluebutton.lib.timer.services {
	
	import org.bigbluebutton.lib.timer.models.TimerMessageVO;
	import org.bigbluebutton.lib.timer.models.ITimerMessagesSession;
	import org.bigbluebutton.lib.common.models.IMessageListener;
	import org.bigbluebutton.lib.main.models.IUserSession;
	
	public class TimerMessageReceiver implements IMessageListener {
		private const LOG:String = "TimerMessageReceiver::";
		
		public var userSession:IUserSession;
		
		public var timerMessagesSession:ITimerMessagesSession;
		
		public function TimerMessageReceiver(userSession:IUserSession, timerMessagesSession:ITimerMessagesSession) {
			this.userSession = userSession;
			this.timerMessagesSession = timerMessagesSession;
		}
		
		public function onMessage(messageName:String, message:Object):void {
			switch (messageName) {
				case "TimerReceivePublicMessageCommand":
					handleTimerReceivePublicMessageCommand(message);
					break;
				case "TimerRequestMessageHistoryReply":
					handleTimerRequestMessageHistoryReply(message);
					break;
				default:
					//   LogUtil.warn("Cannot handle message [" + messageName + "]");
			}
		}
		
		private function handleTimerRequestMessageHistoryReply(message:Object):void {
			trace(LOG + "Handling timer history message [" + message.msg + "]");
			var timers:Array = JSON.parse(message.msg) as Array;
			
			for (var i:int = 0; i < timers.length; i++) {
				handleTimerReceivePublicMessageCommand(timers[i]);
			}
		}
		
		private function handleTimerReceivePublicMessageCommand(message:Object):void {
			trace(LOG + "Handling public timer message [" + message.message + "]");
			var msg:TimerMessageVO = new TimerMessageVO();
			msg.timerType = message.timerType;
			msg.fromUserID = message.fromUserID;
			msg.fromUsername = message.fromUsername;
			msg.fromColor = message.fromColor;
			msg.fromLang = message.fromLang;
			msg.fromTime = message.fromTime;
			msg.fromTimezoneOffset = message.fromTimezoneOffset;
			msg.toUserID = message.toUserID;
			msg.toUsername = message.toUsername;
			msg.message = message.message;
			timerMessagesSession.publicTimer.newTimerMessage(msg);
		}
	}
}
