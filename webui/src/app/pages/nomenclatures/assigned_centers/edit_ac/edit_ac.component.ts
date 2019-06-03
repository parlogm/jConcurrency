import {Component, Inject, OnInit} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormControl, Validators} from "@angular/forms";
import {NotificationsService} from "angular2-notifications";
import {CenterNomenclature} from "../../../../model/centernomenclature.model";
import {AssignedCenterService} from "../../../../services/api/assigned-center.service";

@Component({
    selector: 's-edit_ac-pg',
    templateUrl: './edit_ac.component.html',
    styleUrls: ['./edit_ac.scss'],
})

export class EditACComponent {
    constructor(public dialogRef: MatDialogRef<EditACComponent>,
                @Inject(MAT_DIALOG_DATA) public data: CenterNomenclature,
                public assignedCenterService: AssignedCenterService,
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
        this.assignedCenterService.updateCenter(this.data).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    this.notificationService.success('Center updated!', '', {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                } else {
                    this.notificationService.error('Center could not be updated!', jsonResp.operationMessage, {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                }
            },
            err => {
                this.notificationService.error('Center could not be updated!', err.toString(), {
                    timeOut: 3000,
                    showProgressBar: true,
                    pauseOnHover: true,
                    clickToClose: false,
                    clickIconToClose: true
                });
            });
    }

}
