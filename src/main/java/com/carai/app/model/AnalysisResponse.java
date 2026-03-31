package com.carai.app.model;

public class AnalysisResponse {

    private String vin;
    private String amount;
    private String date;
    private String email;
    private String risk;
    private String aiSummary;
    private String score;

    public AnalysisResponse() {
    }

    public AnalysisResponse(String vin, String amount, String date, String email, String risk, String aiSummary, String score) {
        this.vin = vin;
        this.amount = amount;
        this.date = date;
        this.email = email;
        this.risk = risk;
        this.aiSummary = aiSummary;
        this.score = score;
    }

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRisk() { return risk; }
    public void setRisk(String risk) { this.risk = risk; }

    public String getAiSummary() { return aiSummary; }
    public void setAiSummary(String aiSummary) { this.aiSummary = aiSummary; }

    public String getScore() { return score; }
    public void setScore(String score) { this.score = score; }
}