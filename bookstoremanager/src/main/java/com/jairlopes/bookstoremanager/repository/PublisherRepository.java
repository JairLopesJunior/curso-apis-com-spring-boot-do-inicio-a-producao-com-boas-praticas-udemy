package com.jairlopes.bookstoremanager.repository;

import com.jairlopes.bookstoremanager.entity.Author;
import com.jairlopes.bookstoremanager.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findByNameOrCode(String name, String code);
}
