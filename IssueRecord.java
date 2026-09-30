package com.library.model;
import java.time.LocalDate;
import java.math.BigDecimal;
public record IssueRecord(int id,String bookTitle,String memberName,LocalDate issueDate,LocalDate dueDate,LocalDate returnDate,BigDecimal fine,String status){}
