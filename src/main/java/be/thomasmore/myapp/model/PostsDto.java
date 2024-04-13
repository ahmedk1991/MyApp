package be.thomasmore.myapp.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class PostsDto {
    @NotEmpty(message = "Url is required")
    private String name;
    @Size(min = 10, message = "The description should be at least 10 characters")
    @Size(max = 500, message = "The description cannot exceed 500 characters")
    private String post;

    private MultipartFile imageFile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
