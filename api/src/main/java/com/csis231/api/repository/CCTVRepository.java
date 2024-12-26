package com.csis231.api.repository;

import java.util.Optional;

public interface CCTVRepository {
    Optional<Object> findById(Long cctvId);
}
