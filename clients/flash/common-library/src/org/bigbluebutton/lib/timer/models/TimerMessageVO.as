package org.bigbluebutton.lib.timer.models {
	
	public class TimerMessageVO {
		// The type of timer (PUBLIC or PRIVATE)
		public var timerType:String;
		
		// The sender
		public var fromUserID:String;
		
		public var fromUsername:String;
		
		public var fromColor:String;
		
		// Store the UTC time when the message was sent.
		public var fromTime:Number;
		
		// Stores the timezone offset (in minutes) when the message was
		// sent. This is used by the receiver to convert to locale time.
		public var fromTimezoneOffset:Number;
		
		public var fromLang:String;
		
		// The receiver. 
		public var toUserID:String = "public_timer_userid";
		
		public var toUsername:String = "public_timer_username";
		
		public var message:String;
		
		public function toObj():Object {
			var m:Object = new Object();
			m.timerType = timerType;
			m.fromUserID = fromUserID;
			m.fromUsername = fromUsername;
			m.fromColor = fromColor;
			m.fromTime = fromTime;
			m.fromTimezoneOffset = fromTimezoneOffset;
			m.fromLang = fromLang;
			m.message = message;
			m.toUserID = toUserID;
			m.toUsername = toUsername;
			return m;
		}
	}
}
