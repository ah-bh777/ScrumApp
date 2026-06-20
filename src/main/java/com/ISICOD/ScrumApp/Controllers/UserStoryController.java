package com.ISICOD.ScrumApp.Controllers;

import com.ISICOD.ScrumApp.Entities.UserStory;
import com.ISICOD.ScrumApp.Services.UserStoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user-stories")
@RequiredArgsConstructor
public class UserStoryController {

    private final UserStoryService userStoryService;

    @PostMapping
    public ResponseEntity<UserStory> createUserStory(
            @RequestBody UserStory userStory) {

        return ResponseEntity.ok(
                userStoryService.createUserStory(userStory)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserStoryById(
            @PathVariable Integer id) {

        return userStoryService.getUserStoryById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(()->{
                    Map<String ,Object> error = new HashMap<>();
                    error.put("message" , "not found");
                    error.put("id",id);

                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

                });
    }

    @GetMapping
    public ResponseEntity<List<UserStory>> getAllUserStories() {

        return ResponseEntity.ok(
                userStoryService.getAllUserStories()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUserStory(
            @PathVariable Integer id,
            @RequestBody UserStory userStory) {


        return userStoryService.getUserStoryById(id)
                .<ResponseEntity<?>>map(existing -> {


                    if (userStory.getTitre() != null) {
                        existing.setTitre(userStory.getTitre());
                    }


                    if (userStory.getDescription() != null) {
                        existing.setDescription(userStory.getDescription());
                    }


                    if (userStory.getPriorite() != null) {
                        existing.setPriorite(userStory.getPriorite());
                    }


                    if (userStory.getStoryPoints() != null) {
                        existing.setStoryPoints(userStory.getStoryPoints());
                    }


                    if (userStory.getCreeA() != null) {
                        existing.setCreeA(userStory.getCreeA());
                    }


                    if (userStory.getProductBacklog() != null) {
                        existing.setProductBacklog(userStory.getProductBacklog());
                    }


                    UserStory updated =
                            userStoryService.createUserStory(existing);


                    return ResponseEntity.ok(updated);

                })
                .orElseGet(() -> {

                    Map<String,Object> error = new HashMap<>();

                    error.put("message", "UserStory not found");
                    error.put("id", id);

                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(error);
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserStory(
            @PathVariable Integer id) {

        userStoryService.deleteUserStory(id);

        return ResponseEntity.noContent().build();
    }
}