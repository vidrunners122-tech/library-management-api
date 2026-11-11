package com.example.Library.service;

import com.example.Library.entity.Book;
import com.example.Library.entity.Loan;
import com.example.Library.entity.Member;
import com.example.Library.exception.BookNotAvailableException;
import com.example.Library.exception.ResourceNotFoundException;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.LoanRepository;
import com.example.Library.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final MemberService memberService;

    public LoanService(LoanRepository loanRepository,
                       BookRepository bookRepository,
                       MemberService memberService) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.memberService = memberService;
    }

    @Transactional
    public Loan borrowBook(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));


        if (!book.getAvailable()) {
            throw new BookNotAvailableException("Book is already borrowed");
        }

        Member member = memberService.getMemberById(memberId);

        book.setAvailable(false);
        bookRepository.save(book);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setMember(member);
        loan.setLoanDate(LocalDate.now());
        return loanRepository.save(loan);
    }


    @Transactional
    public Loan returnBook(Long bookId) {
        Loan loan = loanRepository.findByBookIdAndReturnDateIsNull(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("No active loan for book id: " + bookId));

        loan.setReturnDate(LocalDate.now());

        Book book = loan.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        return loanRepository.save(loan);


    }
}