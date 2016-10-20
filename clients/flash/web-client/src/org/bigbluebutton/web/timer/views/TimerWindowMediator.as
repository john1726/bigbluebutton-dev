package org.bigbluebutton.web.timer.views {
	import flash.events.MouseEvent;
	
	import robotlegs.bender.bundles.mvcs.Mediator;
	
	public class TimerWindowMediator extends Mediator {
		
		[Inject]
		public var view:TimerWindow;
		
		override public function initialize():void {

		}
		
		override public function destroy():void {
			super.destroy();
			view = null;
		}
	}
}
