import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {MatDialog, MatPaginator, MatTableDataSource} from "@angular/material";
import {Router} from "@angular/router";
import {NotificationsService} from "angular2-notifications";
import {PriceGroupsService} from "../../../services/api/price-groups.service";
import {AddPGComponent} from "./add_pg/add_pg.component";
import {PriceNomenclature} from "../../../model/pricenomenclature.model";
import {EditPGComponent} from "./edit_pg/edit_pg.component";

@Component({
    selector: 's-price_groups-pg',
    templateUrl: './price_groups.component.html',
    styleUrls: ['./price_groups.scss'],
})

export class PriceGroupsComponent implements OnInit {

    @ViewChild('actionBtns') actionBtns : TemplateRef<any>;

    columns: any[];
    rows: any[];
    completed: boolean;

    constructor(public dialog: MatDialog,
                public priceGroupsService: PriceGroupsService,
                private router: Router,
                private notificationService: NotificationsService) {}

    ngOnInit() {
        var me = this;
        me.getPageData();

        this.columns = [
            {prop: "groupName", name: "Group name", width: 100},
            {prop: "id", name: "Actions", width: 200, cellTemplate: this.actionBtns}
        ];
    }

    getPageData() {
        this.priceGroupsService.getPriceGroups().subscribe((data) => {
            this.rows = data.items;
        });
    }

    addNew() {
        const dialogRef = this.dialog.open(AddPGComponent, {
            data: {groupName: '' }
        });

        dialogRef.afterClosed().subscribe(result => {
            this.refreshTable();
        });
    }

    private refreshTable() {
        this.getPageData();
    }

    edit(pg: PriceNomenclature) {
        const dialogRef = this.dialog.open(EditPGComponent, {
            data: pg
        });

        dialogRef.afterClosed().subscribe(result => {
            this.refreshTable();
        });
    }

    delete(id) {
        this.priceGroupsService.deletePriceGroup(id).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    this.priceGroupsService.getPriceGroups().subscribe((data) => {
                        this.rows = data.items;
                    });
                    this.notificationService.error('Price group deleted!', '', {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                } else {
                    this.notificationService.error('Price group could not be deleted!', jsonResp.operationMessage, {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                }
            },
            err => {
                this.notificationService.error('Price group could not be deleted!', err.toString(), {
                    timeOut: 3000,
                    showProgressBar: true,
                    pauseOnHover: true,
                    clickToClose: false,
                    clickIconToClose: true
                });
            });
    };

}
