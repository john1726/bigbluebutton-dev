/**
* BigBlueButton open source conferencing system - http://www.bigbluebutton.org/
* 
* Copyright (c) 2012 BigBlueButton Inc. and by respective authors (see below).
*
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License as published by the Free Software
* Foundation; either version 3.0 of the License, or (at your option) any later
* version.
* 
* BigBlueButton is distributed in the hope that it will be useful, but WITHOUT ANY
* WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
* PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License along
* with BigBlueButton; if not, see <http://www.gnu.org/licenses/>.
*
*/
package org.bigbluebutton.core.managers {
	import mx.collections.ArrayCollection;

	/**
	 * The TimerManager allows you to interact with the user data of those currently logged in to the conference.
	 * 
	 */	
	public class TimerManager {
		private static var instance:TimerManager = null;
		private var listeners:ArrayCollection;

		/**
		 * This class is a singleton. Please initialize it using the getInstance() method.
		 * 
		 */		
		public function TimerManager(enforcer:SingletonEnforcer) {
			if (enforcer == null){
				throw new Error("There can only be 1 TimerManager instance");
			}
			initialize();
		}
		
		private function initialize():void{
			listeners = new ArrayCollection();
		}
		
		/**
		 * Return the single instance of the TimerManager class
		 */
		public static function getInstance():TimerManager{
			if (instance == null){
				instance = new TimerManager(new SingletonEnforcer());
			}
			return instance;
		}
	}
}

class SingletonEnforcer{}
