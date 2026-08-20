package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.DTOs.Espace.EspaceBacklogDTO;
import com.ISICOD.ScrumApp.Entities.ProductBacklog;
import com.ISICOD.ScrumApp.Repositories.ProductBacklogRepository;
import com.ISICOD.ScrumApp.Services.Builders.EspaceBacklogBuilder;
import com.ISICOD.ScrumApp.Services.ProductBacklogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductBacklogServiceImpl
        implements ProductBacklogService {

    private final ProductBacklogRepository productBacklogRepository;

    private final EspaceBacklogBuilder espaceBacklogBuilder;


    // ============================================================
    // CREATE
    // ============================================================

    @Override
    public ProductBacklog createProductBacklog(
            ProductBacklog productBacklog
    ) {

        return productBacklogRepository.save(productBacklog);
    }


    // ============================================================
    // GET BY ID
    // ============================================================

    @Override
    public Optional<ProductBacklog> getProductBacklogById(
            Integer id
    ) {

        return productBacklogRepository.findById(id);
    }


    // ============================================================
    // GET ALL
    // ============================================================

    @Override
    public List<ProductBacklog> getAllProductBacklogs() {

        return productBacklogRepository.findAll();
    }


    // ============================================================
    // UPDATE
    // ============================================================

    @Override
    public ProductBacklog updateProductBacklog(
            Integer id,
            ProductBacklog productBacklog
    ) {

        ProductBacklog existing =
                productBacklogRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "ProductBacklog introuvable avec id : "
                                                + id
                                )
                        );

        if (productBacklog.getCreeA() != null) {

            existing.setCreeA(
                    productBacklog.getCreeA()
            );
        }

        if (productBacklog.getEspace() != null) {

            existing.setEspace(
                    productBacklog.getEspace()
            );
        }

        return productBacklogRepository.save(existing);
    }


    // ============================================================
    // DELETE
    // ============================================================

    @Override
    public void deleteProductBacklog(
            Integer id
    ) {

        ProductBacklog backlog =
                productBacklogRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "ProductBacklog introuvable avec id : "
                                                + id
                                )
                        );

        productBacklogRepository.delete(backlog);
    }


    // ============================================================
    // ESPACE BACKLOG
    // ============================================================

    @Override
    public EspaceBacklogDTO getEspaceBacklog(
            Integer espaceId
    ) {

        ProductBacklog productBacklog =
                productBacklogRepository
                        .findByEspaceId(espaceId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product Backlog introuvable pour l'espace : "
                                                + espaceId
                                )
                        );

        if (productBacklog.getEspace() == null) {

            throw new RuntimeException(
                    "L'espace du Product Backlog est introuvable : "
                            + espaceId
            );
        }

        return espaceBacklogBuilder.build(
                productBacklog.getEspace(),
                productBacklog
        );
    }
}