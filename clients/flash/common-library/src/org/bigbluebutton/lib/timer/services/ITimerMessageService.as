package org.bigbluebutton.lib.timer.services {
	
	import org.bigbluebutton.lib.timer.models.TimerMessageVO;
	import org.osflash.signals.ISignal;
	
	public interface ITimerMessageService {
		function get sendMessageOnSuccessSignal():ISignal;
		function get sendMessageOnFailureSignal():ISignal;
		function setupMessageSenderReceiver():void;
		function getPublicTimerMessages():void;
		function sendPublicMessage(message:TimerMessageVO):void;
		function sendWelcomeMessage():void;
	}
}
