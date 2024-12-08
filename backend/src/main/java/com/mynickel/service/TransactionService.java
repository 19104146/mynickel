package com.mynickel.service;

import com.mynickel.model.Transaction;
import com.mynickel.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        Iterable<Transaction> transactions = transactionRepository.findAll();
        List<Transaction> transactionList = new ArrayList<>();
        transactions.forEach(transactionList::add);
        return transactionList;
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Transaction not found with id: " + id));
    }

    public Transaction updateTransaction(Long id, Transaction transactionDetails) {
        Transaction transaction = getTransactionById(id);

        transaction.setAmount(transactionDetails.getAmount());
        transaction.setCategory(transactionDetails.getCategory());
        transaction.setDate(transactionDetails.getDate());
        transaction.setNotes(transactionDetails.getNotes());
        transaction.setType(transactionDetails.getType());
        transaction.setRecurrenceFrequency(transactionDetails.getRecurrenceFrequency());
        transaction.setStartDate(transactionDetails.getStartDate());
        transaction.setEndDate(transactionDetails.getEndDate());
        transaction.setNextOccurrence(transactionDetails.getNextOccurrence());

        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public Transaction updateRecurringTransaction(Long id, Transaction transactionDetails) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();

            // Update transaction fields
            transaction.setAmount(transactionDetails.getAmount());
            transaction.setCategory(transactionDetails.getCategory());
            transaction.setDate(transactionDetails.getDate());
            transaction.setNotes(transactionDetails.getNotes());
            transaction.setType(transactionDetails.getType());
            transaction.setRecurrenceFrequency(transactionDetails.getRecurrenceFrequency());
            transaction.setStartDate(transactionDetails.getStartDate());
            transaction.setEndDate(transactionDetails.getEndDate());
            transaction.setNextOccurrence(transactionDetails.getNextOccurrence());

            return transactionRepository.save(transaction);
        } else {
            throw new RuntimeException("Transaction not found with id: " + id);
        }
    }

    public void deleteRecurringTransaction(Long id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            transactionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Transaction not found with id: " + id);
        }
    }
}
