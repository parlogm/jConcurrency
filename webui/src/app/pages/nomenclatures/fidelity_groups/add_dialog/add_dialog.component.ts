import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormControl, Validators} from "@angular/forms";
import {FidelityGroupsService} from "../../../../services/api/fidelity-groups.service";
import {FidelityNomenclature} from "../../../../model/fidelitynomenclature.model";
import {NotificationsService} from "angular2-notifications";

@Component({
    selector: 's-add_dialog-pg',
    templateUrl: './add_dialog.component.html',
    styleUrls: ['./add_dialog.scss'],
})

export class AddDialogComponent {
    constructor(public dialogRef: MatDialogRef<AddDialogComponent>,
                @Inject(MAT_DIALOG_DATA) public data: FidelityNomenclature,
                public fidelityGroupsService: FidelityGroupsService,
                private notificationService: NotificationsService) { }

    formControl = new FormControl('', [
        Validators.required
        // Validators.email,
    ]);

    getErrorMessage() {
        return this.formControl.hasError('required') ? 'Required field' :
            this.formControl.hasError('email') ? 'Not a valid email' :
                '';
    }

    submit() {
        // emppty stuff
    }

    onNoClick(): void {
        this.dialogRef.close();
    }

    public confirmAdd(): void {
        //this.fidelityGroupsService.addFG(new FidelityNomenclature());
        this.fidelityGroupsService.addFG(this.data).subscribe(jsonResp => {
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
    }
}
