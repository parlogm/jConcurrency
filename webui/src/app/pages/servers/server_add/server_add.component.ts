import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {Server} from "../../../model/server.model";
import {ServerService} from "../../../services/api/server.service";
import {NotificationsService} from "angular2-notifications";


@Component({
    selector: 's-server_add-pg',
    templateUrl: './server_add.component.html',
    styleUrls: ['./server_add.scss'],
})
export class ServerAddComponent {

    server: Server = new Server();

    constructor(private router: Router, private serverService: ServerService, private notificationService: NotificationsService) {

    }

    addServer(): void {
        this.serverService.addServer(this.server).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    this.notificationService.success('Server created!', '', {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                } else {
                    this.notificationService.error('Server could not be added!', jsonResp.operationMessage, {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                }
            },
            err => {
                this.notificationService.error('Server could not be added!', err.toString(), {
                    timeOut: 3000,
                    showProgressBar: true,
                    pauseOnHover: true,
                    clickToClose: false,
                    clickIconToClose: true
                });
            });
    };

}
