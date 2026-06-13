package edu.unicen.tallerjava.todo.users;

import org.springframework.data.repository.CrudRepository;


import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer>{
    Optional<User> findFirstByOrderByIdDesc();
}
