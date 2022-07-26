package com.linkgenerator.repository;

import com.linkgenerator.model.ShortLink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortLinkRepository extends CrudRepository<ShortLink, String> {
    void updateCountOfTransactions(int count, String link);
}
