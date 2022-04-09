package com.devneutro.repository;

import com.devneutro.domain.Expense;
import org.springframework.data.repository.CrudRepository;

public interface ExpenseRepository  extends CrudRepository<Expense, Long> {

}
