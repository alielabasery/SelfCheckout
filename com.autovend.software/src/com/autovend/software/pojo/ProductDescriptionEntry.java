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
package com.autovend.software.pojo;

import java.util.Arrays;

public class ProductDescriptionEntry {
    private String descriptiption;
    private char type;
    private Object code;

    /**
     * Constructor for ProductDescriptionEntry
     * @param descriptiption
     * 		The description
     * @param type
     * 		the type of the product
     * @param code
     * 		the product code
     */
    public ProductDescriptionEntry(String descriptiption, char type, Object code) {
        this.descriptiption = descriptiption;
        this.type = type;
        this.code = code;
    }

    /**
     * Getter for the description
     * @return
     * 		The description
     */
    public String getDescriptiption() {
        return descriptiption;
    }

    /**
     * Sets the description
     * @param descriptiption
     * 		The description to set
     */
    public void setDescriptiption(String descriptiption) {
        this.descriptiption = descriptiption;
    }

    /**
     * Getter for the type
     * @return
     * 		The type for the item
     */
    public char getType() {
        return type;
    }

    /**
     * Sets the type
     * @param type
     * 		Type to be set
     */
    public void setType(char type) {
        this.type = type;
    }

    /**
     * Getter for the code
     * @return
     * 		The code
     */
    public Object getCode() {
        return code;
    }

    /**
     * Setter for the code
     * @param code
     * 		Code to be set.
     */
    public void setCode(Object code) {
        this.code = code;
    }
}
