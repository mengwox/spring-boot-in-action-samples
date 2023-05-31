package com.mawenhao.springbootinaction.repository;

import com.mawenhao.springbootinaction.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TODO
 *
 * @author mawenhao 2023/5/29
 */
public interface ReaderRepository extends JpaRepository<Reader, String> {
}
