package com.ace.aws.db;

import com.ace.aws.domain.Movie;
import com.ace.aws.domain.MovieFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ContextConfiguration(classes = SpringMovieRepository.class)
@AutoConfigurationPackage(basePackages = "com.ace.aws.db")
@TestPropertySource(locations = "/com/ace/aws/spring-h2-application-context-test.properties")
@EnableAutoConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SpringMovieRepositoryTest
{
    @Autowired
    @SuppressWarnings("FieldCanBeLocal,UnusedDeclaration")
    private TransactionTemplate transactionTemplate;

    @Autowired
    @SuppressWarnings("FieldCanBeLocal,UnusedDeclaration")
    private MovieRepository movieRepository;

    private Movie deathStar;
    private Movie starTrek;
    private Movie starWars;

    @BeforeEach
    public void setUp()
    {
        deathStar = new MovieFixture().build();
        starTrek = new MovieFixture().withTitle("Star Trek").withRelease(1974).build();
        starWars = new MovieFixture().withTitle("Star Wars").withRelease(1974).build();

        transactionTemplate.setPropagationBehavior(Propagation.REQUIRES_NEW.value());
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);

        transactionTemplate.execute(new TransactionCallbackWithoutResult()
        {
            @Override
            protected void doInTransactionWithoutResult(@SuppressWarnings("NullableProblems") TransactionStatus transactionStatus)
            {
                movieRepository.addMovie(starTrek);
                movieRepository.addMovie(starWars);
            }
        });
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void shouldRetrieveMoviesByRelease()
    {
        List<MovieDataObject> movies = transactionTemplate.execute(transactionStatus -> movieRepository.findMoviesByRelease(1974));
        assertEquals(movies.size(), 2);
        assertEquals(starTrek.getTitle(), movies.get(0).getMovie().getTitle());
        assertEquals(starWars.getTitle(), movies.get(1).getMovie().getTitle());
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void shouldRetrieveAllMovies()
    {
        List<MovieDataObject> movies = transactionTemplate.execute(transactionStatus -> movieRepository.findMoviesByFirstLetterOfTitle('S'));
        assertEquals(movies.size(), 2);
        assertEquals(starTrek.getTitle(), movies.get(0).getMovie().getTitle());
        assertEquals(starWars.getTitle(), movies.get(1).getMovie().getTitle());
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void shouldAddNewMovie()
    {
        transactionTemplate.execute(new TransactionCallbackWithoutResult()
        {
            @Override
            protected void doInTransactionWithoutResult(@SuppressWarnings("NullableProblems") TransactionStatus transactionStatus)
            {
                movieRepository.addMovie(deathStar);
            }
        });

        MovieDataObject movie = transactionTemplate.execute(transactionStatus -> movieRepository.findMovieByTitle(deathStar.getTitle()));
        assertEquals(movie.getTitle(), deathStar.getTitle());
    }

    @Test
    public void shouldRetrieveMovieWithAudits()
    {
        MovieDataObject movie = transactionTemplate.execute(transactionStatus -> movieRepository.findMovieByTitle(starWars.getTitle()));
        List<AuditDataObject> auditEvents = transactionTemplate.execute(transactionStatus -> movieRepository.listAllAuditEvents());
        assertNotNull(movie);
        assertEquals(starWars.getTitle(), movie.getTitle());
        assertNotNull(auditEvents);
        assertEquals(2, auditEvents.size());

        List<AuditDataObject> queriedAuditEvents = transactionTemplate.execute(transactionStatus -> movieRepository.getAuditEntriesByIdentifier(starTrek.getTitle()));
        assert queriedAuditEvents != null;
        assertEquals(1, queriedAuditEvents.size());
        AuditDataObject auditDataObject = queriedAuditEvents.get(0);
        assertEquals(starTrek.getTitle(), auditDataObject.getEntityId());
        assertEquals("MOVIE", auditDataObject.getEntityType());
        assertEquals("CREATE", auditDataObject.getEvent());
        assertEquals("host1", auditDataObject.getHostName());
        assertNotNull(auditDataObject.getId());
        assertNotNull(auditDataObject.getCreateDate());
    }

    @Test()
    public void shouldThrowExceptionOnDuplicateInsertion()
    {
        Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> transactionTemplate
                        .execute((TransactionCallback<Void>) transactionStatus ->
                        {
                            movieRepository.addMovie(starTrek);
                            return null;
                        }));
    }
}
