package com.sky.library;

public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book retrieveBook(String bookReference) throws BookNotFoundException, InvalidBookReferenceException {
        if (isInvalidBookReference(bookReference))
            throw new InvalidBookReferenceException("book reference must begin with BOOK-");

        final Book result = bookRepository.retrieveBook(bookReference);

        if (result == null)
            throw new BookNotFoundException();

        return result;
    }

    @Override
    public String getBookSummary(String bookReference) throws BookNotFoundException, InvalidBookReferenceException {
        final Book result = this.retrieveBook(bookReference);
        return "[" + result.getReference() + "] " + result.getTitle() + " - " + retrieveTheFirstNineWords(result.getReview());
    }

    private boolean isInvalidBookReference(String bookReference) {
        return bookReference == null || !bookReference.strip().startsWith("BOOK-");
    }

    private String retrieveTheFirstNineWords(String review) {
        final String[] words = review.split(" ");
        if (words.length < 9) {
            return review;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            stringBuilder.append(words[i]);
            if (i == 8 ) {
                stringBuilder.append("...");
            } else {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }
}
