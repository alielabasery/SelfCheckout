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
* Sahaj Malhotra (30144405) 
* Ali Elabasery (30148424)
* Fabiha Fairuzz Subha (30148674) 
* Umesh Oad (30152293)
* Daniel Boettcher (30153811) 
* Nam Nguyen Vu (30154892)
* 
*/





package com.autovend.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import com.autovend.software.controllers.SelectLanguageController;

public class SelectLanguageControllerTest {

    private SelectLanguageController selectLanguageController;
    private ArrayList<String> languages;

    @Before
    public void setUp() {
        languages = new ArrayList<>();
        languages.add("English");
        languages.add("Spanish");
        languages.add("French");
        selectLanguageController = new SelectLanguageController(languages, "English", false);
    }

    @Test
    public void testDisplayLanguageOptions() {
        selectLanguageController.displayLanguageOptions();
    }

    @Test
    public void testSelectLanguage() {
        boolean result1 = selectLanguageController.selectLanguage("Spanish");
        assertTrue(result1);
        assertEquals("Spanish", selectLanguageController.getUserLanguage());

        boolean result2 = selectLanguageController.selectLanguage("German");
        assertFalse(result2);
    }

    @Test
    public void testAddLanguage() {
        selectLanguageController.addLanguage("German");
        assertTrue(languages.contains("German"));
    }

    @Test
    public void testChangeLanguage() {
        selectLanguageController.changeLanguage("French");
        assertNull(selectLanguageController.getUserLanguage());
        assertTrue(selectLanguageController.getLanguageChange());
    }

    @Test
    public void testConfirmSelection() {
        selectLanguageController.confirmSelection("Spanish");
        assertTrue(selectLanguageController.getConfirmSelection());
    }

    @Test
    public void testCancelSelection() {
        selectLanguageController.cancelSelection();
        assertTrue(selectLanguageController.isCancel());
        assertNull(selectLanguageController.getUserLanguage());
    }
}
