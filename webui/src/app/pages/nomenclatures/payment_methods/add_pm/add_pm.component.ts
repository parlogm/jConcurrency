import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormControl, Validators} from "@angular/forms";
import {NotificationsService} from "angular2-notifications";
import {PaymentNomenclature} from "../../../../model/paymentnomenclature.model";
import {PaymentMethodsService} from "../../../../services/api/payment-methods.service";

@Component({
    selector: 's-add_pm-pg',
    templateUrl: './add_pm.component.html',
    styleUrls: ['./add_pm.scss'],
})

export class AddPMComponent {
    constructor(public dialogRef: MatDialogRef<AddPMComponent>,
                @Inject(MAT_DIALOG_DATA) public data: PaymentNomenclature,
                public paymentMethodsService: PaymentMethodsService,
                private notificationService: NotificationsService) { }

    formControl = new FormControl('', [
        Validators.required
    ]);

    getErrorMessage() {
        return this.formControl.hasError('required') ? 'Required field' :
            '';
    }

    submit() {
        // emppty stuff
    }

    onNoClick(): void {
        this.dialogRef.close();
    }

    public confirmAdd(): void {
        this.paymentMethodsService.addPaymentMethod(this.data).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    this.notificationService.success('Payment method created!', '', {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                } else {
                    this.notificationService.error('Payment method could not be added!', jsonResp.operationMessage, {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                }
            },
            err => {
                this.notificationService.error('Payment method could not be added!', err.toString(), {
                    timeOut: 3000,
                    showProgressBar: true,
                    pauseOnHover: true,
                    clickToClose: false,
                    clickIconToClose: true
                });
            });
    }
}
