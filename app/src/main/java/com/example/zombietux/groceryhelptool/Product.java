package com.example.zombietux.groceryhelptool;


public class Product {
    public static int cnt;
    private String ProductId;
    private String FullDisplayName;
    private String BrandName;
    private Boolean IsAgeRequired;
    private String SizeLabel;
    private String Size;
    private String ProductUrl;
    private String ProductImageUrl;
    private Boolean HasNewPrice;
    private Double RegularPrice;
    private String PromotionName;
    private Double SalesPrice;

    //{'ProductId':'00000_000000004905846692','BrandName':'Diva','FullDisplayName':' Endives',
// 'IsAgeRequired':false,'SizeLabel':'','Size':'','ProductUrl':'/en/product/endives/00000_000000004905846692',
// 'ProductImageUrl':'https://az836796.vo.msecnd.net/media/image/product/en/medium/0004905846692.jpg',
// 'HasNewPrice':false,'PromotionName':null,'RegularPrice':3.49000,'SalesPrice':null}"
    Product() {
        cnt++;
    }

    Product(String name, Double regularPrice) {
        FullDisplayName = name;
        RegularPrice = regularPrice;
        cnt++;
    }

    Product(String productId, String name, String brandName, Boolean isAgeRequired, String sizeLabel, String size, String productUrl, String productImageUrl, Boolean hasNewPrice, Double regularPrice, String promotionName, Double salesPrice) {

        setProductId(productId);
        setName(name);
        setBrandName(brandName);
        setAgeRequired(isAgeRequired);
        setSizeLabel(sizeLabel);
        setSize(size);
        setProductUrl(productUrl);
        setProductImageUrl(productImageUrl);
        setHasNewPrice(hasNewPrice);
        setRegularPrice(regularPrice);
        if (promotionName != null) {
            setPromotionName(promotionName);
        } else {
            setPromotionName("");

        }
        if (salesPrice != null) {
            setSalesPrice(salesPrice);
        } else {
            setSalesPrice(0.0);
        }
        cnt++;
    }

    public String getProductId() {
        return ProductId;
    }

    private void setProductId(String productId) {
        ProductId = productId;
    }

    public String getName() {
        return FullDisplayName;
    }

    public void setName(String name) {
        FullDisplayName = name;
    }

    public String getBrandName() {
        return BrandName;
    }

    private void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public Boolean getAgeRequired() {
        return IsAgeRequired;
    }

    private void setAgeRequired(Boolean ageRequired) {
        IsAgeRequired = ageRequired;
    }

    public String getSizeLabel() {
        return SizeLabel;
    }

    private void setSizeLabel(String sizeLabel) {
        SizeLabel = sizeLabel;
    }

    public String getSize() {
        return Size;
    }

    private void setSize(String size) {
        Size = size;
    }

    public String getProductUrl() {
        return ProductUrl;
    }

    private void setProductUrl(String productUrl) {
        ProductUrl = productUrl;
    }

    public String getProductImageUrl() {
        return ProductImageUrl;
    }

    private void setProductImageUrl(String productImageUrl) {
        ProductImageUrl = productImageUrl;
    }

    public Boolean getHasNewPrice() {
        return HasNewPrice;
    }

    private void setHasNewPrice(Boolean hasNewPrice) {
        HasNewPrice = hasNewPrice;
    }

    Double getRegularPrice() {
        return RegularPrice;
    }

    private void setRegularPrice(Double regularPrice) {
        RegularPrice = regularPrice;
    }

    public String getPromotionName() {
        return PromotionName;
    }

    private void setPromotionName(String promotionName) {
        PromotionName = promotionName;
    }

    public Double getSalesPrice() {
        return SalesPrice;
    }

    private void setSalesPrice(Double salesPrice) {
        SalesPrice = salesPrice;
    }
}
