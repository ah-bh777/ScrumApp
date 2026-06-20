package com.ISICOD.ScrumApp.Repositories;

import com.ISICOD.ScrumApp.Entities.ActionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionItemRepository extends JpaRepository<ActionItem, Integer> {
}