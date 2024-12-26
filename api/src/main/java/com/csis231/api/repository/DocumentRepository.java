package com.csis231.api.repository;

import com.csis231.api.model.Document;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository {
    Document save(Document document);

    List<Document> findByUserId(Long userId);

    Optional<Object> findById(Long documentId);

    void delete(Document document);
}
