import { Component, OnInit } from '@angular/core';
import { ServerService } from '../../../services/api/server.service';
import { Router } from '@angular/router';
import 'rxjs/add/operator/mergeMap';


@Component({
    selector: 's-server_stats-pg',
    templateUrl: './server_stats.component.html',
    styleUrls: [ './server_stats.scss'],
})

export class ServerStatsComponent implements OnInit {
    chartView: any[] = [900, 500];
    view: any[] = [480, 180];
    serversByAvailabilityData : any[] = [];
    serversByEnvironmentData: any[] = [];

    showXAxis = true;
    showYAxis = true;
    gradient = false;
    showLegend = true;
    /*showXAxisLabel = true;*/
    /*xAxisLabel = '';*/
    /*showYAxisLabel = true;*/
    /*yAxisLabel = 'Load';*/
    timeline = true;
    autoScale = true;

    testData: any[] = [];
    colorScheme = {
        domain: ['#007cbb', '#61c673', '#ff8e28', '#ef2e2e']
    };
    barColorScheme = {
        domain: ['#007cbb']
    }

    constructor(private router: Router, private serverService: ServerService) { }

    ngOnInit() {
        var me = this;
        this.getPageData()
    }

    onSelect(event) {
        console.log(event);
    }

    getPageData() {
        var me = this;

        /**
         * This is an Example of sequencing RxJS observable using mergeMap
         * (We are sequencing the API calls as the H2 DB used by the backend is failing to serve multiple request at once)
         */

        /*me.serverService.getServerStats("availability")
            .mergeMap(function(availabilityData) {
                me.serversByAvailabilityData = availabilityData.items;
                console.log("Received Servers By Availability");
                return me.serverService.getServerStats("test");
            }).mergeMap(function(testData) {
                me.testData = testData.items;
                console.log("Received Servers By test");
                return me.serverService.getServerStats("environment");
            }).subscribe(function(environmentData){
            me.serversByEnvironmentData = environmentData.items;
            console.log("Received Servers By Environment");
        });*/


        me.serverService.getServerStats("availability")
            .mergeMap(function(availabilityData) {
                me.serversByAvailabilityData = availabilityData.items;
                console.log("Received Servers By Availability");
                return me.serverService.getServerStats("environment");
            }).subscribe(function(environmentData){
            me.serversByEnvironmentData = environmentData.items;
            console.log("Received Servers By Environment");
        });
    }


}