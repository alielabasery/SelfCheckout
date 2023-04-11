// SENG 300 Iteration 3 
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




package com.autovend.software.controllers;

import java.util.ArrayList;


public class SelectLanguageController {
	public ArrayList <String> languages = new ArrayList<String>();

	private boolean cancel = false;
	
	// boolean to signal the attendant
	public boolean attendantFlag = false;
	// boolean when customer decides to change the language
	private boolean languageChange = false;	
	private boolean confirmSelection = false;
	private boolean check_language = false;
	String userLanguage;

	// -- CONSTRUCTOR --
	/**
	 * constructor takes in argument arraylist of languages, the desired userlanguage, boolean audio, and final selection of customer
	 * @param args
	 */
	 public SelectLanguageController(ArrayList<String> languages, String userLanguage, boolean confirmSelection) {
		 this.languages = languages;
	
	}
	 
	 public void displayLanguageOptions() {
		    System.out.println("Available languages:");
		    for (String language : languages) {
		        System.out.println("- " + language);
		    }
		}
	 

	// boolean method which allows user to select a language 
	public boolean selectLanguage(String lang) {
			    for (String language : languages) {
			        if (language.equals(lang)) {
			            userLanguage = lang;
			            return true;
			        }
			    }
			    attendant();
			    return false;
			}
		 
		 
	 
	 

	 
	// method for calling attendant 
	 public void attendant() {
		 attendantFlag = true;
		 System.out.println("Attendant will be here shortly.");
	 }
	 
	 
	 
	 
	 /**
	  * to add a new language on top of the current available languages in system
	  * not available for users to add, only for developers to add a language if necessary
	  */
	 public void addLanguage(String language) {
		 languages.add(language);
	 }
	 
	 /**
	  * if customer decides to change language
	  */
	 public void changeLanguage(String userLanguage) {
		 languageChange = true;
		 attendant();
		 userLanguage = null;
	 }
	 
	 public void confirmSelection ( String userLanguage) {
		 System.out.println("You have selected:" + userLanguage);
		 confirmSelection = true;
	 }
	 
	 public void cancelSelection() {
		    System.out.println("Selection canceled.");
		    userLanguage = null;
		    cancel = true;
		}


	 
	 	// GETTERS AND SETTERS

		public boolean getConfirmSelection() {
			return confirmSelection;
		}



		public String getUserLanguage() {
			return userLanguage;
		}

		public void setUserLanguage(String userLanguage) {
			this.userLanguage = userLanguage;
		}


		public boolean getCheck_language() {
			return check_language;
		}


		public void setCheck_language(boolean check_language) {
			this.check_language = check_language;
		}

		public boolean getLanguageChange() {
			return languageChange;
		}
		
		public boolean isCancel() {
			return cancel;
		}


}
