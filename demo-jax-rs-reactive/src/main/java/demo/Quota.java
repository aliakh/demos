package demo;

public class Quota {

    private Metal metal;
    private Price price;

    public Quota() {
    }

    public Quota(Metal metal, Price price) {
        this.metal = metal;
        this.price = price;
    }

    public Metal getMetal() {
        return metal;
    }

    public Price getPrice() {
        return price;
    }

    public Quota updatePrice(Price price) {
        this.price = price;
        return this;
    }
}