package com.kredivation.tuktuk.report;

public class ReportData {
    private String report;
    private int id;

    public ReportData() {
    }

    public ReportData(String report, int id) {
        this.report = report;
        this.id = id;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        ReportData itemCompare = (ReportData) obj;
        if(itemCompare.getReport().equals(this.getReport()))
            return true;

        return false;
    }
}
