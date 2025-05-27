package com.codestorykh.cache;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findFirstById(Long id);

    @Modifying
    @Transactional
    @Query(value = "update book SET title = ?1, year = ?2 where id = ?3", nativeQuery = true)
    int updateBookTitleAndYear(String title, int year, Long id);

    @Modifying
    @Transactional
    @Query(value = "update book set title = :title, year = :year where id = :id", nativeQuery = true)
    int updateBookTitleAndYearById(@Param("title") String title,
                                   @Param("year") int year,
                                   @Param("id") Long id);
}
