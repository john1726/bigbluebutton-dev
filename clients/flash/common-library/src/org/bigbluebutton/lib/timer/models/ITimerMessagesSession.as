package org.bigbluebutton.lib.timer.models {
	
	import mx.collections.ArrayCollection;
	
	import org.osflash.signals.ISignal;
	
	public interface ITimerMessagesSession {
		function get publicTimer():TimerMessages;
		function set publicTimer(value:TimerMessages):void;
		function get timerMessageChangeSignal():ISignal;
		function set timerMessageChangeSignal(signal:ISignal):void;
		function timerMessageDispatchSignal(UserID:String):void
	}
}
