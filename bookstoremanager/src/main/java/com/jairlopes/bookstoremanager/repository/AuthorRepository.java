package com.jairlopes.bookstoremanager.repository;

import com.jairlopes.bookstoremanager.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
