import { Component, OnInit } from '@angular/core';
import { GraphiteService } from "../../../services/api/graphite.service";
import { Router } from '@angular/router';
import 'rxjs/add/operator/mergeMap';
import {GraphiteFilter} from "../../../model/graphite-filter.model";
import {FormBuilder, FormGroup} from "@angular/forms";


@Component({
    selector: 's-graphite_metrics-pg',
    templateUrl: './graphite_metrics.component.html',
    styleUrls: [ './graphite_metrics.scss']
})

export class GraphiteMetricsCompoment implements OnInit {

    panelOpenState = false;
    public graphiteForm: FormGroup;

    graphiteFilter: GraphiteFilter = new GraphiteFilter();

    chartView: any[] = [850, 500];
    serversByAvailabilityData : any[] = [];
    serversByEnvironmentData: any[] = [];

    maxDate = new Date();
    backDate = new Date();

    showXAxis = true;
    showYAxis = true;
    gradient = false;
    showLegend = true;
    timeline = true;
    autoScale = true;

    availabilityData: any[] = [];
    calcServerTradeProcessingData: any[] = [];
    calcServerTradeProcessingData2: any[] = [];
    calcServerTradeProcessingData3: any[] = [];
    calcServerTradeProcessingData4: any[] = [];
    calcServerTradeProcessingData5: any[] = [];
    loadCalcServerSchedTaskData1: any[] = [];
    loadCalcServerSchedTaskData2: any[] = [];
    loadCalcServerSchedTaskData3: any[] = [];
    loadCalcServerSchedTaskData4: any[] = [];
    loadCalcServerSchedTaskData5: any[] = [];
    concurrentUsersData: any[] = [];
    trioptimaRun: any[] = [];
    tradeReportMonitorData: any[] = [];
    transferReportMonitorData: any[] = [];

    colorScheme = {
        domain: ['#007cbb', '#61c673', '#ff8e28', '#ef2e2e']
    };
    barColorScheme = {
        domain: ['#007cbb']
    }

    constructor(private router: Router, private graphiteService: GraphiteService, private _fb: FormBuilder) {
        this.backDate.setDate(this.maxDate.getDate() - 2);
        this.graphiteFilter.dateRange = [this.backDate, this.maxDate];
    }

    ngOnInit() {
        var me = this;
        me.getPageData()
        me.graphiteFilter.listOfTypes = new Array<string>();
        me.graphiteForm = this._fb.group({
            checked: false,
            countryName: 'Test',
            toDate: new Date(),
            fromDate: me.backDate,
            hasAvailability: false,
            hasCalcServerTradeProcessingData: false,
            hasLoadCalcServerSchedTaskData: false,
            hasConcurrentUsersData: false,
            hasTrioptimaRun: false,
            hasTradeReportMonitorData: false,
            hasTransferReportMonitorData: false
        });
    }

    onSelect(event) {
        console.log(event);
    }

    getPageData() {
        var me = this;
        /*me.graphiteService.getGraphiteMetrics(me.graphiteFilter).subscribe(function(testData) {
            me.testData = testData.availabilityData;

            const index = me.graphiteFilter.listOfTypes.indexOf("hasCalcServerTradeProcessingData", 0);
            if (index > -1) {
                me.graphiteFilter.listOfTypes.splice(index, 1);
            }
        });*/
    }

    mapFormDataToGraphiteFilter() {
        var me = this;
        me.graphiteFilter.hasAvailability = me.graphiteForm.controls['hasAvailability'].value;
        me.graphiteFilter.hasCalcServerTradeProcessingData = me.graphiteForm.controls['hasCalcServerTradeProcessingData'].value;
        me.graphiteFilter.hasLoadCalcServerSchedTaskData = me.graphiteForm.controls['hasLoadCalcServerSchedTaskData'].value;
        me.graphiteFilter.hasConcurrentUsersData = me.graphiteForm.controls['hasConcurrentUsersData'].value;
        me.graphiteFilter.hasTrioptimaRun = me.graphiteForm.controls['hasTrioptimaRun'].value;
        me.graphiteFilter.hasTradeReportMonitorData = me.graphiteForm.controls['hasTradeReportMonitorData'].value;
        me.graphiteFilter.hasTransferReportMonitorData = me.graphiteForm.controls['hasTransferReportMonitorData'].value;
        me.graphiteFilter.dateRange = new Array<Date>();
        me.graphiteFilter.dateRange.push(me.graphiteForm.controls['fromDate'].value);
        me.graphiteFilter.dateRange.push(me.graphiteForm.controls['toDate'].value);
    }

    filter() {
        var me = this;

        // map values from form control to graphite filter object
        me.mapFormDataToGraphiteFilter();

        if (me.graphiteFilter.hasAvailability) {
            me.graphiteService.getGraphiteAvailabilityMetrics(me.graphiteFilter).subscribe(function(graphiteData) {
                me.availabilityData = graphiteData.availabilityData;
            });
        }

        if (me.graphiteFilter.hasCalcServerTradeProcessingData) {
            me.graphiteService.getGraphitecstpdMetrics(me.graphiteFilter).subscribe(function(graphiteData) {
                me.calcServerTradeProcessingData = graphiteData.calcServerTradeProcessingData;
                me.calcServerTradeProcessingData2 = graphiteData.calcServerTradeProcessingData2;
                me.calcServerTradeProcessingData3 = graphiteData.calcServerTradeProcessingData3;
                me.calcServerTradeProcessingData4 = graphiteData.calcServerTradeProcessingData4;
                me.calcServerTradeProcessingData5 = graphiteData.calcServerTradeProcessingData5;
            });
        }

        if (me.graphiteFilter.hasLoadCalcServerSchedTaskData) {
            me.graphiteService.getGraphitelcsstdMetrics(me.graphiteFilter).subscribe(function(graphiteData) {
                me.loadCalcServerSchedTaskData1 = graphiteData.loadCalcServerSchedTaskData1;
                me.loadCalcServerSchedTaskData2 = graphiteData.loadCalcServerSchedTaskData2;
                me.loadCalcServerSchedTaskData3 = graphiteData.loadCalcServerSchedTaskData3;
                me.loadCalcServerSchedTaskData4 = graphiteData.loadCalcServerSchedTaskData4;
                me.loadCalcServerSchedTaskData5 = graphiteData.loadCalcServerSchedTaskData5;
            });
        }

        if (me.graphiteFilter.hasConcurrentUsersData) {
            me.graphiteService.getGraphitecudMetrics(me.graphiteFilter).subscribe(function(graphiteData) {
                me.concurrentUsersData = graphiteData.concurrentUsersData;
            });
        }

        if (me.graphiteFilter.hasTrioptimaRun) {
            me.graphiteService.getGraphitetrMetrics(me.graphiteFilter).subscribe(function(graphiteData) {
                me.trioptimaRun = graphiteData.trioptimaRun;
            });
        }

        if (me.graphiteFilter.hasTradeReportMonitorData) {
            me.graphiteService.getGraphitetdrmdMetrics(me.graphiteFilter).subscribe(function(graphiteData) {
                me.tradeReportMonitorData = graphiteData.tradeReportMonitorData;
            });
        }

        if (me.graphiteFilter.hasTransferReportMonitorData) {
            me.graphiteService.getGraphitetfrmdMetrics(me.graphiteFilter).subscribe(function(graphiteData) {
                me.transferReportMonitorData = graphiteData.transferReportMonitorData;
            });
        }

        /*if (me.graphiteFilter.listOfTypes.length > 0) {
            me.graphiteService.getGraphiteMetrics(me.graphiteFilter).subscribe(function(graphiteData) {
                me.availabilityData = graphiteData.availabilityData;
                me.calcServerTradeProcessingData = graphiteData.calcServerTradeProcessingData;
            });
        }*/
    }

}