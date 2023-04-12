/** 
* Group Members: 
* 
* Ella Tomlinson (30140549)
* Kofi Frempong (30054189) 
* Adam Beauferris (30056865) 
* Niran Malla (30086877)
* Owen Tinning (30102041)
* Victor Campos Goitia (30106934)
* Zoe Kirsman (30113704) 
* Youssef Abdelrhafour (30085837) 
* James Rettie (30123362) 
* Rezwan Ahmed (30134609)
* Angeline Tran (30139846) 
* Saad Elkadri (30089084) 
* Dante Kirsman (30120778) 
* Riyad Abdullayev (30140509)
* Saksham Puri (30140617) 
* Faisal Islam (30140826)
* Naheen Kabir (30142101) 
* Jose Perales Rivera (30143354) 
* Aditi Yadav (30143652)
* Sahaj Malhotra () 
* Ali Elabasery (30148424)
* Fabiha Fairuzz Subha (30148674) 
* Umesh Oad (30152293)
* Daniel Boettcher (30153811) 
* Nam Nguyen Vu (30154892)
* 
*/
package Networking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.autovend.software.controllers.AttendentController;
import com.autovend.software.controllers.CheckoutController;
import com.autovend.software.pojo.CheckoutStation;

public class NetworkController {
	private static AttendentController attendentController = null;
	private static HashMap<String, CheckoutController> checkoutControllers = new HashMap<>();
	
	/**
	 * Registers an attendant controller
	 * @param attendentControllerInstance
	 * 		The AttendantController to register
	 */
	public static void registerAttendentController(AttendentController attendentControllerInstance) {
		attendentController = attendentControllerInstance;
	}
	
	/**
	 * Registers a checkout station
	 * @param checkoutStationName
	 * 		The name of the checkout station to register
	 * @param checkoutController
	 * 		The controller of the named checkout station
	 */
	public static void registerCheckoutStation(String checkoutStationName, CheckoutController checkoutController) {
		checkoutControllers.put(checkoutStationName.toLowerCase(), checkoutController);
		if (attendentController != null) {
			attendentController.getSupervisionStation().add(checkoutController.getSelfCheckoutStation());
		}
	}
	
	public static void deregisterCheckoutStation(String checkoutStationName) {
		System.out.println("NetworkController: Deregistering station " + checkoutStationName);
		checkoutControllers.remove(checkoutStationName.toLowerCase());
	}
	
	/**
	 * Returns the attendant controller
	 * @return
	 * 		The attendant controller
	 */
	public static AttendentController getAttendentController() {
		return attendentController;
	}
		
	/**
	 * Returns the checkout controller
	 * @param checkoutStationName
	 * 		The name of the checkout station to get the controller of
	 * @return
	 * 		The controller of the named checkout station
	 */
	public static CheckoutController getCheckoutStationController(String checkoutStationName) {		
		return checkoutControllers.get(checkoutStationName.toLowerCase());
	}
	
	/**
	 * Returns the list of checkout station names
	 * @return
	 * 		The list of checkout station names
	 */
	public static List<String> getCheckoutStationNames() {
		List<String> stationNames = new ArrayList<>();
		for (CheckoutController station : checkoutControllers.values()) {
			stationNames.add(station.getStationName());
		}
		return stationNames;
	}
}