package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.ActionItem;
import com.ISICOD.ScrumApp.Services.ActionItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/action-items")
@RequiredArgsConstructor
public class ActionItemController {

    private final ActionItemService actionItemService;


    @PostMapping
    public ResponseEntity<ActionItem> createActionItem(
            @RequestBody ActionItem actionItem) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(actionItemService.createActionItem(actionItem));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getActionItemById(
            @PathVariable Integer id) {

        return actionItemService.getActionItemById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {

                    Map<String, Object> error = new HashMap<>();
                    error.put("message", "ActionItem not found");
                    error.put("id", id);

                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);
                });
    }


    @GetMapping
    public ResponseEntity<List<ActionItem>> getAllActionItems() {

        return ResponseEntity.ok(
                actionItemService.getAllActionItems()
        );
    }


    // Partial update
    @PatchMapping("/{id}")
    public ResponseEntity<ActionItem> updateActionItem(
            @PathVariable Integer id,
            @RequestBody ActionItem actionItem) {

        return ResponseEntity.ok(
                actionItemService.updateActionItem(id, actionItem)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActionItem(
            @PathVariable Integer id) {

        actionItemService.deleteActionItem(id);

        return ResponseEntity.noContent().build();
    }
}