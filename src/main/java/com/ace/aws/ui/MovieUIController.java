package com.ace.aws.ui;

import com.ace.aws.domain.Movie;
import com.ace.aws.domain.Review;
import com.ace.aws.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

import static com.ace.aws.common.StringUtilities.toDayMonthYear;

@Controller
@RequestMapping("/ui")
public class MovieUIController
{
    @Autowired
    MovieService movieService;

    @PostMapping("/movies/addReview")
    @SuppressWarnings("UnusedDeclaration")
    public String addReview(@ModelAttribute Review review, Model model)
    {
        populateReviews(review.getTitle(), model);
        model.addAttribute("result", "Review Submitted");
        return "reviews";
    }

    @GetMapping("/movies/")
    @SuppressWarnings("UnusedDeclaration")
    public String getReviews(@RequestParam(name = "title") String title, Model model)
    {
        populateReviews(title, model);
        return "reviews";
    }

    private void populateReviews(String title, Model model)
    {
        Movie movie = movieService.getMovie(title);
        model.addAttribute("title", movie.getTitle());
        model.addAttribute("director", movie.getDirector());
        model.addAttribute("release", movie.getRelease());
        model.addAttribute("today", toDayMonthYear(ZonedDateTime.now()));
        model.addAttribute("poster", movie.getPosterImageFilename());
    }
}