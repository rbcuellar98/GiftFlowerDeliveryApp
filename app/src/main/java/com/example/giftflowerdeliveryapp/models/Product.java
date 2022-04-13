package com.example.giftflowerdeliveryapp.models;

/**
 * This class models any product to be sold
 * Class holding product definitions
 */
public abstract class Product {
    private long id;
    private int image;
    private String title;
    private float price;
    private int favorite;

    /**
     * Product class constructor
     * @param id the id of the product
     * @param image the image of the product
     * @param title the name of the product
     * @param price the price of the product
     * @param favorite whether it has been added to favorite or not
     */
    public Product(long id, int image, String title, float price, int favorite) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.price = price;
        this.favorite = favorite;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
