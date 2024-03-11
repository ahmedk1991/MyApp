package be.thomasmore.myapp.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class GamesDto {
    @NotEmpty(message = "The name is required")
    private String name;
    @NotEmpty(message = "The name is required")
    private String category;
    @Min(0)
    private double price;
    @Size(min=10,message = "The description should be at least 10 characters")
    @Size(max=500,message = "The description cannot exceed 500 characters")
    private String description;

    private MultipartFile imageFile;

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

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFileName(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}