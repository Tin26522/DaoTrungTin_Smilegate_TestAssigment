package com.example.daotrungtin_smilegate_testassigment.repository;

import com.example.daotrungtin_smilegate_testassigment.entity.Category;
import com.example.daotrungtin_smilegate_testassigment.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends JpaRepository<Game, String> {

    @Query("""
        select distinct g from Game g
        left join g.names n
        where (:category is null or g.category = :category)
          and (:q is null or lower(g.id) like lower(concat('%', :q, '%'))
               or lower(n.value) like lower(concat('%', :q, '%')))
    """)
    Page<Game> search(
            @Param("category") Category category,
            @Param("q") String q,
            Pageable pageable
    );
}