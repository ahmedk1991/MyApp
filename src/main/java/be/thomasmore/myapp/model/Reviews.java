package be.thomasmore.myapp.model;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;

import java.util.Collection;
import java.util.Date;

@Entity
public class Reviews {
    @Id
    private long id;
    @Column(length = 500)
    private String review;
    @Timestamp
    private Date date;

    private String name;



    @ManyToMany
    private Collection<Games>games;
    public Reviews() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Games> getGames() {
        return games;
    }

    public void setGames(Collection<Games> games) {
        this.games = games;
    }
}
