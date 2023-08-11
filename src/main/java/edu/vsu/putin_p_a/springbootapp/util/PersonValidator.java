package edu.vsu.putin_p_a.springbootapp.util;

import edu.vsu.putin_p_a.springbootapp.models.Person;
import edu.vsu.putin_p_a.springbootapp.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Year;

@Component
public class PersonValidator implements Validator {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonValidator(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (!peopleRepository.getPeopleByIdIsNotAndFullName(person.getId(), person.getFullName()).isEmpty()) {
            errors.rejectValue("fullName", "", "Full name isn't unique");
        }

        if (person.getBirthdayYear() > Year.now().getValue()) {
            errors.rejectValue("birthdayYear", "", "Birthday year must be earlier than the current one");
        }
    }
}
