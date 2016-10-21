package org.bigbluebutton.web.user.views {
	import mx.controls.Button;
	import mx.core.ClassFactory;
	
	import org.apache.flex.collections.ArrayList;
	import org.bigbluebutton.web.window.views.BBBWindow;
	import org.osflash.signals.Signal;
	
	public class TimerWindow extends BBBWindow {
		public function TimerWindow() {
			super();
			
			title = "Timer";
			width = 300;
			height = 400;
		}
	}
}
