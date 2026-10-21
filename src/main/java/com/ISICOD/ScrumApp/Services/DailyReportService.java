package com.ISICOD.ScrumApp.Services;

public interface DailyReportService {

    byte[] generatePdf(
            Integer sessionId
    );
}