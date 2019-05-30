import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {MatDialog, MatPaginator, MatTableDataSource} from "@angular/material";
import {Router} from "@angular/router";
import {CountryService} from "../../../services/api/country.service";
import {AddCountryComponent} from "./add_country/add_country.component";
import {CountryNomenclature} from "../../../model/countrynomenclature.mode";
import {EditCountryComponent} from "./edit_country/edit_country.component";
import {NotificationsService} from "angular2-notifications";

@Component({
    selector: 's-countries-pg',
    templateUrl: './countries.component.html',
    styleUrls: ['./countries.scss'],
})

export class CountriesComponent implements OnInit {

    @ViewChild('actionBtns') actionBtns : TemplateRef<any>;

    columns: any[];
    rows: any[];
    completed: boolean;

    constructor(public dialog: MatDialog,
                public countryService: CountryService,
                private router: Router,
                private notificationService: NotificationsService) {}

    ngOnInit() {
        var me = this;
        me.getPageData();

        this.columns = [
            {prop: "country", name: "Country", width: 100},
            {prop: "code", name: "Code", width: 100},
            {prop: "id", name: "Actions", width: 200, cellTemplate: this.actionBtns}
        ];
    }

    getPageData() {
        this.countryService.getCountries().subscribe((data) => {
            this.rows = data.items;
        });
    }

    addNew() {
        const dialogRef = this.dialog.open(AddCountryComponent, {
            data: {groupName: '' }
        });

        dialogRef.afterClosed().subscribe(result => {
            this.refreshTable();
        });
    }

    private refreshTable() {
        this.getPageData();
    }

    edit(country: CountryNomenclature) {
        const dialogRef = this.dialog.open(EditCountryComponent, {
            data: country
        });

        dialogRef.afterClosed().subscribe(result => {
            this.refreshTable();
        });
    }

    delete(id) {
        this.countryService.deleteCountry(id).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    this.countryService.getCountries().subscribe((data) => {
                        this.rows = data.items;
                    });
                    this.notificationService.error('Country deleted!', '', {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                } else {
                    this.notificationService.error('Country could not be deleted!', jsonResp.operationMessage, {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                }
            },
            err => {
                this.notificationService.error('Country could not be deleted!', err.toString(), {
                    timeOut: 3000,
                    showProgressBar: true,
                    pauseOnHover: true,
                    clickToClose: false,
                    clickIconToClose: true
                });
            });
    };

}
