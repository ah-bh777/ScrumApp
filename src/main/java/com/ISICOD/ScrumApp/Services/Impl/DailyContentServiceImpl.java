package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.DailyContent;

import com.ISICOD.ScrumApp.Repositories.DailyContentRepository;
import com.ISICOD.ScrumApp.Services.DailyContentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyContentServiceImpl implements DailyContentService {

    private final DailyContentRepository dailyContentRepository;

    @Override
    public DailyContent createDailyContent(DailyContent dailyContent) {
        return dailyContentRepository.save(dailyContent);
    }

    @Override
    public Optional<DailyContent> getDailyContentById(Integer id) {
        return dailyContentRepository.findById(id);
    }

    @Override
    public List<DailyContent> getDailyContentAll() {
        return dailyContentRepository.findAll();
    }

    @Override
    public DailyContent updateDailyContent(Integer id, DailyContent dailyContent) {

        DailyContent existing = dailyContentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("DailyContent introuvable avec id : " + id));

        if (dailyContent.getContenu() != null) {
            existing.setContenu(dailyContent.getContenu());
        }

        if (dailyContent.getTypeContenu() != null) {
            existing.setTypeContenu(dailyContent.getTypeContenu());
        }

        if (dailyContent.getCreeA() != null) {
            existing.setCreeA(dailyContent.getCreeA());
        }

        if (dailyContent.getSession() != null) {
            existing.setSession(dailyContent.getSession());
        }

        if (dailyContent.getParticipantSession() != null) {
            existing.setParticipantSession(dailyContent.getParticipantSession());
        }

        return dailyContentRepository.save(existing);
    }

    @Override
    public void deleteDailyContent(Integer id) {

        DailyContent existing = dailyContentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("DailyContent introuvable avec id : " + id));

        dailyContentRepository.delete(existing);
    }
}