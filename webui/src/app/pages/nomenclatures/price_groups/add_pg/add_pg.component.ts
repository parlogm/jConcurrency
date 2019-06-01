import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormControl, Validators} from "@angular/forms";
import {NotificationsService} from "angular2-notifications";
import {PriceNomenclature} from "../../../../model/pricenomenclature.model";
import {PriceGroupsService} from "../../../../services/api/price-groups.service";

@Component({
    selector: 's-add_pg-pg',
    templateUrl: './add_pg.component.html',
    styleUrls: ['./add_pg.scss'],
})

export class AddPGComponent {
    constructor(public dialogRef: MatDialogRef<AddPGComponent>,
                @Inject(MAT_DIALOG_DATA) public data: PriceNomenclature,
                public priceGroupsService: PriceGroupsService,
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
        this.priceGroupsService.addPriceGroup(this.data).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    this.notificationService.success('Price group created!', '', {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                } else {
                    this.notificationService.error('Price group could not be added!', jsonResp.operationMessage, {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                }
            },
            err => {
                this.notificationService.error('Price group could not be added!', err.toString(), {
                    timeOut: 3000,
                    showProgressBar: true,
                    pauseOnHover: true,
                    clickToClose: false,
                    clickIconToClose: true
                });
            });
    }
}
