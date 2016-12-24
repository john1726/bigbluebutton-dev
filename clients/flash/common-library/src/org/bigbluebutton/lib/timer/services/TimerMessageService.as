package org.bigbluebutton.lib.timer.services {
	
	import org.bigbluebutton.lib.timer.models.TimerMessageVO;
	import org.bigbluebutton.lib.timer.models.ITimerMessagesSession;
	import org.bigbluebutton.lib.common.models.IMessageListener;
	import org.bigbluebutton.lib.main.models.IConferenceParameters;
	import org.bigbluebutton.lib.main.models.IUserSession;
	import org.osflash.signals.ISignal;
	import org.osflash.signals.Signal;
	
	public class TimerMessageService implements ITimerMessageService {
		
		[Inject]
		public var userSession:IUserSession;
		
		[Inject]
		public var conferenceParameters:IConferenceParameters;
		
		[Inject]
		public var timerMessagesSession:ITimerMessagesSession;
		
		public var timerMessageSender:TimerMessageSender;
		
		public var timerMessageReceiver:TimerMessageReceiver;
		
		private var _sendMessageOnSuccessSignal:ISignal = new Signal();
		
		private var _sendMessageOnFailureSignal:ISignal = new Signal();
		
		public function get sendMessageOnSuccessSignal():ISignal {
			return _sendMessageOnSuccessSignal;
		}
		
		public function get sendMessageOnFailureSignal():ISignal {
			return _sendMessageOnFailureSignal;
		}
		
		public function TimerMessageService() {
		}
		
		public function setupMessageSenderReceiver():void {
			timerMessageSender = new TimerMessageSender(userSession, _sendMessageOnSuccessSignal, _sendMessageOnFailureSignal);
			timerMessageReceiver = new TimerMessageReceiver(userSession, timerMessagesSession);
			userSession.mainConnection.addMessageListener(timerMessageReceiver as IMessageListener);
		}
		
		public function getPublicTimerMessages():void {
			timerMessageSender.getPublicTimerMessages();
		}
        //TODO: change this to sendPublicMessage?
		public function sendPublicMessage(message:TimerMessageVO):void {
			timerMessageSender.sendPublicMessage(message);
		}
		
		public function sendPrivateMessage(message:TimerMessageVO):void {
			timerMessageSender.sendPrivateMessage(message);
		}
		
		/**
		 * Creates new instance of TimerMessageVO with Welcome message as message string
		 * and imitates new public message being sent
		 **/
		public function sendWelcomeMessage():void {
			// retrieve welcome message from conference parameters
			var welcome:String = conferenceParameters.welcome;
			if (welcome != "") {
				var msg:TimerMessageVO = new TimerMessageVO();
				msg.timerType = "PUBLIC_TIMER"
				msg.fromUserID = " ";
				msg.fromUsername = " ";
				msg.fromColor = "86187";
				msg.fromLang = "en";
				msg.fromTime = new Date().time;
				msg.fromTimezoneOffset = new Date().timezoneOffset;
				msg.toUserID = " ";
				msg.toUsername = " ";
				msg.message = welcome;
				// imitate new public message being sent
				timerMessageReceiver.onMessage("TimerReceivePublicMessageCommand", msg);
			}
		}
	}
}
