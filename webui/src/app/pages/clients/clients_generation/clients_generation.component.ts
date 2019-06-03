import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {ClientService} from "../../../services/api/client.service";
import {MatDialog} from "@angular/material";

@Component({
    selector: 's-clients_generation-pg',
    templateUrl: './clients_generation.component.html',
    styleUrls: ['./clients_generation.scss'],
})

export class ClientsGenerationComponent implements OnInit {

    panelOpenState = false;
    iterativeRecordsGenerated: any;
    iterativeElapsedTime: any;
    iterativeNumberOfRecords: number;
    iterativeSaveToDatabase: boolean;
    multiThreadedRecordsGenerated: any;
    multiThreadedElapsedTime: any;
    multiThreadedNumberOfRecords: number;
    multiThreadedSaveToDatabase: boolean;



    constructor(public dialog: MatDialog, private router: Router, private clientService: ClientService) {
    }

    ngOnInit() {
        var me = this;

    }

    generateIterative() {
        this.clientService. iterativeGeneration(this.iterativeNumberOfRecords, this.iterativeSaveToDatabase)
            .subscribe((data) => {
                //this.rows = data.items;
                this.iterativeRecordsGenerated = data.numbersGenerated;
                this.iterativeElapsedTime = data.elapsedTime;
            });
    }

    generateMultiThreaded() {

        this.clientService. multiThreadedGeneration(this.multiThreadedNumberOfRecords, this.multiThreadedSaveToDatabase)
            .subscribe((data) => {
                //this.rows = data.items;
                this.multiThreadedRecordsGenerated = data.numbersGenerated;
                this.multiThreadedElapsedTime = data.elapsedTime;
            });

    }

}
