import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {MatDialog, MatPaginator, MatTableDataSource} from "@angular/material";
import {AddDialogComponent} from "./add_dialog/add_dialog.component";
import {FidelityGroupsService} from "../../../services/api/fidelity-groups.service";
import {Router} from "@angular/router";
import {FidelityNomenclature} from "../../../model/fidelitynomenclature.model";
import {EditDialogComponent} from "./edit_dialog/edit_dialog.component";
import {NotificationsService} from "angular2-notifications";

@Component({
    selector: 's-fidelity_groups-pg',
    templateUrl: './fidelity_groups.component.html',
    styleUrls: ['./fidelity_groups.scss'],
})

export class FidelityGroupsComponent implements OnInit {

    @ViewChild('actionBtns') actionBtns : TemplateRef<any>;

    //ngx-Datatable Variables
    columns: any[];
    rows: any[];
    completed: boolean;

    constructor(public dialog: MatDialog,
                public fidelityGroupsService: FidelityGroupsService,
                private notificationService: NotificationsService,
                private router: Router) {}

    ngOnInit() {
        //this.dataSource.paginator = this.paginator;
        var me = this;
        me.getPageData();

        this.columns = [
            {prop: "groupName", name: "Name", width: 100},
            {prop: "id", name: "Actions", width: 200, cellTemplate: this.actionBtns}
        ];
    }

    getPageData() {
        this.fidelityGroupsService.getFG().subscribe((data) => {
            this.rows = data.items;
        });
    }

    addNew() {
        const dialogRef = this.dialog.open(AddDialogComponent, {
            data: {groupName: '' }
        });

        dialogRef.afterClosed().subscribe(result => {
            //if (result === 1) {
                // After dialog is closed we're doing frontend updates
                // For add we're just pushing a new row inside DataService
                //this.exampleDatabase.dataChange.value.push(this.dataService.getDialogData());
                this.refreshTable();
            //}
        });
    }

    private refreshTable() {
        //this.paginator._changePageSize(this.paginator.pageSize);
        this.getPageData();
    }

    edit(fn: FidelityNomenclature) {
        //this.clientService.updateClient(row);
        const dialogRef = this.dialog.open(EditDialogComponent, {
            data: fn
        });

        dialogRef.afterClosed().subscribe(result => {
            this.refreshTable();
        });
    }

    delete(id) {
        this.fidelityGroupsService.deleteFG(id).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    this.fidelityGroupsService.getFG().subscribe((data) => {
                        this.rows = data.items;
                    });
                    this.notificationService.error('Fidelity group deleted!', '', {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                } else {
                    this.notificationService.error('Fidelity group could not be deleted!', jsonResp.operationMessage, {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                }
            },
            err => {
                this.notificationService.error('Fidelity group could not be deleted!', err.toString(), {
                    timeOut: 3000,
                    showProgressBar: true,
                    pauseOnHover: true,
                    clickToClose: false,
                    clickIconToClose: true
                });
            });
    }

}
