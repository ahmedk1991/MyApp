package be.thomasmore.myapp.model;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class Ratings {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private double rating;


    @ManyToMany
    private Collection<Games>games;

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
    public Collection<Games> getGames() {
        return games;
    }

    public void setGames(Collection<Games> games) {
        this.games = games;
    }

}
