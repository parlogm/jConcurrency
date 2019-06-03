import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {MatDialog, MatPaginator, MatTableDataSource} from "@angular/material";
import {Router} from "@angular/router";
import {NotificationsService} from "angular2-notifications";
import {PaymentMethodsService} from "../../../services/api/payment-methods.service";
import {AddPMComponent} from "./add_pm/add_pm.component";
import {PaymentNomenclature} from "../../../model/paymentnomenclature.model";
import {EditPMComponent} from "./edit_pm/edit_pm.component";

@Component({
    selector: 's-payment_methods-pg',
    templateUrl: './payment_methods.component.html',
    styleUrls: ['./payment_methods.scss'],
})

export class PaymentMethodsComponent implements OnInit {

    @ViewChild('actionBtns') actionBtns : TemplateRef<any>;

    columns: any[];
    rows: any[];
    completed: boolean;

    constructor(public dialog: MatDialog,
                public paymentMethodsService: PaymentMethodsService,
                private router: Router,
                private notificationService: NotificationsService) {}

    ngOnInit() {
        var me = this;
        me.getPageData();

        this.columns = [
            {prop: "methodName", name: "Method", width: 100},
            {prop: "id", name: "Actions", width: 200, cellTemplate: this.actionBtns}
        ];
    }

    getPageData() {
        this.paymentMethodsService.getPaymentMethods().subscribe((data) => {
            this.rows = data.items;
        });
    }

    addNew() {
        const dialogRef = this.dialog.open(AddPMComponent, {
            data: {methodName: '' }
        });

        dialogRef.afterClosed().subscribe(result => {
            this.refreshTable();
        });
    }

    private refreshTable() {
        this.getPageData();
    }

    edit(pm: PaymentNomenclature) {
        const dialogRef = this.dialog.open(EditPMComponent, {
            data: pm
        });

        dialogRef.afterClosed().subscribe(result => {
            this.refreshTable();
        });
    }

    delete(id) {
        this.paymentMethodsService.deletePaymentMethod(id).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    this.paymentMethodsService.getPaymentMethods().subscribe((data) => {
                        this.rows = data.items;
                    });
                    this.notificationService.error('Payment method deleted!', '', {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                } else {
                    this.notificationService.error('Payment method could not be deleted!', jsonResp.operationMessage, {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                }
            },
            err => {
                this.notificationService.error('Payment method could not be deleted!', err.toString(), {
                    timeOut: 3000,
                    showProgressBar: true,
                    pauseOnHover: true,
                    clickToClose: false,
                    clickIconToClose: true
                });
            });
    };

}
