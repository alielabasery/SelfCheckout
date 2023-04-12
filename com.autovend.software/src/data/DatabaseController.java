package data;

import java.util.ArrayList;
import java.util.List;

import com.autovend.Barcode;
import com.autovend.PriceLookUpCode;
import com.autovend.external.ProductDatabases;
import com.autovend.products.BarcodedProduct;
import com.autovend.products.PLUCodedProduct;
import com.autovend.products.Product;
import com.autovend.software.pojo.ProductDescriptionEntry;

public class DatabaseController {
	
	/**
	 * Add a product to the database by Barcode
	 * @param code
	 * 		The barcode of the product
	 * @param product
	 * 		The product to be added
	 */
    public static void addBarcodedProduct(Barcode code, BarcodedProduct product) {
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(code, product);
        ProductIndex.productDescriptions.add(new ProductDescriptionEntry(product.getDescription(), 'B', code));
    }
    
    /**
     * Add a product to the database by the PLU code
     * @param code
     * 		The PLU code of the product
     * @param product
     * 		The product to be added
     */
    public static void addPLUdProduct(PriceLookUpCode code, PLUCodedProduct product) {
        ProductDatabases.PLU_PRODUCT_DATABASE.put(code, product);
        ProductIndex.productDescriptions.add(new ProductDescriptionEntry(product.getDescription(), 'P', code));
    }
    
    /**
     * Find the product by description
     * @param keyword
     * 		The keyword to search
     * @return
     * 		The list of objects containing that keyword
     */
    public static List<Object> findProductByDescription(String keyword) {
        List<Object> foundProducts = new ArrayList<>();
        keyword = keyword.toLowerCase();
        for (ProductDescriptionEntry entry: ProductIndex.productDescriptions) {
            if (entry.getDescriptiption().toLowerCase().contains(keyword)) {
                if (entry.getType() == 'B') {
                    foundProducts.add(ProductDatabases.BARCODED_PRODUCT_DATABASE.get(entry.getCode()));
                } else if (entry.getType() == 'P') {
                    foundProducts.add(ProductDatabases.PLU_PRODUCT_DATABASE.get(entry.getCode()));
                }
            }
        }
        return foundProducts;
    }
    
    /**
     * Returns a product by code
     * @param code
     * 		Code of the product
     * @param type
     * 		The type of the product
     * @return
     * 		The product searched
     */
    public static Product getProduct(Object code, char type) {
    	Product product = null;
    	if (type == 'P') {
    		product = ProductDatabases.PLU_PRODUCT_DATABASE.get(code);
    	} else if (type == 'B') {
    		product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(code);
    	}
    	return product;
    }
    
}
