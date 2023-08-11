package edu.vsu.putin_p_a.springbootapp.repository;

import edu.vsu.putin_p_a.springbootapp.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    @EntityGraph(attributePaths = "owner", type = EntityGraph.EntityGraphType.LOAD)
    List<Book> findAll();

    @EntityGraph(attributePaths = "owner", type = EntityGraph.EntityGraphType.LOAD)
    Page<Book> findAll(Pageable pageable);

    List<Book> findBooksByNameStartingWithIgnoreCase(String prefix);
}
