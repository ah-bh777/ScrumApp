package com.ISICOD.ScrumApp.Services;

import com.ISICOD.ScrumApp.Entities.ProductBacklog;

import java.util.List;
import java.util.Optional;

public interface ProductBacklogService {

    ProductBacklog createProductBacklog(ProductBacklog productBacklog);

    Optional<ProductBacklog> getProductBacklogById(Integer id);

    List<ProductBacklog> getAllProductBacklogs();

    ProductBacklog updateProductBacklog(Integer id, ProductBacklog productBacklog);

    void deleteProductBacklog(Integer id);
}