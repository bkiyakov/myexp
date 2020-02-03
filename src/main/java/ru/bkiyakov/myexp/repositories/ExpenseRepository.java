package ru.bkiyakov.myexp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bkiyakov.myexp.models.Expense;
import ru.bkiyakov.myexp.models.User;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUser(User user);
    //TODO добавить еще
}
