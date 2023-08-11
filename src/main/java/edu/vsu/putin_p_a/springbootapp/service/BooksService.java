package edu.vsu.putin_p_a.springbootapp.service;

import edu.vsu.putin_p_a.springbootapp.models.Book;
import edu.vsu.putin_p_a.springbootapp.repository.BooksRepository;
import edu.vsu.putin_p_a.springbootapp.repository.PeopleRepository;
import edu.vsu.putin_p_a.springbootapp.util.PaginationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;
    private final PaginationState paginationState;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
        this.paginationState = PaginationState.init(booksRepository.count());
    }

    public List<Book> getBooks(Optional<Integer> page, Optional<Integer> booksPerPage, Optional<Boolean> sortByYear) {
        Sort sort = sortByYear.isPresent() && sortByYear.get() ?
                Sort.by("publishYear") :
                Sort.unsorted();

        if (page.isEmpty() || booksPerPage.isEmpty()) {
            return booksRepository.findAll(sort);
        }

        paginationState.update(booksRepository.count(), page.get(), booksPerPage.get());
        PageRequest pr = PageRequest.of(
                (int) paginationState.getCurrent(),
                paginationState.getPageSize(),
                sort);

        return booksRepository.findAll(pr).getContent();
    }

    public Optional<Book> getBookById(int id) {
        return booksRepository.findById(id);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void edit(int id, Book editingBook) {
        editingBook.setId(id);
        booksRepository.save(editingBook);
    }

    @Transactional
    public void remove(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void free(int id) {
        booksRepository.findById(id).ifPresent(book -> {
            book.setOwner(null);
            book.setCaptureDate(null);
            booksRepository.save(book);
        });

    }

    @Transactional
    public void setOwner(int id, int ownerId) {
        booksRepository.findById(id)
                .ifPresent(book -> {
                    book.setOwner(peopleRepository.getReferenceById(ownerId));
                    book.setCaptureDate(new Date());
                    booksRepository.save(book);
                });
    }

    public PaginationState getPaginationState() {
        return paginationState;
    }

    public List<Book> findBooksByNameStartingWithIgnoreCase(String searchQuery) {
        return booksRepository.findBooksByNameStartingWithIgnoreCase(searchQuery);
    }
}
