package edu.vsu.putin_p_a.springbootapp.util;

import edu.vsu.putin_p_a.springbootapp.models.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Year;

@Component
public class BookValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (book.getPublishYear() != null && book.getPublishYear() > Year.now().getValue()) {
            errors.rejectValue("publishYear", "", "Publish year must be earlier than the current one");
        }
    }
}
