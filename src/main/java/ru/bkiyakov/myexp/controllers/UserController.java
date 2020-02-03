package ru.bkiyakov.myexp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bkiyakov.myexp.exceptions.UserNotFoundException;
import ru.bkiyakov.myexp.models.Expense;
import ru.bkiyakov.myexp.models.User;
import ru.bkiyakov.myexp.repositories.UserRepository;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

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

    @PostMapping("/{id}/addexpense")
    public void addExpense(@PathVariable Long id, @RequestBody Expense expense){
        User user = userRepository.findById(id).orElseThrow(() -> {return new UserNotFoundException(id);}); //TODO ПРОВЕРИТЬ
        user.addExpense(expense);
    }
}
