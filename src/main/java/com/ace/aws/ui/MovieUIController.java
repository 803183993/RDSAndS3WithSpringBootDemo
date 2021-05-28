package com.ace.aws.ui;

import com.ace.aws.domain.Movie;
import com.ace.aws.domain.Review;
import com.ace.aws.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.ace.aws.common.StringUtilities.*;
import static java.lang.String.format;

@Controller
@RequestMapping("/ui")
public class MovieUIController
{
    @Autowired
    MovieService movieService;

    @PostMapping("/movies/addReview")
    @SuppressWarnings("UnusedDeclaration")
    public String addReview(@ModelAttribute Review review, Model model, HttpServletRequest request)
    {
        String ipAddress = Optional.ofNullable((request.getHeader("X-FORWARDED-FOR"))).orElse(request.getRemoteAddr());
        review.setIpAddress(ipAddress);
        movieService.addReview(review);
        model.addAttribute("msg", "Review Submitted");
        return populateReviews(review.getTitle(), model);
    }

    @GetMapping("/movies/")
    @SuppressWarnings("UnusedDeclaration")
    public String getReviews(@RequestParam(name = "title") String title, Model model)
    {
        return populateReviews(title, model);
    }

    private String populateReviews(String title, Model model)
    {
        Movie movie = movieService.getMovieWithReviews(title);
        if (movie == null)
        {
            model.addAttribute("msg", format("Movie [%s] not found!", capitaliseWords(title)));
            return "error";
        }
        else
        {
            List<Review> reviews = movie.getReviews();
            model.addAttribute("numberOfReviews", numberToWord(reviews == null ? 0 : reviews.size()));
            if (reviews != null)
            {
                Collections.sort(reviews);
                model.addAttribute("reviews", reviews);
            }
            populateMovie(movie, model);
            return "reviews";
        }
    }

    private void populateMovie(Movie movie, Model model)
    {
        model.addAttribute("title", movie.getTitle());
        model.addAttribute("director", movie.getDirector());
        model.addAttribute("release", movie.getRelease());
        model.addAttribute("today", toDayMonthYear(ZonedDateTime.now()));
        model.addAttribute("poster", movie.getPosterImageFilename());
    }
}
