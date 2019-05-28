import {Component, Inject, OnInit} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormControl, Validators} from "@angular/forms";
import {FidelityGroupsService} from "../../../../services/api/fidelity-groups.service";
import {FidelityNomenclature} from "../../../../model/fidelitynomenclature.model";
import {NotificationsService} from "angular2-notifications";

@Component({
    selector: 's-edit_dialog-pg',
    templateUrl: './edit_dialog.component.html',
    styleUrls: ['./edit_dialog.scss'],
})

export class EditDialogComponent {
    constructor(public dialogRef: MatDialogRef<EditDialogComponent>,
                @Inject(MAT_DIALOG_DATA) public data: FidelityNomenclature,
                public fidelityGroupsService: FidelityGroupsService,
                private notificationService: NotificationsService) { }

    formControl = new FormControl('', [
        Validators.required
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

    public confirmUpdate(): void {
        //this.fidelityGroupsService.addFG(new FidelityNomenclature());
        this.fidelityGroupsService.updateFG(this.data);
    }

}
