package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.ProductBacklog;
import com.ISICOD.ScrumApp.Repositories.ProductBacklogRepository;
import com.ISICOD.ScrumApp.Services.ProductBacklogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductBacklogServiceImpl implements ProductBacklogService {

    private final ProductBacklogRepository productBacklogRepository;

    @Override
    public ProductBacklog createProductBacklog(ProductBacklog productBacklog) {
        return productBacklogRepository.save(productBacklog);
    }

    @Override
    public Optional<ProductBacklog> getProductBacklogById(Integer id) {
        return productBacklogRepository.findById(id);
    }

    @Override
    public List<ProductBacklog> getAllProductBacklogs() {
        return productBacklogRepository.findAll();
    }

    @Override
    public ProductBacklog updateProductBacklog(Integer id, ProductBacklog productBacklog) {

        ProductBacklog existing = productBacklogRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ProductBacklog introuvable avec id : " + id));

        if (productBacklog.getCreeA() != null) {
            existing.setCreeA(productBacklog.getCreeA());
        }

        if (productBacklog.getEspace() != null) {
            existing.setEspace(productBacklog.getEspace());
        }

        return productBacklogRepository.save(existing);
    }

    @Override
    public void deleteProductBacklog(Integer id) {

        ProductBacklog backlog = productBacklogRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("ProductBacklog introuvable avec id : " + id));

        productBacklogRepository.delete(backlog);
    }
}