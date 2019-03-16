import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import 'rxjs/add/operator/mergeMap';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {DatePipe} from "@angular/common";
import {UserInfoService} from "../../../services/user-info.service";
import {ClientService} from "../../../services/api/client.service";

@Component({
    selector: 's-client_add-pg',
    templateUrl: './client_add.component.html',
    styleUrls: [ './client_add.scss'],
    providers: [DatePipe]
})

export class ClientAddComponent implements OnInit {

    panelOpenState = false;
    public clientForm: FormGroup;
    private clientId: number;

    maxDate = new Date();
    backDate = new Date();

    emailFormControl = new FormControl('', [
        Validators.required,
        Validators.email,
    ]);

    constructor(private router: Router, private clientService: ClientService, private _fb: FormBuilder, private datePipe: DatePipe,
                private userInfoService:UserInfoService) {
        var me = this;
        me.clientForm = this._fb.group({
            isCorporate : false,
            name: '',
            createdAt: new Date(),
            updatedAt: new Date(),
            clientPriceGroup: '',
            salesAgentId: '',
            fidelityCardId: '',
            fidelityGroup:'',
            lastBillingDate: new Date(),
            billingRateIndex: '',
            emailConfirmation: false,
            daysFromLastBill: '',
            email:'',
            assignedCenter:'',
            country:'',
            countryCode:'',
            address: '',
            contactPhone:'',
            contact:'',
            paymentMethod:'',
            paymentDueIn:'',
            paymentNotification:'',
            contractNr:'',
            contractExpirationDate: new Date(),
            regCode:'',
            isVatApplicable: false,
            balance:'',
            debit:'',
            credit:'',
            creditApprovedLimit:'',
            creditLimit:'',
            legalNoticeReceivedOn: new Date(),
            legalNoticeSentOn: new Date(),
            legalNoticeOutcome:'',
            cipReceivedOn: new Date(),
            cipSentOn: new Date(),
            cipOutcome:'',
            finNoticeReceivedOn: new Date(),
            finNoticeSentOn: new Date(),
            finNoticeOutcome:'',
            orgType:'',
            salesAmount: '',
            comment:'',
        });
    }

    ngOnInit() {
        var me = this;
    }



    submit() {
        alert("save");
    }

    /*


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
            hasTransferReportMonitorData: false,
            hasEODDuration: false,
            hasEODEndTime: false
        });
    }

    onSelect(event) {
        console.log(event);
    }

    public formatBytesTickFormatting(bytes: any): string {
        if(bytes < 1024) return bytes + " Bytes";
        else if(bytes < 1048576) return(bytes / 1024).toFixed(3) + " KB";
        else if(bytes < 1073741824) return(bytes / 1048576).toFixed(3) + " MB";
        else return(bytes / 1073741824).toFixed(3) + " GB";
    }

    public percentTickFormatting(val: any): string {
        var percent = ((val/21474836480)*100).toFixed(2) + '%';
        return percent;
    }

    public dateTickFormatting(val: any): string {
        var date = new Date(val);
        return date.toLocaleDateString();
    }

    getPageData() {
        var me = this;
        /!*me.graphiteService.getGraphiteMetrics(me.graphiteFilter).subscribe(function(testData) {
            me.testData = testData.availabilityData;

            const index = me.graphiteFilter.listOfTypes.indexOf("hasCalcServerTradeProcessingData", 0);
            if (index > -1) {
                me.graphiteFilter.listOfTypes.splice(index, 1);
            }
        });*!/
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
        me.graphiteFilter.hasEODDuration = me.graphiteForm.controls['hasEODDuration'].value;
        me.graphiteFilter.hasEODEndTime = me.graphiteForm.controls['hasEODEndTime'].value;
        me.graphiteFilter.dateRange = new Array<Date>();
        me.graphiteFilter.dateRange.push(me.graphiteForm.controls['fromDate'].value);
        me.graphiteFilter.dateRange.push(me.graphiteForm.controls['toDate'].value);
    }

    genReport() {
        this.graphiteService.genReport();
    }

    getReport() {
        var me = this;
        me.graphiteService.getJasperReport(me.graphiteFilter,
            me.datePipe.transform(me.graphiteForm.controls['fromDate'].value, 'yyyy-MM-dd'),
            me.datePipe.transform(me.graphiteForm.controls['toDate'].value, 'yyyy-MM-dd'),
            me.userId);
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

        if (me.graphiteFilter.hasEODDuration) {
            me.graphiteService.getGraphiteEODDuration(me.graphiteFilter).subscribe(function(graphiteData) {
                me.eodDurationData = graphiteData.eodDurationData;
            });
        }

        if (me.graphiteFilter.hasEODEndTime) {
            me.graphiteService.getGraphiteEODEndTime(me.graphiteFilter).subscribe(function(graphiteData) {
                me.eodEndTimeData = graphiteData.eodEndTimeData;
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

        /!*if (me.graphiteFilter.listOfTypes.length > 0) {
            me.graphiteService.getGraphiteMetrics(me.graphiteFilter).subscribe(function(graphiteData) {
                me.availabilityData = graphiteData.availabilityData;
                me.calcServerTradeProcessingData = graphiteData.calcServerTradeProcessingData;
            });
        }*!/
    }*/

}