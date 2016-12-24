package org.bigbluebutton.lib.timer.models {
	
	import mx.collections.ArrayCollection;
	
	import org.bigbluebutton.lib.timer.utils.TimerUtil;
	import org.osflash.signals.ISignal;
	import org.osflash.signals.Signal;
	
	public class TimerMessages {
		
		[Bindable]
		public var messages:ArrayCollection = new ArrayCollection();
		
		private var _newMessages:uint = 0;
		
		private var _autoTranslate:Boolean = false;
		
		private var _timerMessageChangeSignal:ISignal = new Signal();
		
		public function numMessages():int {
			return messages.length;
		}
		
		public function newTimerMessage(msg:TimerMessageVO):void {
			var cm:TimerMessage = new TimerMessage();
			if (messages.length == 0) {
				cm.lastSenderId = "";
				cm.lastTime = cm.time;
			} else {
				cm.lastSenderId = getLastSender();
				cm.lastTime = getLastTime();
			}
			cm.senderId = msg.fromUserID;
			cm.senderLanguage = msg.fromLang;
			cm.receiverLanguage = TimerUtil.getUserLang();
			cm.translate = _autoTranslate;
			cm.translatedText = msg.message;
			cm.senderText = msg.message;
			cm.name = msg.fromUsername;
			cm.senderColor = uint(msg.fromColor);
			cm.translatedColor = uint(msg.fromColor);
			cm.fromTime = msg.fromTime;
			cm.fromTimezoneOffset = msg.fromTimezoneOffset;
			var sentTime:Date = new Date();
			sentTime.setTime(cm.fromTime);
			cm.time = TimerUtil.getHours(sentTime) + ":" + TimerUtil.getMinutes(sentTime);
			messages.addItem(cm);
			_newMessages++;
			timerMessageChange(cm.senderId);
		}
		
		public function getAllMessageAsString():String {
			var allText:String = "";
			for (var i:int = 0; i < messages.length; i++) {
				var item:TimerMessage = messages.getItemAt(i) as TimerMessage;
				allText += "\n" + item.name + " - " + item.time + " : " + item.translatedText;
			}
			return allText;
		}
		
		private function getLastSender():String {
			var msg:TimerMessage = messages.getItemAt(messages.length - 1) as TimerMessage;
			return msg.senderId;
		}
		
		private function getLastTime():String {
			var msg:TimerMessage = messages.getItemAt(messages.length - 1) as TimerMessage;
			return msg.time;
		}
		
		public function get timerMessageChangeSignal():ISignal {
			return _timerMessageChangeSignal;
		}
		
		public function set timerMessageChangeSignal(signal:ISignal):void {
			_timerMessageChangeSignal = signal;
		}
		
		private function timerMessageChange(UserID:String = null):void {
			if (_timerMessageChangeSignal) {
				_timerMessageChangeSignal.dispatch(UserID);
			}
		}
		
		public function get newMessages():uint {
			return _newMessages;
		}
		
		public function resetNewMessages():void {
			_newMessages = 0;
			timerMessageChange();
		}
	}
}
