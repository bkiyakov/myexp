package ru.bkiyakov.myexp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bkiyakov.myexp.models.User;
import ru.bkiyakov.myexp.repositories.UserRepository;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> findAll(){

        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User find(@PathVariable Long id){

        return userRepository.findById(id).orElse(null);
    }

    @PostMapping("/users")
    public User save(@RequestBody User user){

        return userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable Long id){

        userRepository.deleteById(id);
    }

    //TODO проверить (не работает, обновление происходит при попытке сохранения пользователся со старым id)
    @PutMapping("/user")
    public User update(@RequestBody User newUser){
        User user = userRepository.findById(newUser.getId()).orElse(new User()); //TODO в orElse поменять на null, и если null возвращать ошибку
        user.setUsername(newUser.getUsername());

        return userRepository.save(user);
    }
}
