package org.pahappa.systems.kimanyisacco.views.adminReports;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.annotation.PostConstruct;

import org.pahappa.systems.kimanyisacco.services.Interfaces.MemberService;
import org.pahappa.systems.kimanyisacco.services.Interfaces.TransactionService;
import org.pahappa.systems.kimanyisacco.services.MemberServiceImpl;
import org.pahappa.systems.kimanyisacco.services.TransactionServiceImpl;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import java.util.ArrayList;
import java.util.List;


@ManagedBean(name = "adminReports")
@SessionScoped
public class AdminReports {
    MemberService memberService = new MemberServiceImpl();
    TransactionService transactionService = new TransactionServiceImpl();
    private PieChartModel pieModel;
    public PieChartModel getPieModel(){
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel){
        this.pieModel = pieModel;
    }

    @PostConstruct
    public void init(){
        createPieModel();
    }

    public void AdminReports(){
        createPieModel();
    }


    private void createPieModel() {
        pieModel = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(transactionService.getWithdrawType());
        values.add(transactionService.getDepositType());

        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 99, 132)");

        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Withdraws");
        labels.add("Deposits");

        data.setLabels(labels);

        pieModel.setData(data);
    }

}
