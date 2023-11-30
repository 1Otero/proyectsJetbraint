package com.example.jwtspring.repositorys;

import com.example.jwtspring.modelos.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface clientRepository extends CrudRepository<Client, Long> {

    /*@Query(value = "SELECT OBJECT(c) FROM Client AS c")
    List<Client> findAllClient();*/
    Optional<Client> findOneByCorreo(String correo);
}
