package com.devneutro.api;

import com.devneutro.domain.Expense;
import com.devneutro.helper.NullAwareBeanUtilsBean;
import com.devneutro.repository.ExpenseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class ExpenseController {

    private ExpenseRepository expenseRepository;
    private NullAwareBeanUtilsBean beanUtilsBean;

    public ExpenseController(ExpenseRepository expenseRepository, NullAwareBeanUtilsBean beanUtilsBean) {
        this.expenseRepository = expenseRepository;
        this.beanUtilsBean = beanUtilsBean;
    }

    @GetMapping("/expenses")
    public List<Expense> findAll() {
        return StreamSupport.stream(expenseRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

    }

    @GetMapping("/expenses/{id}")
    public Expense findById(@PathVariable("id") Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/expenses")
    public Expense save(@Valid @RequestBody Expense expense){
        return expenseRepository.save(expense);
    }

    @PutMapping("/expenses/{id}")
    public Expense update(@Valid @RequestBody Expense expense, @PathVariable("id") Long id){
        findById(id);
        expense.setId(id);
        return expenseRepository.save(expense);
    }

    @PatchMapping("/expenses/{id}")
    public Expense patchUpdate( @RequestBody Expense expense, @PathVariable("id") Long id) throws InvocationTargetException, IllegalAccessException {
        Expense existingExpense = findById(id);
        beanUtilsBean.copyProperties(existingExpense, expense);
        return expenseRepository.save(existingExpense);
    }

    @DeleteMapping("/expenses/{id}")
    public void delete(@PathVariable("id") Long id){
        Expense existingExpense = findById(id);
        expenseRepository.deleteById(id);
    }






}
