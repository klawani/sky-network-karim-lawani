package com.sky.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private BookServiceImpl bookServiceImpl;
    private BookRepositoryStub bookRepositoryStub;
    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void setup() {
        bookRepositoryStub = new BookRepositoryStub();
        bookServiceImpl = new BookServiceImpl(bookRepository);
    }

    @Test
    public void retrieveBookShouldReturnValidBookWhenUsingValidBookReference() throws BookNotFoundException, InvalidBookReferenceException {
        final String validBookReference = "BOOK-GRUFF472";
        final Book expectedBook = bookRepositoryStub.retrieveBook(validBookReference);

        when(bookRepository.retrieveBook(validBookReference)).thenReturn(expectedBook);
        final Book actualBook = bookServiceImpl.retrieveBook(validBookReference);
        assertEquals(expectedBook, actualBook);
    }

    @Test
    public void retrieveBookShouldThrowBookNotFoundExceptionWhenUsingNonExistingBookReference() {
        final String nonExistingBookReference = "BOOK-999";
        final Book expectedBook = bookRepositoryStub.retrieveBook(nonExistingBookReference);

        when(bookRepository.retrieveBook(nonExistingBookReference)).thenReturn(expectedBook);
        assertThrowsExactly(BookNotFoundException.class, () -> bookServiceImpl.retrieveBook(nonExistingBookReference));
    }

    @Test
    public void retrieveBookShouldThrowInvalidBookReferenceExceptionWhenUsingInvalidBookReference() {
        final String invalidBookReference = "INVALID-TEXT";
        InvalidBookReferenceException exception = assertThrowsExactly(InvalidBookReferenceException.class, () -> bookServiceImpl.retrieveBook(invalidBookReference));
        assertEquals("book reference must begin with BOOK-", exception.getMessage());
    }

    @Test
    public void retrieveBookShouldThrowInvalidBookReferenceExceptionWhenUsingEmptyBookReference() {
        final String invalidBookReference = "";
        InvalidBookReferenceException exception = assertThrowsExactly(InvalidBookReferenceException.class, () -> bookServiceImpl.retrieveBook(invalidBookReference));
        assertEquals("book reference must begin with BOOK-", exception.getMessage());
    }

    @Test
    public void retrieveBookShouldThrowInvalidBookReferenceExceptionWhenUsingNullBookReference() {
        InvalidBookReferenceException exception = assertThrowsExactly(InvalidBookReferenceException.class, () -> bookServiceImpl.retrieveBook(null));
        assertEquals("book reference must begin with BOOK-", exception.getMessage());
    }

    @Test
    public void getBookSummaryShouldReturnGruffaloSummaryWhenUsingGruffaloBookReference() throws BookNotFoundException, InvalidBookReferenceException {
        final String gruffaloBookReference = "BOOK-GRUFF472";
        final String expectedBookSummary = "[BOOK-GRUFF472] The Gruffalo - A mouse taking a walk in the woods.";

        final Book gruffaloBook = bookRepositoryStub.retrieveBook(gruffaloBookReference);
        when(bookRepository.retrieveBook(gruffaloBookReference)).thenReturn(gruffaloBook);

        final String actualBookSummary = bookServiceImpl.getBookSummary(gruffaloBookReference);
        assertEquals(expectedBookSummary, actualBookSummary);
    }

    @Test
    public void getBookSummaryShouldReturnPoohSummaryWhenUsingPoohBookReference() throws BookNotFoundException, InvalidBookReferenceException {
        final String poohBookReference = "BOOK-POOH222";
        final String expectedBookSummary = "[BOOK-POOH222] Winnie The Pooh - In this first volume, we meet all the friends...";

        final Book poohBook = bookRepositoryStub.retrieveBook(poohBookReference);
        when(bookRepository.retrieveBook(poohBookReference)).thenReturn(poohBook);

        final String actualBookSummary = bookServiceImpl.getBookSummary(poohBookReference);
        assertEquals(expectedBookSummary, actualBookSummary);
    }

    @Test
    public void getBookSummaryShouldReturnWillSummaryWhenUsingWillBookReference() throws BookNotFoundException, InvalidBookReferenceException {
        final String willBookReference = "BOOK-WILL987";
        final String expectedBookSummary = "[BOOK-WILL987] The Wind In The Willows - With the arrival of spring and fine weather outside,...";

        final Book willBook = bookRepositoryStub.retrieveBook(willBookReference);
        when(bookRepository.retrieveBook(willBookReference)).thenReturn(willBook);

        final String actualBookSummary = bookServiceImpl.getBookSummary(willBookReference);
        assertEquals(expectedBookSummary, actualBookSummary);
    }

    @Test
    public void getBookSummaryShouldThrowBookNotFoundExceptionWhenUsingNonExistingBookReference() {
        final String nonExistingBookReference = "BOOK-999";
        final Book expectedBook = bookRepositoryStub.retrieveBook(nonExistingBookReference);

        when(bookRepository.retrieveBook(nonExistingBookReference)).thenReturn(expectedBook);
        assertThrowsExactly(BookNotFoundException.class, () -> bookServiceImpl.getBookSummary(nonExistingBookReference));
    }

    @Test
    public void getBookSummaryShouldThrowInvalidBookReferenceExceptionWhenUsingInvalidBookReference() {
        final String invalidBookReference = "INVALID-TEXT";
        InvalidBookReferenceException exception = assertThrowsExactly(InvalidBookReferenceException.class, () -> bookServiceImpl.getBookSummary(invalidBookReference));
        assertEquals("book reference must begin with BOOK-", exception.getMessage());
    }
}
