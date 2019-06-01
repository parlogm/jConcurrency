import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {MatDialog, MatPaginator, MatTableDataSource} from "@angular/material";
import {Router} from "@angular/router";
import {NotificationsService} from "angular2-notifications";
import {AssignedCenterService} from "../../../services/api/assigned-center.service";
import {AddACComponent} from "./add_ac/add_ac.component";
import {CenterNomenclature} from "../../../model/centernomenclature.model";
import {EditACComponent} from "./edit_ac/edit_ac.component";

@Component({
    selector: 's-assigned_centers-pg',
    templateUrl: './assigned_centers.component.html',
    styleUrls: ['./assigned_centers.scss'],
})

export class AssignedCentersComponent implements OnInit {

    @ViewChild('actionBtns') actionBtns : TemplateRef<any>;

    columns: any[];
    rows: any[];
    completed: boolean;

    constructor(public dialog: MatDialog,
                public assignedCenterService: AssignedCenterService,
                private router: Router,
                private notificationService: NotificationsService) {}

    ngOnInit() {
        var me = this;
        me.getPageData();

        this.columns = [
            {prop: "name", name: "Name", width: 100},
            {prop: "id", name: "Actions", width: 200, cellTemplate: this.actionBtns}
        ];
    }

    getPageData() {
        this.assignedCenterService.getCenters().subscribe((data) => {
            this.rows = data.items;
        });
    }

    addNew() {
        const dialogRef = this.dialog.open(AddACComponent, {
            data: {name: '' }
        });

        dialogRef.afterClosed().subscribe(result => {
            this.refreshTable();
        });
    }

    private refreshTable() {
        this.getPageData();
    }

    edit(ac: CenterNomenclature) {
        const dialogRef = this.dialog.open(EditACComponent, {
            data: ac
        });

        dialogRef.afterClosed().subscribe(result => {
            this.refreshTable();
        });
    }

    delete(id) {
        this.assignedCenterService.deleteCenter(id).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    this.assignedCenterService.getCenters().subscribe((data) => {
                        this.rows = data.items;
                    });
                    this.notificationService.error('Center deleted!', '', {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                } else {
                    this.notificationService.error('Center could not be deleted!', jsonResp.operationMessage, {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                }
            },
            err => {
                this.notificationService.error('Center could not be deleted!', err.toString(), {
                    timeOut: 3000,
                    showProgressBar: true,
                    pauseOnHover: true,
                    clickToClose: false,
                    clickIconToClose: true
                });
            });
    };

}
