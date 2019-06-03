import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {MatDialog, MatPaginator, MatTableDataSource} from "@angular/material";
import {Router} from "@angular/router";
import {NotificationsService} from "angular2-notifications";
import {OrgTypesService} from "../../../services/api/org-types.service";
import {AddOTComponent} from "./add_ot/add_ot.component";
import {OrgNomenclature} from "../../../model/orgnomenclature.model";
import {EditOTComponent} from "./edit_ot/edit_ot.component";

@Component({
    selector: 's-org_types-pg',
    templateUrl: './org_types.component.html',
    styleUrls: ['./org_types.scss'],
})

export class OrgTypesComponent implements OnInit {

    @ViewChild('actionBtns') actionBtns : TemplateRef<any>;

    columns: any[];
    rows: any[];
    completed: boolean;

    constructor(public dialog: MatDialog,
                public orgTypesService: OrgTypesService,
                private router: Router,
                private notificationService: NotificationsService) {}

    ngOnInit() {
        var me = this;
        me.getPageData();

        this.columns = [
            {prop: "type", name: "Org type", width: 100},
            {prop: "id", name: "Actions", width: 200, cellTemplate: this.actionBtns}
        ];
    }

    getPageData() {
        this.orgTypesService.getOrgTypes().subscribe((data) => {
            this.rows = data.items;
        });
    }

    addNew() {
        const dialogRef = this.dialog.open(AddOTComponent, {
            data: {type: '' }
        });

        dialogRef.afterClosed().subscribe(result => {
            this.refreshTable();
        });
    }

    private refreshTable() {
        this.getPageData();
    }

    edit(ot: OrgNomenclature) {
        const dialogRef = this.dialog.open(EditOTComponent, {
            data: ot
        });

        dialogRef.afterClosed().subscribe(result => {
            this.refreshTable();
        });
    }

    delete(id) {
        this.orgTypesService.deleteOrgType(id).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    this.orgTypesService.getOrgTypes().subscribe((data) => {
                        this.rows = data.items;
                    });
                    this.notificationService.error('Org type deleted!', '', {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                } else {
                    this.notificationService.error('Org type could not be deleted!', jsonResp.operationMessage, {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                }
            },
            err => {
                this.notificationService.error('Org type could not be deleted!', err.toString(), {
                    timeOut: 3000,
                    showProgressBar: true,
                    pauseOnHover: true,
                    clickToClose: false,
                    clickIconToClose: true
                });
            });
    };

}
