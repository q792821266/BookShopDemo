package com.example.bookShopDemo.repository;

import com.example.bookShopDemo.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
