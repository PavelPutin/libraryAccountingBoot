package edu.vsu.putin_p_a.springbootapp.repository;

import edu.vsu.putin_p_a.springbootapp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    List<Person> getPeopleByIdIsNotAndFullName(int id, String fullName);

    @Query("select p from Person p left join fetch p.books where p.id = :id")
    Optional<Person> getPersonByIdWithBooks(@Param("id") int id);
}
