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
	private static HashMap<String, CheckoutStation> checkoutStations = new HashMap<>();
	
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
		checkoutStations.put(checkoutStationName.toLowerCase(), new CheckoutStation(checkoutStationName, checkoutController));
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
		CheckoutStation checkoutStation = checkoutStations.get(checkoutStationName.toLowerCase());
		return checkoutStation.getCheckoutController();
	}
	
	/**
	 * Returns the list of checkout station names
	 * @return
	 * 		The list of checkout station names
	 */
	public static List<String> getCheckoutStationName() {
		List<String> stationNames = new ArrayList<>();
		for (CheckoutStation checkoutStation : checkoutStations.values()) {
			stationNames.add(checkoutStation.getCheckoutStationName());
		}
		return stationNames;
	}
}
