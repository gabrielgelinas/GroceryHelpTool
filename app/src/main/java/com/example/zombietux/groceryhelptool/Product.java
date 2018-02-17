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
        if (ProductId==null) ProductId = "";
        return ProductId;
    }

    private void setProductId(String productId) {
        ProductId = productId;
    }

    public String getName() {
        if (FullDisplayName==null) FullDisplayName = "";
        return FullDisplayName;
    }

    public void setName(String name) {
        FullDisplayName = name;
    }

    public String getBrandName() {
        if (BrandName==null) BrandName = "";
        return BrandName;
    }

    private void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public Boolean getAgeRequired() {
        if (IsAgeRequired==null) IsAgeRequired = false;
        return IsAgeRequired;
    }

    private void setAgeRequired(Boolean ageRequired) {
        IsAgeRequired = ageRequired;
    }

    public String getSizeLabel() {
        if (SizeLabel==null) SizeLabel = "";
        return SizeLabel;
    }

    private void setSizeLabel(String sizeLabel) {
        SizeLabel = sizeLabel;
    }

    public String getSize() {
        if (ProductId==null) ProductId = "";
        return Size;
    }

    private void setSize(String size) {
        Size = size;
    }

    public String getProductUrl() {
        if (ProductUrl==null) ProductUrl = "";
        return ProductUrl;
    }

    private void setProductUrl(String productUrl) {
        ProductUrl = productUrl;
    }

    public String getProductImageUrl() {
        if (ProductImageUrl==null) ProductImageUrl = "";
        return ProductImageUrl;
    }

    private void setProductImageUrl(String productImageUrl) {
        ProductImageUrl = productImageUrl;
    }

    public Boolean getHasNewPrice() {
        if (HasNewPrice==null) HasNewPrice = false;
        return HasNewPrice;
    }

    private void setHasNewPrice(Boolean hasNewPrice) {
        HasNewPrice = hasNewPrice;
    }

    Double getRegularPrice() {
        if (RegularPrice==null) RegularPrice = 0.0;
        return RegularPrice;
    }

    private void setRegularPrice(Double regularPrice) {
        RegularPrice = regularPrice;
    }

    public String getPromotionName() {
        if (PromotionName==null) PromotionName = "";
        return PromotionName;
    }

    private void setPromotionName(String promotionName) {
        PromotionName = promotionName;
    }

    public Double getSalesPrice() {
        if (SalesPrice==null) SalesPrice = 0.0;
        return SalesPrice;
    }

    private void setSalesPrice(Double salesPrice) {
        SalesPrice = salesPrice;
    }
}
