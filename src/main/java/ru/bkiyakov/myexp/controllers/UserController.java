package ru.bkiyakov.myexp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bkiyakov.myexp.exceptions.UserNotFoundException;
import ru.bkiyakov.myexp.models.Expense;
import ru.bkiyakov.myexp.models.User;
import ru.bkiyakov.myexp.repositories.ExpenseRepository;
import ru.bkiyakov.myexp.repositories.UserRepository;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping("/")
    public List<User> findAll(){

        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User find(@PathVariable Long id){

        return userRepository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public User save(@RequestBody User user){

        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){

        userRepository.deleteById(id);
    }

    //TODO проверить (не работает, обновление происходит при попытке сохранения пользователся со старым id)
    @PutMapping("/{id}")
    public User update(@RequestBody User newUser, @PathVariable Long id){
        User user = userRepository.findById(id).orElse(new User()); //TODO в orElse поменять на null, и если null возвращать ошибку
        user.setUsername(newUser.getUsername());

        return userRepository.save(user);
    }

    // EXPENSES

    @GetMapping("/{user_id}/expenses")
    public List<Expense> findUserExpenses(@PathVariable Long user_id){
        User user = userRepository.findById(user_id).orElseThrow(() -> new UserNotFoundException(user_id));
        System.out.println("DEBUG\n" + user);
        return expenseRepository.findAllByUser(user);
    }

    @PostMapping("/{user_id}/addexpense")
    public void addExpense(@PathVariable Long user_id, @RequestBody Expense newExpense){
        User user = userRepository.findById(user_id).orElseThrow(() -> new UserNotFoundException(user_id));
        System.out.println("DEBUG\n" + user);
        Expense expense = new Expense(user, newExpense.getCreatedDate(), newExpense.getSum(), newExpense.getNote());
        System.out.println("DEBUG\n" + expense);
        expenseRepository.save(expense);
    }
}
