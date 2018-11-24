package com.etl.etl.model.entities;

public class Review {
    private Integer id;
    private String reviewContent;
    private String nameOfReviewer;
    private Integer likesForOpinion; // number of likes on the opinion
    private Integer dislikesForOpinion; // number of dislikes for opinion

    public Review(Integer id, String reviewContent, String nameOfReviewer, Integer likesForOpinion, Integer dislikesForOpinion) {
        this.id = id;
        this.reviewContent = reviewContent;
        this.nameOfReviewer = nameOfReviewer;
        this.likesForOpinion = likesForOpinion;
        this.dislikesForOpinion = dislikesForOpinion;
    }

    public Integer getId() {
        return id;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public String getNameOfReviewer() {
        return nameOfReviewer;
    }

    public Integer getLikesForOpinion() {
        return likesForOpinion;
    }

    public Integer getDislikesForOpinion() {
        return dislikesForOpinion;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public void setNameOfReviewer(String nameOfReviewer) {
        this.nameOfReviewer = nameOfReviewer;
    }

    public void setLikesForOpinion(Integer likesForOpinion) {
        this.likesForOpinion = likesForOpinion;
    }

    public void setDislikesForOpinion(Integer dislikesForOpinion) {
        this.dislikesForOpinion = dislikesForOpinion;
    }
}
