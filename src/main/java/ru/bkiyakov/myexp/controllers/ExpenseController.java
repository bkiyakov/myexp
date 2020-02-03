package ru.bkiyakov.myexp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bkiyakov.myexp.models.Expense;
import ru.bkiyakov.myexp.models.User;
import ru.bkiyakov.myexp.repositories.ExpenseRepository;
import ru.bkiyakov.myexp.repositories.UserRepository;

import java.util.List;

@RestController
@RequestMapping(value = "/api/expenses")
public class ExpenseController {
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public List<Expense> findAll(){
        return expenseRepository.findAll();
    }

    @GetMapping("/{user_id}")
    public List<Expense> findAllByUser(@PathVariable Long user_id){
        return expenseRepository.findAllByUser(userRepository.findById(user_id).orElse(null));
    }

    @PostMapping("/")
    public Expense save(Expense expense){
        return expenseRepository.save(expense);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        expenseRepository.deleteById(id);
    }

    //TODO проверить (не работает, обновление происходит при попытке сохранения пользователся со старым id)
    @PutMapping("/{id}")
    public Expense update(@RequestBody Expense newExpense, @PathVariable Long id){
        Expense expense = expenseRepository.findById(id).orElse(new Expense()); //TODO в orElse поменять на null, и если null возвращать ошибку
        expense.setCreatedDate(newExpense.getCreatedDate());
        expense.setUser(newExpense.getUser());
        expense.setSum(newExpense.getSum());

        if(newExpense.getNote() != null){
            expense.setNote(newExpense.getNote());
        }

        return expenseRepository.save(expense);
    }
}
