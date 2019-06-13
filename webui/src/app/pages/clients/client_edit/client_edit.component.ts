import {Component, Inject, OnInit} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormControl, Validators} from "@angular/forms";
import {NotificationsService} from "angular2-notifications";
import {Client} from "../../../model/client.model";
import {ClientService} from "../../../services/api/client.service";
import {FidelityNomenclature} from "../../../model/fidelitynomenclature.model";
import {PriceNomenclature} from "../../../model/pricenomenclature.model";
import {CenterNomenclature} from "../../../model/centernomenclature.model";
import {PaymentNomenclature} from "../../../model/paymentnomenclature.model";
import {OrgNomenclature} from "../../../model/orgnomenclature.model";
import {CountryNomenclature} from "../../../model/countrynomenclature.mode";
import {FidelityGroupsService} from "../../../services/api/fidelity-groups.service";
import {PriceGroupsService} from "../../../services/api/price-groups.service";
import {AssignedCenterService} from "../../../services/api/assigned-center.service";
import {PaymentMethodsService} from "../../../services/api/payment-methods.service";
import {OrgTypesService} from "../../../services/api/org-types.service";
import {CountryService} from "../../../services/api/country.service";

@Component({
    selector: 's-client_edit-pg',
    templateUrl: './client_edit.component.html',
    styleUrls: ['./client_edit.scss'],
})

export class ClientEditComponent implements OnInit{
    constructor(public dialogRef: MatDialogRef<ClientEditComponent>,
                @Inject(MAT_DIALOG_DATA) public data: Client,
                public clientService: ClientService,
                private fidelityGroupsService: FidelityGroupsService,
                private priceGroupsService: PriceGroupsService, private assignedCenterService: AssignedCenterService,
                private paymentMethodService: PaymentMethodsService, private orgTypeService: OrgTypesService,
                private notificationService: NotificationsService, private countryService: CountryService) { }

    formControl = new FormControl('', [
        Validators.required
    ]);

    fidelityGroupList: any[];
    priceGroupList: any[];
    assignedCenterList: any[];
    paymentMethodList: any[];
    orgTypeList: any[];
    countryList: any[];

    ngOnInit() {
        var me = this;

        this.fidelityGroupsService.getFG().subscribe((data) => {
            this.fidelityGroupList = data.items;
        });

        this.priceGroupsService.getPriceGroups().subscribe((data) => {
            this.priceGroupList = data.items;
        });

        this.assignedCenterService.getCenters().subscribe((data) => {
            this.assignedCenterList = data.items;
        });

        this.paymentMethodService.getPaymentMethods().subscribe((data) => {
            this.paymentMethodList = data.items;
        });

        this.orgTypeService.getOrgTypes().subscribe((data) => {
            this.orgTypeList = data.items;
        });

        this.countryService.getCountries().subscribe((data) => {
            this.countryList = data.items;
        })
    }

    compareFn: ((f1: any, f2: any) => boolean) | null = this.compareByValue;

    compareByValue(f1: any, f2: any) {
        return f1 && f2 && f1.id === f2.id;
    }

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
        this.clientService.updateClient(this.data).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    this.notificationService.success('Client updated!', '', {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                } else {
                    this.notificationService.error('Client could not be updated!', jsonResp.operationMessage, {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                }
            },
            err => {
                this.notificationService.error('Client could not be updated!', err.toString(), {
                    timeOut: 3000,
                    showProgressBar: true,
                    pauseOnHover: true,
                    clickToClose: false,
                    clickIconToClose: true
                });
            });
    }

}
