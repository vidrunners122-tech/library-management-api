package com.example.Library.controller;

import com.example.Library.entity.Loan;
import com.example.Library.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/borrow")
    public Loan borrowBook(@RequestParam Long bookId, @RequestParam Long memberId) {
        return loanService.borrowBook(bookId, memberId);
    }

    @PostMapping("/return")
    public Loan returnBook(@RequestParam Long bookId) {
        return loanService.returnBook(bookId);
    }
    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

}