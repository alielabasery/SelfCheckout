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
	
	public static void registerAttendentController(AttendentController attendentControllerInstance) {
		attendentController = attendentControllerInstance;
	}
	
	public static void registerCheckoutStation(String checkoutStationName, CheckoutController checkoutController) {
		checkoutStations.put(checkoutStationName.toLowerCase(), new CheckoutStation(checkoutStationName, checkoutController));
	}
	
	public static AttendentController getAttendentController() {
		return attendentController;
	}
		
	public static CheckoutController getCheckoutStationController(String checkoutStationName) {		
		CheckoutStation checkoutStation = checkoutStations.get(checkoutStationName.toLowerCase());
		return checkoutStation.getCheckoutController();
	}
	
	public static List<String> getCheckoutStationName() {
		List<String> stationNames = new ArrayList<>();
		for (CheckoutStation checkoutStation : checkoutStations.values()) {
			stationNames.add(checkoutStation.getCheckoutStationName());
		}
		return stationNames;
	}
}
