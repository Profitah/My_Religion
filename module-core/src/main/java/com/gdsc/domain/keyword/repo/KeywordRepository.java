package com.gdsc.domain.keyword.repo;

import com.gdsc.domain.keyword.entity.Keyword;
import com.gdsc.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findByContent(String content);

    Optional<List<Keyword>> findByUser(User user);
}
