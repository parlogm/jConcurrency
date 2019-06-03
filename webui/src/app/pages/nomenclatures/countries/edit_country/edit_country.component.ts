import {Component, Inject, OnInit} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormControl, Validators} from "@angular/forms";
import {NotificationsService} from "angular2-notifications";
import {CountryNomenclature} from "../../../../model/countrynomenclature.mode";
import {CountryService} from "../../../../services/api/country.service";

@Component({
    selector: 's-edit_country-pg',
    templateUrl: './edit_country.component.html',
    styleUrls: ['./edit_country.scss'],
})

export class EditCountryComponent {
    constructor(public dialogRef: MatDialogRef<EditCountryComponent>,
                @Inject(MAT_DIALOG_DATA) public data: CountryNomenclature,
                public countryService: CountryService,
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

    public confirmUpdate(): void {
        //this.countryService.updateCountry(this.data);
        this.countryService.updateCountry(this.data).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    this.notificationService.success('Country updated!', '', {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                } else {
                    this.notificationService.error('Country could not be updated!', jsonResp.operationMessage, {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                }
            },
            err => {
                this.notificationService.error('Country could not be updated!', err.toString(), {
                    timeOut: 3000,
                    showProgressBar: true,
                    pauseOnHover: true,
                    clickToClose: false,
                    clickIconToClose: true
                });
            });
    }

}
