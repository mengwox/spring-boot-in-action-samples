package com.mawenhao.springbootinaction.repository;

import com.mawenhao.springbootinaction.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * TODO
 *
 * @author mawenhao 2023/5/29
 */
public interface ReadingListRepository extends JpaRepository<Book, Long> {
    List<Book> findByReader(String reader);
}