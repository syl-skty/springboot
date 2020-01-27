package com.example.skty.springboot.repository.db2;

import com.example.skty.springboot.entity.db2.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 书籍数据库访问dao
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * 根据书名查询所有书籍
     *
     * @param name
     * @return
     */
    List<Book> findBooksByBookNameContains(String name);

    /**
     * 查询书价大于指定价格的书
     *
     * @param price
     * @return
     */
    List<Book> findBookByPriceIsGreaterThan(float price);

}
