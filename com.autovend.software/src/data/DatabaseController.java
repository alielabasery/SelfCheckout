package data;

import java.util.ArrayList;
import java.util.List;

import com.autovend.Barcode;
import com.autovend.PriceLookUpCode;
import com.autovend.external.ProductDatabases;
import com.autovend.products.BarcodedProduct;
import com.autovend.products.PLUCodedProduct;
import com.autovend.software.pojo.ProductDescriptionEntry;

public class DatabaseController {
    public static void addBarcodedProduct(Barcode code, BarcodedProduct product) {

        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(code, product);
        ProductIndex.productDescriptions.add(new ProductDescriptionEntry("", 'B', code));
    }
    public static void addPLUdProduct(PriceLookUpCode code, PLUCodedProduct product) {
        ProductDatabases.PLU_PRODUCT_DATABASE.put(code, product);
        ProductIndex.productDescriptions.add(new ProductDescriptionEntry(product.getDescription(), 'P', code));
    }
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
}
