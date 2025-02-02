package be.thomasmore.myapp.model;

import jakarta.persistence.*;

import java.util.Collection;

@Entity

public class Games {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String category;
    private double price;
    @Column(length = 500)
    private String description;

    private String console;
    private String imageFileName;

    @ManyToMany(mappedBy = "games")
    private Collection<Ratings> ratings;

    @Column(length = 300)
    private String videoUrl;
    @ManyToMany
    private Collection<Reviews> reviews;

    public Games() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public Collection<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<Reviews> reviews) {
        this.reviews = reviews;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }


    public Collection<Ratings> getRatings() {
        return ratings;
    }

    public void setRatings(Collection<Ratings> ratings) {
        this.ratings = ratings;
    }
}
