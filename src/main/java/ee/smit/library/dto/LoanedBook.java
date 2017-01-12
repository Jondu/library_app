package ee.smit.library.dto;

/**
 * Created by Hando.
 */
public class LoanedBook {

    private Book book;
    private User loanedTo;

    public LoanedBook() {
    }

    public LoanedBook(Book book, User loanedTo) {
        this.book = book;
        this.loanedTo = loanedTo;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getLoanedTo() {
        return loanedTo;
    }

    public void setLoanedTo(User loanedTo) {
        this.loanedTo = loanedTo;
    }
}
