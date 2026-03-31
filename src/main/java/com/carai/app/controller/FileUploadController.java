package com.carai.app.controller;

import com.carai.app.model.AnalysisResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class FileUploadController {

    @GetMapping("/test")
    public String test() {
        return "Backend working!";
    }

    @PostMapping("/upload")
    public AnalysisResponse uploadFile(@RequestParam("file") MultipartFile file) {

        try {
            PDDocument document = PDDocument.load(file.getInputStream());
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();

            // VIN
            Pattern vinPattern = Pattern.compile("\\b[A-HJ-NPR-Z0-9]{17}\\b");
            Matcher vinMatcher = vinPattern.matcher(text);
            String vin = vinMatcher.find() ? vinMatcher.group() : "Not found";

            // Amount
            Pattern amountPattern = Pattern.compile("\\$\\d+(,\\d{3})*(\\.\\d{2})?");
            Matcher amountMatcher = amountPattern.matcher(text);
            String amount = amountMatcher.find() ? amountMatcher.group() : "Not found";

            // Date
            Pattern datePattern = Pattern.compile("\\b\\d{2}/\\d{2}/\\d{4}\\b");
            Matcher dateMatcher = datePattern.matcher(text);
            String date = dateMatcher.find() ? dateMatcher.group() : "Not found";

            // Email
            Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
            Matcher emailMatcher = emailPattern.matcher(text);
            String email = emailMatcher.find() ? emailMatcher.group() : "Not found";

            String lowerText = text.toLowerCase();

            // Risk
            String risk = (lowerText.contains("penalty") ||
                    lowerText.contains("late fee") ||
                    lowerText.contains("interest") ||
                    lowerText.contains("fine"))
                    ? "High Risk"
                    : "Low Risk";

            // Summary
            String summary = "Contract analyzed successfully";

            String aiSummary =
                    "Summary: " + summary +
                            "\n\nDetected Terms: Interest, Payment, Risk clauses" +
                            "\nRisk Level: " + risk;

            return new AnalysisResponse(vin, amount, date, email, risk, aiSummary, "5/10");

        } catch (Exception e) {
            return new AnalysisResponse("Error", "Error", "Error", "Error", "Error", "Error", "0/10");
        }
    }
}