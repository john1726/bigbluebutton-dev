package org.bigbluebutton.lib.timer.models {
	
	import mx.collections.ArrayCollection;
	
	import org.osflash.signals.ISignal;
	import org.osflash.signals.Signal;
	
	public class TimerMessagesSession implements ITimerMessagesSession {
		private var _publicTimer:TimerMessages = new TimerMessages();
		
		private var _timerMessageChangeSignal:ISignal = new Signal();
		
		public function set publicTimer(value:TimerMessages):void {
			_publicTimer = value;
		}
		
		public function get publicTimer():TimerMessages {
			return _publicTimer;
		}
		
		public function timerMessageDispatchSignal(UserID:String):void {
			if (_timerMessageChangeSignal) {
				_timerMessageChangeSignal.dispatch(UserID);
			}
		}
		
		public function get timerMessageChangeSignal():ISignal {
			return _timerMessageChangeSignal;
		}
		
		public function set timerMessageChangeSignal(signal:ISignal):void {
			_timerMessageChangeSignal = signal;
		}
	}
}
