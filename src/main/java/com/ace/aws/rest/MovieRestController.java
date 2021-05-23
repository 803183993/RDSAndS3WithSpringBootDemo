package com.ace.aws.rest;

import com.ace.aws.domain.Movie;
import com.ace.aws.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class MovieRestController
{
    @Autowired
    MovieService movieService;

    @GetMapping("/movies/{title}")
    @SuppressWarnings("UnusedDeclaration")
    public ResponseEntity<Movie> getMovieByTitle(@PathVariable(name = "title") String title)
    {
        Movie movie = movieService.getMovie(title);
        return new ResponseEntity<>(movie, (movie == null) ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/movies/byLetter/{letter}")
    @SuppressWarnings("UnusedDeclaration")
    public ResponseEntity<List<Movie>> getMoviesByLetter(@PathVariable(name = "letter") String letter)
    {
        List<Movie> movie = movieService.getMovieByFirstLetterOfTitle(letter.charAt(0));
        return new ResponseEntity<>(movie, (movie == null) ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/movies/byYear/{year}")
    @SuppressWarnings("UnusedDeclaration")
    public ResponseEntity<List<Movie>> getMoviesByYear(@PathVariable(name = "year") int year)
    {
        List<Movie> movie = movieService.getMovieByRelease(year);
        return new ResponseEntity<>(movie, (movie == null) ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @PostMapping("/movies/add")
    @SuppressWarnings("UnusedDeclaration")
    public ResponseEntity<String> addMovie(@RequestBody Movie movie)
    {
        try
        {
            movieService.addMovie(movie);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (DataIntegrityViolationException duplicateKeyException)
        {
            return new ResponseEntity<>("DuplicateKeyException", HttpStatus.CONFLICT);
        } catch (Exception exception)
        {
            return new ResponseEntity<>("Unknown", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
