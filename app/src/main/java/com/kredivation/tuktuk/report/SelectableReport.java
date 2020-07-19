package com.kredivation.tuktuk.report;

/**
 * Created by Narayan on 19-07-2020.
 */
public class SelectableReport extends ReportData {
    private boolean isSelected = false;


    public SelectableReport(ReportData item,boolean isSelected) {
        super(item.getReport(),item.getId());
        this.isSelected = isSelected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}