import com.example.Library.entity.Book;
import com.example.Library.entity.Loan;
import com.example.Library.entity.Member;
import com.example.Library.exception.BookNotAvailableException;
import com.example.Library.exception.ResourceNotFoundException;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.LoanRepository;
import com.example.Library.service.LoanService;
import com.example.Library.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private MemberService memberService;

    @InjectMocks
    private LoanService loanService;

    @Test
    void borrowBook_whenAvailable_succeeds() {
        Book book = new Book();
        book.setAvailable(true);
        Member member = new Member();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(memberService.getMemberById(1L)).thenReturn(member);
        when(loanRepository.save(any(Loan.class))).thenAnswer(i -> i.getArgument(0));

        Loan loan = loanService.borrowBook(1L, 1L);

        assertFalse(book.getAvailable());
        assertEquals(book, loan.getBook());
        assertNotNull(loan.getLoanDate());
    }

    @Test
    void borrowBook_whenAlreadyBorrowed_throws() {
        Book book = new Book();
        book.setAvailable(false);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertThrows(BookNotAvailableException.class,
                () -> loanService.borrowBook(1L, 1L));
    }

    @Test
    void returnBook_whenNoActiveLoan_throws() {
        when(loanRepository.findByBookIdAndReturnDateIsNull(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> loanService.returnBook(1L));
    }
}