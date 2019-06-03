import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import 'rxjs/add/operator/mergeMap';
import {ClientService} from "../../../services/api/client.service";


@Component({
    selector: 's-client_stats-pg',
    templateUrl: './client_stats.component.html',
    styleUrls: [ './client_stats.scss'],
})

export class ClientStatsComponent implements OnInit {
    chartView: any[] = [900, 500];
    view: any[] = [480, 180];
    clientsByOrgTypeData : any[] = [];
    clientsByCountryData: any[] = [];
    clientsByFidelityGroupsData: any[] = [];

    showXAxis = true;
    showYAxis = true;
    gradient = false;
    showLegend = true;
    /*showXAxisLabel = true;
    xAxisLabel = '';
    showYAxisLabel = true;
    yAxisLabel = 'Load';*/
    timeline = true;
    autoScale = true;

    testData: any[] = [];
    colorScheme = {
        domain: ['#007cbb', '#61c673', '#ff8e28', '#ef2e2e']
    };
    barColorScheme = {
        domain: ['#007cbb']
    }

    constructor(private router: Router, private clientService: ClientService) { }

    ngOnInit() {
        var me = this;
        this.getPageData()
    }

    onSelect(event) {
        console.log(event);
    }

    getPageData() {
        var me = this;

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

        me.clientService.getClientStats("orgType")
            .mergeMap(function(orgTypeData) {
                me.clientsByOrgTypeData = orgTypeData.items;
                console.log("Received clients by organization type");
                return me.clientService.getClientStats("fidelityGroup");
            }).mergeMap(function(fidelityGroupData) {
                me.clientsByFidelityGroupsData = fidelityGroupData.items;
                console.log("Received clients by fidelity group data");
                return me.clientService.getClientStats("country");
            }).subscribe(function(countryData){
            me.clientsByCountryData = countryData.items;
            console.log("Received clients by country");
        });
    }


}
