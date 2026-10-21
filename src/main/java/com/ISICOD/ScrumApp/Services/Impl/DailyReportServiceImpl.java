
package com.ISICOD.ScrumApp.Services.Impl;

import com.ISICOD.ScrumApp.Entities.*;
import com.ISICOD.ScrumApp.Enums.TypeSessionCode;
import com.ISICOD.ScrumApp.Exceptions.*;
import com.ISICOD.ScrumApp.Repositories.*;
import com.ISICOD.ScrumApp.Services.DailyReportService;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DailyReportServiceImpl implements DailyReportService {

    private final SessionRepository sessionRepository;
    private final ParticipantSessionRepository participantRepository;
    private final DailyContentRepository contentRepository;

    private static final Color NAVY = new Color(22, 50, 79);
    private static final Color BLUE = new Color(37, 99, 235);
    private static final Color LIGHT = new Color(242, 246, 250);
    private static final Color GRAY = new Color(100, 116, 139);
    private static final Color GREEN = new Color(22, 163, 74);
    private static final Color ORANGE = new Color(217, 119, 6);
    private static final Color RED = new Color(220, 38, 38);

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final Font titleFont = FontFactory.getFont(
            FontFactory.HELVETICA_BOLD, 19, Color.WHITE
    );

    private final Font sectionFont = FontFactory.getFont(
            FontFactory.HELVETICA_BOLD, 13, NAVY
    );

    private final Font normalFont = FontFactory.getFont(
            FontFactory.HELVETICA, 10, Color.DARK_GRAY
    );

    private final Font boldFont = FontFactory.getFont(
            FontFactory.HELVETICA_BOLD, 10, NAVY
    );

    private final Font smallFont = FontFactory.getFont(
            FontFactory.HELVETICA, 9, GRAY
    );

    @Override
    @Transactional(readOnly = true)
    public byte[] generatePdf(Integer sessionId ) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Daily introuvable : " + sessionId
                ));

        if (session.getTypeSession() == null
                || session.getTypeSession().getCode() != TypeSessionCode.DAILY) {
            throw new BadRequestException(
                    "Cette session n'est pas une Daily."
            );
        }

        List<ParticipantSession> participants =
                participantRepository.findBySessionIdOrderByIdAsc(sessionId);

        List<DailyContent> contents =
                contentRepository.findBySessionIdOrderByCreeAAsc(sessionId);

        Map<Integer, List<DailyContent>> contentsByParticipant =
                contents.stream().collect(
                        Collectors.groupingBy(
                                c -> c.getParticipantSession().getId()
                        )
                );

        long submitted = participants.stream()
                .filter(p -> Boolean.TRUE.equals(p.getSoumise()))
                .count();

        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            Document document = new Document(
                    PageSize.A4, 42, 42, 45, 45
            );

            PdfWriter writer = PdfWriter.getInstance(document, output);
            writer.setPageEvent(new PdfPageEventHelper() {
                @Override
                public void onEndPage(PdfWriter writer, Document document) {
                    Phrase footer = new Phrase(
                            "SCRUM PLATFORM  |  Page " + writer.getPageNumber(),
                            FontFactory.getFont(
                                    FontFactory.HELVETICA, 8, GRAY
                            )
                    );

                    ColumnText.showTextAligned(
                            writer.getDirectContent(),
                            Element.ALIGN_CENTER,
                            footer,
                            (document.left() + document.right()) / 2,
                            document.bottom() - 22,
                            0
                    );
                }
            });

            document.open();

            addHeader(document, session);
            addSummary(document, participants.size(), submitted, contents.size());
            addAttendance(document, participants);
            addContributions(document, participants, contentsByParticipant);

            document.close();

            return output.toByteArray();

        } catch (DocumentException e) {
            throw new IllegalStateException(
                    "Impossible de générer le rapport PDF.", e
            );
        } catch (java.io.IOException e) {
            throw new IllegalStateException(
                    "Erreur lors de la fermeture du rapport PDF.", e
            );
        }
    }

    private void addHeader(Document document, Session session)
            throws DocumentException {

        PdfPTable header = new PdfPTable(1);
        header.setWidthPercentage(100);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(NAVY);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(18);

        Paragraph eyebrow = new Paragraph(
                "SCRUM PLATFORM  /  RAPPORT DE SESSION",
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD, 9,
                        new Color(156, 212, 245)
                )
        );
        eyebrow.setSpacingAfter(8);
        cell.addElement(eyebrow);

        Paragraph title = new Paragraph(
                safe(session.getTitre()), titleFont
        );
        title.setSpacingAfter(8);
        cell.addElement(title);

        String dates = format(session.getCommenceA())
                + "  -  " + format(session.getTermineA());

        cell.addElement(new Paragraph(
                dates,
                FontFactory.getFont(
                        FontFactory.HELVETICA, 10, Color.WHITE
                )
        ));

        cell.addElement(new Paragraph(
                "Statut : " + session.getStatus(),
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD, 10, Color.WHITE
                )
        ));

        header.addCell(cell);
        document.add(header);
        document.add(Chunk.NEWLINE);
    }

    private void addSummary(
            Document document,
            int participantCount,
            long submitted,
            int contributionCount
    ) throws DocumentException {

        PdfPTable stats = new PdfPTable(3);
        stats.setWidthPercentage(100);
        stats.setSpacingAfter(22);

        addStat(stats, String.valueOf(participantCount), "PARTICIPANTS");
        addStat(stats, String.valueOf(submitted), "SOUMISSIONS");
        addStat(stats, String.valueOf(contributionCount), "CONTRIBUTIONS");

        document.add(stats);
    }

    private void addStat(PdfPTable table, String value, String label) {

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(LIGHT);
        cell.setBorderColor(Color.WHITE);
        cell.setBorderWidth(4);
        cell.setPadding(12);

        Paragraph number = new Paragraph(
                value,
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD, 20, NAVY
                )
        );
        number.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(number);

        Paragraph description = new Paragraph(label, smallFont);
        description.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(description);

        table.addCell(cell);
    }

    private void addAttendance(
            Document document,
            List<ParticipantSession> participants
    ) throws DocumentException {

        addSectionTitle(document, "Présence des participants");

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3, 2, 2.4f, 2.4f, 1.5f});
        table.setSpacingAfter(22);
        table.setHeaderRows(1);

        for (String heading : new String[]{
                "Participant", "Rôle", "Entrée", "Sortie", "Soumis"
        }) {
            PdfPCell cell = new PdfPCell(new Phrase(
                    heading,
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD, 9, Color.WHITE
                    )
            ));
            cell.setBackgroundColor(NAVY);
            cell.setPadding(8);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        }

        int row = 0;

        for (ParticipantSession p : participants) {
            Color background = row++ % 2 == 0
                    ? Color.WHITE : LIGHT;

            addTableCell(table, p.getPseudo(), background);
            addTableCell(table, String.valueOf(p.getRoleSession()), background);
            addTableCell(table, format(p.getRejointA()), background);
            addTableCell(table, format(p.getQuitteA()), background);
            addTableCell(table,
                    Boolean.TRUE.equals(p.getSoumise()) ? "Oui" : "Non",
                    background
            );
        }

        document.add(table);
    }

    private void addContributions(
            Document document,
            List<ParticipantSession> participants,
            Map<Integer, List<DailyContent>> contentsByParticipant
    ) throws DocumentException {

        addSectionTitle(document, "Contributions et commentaires");

        for (ParticipantSession participant : participants) {

            List<DailyContent> contributions =
                    contentsByParticipant.getOrDefault(
                            participant.getId(), List.of()
                    );

            Paragraph name = new Paragraph(
                    safe(participant.getPseudo()), sectionFont
            );
            name.setSpacingBefore(12);
            name.setSpacingAfter(8);
            document.add(name);

            if (contributions.isEmpty()) {
                Paragraph empty = new Paragraph(
                        "Aucune contribution enregistrée.", smallFont
                );
                empty.setIndentationLeft(12);
                document.add(empty);
                continue;
            }

            for (DailyContent content : contributions) {
                addContributionCard(document, content);
            }
        }
    }

    private void addContributionCard(
            Document document,
            DailyContent content
    ) throws DocumentException {

        Color categoryColor = getCategoryColor(
                String.valueOf(content.getTypeContenu())
        );

        PdfPTable card = new PdfPTable(1);
        card.setWidthPercentage(100);
        card.setSpacingAfter(10);
        card.setKeepTogether(true);

        PdfPCell cell = new PdfPCell();
        cell.setBorderColor(new Color(222, 229, 236));
        cell.setBorderWidth(0.7f);
        cell.setBorderWidthLeft(4);
        cell.setBorderColorLeft(categoryColor);
        cell.setBackgroundColor(Color.WHITE);
        cell.setPadding(12);

        Paragraph category = new Paragraph(
                displayCategory(String.valueOf(content.getTypeContenu())),
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD, 10, categoryColor
                )
        );
        category.setSpacingAfter(5);
        cell.addElement(category);

        Paragraph timestamp = new Paragraph(
                format(content.getCreeA()), smallFont
        );
        timestamp.setSpacingAfter(8);
        cell.addElement(timestamp);

        Paragraph comment = new Paragraph(
                safe(content.getContenu()), normalFont
        );
        comment.setSpacingAfter(8);
        cell.addElement(comment);

        SprintUserStory sprintStory = content.getSprintUserStory();

        if (sprintStory != null && sprintStory.getUserStory() != null) {

            UserStory userStory = sprintStory.getUserStory();

            Paragraph storyTitle = new Paragraph(
                    "User Story : " + safe(userStory.getTitre()),
                    boldFont
            );
            storyTitle.setSpacingBefore(6);
            storyTitle.setSpacingAfter(4);
            cell.addElement(storyTitle);

            if (userStory.getDescription() != null
                    && !userStory.getDescription().isBlank()) {

                Paragraph description = new Paragraph(
                        userStory.getDescription(), normalFont
                );
                cell.addElement(description);
            }
        }

        card.addCell(cell);
        document.add(card);
    }

    private void addSectionTitle(Document document, String text)
            throws DocumentException {

        Paragraph section = new Paragraph(text, sectionFont);
        section.setSpacingBefore(8);
        section.setSpacingAfter(12);
        document.add(section);
    }

    private void addTableCell(
            PdfPTable table,
            String value,
            Color background
    ) {
        PdfPCell cell = new PdfPCell(new Phrase(
                safe(value), normalFont
        ));
        cell.setBackgroundColor(background);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(8);
        table.addCell(cell);
    }

    private Color getCategoryColor(String category) {
        return switch (category) {
            case "TERMINEE" -> GREEN;
            case "A_TESTER" -> ORANGE;
            case "BLOCKAGE", "BLOCAGE" -> RED;
            default -> BLUE;
        };
    }

    private String displayCategory(String category) {
        return switch (category) {
            case "TERMINEE" -> "TERMINÉE";
            case "A_TESTER" -> "À TESTER";
            case "BLOCKAGE", "BLOCAGE" -> "BLOCAGE";
            default -> category.replace('_', ' ');
        };
    }

    private String format(LocalDateTime date) {
        return date == null ? "—" : date.format(DATE_FORMAT);
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "—" : value;
    }
}
