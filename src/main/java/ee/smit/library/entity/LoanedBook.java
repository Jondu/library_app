package ee.smit.library.entity;

import lombok.NoArgsConstructor;

/**
 * Created by Hando.
 */
@NoArgsConstructor
public class LoanedBook {

    private Book book;
    private Person loanedTo;

    public LoanedBook(Book book, Person loanedTo) {
        this.book = book;
        this.loanedTo = loanedTo;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Person getLoanedTo() {
        return loanedTo;
    }

    public void setLoanedTo(Person loanedTo) {
        this.loanedTo = loanedTo;
    }
}
