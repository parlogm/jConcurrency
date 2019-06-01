import {Component, Inject, OnInit} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormControl, Validators} from "@angular/forms";
import {NotificationsService} from "angular2-notifications";
import {OrgNomenclature} from "../../../../model/orgnomenclature.model";
import {OrgTypesService} from "../../../../services/api/org-types.service";

@Component({
    selector: 's-edit_ot-pg',
    templateUrl: './edit_ot.component.html',
    styleUrls: ['./edit_ot.scss'],
})

export class EditOTComponent {
    constructor(public dialogRef: MatDialogRef<EditOTComponent>,
                @Inject(MAT_DIALOG_DATA) public data: OrgNomenclature,
                public orgTypesService: OrgTypesService,
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
        this.orgTypesService.updateOrgType(this.data).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    this.notificationService.success('Org type updated!', '', {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                } else {
                    this.notificationService.error('Org type could not be updated!', jsonResp.operationMessage, {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                }
            },
            err => {
                this.notificationService.error('Org type could not be updated!', err.toString(), {
                    timeOut: 3000,
                    showProgressBar: true,
                    pauseOnHover: true,
                    clickToClose: false,
                    clickIconToClose: true
                });
            });
    }

}
