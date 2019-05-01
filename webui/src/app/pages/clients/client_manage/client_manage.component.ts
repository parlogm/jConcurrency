import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {ClientService} from "../../../services/api/client.service";
import {AddDialogComponent} from "../../nomenclatures/fidelity_groups/add_dialog/add_dialog.component";
import {MatDialog} from "@angular/material";
import {FidelityNomenclature} from "../../../model/fidelitynomenclature.model";

@Component({
    selector: 's-client_manage-pg',
    templateUrl: './client_manage.component.html',
    styleUrls: ['./client_manage.scss'],
})

export class ClientManageComponent implements OnInit {

    @ViewChild('clientTpl') clientTpl: TemplateRef<any>;
    @ViewChild('actionBtns') actionBtns : TemplateRef<any>;

    //ngx-Datatable Variables
    columns: any[];
    rows: any[];
    completed: boolean;

    constructor(public dialog: MatDialog, private router: Router, private clientService: ClientService) {
    }

    ngOnInit() {
        var me = this;
        me.getPageData();

        this.columns = [
            {prop: "name", name: "Name", width: 100},
            {prop: "address", name: "Address", width: 200},
            {prop: "country", name: "Country", width: 150},
            {prop: "isCorporate", name: "Corporate", width: 90, cellTemplate: this.clientTpl},
            {prop: "id", name: "Actions", width: 200, cellTemplate: this.actionBtns}
        ];
    }

    addNew(fn: FidelityNomenclature) {
        const dialogRef = this.dialog.open(AddDialogComponent, {
            data: {groupName: fn }
        });
        console.log('test');
        dialogRef.afterClosed().subscribe(result => {
            if (result === 1) {
                // After dialog is closed we're doing frontend updates
                // For add we're just pushing a new row inside DataService
                //this.exampleDatabase.dataChange.value.push(this.dataService.getDialogData());

            }
        });
    }

    getPageData() {
        var me = this;
        this.clientService.getClients().subscribe((data) => {
            this.rows = data.items;
        });
    }

    updateClient(row) {
        this.clientService.updateClient(row);
    }

    delete(id) {
        this.clientService.deleteClient(id).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    this.clientService.getClients().subscribe((data) => {
                        this.rows = data.items;
                    });
                }
            },
            err => {
                console.error("aaaaaaaaaaaaaaaaaaaaaa ba");
            });
    }

}
