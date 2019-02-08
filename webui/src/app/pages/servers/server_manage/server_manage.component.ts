import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {ServerService} from '../../../services/api/server.service';
import {Router} from '@angular/router';

@Component({
    selector: 's-server_manage-pg',
    templateUrl: './server_manage.component.html',
    styleUrls: ['./server_manage.scss'],
})

export class ServerManageComponent implements OnInit {

    @ViewChild('serverTpl') serverTpl: TemplateRef<any>;
    @ViewChild('actionBtns') actionBtns : TemplateRef<any>;

    //ngx-Datatable Variables
    columns: any[];
    rows: any[];
    completed: boolean;

    constructor(private router: Router, private serverService: ServerService) {
    }

    ngOnInit() {
        var me = this;
        me.getPageData();

        this.columns = [
            {prop: "name", name: "Name", width: 100},
            {prop: "address", name: "Address", width: 200},
            {prop: "environment", name: "Environment", width: 150},
            {prop: "availableFlag", name: "Available", width: 90, cellTemplate: this.serverTpl},
            {prop: "id", name: "Actions", width: 200, cellTemplate: this.actionBtns}
        ];
    }

    getPageData() {
        var me = this;
        this.serverService.getServers().subscribe((data) => {
            this.rows = data.items;
        });
    }

    updateUser(row) {
        this.serverService.updateServer(row);
    }

    delete(id) {
        this.serverService.deleteServer(id).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    this.serverService.getServers().subscribe((data) => {
                        this.rows = data.items;
                    });
                }
            },
            err => {
                console.error("aaaaaaaaaaaaaaaaaaaaaa ba");
            });
    }

}
