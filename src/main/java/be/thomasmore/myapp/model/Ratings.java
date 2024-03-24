package be.thomasmore.myapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Ratings {
    @Id
    private Long id;

    private double rating;

    public Ratings() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
