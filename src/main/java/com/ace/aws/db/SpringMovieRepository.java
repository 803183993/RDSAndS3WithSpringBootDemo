package com.ace.aws.db;

import com.ace.aws.domain.Movie;
import com.ace.aws.domain.Review;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
public class SpringMovieRepository implements MovieRepository
{
    private static final String QUERY_GET_MOVIES_BY_FIRST_LETTER = "from MovieDataObject movie where movie.title LIKE '";
    private static final String QUERY_MOVIES_BY_RELEASE = "from MovieDataObject movie where movie.release=";
    @Autowired
    @SuppressWarnings("UnusedDeclaration")
    private EntityManager entityManager;

    @Value("${system.host.name}")
    @SuppressWarnings("UnusedDeclaration")
    private String systemHostName;

    @Override
    public MovieDataObject findMovieByTitle(String title)
    {
        return entityManager.find(MovieDataObject.class, title);
    }

    @Override
    public List<MovieDataObject> findMoviesByFirstLetterOfTitle(char letter)
    {
        Query query = entityManager.createQuery(QUERY_GET_MOVIES_BY_FIRST_LETTER + letter + "%'");
        //noinspection unchecked
        return (List<MovieDataObject>) query.getResultList();
    }

    @Override
    public List<MovieDataObject> findMoviesByRelease(int year)
    {
        Query query = entityManager.createQuery(QUERY_MOVIES_BY_RELEASE + year);
        //noinspection unchecked
        return (List<MovieDataObject>) query.getResultList();
    }

    @Override
    public void addMovie(Movie movie)
    {
        entityManager.persist(MovieDataObject.create(movie));
        entityManager.persist(new AuditDataObject("MOVIE", movie.getTitle(), "CREATE", StringUtils.isEmpty(systemHostName) ? "NOT AVAILABLE" : systemHostName));
    }

    @Override
    public void addReview(Review review)
    {
        entityManager.persist(ReviewDataObject.create(review));
    }

    @Override
    public List<AuditDataObject> listAllAuditEvents()
    {
        TypedQuery<AuditDataObject> listAllAudits = entityManager.createNamedQuery("listAllAudits", AuditDataObject.class);
        return listAllAudits.getResultList();
    }

    @Override
    public List<AuditDataObject> getAuditEntriesByIdentifier(String identifier)
    {
        TypedQuery<AuditDataObject> query = entityManager.createQuery("select a from AuditDataObject a where entityId = :entityNumber", AuditDataObject.class);
        query.setParameter("entityNumber", identifier);
        return query.getResultList();
    }
}
