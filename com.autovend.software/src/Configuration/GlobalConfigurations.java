package Configuration;

import java.math.BigDecimal;

public class GlobalConfigurations {
    public static final String[] WORKSTATIONIDS = new String[] {"1", "2", "3", "4", "5", "6"};   
    public static final int[] BILLDENOMINATIONS = new int[] {5, 10, 20, 50, 100};
    public static final BigDecimal[] COINDENOMINATIONS = new BigDecimal[] {new BigDecimal(0.05), new BigDecimal(0.1), new BigDecimal(0.25), new BigDecimal(100), new BigDecimal(200)};
    public static final String TAXNAME1 = "GST";
    public static final double TAXRATE1 = 0.05;
    public static final String TAXNAME2 = null;
    public static final double TAXRATE2 = 0.0;
    public static final boolean COMPOUNDTAX = false;
}
