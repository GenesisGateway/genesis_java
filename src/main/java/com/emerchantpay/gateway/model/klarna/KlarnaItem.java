package com.emerchantpay.gateway.model.klarna;

import com.emerchantpay.gateway.api.Request;
import com.emerchantpay.gateway.api.RequestBuilder;
import com.emerchantpay.gateway.api.constants.KlarnaItemTypes;
import com.emerchantpay.gateway.model.ProductIdentifiers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;

public class KlarnaItem extends Request {
    private String itemType; //Order line type
    private Integer quantity; //Non-negative. The item quantity.
    private BigDecimal unitPrice;//Minor units. Includes tax, excludes discount. (max value: 100000000)
    private BigDecimal totalAmount; //Includes tax and discount. Must match (quantity unit price) - to- tal discount amount divided by quan- tity. (max value: 100000000)
    private String reference; //Article number, SKU or similar.
    private String name; // Descriptive item name.
    private BigDecimal taxRate;  //Non-negative. In percent, two implicit decimals. I.e 2500 = 25.00 percent.
    private BigDecimal totalDiscountAmount; //Non-negative minor units. Includes tax.
    private BigDecimal totalTaxAmount; //Must be within 1 of total amount - to- tal amount 10000 / (10000 + tax rate). Negative when type is discount.
    private String imageUrl; //URL to an image that can be later embedded in communications between Klarna and the customer. (max 1024 characters)
    private String productUrl; //URL to an image that can be later embedded in communications between Klarna and the customer. (max 1024 characters)
    private String quantityUnit; //Unit used to describe the quantity, e.g. kg, pcs... If defined has to be 1-8 char- acters
    private ProductIdentifiers productIdentifiers; //List with product identifiers
    private String brand; //The product’s brand name as generally recognized by consumers. If no brand is available for a product, do not sup- ply any value.
    private String categoryPath; //The product’s category path as used in the merchant’s webshop. Include the full and most detailed category and separate the segments with ’ ¿ ’
    private String globalTradeItemNumber; //The product’s Global Trade Item Number (GTIN). Common types of GTIN are EAN, ISBN or UPC. Ex- clude dashes and spaces, where possi- ble
    private String manufacturerPartNumber; //The product’s Manufacturer Part Number (MPN), which - together with the brand - uniquely identifies a product. Only submit MPNs assigned by a manufacturer and use the most specific MPN possible
    private MerchantData merchantData; //List with merchant data

    public KlarnaItem(String name, String itemType, Integer quantity, BigDecimal unitPrice, BigDecimal totalAmount) {
        super();

        this.name = name;
        this.itemType = itemType;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalAmount = totalAmount;
    }

    public KlarnaItem setReference(String reference) {
        this.reference = reference;
        return this;
    }

    public KlarnaItem setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public KlarnaItem setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
        return this;
    }

    public KlarnaItem setTotalTaxAmount(BigDecimal totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
        return this;
    }

    public KlarnaItem setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public KlarnaItem setProductUrl(String productUrl) {
        this.productUrl = productUrl;
        return this;
    }

    public KlarnaItem setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
        return this;
    }

    public ProductIdentifiers setProductIdentifiers(ProductIdentifiers productIdentifiers) {
        this.productIdentifiers = productIdentifiers;
        return this.productIdentifiers;
    }

    public MerchantData setMerchantData(MerchantData merchantData) {
        this.merchantData = merchantData;
        return this.merchantData;
    }

    @Override
    public String toXML() {
        return buildRequest("item").toXML();
    }

    @Override
    public String toQueryString(String root) {
        return buildRequest(root).toQueryString();
    }

    protected RequestBuilder buildRequest(String root) {
        RequestBuilder builder = new RequestBuilder(root);

        // Convert amounts
        convertAmounts();

        // Product Identifiers
        if (productIdentifiers != null) {
            builder.addElement(productIdentifiers.toXML());
        }

        // Merchant Data
        if (merchantData != null) {
            builder.addElement(merchantData.toXML());
        }

        builder.addElement("item_type", itemType)
                .addElement("quantity", quantity)
                .addElement("unit_price", unitPrice)
                .addElement("total_amount", totalAmount)
                .addElement("reference", reference)
                .addElement("name", name)
                .addElement("tax_rate", taxRate)
                .addElement("total_discount_amount", totalDiscountAmount)
                .addElement("total_tax_amount", totalTaxAmount)
                .addElement("image_url", imageUrl)
                .addElement("product_url", productUrl)
                .addElement("quantity_unit", quantityUnit);

        return builder;
    }

    protected void convertAmounts() {
        BigDecimal multiplyExp = new BigDecimal(Math.pow(10, 2), MathContext.DECIMAL64);

        // Unit price
        if (unitPrice != null && unitPrice.doubleValue() > 0) {
            unitPrice = unitPrice.multiply(multiplyExp);
            unitPrice = new BigDecimal(unitPrice.intValue());
        } else {
            unitPrice = new BigDecimal(0);
        }

        // Total discount
        if (totalDiscountAmount != null && totalDiscountAmount.doubleValue() > 0) {
            totalDiscountAmount = totalDiscountAmount.multiply(multiplyExp);
        } else {
            totalDiscountAmount = new BigDecimal(0);
        }

        // Total amount convertion
        if (quantity > 0 && unitPrice != null && unitPrice.doubleValue() > 0) {
            Integer totalAmountInt = 0;

            if (isDiscount() && totalDiscountAmount != null
                    && totalDiscountAmount.doubleValue() > 0) {
                totalAmountInt = (quantity * unitPrice.intValue()) - totalDiscountAmount.intValue();
            } else {
                totalDiscountAmount = new BigDecimal(0);
                totalAmountInt = quantity * unitPrice.intValue();
            }

            totalAmount = new BigDecimal(totalAmountInt);
        }

        // Tax rate convertion
        if (taxRate != null && taxRate.doubleValue() > 0) {
            taxRate = taxRate.multiply(multiplyExp);
        } else {
            taxRate = new BigDecimal(0);
        }

        // Total tax amount convertion
        if (totalTaxAmount != null && totalTaxAmount.doubleValue() > 0) {
            totalTaxAmount.multiply(multiplyExp);
            Integer totalTaxAmountInt = (totalAmount.intValue() - (totalAmount.intValue() * 10000))
                    / (taxRate.intValue() + 10000);
            totalTaxAmount = new BigDecimal(totalTaxAmountInt);
            totalTaxAmount = new BigDecimal(Math.ceil(totalTaxAmount.doubleValue()));
        }
    }

    public BigDecimal getTotalAmount() {
        if (totalAmount != null && totalAmount.doubleValue() > 0) {
            return totalAmount;
        } else {
            return new BigDecimal(0);
        }
    }

    public BigDecimal getTotalTaxAmount() {
        if (totalTaxAmount != null && totalTaxAmount.doubleValue() > 0) {
            return totalTaxAmount;
        } else {
            return new BigDecimal(0);
        }
    }

    protected Boolean isDiscount() {
        if (itemType.equals(KlarnaItemTypes.DISCOUNT)) {
            return true;
        } else {
            return false;
        }
    }

    protected HashMap<String, String> setRequiredParams() {
        HashMap<String, String> requiredParamsMap = new HashMap<String, String>();

        requiredParamsMap.put("item_type", itemType);
        requiredParamsMap.put("name", name);
        requiredParamsMap.put("quantity", String.valueOf(quantity));
        requiredParamsMap.put("unit_price", String.valueOf(unitPrice));
        requiredParamsMap.put("total_amount", String.valueOf(totalAmount));

        return requiredParamsMap;
    }

    public HashMap<String, String> getRequiredParams() {
        return setRequiredParams();
    }
}