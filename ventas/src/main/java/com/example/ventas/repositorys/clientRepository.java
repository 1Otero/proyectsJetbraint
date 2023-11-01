package com.example.ventas.repositorys;

import com.example.ventas.modelos.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface clientRepository extends CrudRepository<Client, Long> {

    /*@Query(value = "SELECT OBJECT(c) FROM Client AS c")
    List<Client> findAllClient();*/
}
