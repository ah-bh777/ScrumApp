package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.ProductBacklog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductBacklogRepository extends JpaRepository<ProductBacklog, Integer> {
    Optional<ProductBacklog> findByEspaceId(Integer espaceId);
}