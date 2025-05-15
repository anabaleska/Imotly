package mk.imotly.model;

public class SavedSearchRequest {
    private String email;

    private Filters filters;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        return "SavedSearchRequest{" +
                "email='" + email + '\'' +
                ", filters=" + filters +
                '}';
    }
}
