import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {ClientService} from "../../../services/api/client.service";
import {MatDialog} from "@angular/material";
import {NotificationsService} from "angular2-notifications";

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

    constructor(public dialog: MatDialog, private router: Router, private clientService: ClientService,
                private notificationService: NotificationsService) {
    }

    ngOnInit() {
        var me = this;
        me.getPageData();

        this.columns = [
            {prop: "name", name: "Name", width: 100},
            {prop: "address", name: "Address", width: 200},
            {prop: "countryNomenclature.country", name: "Country", width: 150},
            {prop: "isCorporate", name: "Corporate", width: 90, cellTemplate: this.clientTpl},
            {prop: "id", name: "Actions", width: 200, cellTemplate: this.actionBtns}
        ];
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
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS") {
                    this.clientService.getClients().subscribe((data) => {
                        this.rows = data.items;
                    });
                    this.notificationService.error('Client deleted!', '', {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                } else {
                    this.notificationService.error('Client could not be deleted!', jsonResp.operationMessage, {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                }
            },
            err => {
                this.notificationService.error('Client could not be deleted!', err.toString(), {
                    timeOut: 3000,
                    showProgressBar: true,
                    pauseOnHover: true,
                    clickToClose: false,
                    clickIconToClose: true
                });
            });

    }

}
