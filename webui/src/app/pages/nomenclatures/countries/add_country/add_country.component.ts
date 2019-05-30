import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormControl, Validators} from "@angular/forms";
import {FidelityGroupsService} from "../../../../services/api/fidelity-groups.service";
import {FidelityNomenclature} from "../../../../model/fidelitynomenclature.model";
import {NotificationsService} from "angular2-notifications";
import {CountryNomenclature} from "../../../../model/countrynomenclature.mode";
import {CountryService} from "../../../../services/api/country.service";

@Component({
    selector: 's-add_country-pg',
    templateUrl: './add_country.component.html',
    styleUrls: ['./add_country.scss'],
})

export class AddCountryComponent {
    constructor(public dialogRef: MatDialogRef<AddCountryComponent>,
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

    public confirmAdd(): void {
        this.countryService.addCountry(this.data).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    this.notificationService.success('Country created!', '', {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                } else {
                    this.notificationService.error('Country could not be added!', jsonResp.operationMessage, {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                }
            },
            err => {
                this.notificationService.error('Country could not be added!', err.toString(), {
                    timeOut: 3000,
                    showProgressBar: true,
                    pauseOnHover: true,
                    clickToClose: false,
                    clickIconToClose: true
                });
            });
    }
}
