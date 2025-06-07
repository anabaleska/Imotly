package mk.imotly.model.DTO;

import mk.imotly.model.Ad;

import java.util.List;

public class PagedAdResponse {
    private List<Ad> ads;
    private long total;

    public PagedAdResponse(List<Ad> ads, long total) {
        this.ads = ads;
        this.total = total;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public long getTotal() {
        return total;
    }
}

