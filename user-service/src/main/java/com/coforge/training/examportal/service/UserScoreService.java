package com.coforge.training.examportal.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coforge.training.examportal.model.UserScore;
import com.coforge.training.examportal.repository.UserScoreRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

/*
* Author      : Satyam.3.Singh
* Date        : 11:23:05 am
* Time        : 11:23:05 am
* Project     : user-service
*/

@Service
public class UserScoreService {

    @Autowired
    private UserScoreRepository userScoreRepository;

    public ByteArrayInputStream generateUserScorePdf(Long userId) {
        List<UserScore> userScores = userScoreRepository.findByUserId(userId);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("User Score Report"));

        Table table = new Table(UnitValue.createPercentArray(new float[]{4, 4, 2})).useAllAvailableWidth();
        table.addHeaderCell("User ID");
        table.addHeaderCell("Topic");
        table.addHeaderCell("Score");

        for (UserScore userScore : userScores) {
            table.addCell(userScore.getUserId().toString());
            table.addCell(userScore.getTopic());
            table.addCell(String.valueOf(userScore.getScore()));
        }

        document.add(table);
        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}

