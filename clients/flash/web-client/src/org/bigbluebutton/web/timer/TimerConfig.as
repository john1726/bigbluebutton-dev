package org.bigbluebutton.web.timer {
	import org.bigbluebutton.web.timer.views.TimerWindow;
	import org.bigbluebutton.web.timer.views.TimerWindowMediator;
	
	import robotlegs.bender.extensions.mediatorMap.api.IMediatorMap;

	import robotlegs.bender.framework.api.IConfig;
	import robotlegs.bender.framework.api.IInjector;
	
	public class TimerConfig implements IConfig {
		
		[Inject]
		public var injector:IInjector;
		
		[Inject]
		public var mediatorMap:IMediatorMap;
		
		public function configure():void {
			dependencies();
			mediators();
		}
		
		/**
		 * Specifies all the dependencies for the feature
		 * that will be injected onto objects used by the
		 * application.
		 */
		private function dependencies():void {
			//injector.map(ITimerMessageService).toSingleton(TimerMessageService);
		}
		
		/**
		 * Maps view mediators to views.
		 */
		private function mediators():void {
			mediatorMap.map(TimerWindow).toMediator(TimerWindowMediator);
		}
		
	}
}