package com.example.zombietux.groceryhelptool;

/**
 * Created by gggab(Zombietux) on 2018-02-19.
 */

class ProductOrder {
    private Product product;
    private int qte;
    private Double price;

    public ProductOrder(Product product, int qte, Double price) {
        this.product = product;
        this.qte = qte;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
