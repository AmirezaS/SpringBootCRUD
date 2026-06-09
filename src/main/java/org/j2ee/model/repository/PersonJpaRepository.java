package org.j2ee.model.repository;
import java.util.List;

import org.j2ee.model.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import jakarta.transaction.Transactional;



public interface PersonJpaRepository extends JpaRepository<Person,Long> {

    @Transactional
    @Modifying
    @Query("update Person p set p.name = :n , p.family = :f , p.email = :e WHERE p.id = :i")
    void update(@Param("i") Long id, @Param("n") String name,@Param("f") String family,@Param("e") String email);


    boolean existsByEmail(String email);

    Page<Person> findAll(Pageable pageable);

    List<Person> findAll();

    void deleteById(Long id);
}
