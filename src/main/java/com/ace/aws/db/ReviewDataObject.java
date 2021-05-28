package com.ace.aws.db;

import com.ace.aws.domain.Review;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;

import static com.ace.aws.common.StringUtilities.capitaliseWords;

@Entity
@Table(name = "reviews")
public class ReviewDataObject
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    @SuppressWarnings({"UnusedDeclaration"})
    private int reviewId;

    @Column(name = "title")

    private String title;

    @Column(name = "reviewer")
    private String reviewer;

    @Column(name = "rating")
    private int rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "ip_address")
    private String ipAddress;

    @CreationTimestamp
    @Column(name = "review_date", columnDefinition = "TIMESTAMP")
    @SuppressWarnings({"UnusedDeclaration"})
    private ZonedDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title", insertable = false, updatable = false)
    @SuppressWarnings({"UnusedDeclaration"})
    private MovieDataObject movie;

    @SuppressWarnings({"unused"})
    public ReviewDataObject()
    {

    }

    public ReviewDataObject(String title, String reviewer, int rating, String comment, String ipAddress)
    {
        this.title = title;
        this.comment = comment;
        this.reviewer = reviewer;
        this.rating = rating;
        this.ipAddress = ipAddress;
    }

    public int getReviewId()
    {
        return reviewId;
    }

    public Review getReview()
    {
        Review review = new Review();
        review.setTitle(title);
        review.setReviewer(reviewer);
        review.setComment(comment);
        review.setDate(date);
        review.setRating(rating);
        review.setIpAddress(ipAddress);
        return review;
    }

    public static ReviewDataObject create(Review review)
    {
        return new ReviewDataObject(review.getTitle(), capitaliseWords(review.getReviewer()), review.getRating(), review.getComment(), review.getIpAddress());
    }
}
