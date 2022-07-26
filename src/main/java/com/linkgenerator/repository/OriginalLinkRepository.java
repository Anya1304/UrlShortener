package com.linkgenerator.repository;

import com.linkgenerator.model.OriginalLink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OriginalLinkRepository extends CrudRepository<OriginalLink, String> {
    void updateCountOfShortening(int count, String link);
}
