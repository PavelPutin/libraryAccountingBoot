package edu.vsu.putin_p_a.springbootapp.service;

import edu.vsu.putin_p_a.springbootapp.models.Person;
import edu.vsu.putin_p_a.springbootapp.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> getPeople() {
        return peopleRepository.findAll();
    }

    public Optional<Person> getPersonById(int id) {
        return peopleRepository.findById(id);
    }

    public Optional<Person> getPersonByIdWithBooks(int id) {
        return peopleRepository.getPersonByIdWithBooks(id);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void edit(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void remove(int id) {
        peopleRepository.deleteById(id);
    }
}
