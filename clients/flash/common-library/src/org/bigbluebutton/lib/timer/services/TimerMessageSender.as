package org.bigbluebutton.lib.timer.services {
	
	import org.bigbluebutton.lib.timer.models.TimerMessageVO;
	import org.bigbluebutton.lib.main.models.IUserSession;
	import org.osflash.signals.ISignal;
	import org.osflash.signals.Signal;
	
	public class TimerMessageSender {
		private const LOG:String = "TimerMessageSender::";
		
		public var userSession:IUserSession;
		
		private var successSendingMessageSignal:ISignal;
		
		private var failureSendingMessageSignal:ISignal;
		
		public function TimerMessageSender(userSession:IUserSession, successSendMessageSignal:ISignal, failureSendingMessageSignal:ISignal) {
			this.userSession = userSession;
			this.successSendingMessageSignal = successSendMessageSignal;
			this.failureSendingMessageSignal = failureSendingMessageSignal;
		}
		
		public function getPublicTimerMessages():void {
			trace(LOG + "Sending [timer.getPublicMessages] to server.");
			userSession.mainConnection.sendMessage("timer.sendPublicTimerHistory",
												   function(result:String):void { // On successful result
													   publicTimerMessagesOnSuccessSignal.dispatch(result);
												   },
												   function(status:String):void { // status - On error occurred
													   publicTimerMessagesOnFailureSignal.dispatch(status);
												   }
												   );
		}
		
		public function sendPublicMessage(message:TimerMessageVO):void {
			trace(LOG + "Sending [timer.sendPublicMessage] to server. [" + message.message + "]");
			userSession.mainConnection.sendMessage("timer.sendPublicMessage",
												   function(result:String):void { // On successful result
													   successSendingMessageSignal.dispatch(result);
												   },
												   function(status:String):void { // status - On error occurred
													   failureSendingMessageSignal.dispatch(status);
												   },
												   message.toObj()
												   );
		}
		
		private var _publicTimerMessagesOnSuccessSignal:Signal = new Signal();
		
		private var _publicTimerMessagesOnFailureSignal:Signal = new Signal();
		
		public function get publicTimerMessagesOnSuccessSignal():Signal {
			return _publicTimerMessagesOnSuccessSignal;
		}
		
		public function get publicTimerMessagesOnFailureSignal():Signal {
			return _publicTimerMessagesOnFailureSignal;
		}
	}
}
