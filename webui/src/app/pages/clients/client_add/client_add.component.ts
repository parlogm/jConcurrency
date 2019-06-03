import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import 'rxjs/add/operator/mergeMap';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {DatePipe} from "@angular/common";
import {UserInfoService} from "../../../services/user-info.service";
import {ClientService} from "../../../services/api/client.service";
import {FidelityGroupsService} from "../../../services/api/fidelity-groups.service";
import {FidelityNomenclature} from "../../../model/fidelitynomenclature.model";
import {NotificationsService} from "angular2-notifications";
import {CountryNomenclature} from "../../../model/countrynomenclature.mode";
import {Client} from "../../../model/client.model";
import {CountryService} from "../../../services/api/country.service";
import {PriceNomenclature} from "../../../model/pricenomenclature.model";
import {PriceGroupsService} from "../../../services/api/price-groups.service";
import {AssignedCenterService} from "../../../services/api/assigned-center.service";
import {CenterNomenclature} from "../../../model/centernomenclature.model";
import {PaymentNomenclature} from "../../../model/paymentnomenclature.model";
import {PaymentMethodsService} from "../../../services/api/payment-methods.service";
import {OrgTypesService} from "../../../services/api/org-types.service";
import {OrgNomenclature} from "../../../model/orgnomenclature.model";

@Component({
    selector: 's-client_add-pg',
    templateUrl: './client_add.component.html',
    styleUrls: [ './client_add.scss'],
    providers: [DatePipe]
})

export class ClientAddComponent implements OnInit {

    panelOpenState = false;
    public clientForm: FormGroup;
    private clientId: number;
    private client: Client;

    maxDate = new Date();
    backDate = new Date();

    fidelityGroupList: any[];
    priceGroupList: any[];
    assignedCenterList: any[];
    paymentMethodList: any[];
    orgTypeList: any[];
    countryList: any[];

    emailFormControl = new FormControl('', [
        Validators.required,
        Validators.email,
    ]);

    constructor(private router: Router, private clientService: ClientService, private _fb: FormBuilder, private datePipe: DatePipe,
                private userInfoService:UserInfoService, private fidelityGroupsService: FidelityGroupsService,
                private priceGroupsService: PriceGroupsService, private assignedCenterService: AssignedCenterService,
                private paymentMethodService: PaymentMethodsService, private orgTypeService: OrgTypesService,
                private notificationService: NotificationsService, private countryService: CountryService) {
        var me = this;
        me.clientForm = this._fb.group({
            isCorporate : false,
            name: '',
            createdAt: new Date(),
            updatedAt: new Date(),
            priceGroup: new PriceNomenclature(),
            fidelityNomenclature: new FidelityNomenclature(),
            salesAgentId: '',
            lastBillingDate: new Date(),
            billingRateIndex: '',
            emailConfirmation: false,
            daysFromLastBill: '',
            email:'',
            assignedCenter: new CenterNomenclature(),
            countryNomenclature: new CountryNomenclature(),
            address: '',
            contactPhone:'',
            contact:'',
            paymentMethod: new PaymentNomenclature(),
            paymentDueIn:'',
            paymentNotification:'',
            contractNr:'',
            contractExpirationDate: new Date(),
            regCode:'',
            isVatApplicable: false,
            balance:'',
            debit:'',
            credit:'',
            creditApprovedLimit:'',
            creditLimit:'',
            legalNoticeReceivedOn: new Date(),
            legalNoticeSentOn: new Date(),
            legalNoticeOutcome:'',
            cipReceivedOn: new Date(),
            cipSentOn: new Date(),
            cipOutcome:'',
            finNoticeReceivedOn: new Date(),
            finNoticeSentOn: new Date(),
            finNoticeOutcome:'',
            orgType: new OrgNomenclature(),
            salesAmount: '',
            comment:'',
        });
    }

    getErrorMessage() {
        return this.clientForm.hasError('required') ? 'Required field' :
            '';
    }

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

    mapFormControlToObject() {
        this.client = new Client();
        this.client.isCorporate = this.clientForm.value.isCorporate;
        this.client.name = this.clientForm.value.name;
        this.client.createdAt = this.clientForm.value.createdAt;
        this.client.updatedAt = this.clientForm.value.updatedAt;
        this.client.priceGroup = this.clientForm.value.priceGroup;
        this.client.fidelityGroup = this.clientForm.value.fidelityNomenclature;
        this.client.salesAgentId = this.clientForm.value.salesAgentId;
        this.client.lastBillingDate = this.clientForm.value.lastBillingDate;
        this.client.billingRateIndex = this.clientForm.value.billingRateIndex;
        this.client.emailConfirmation = this.clientForm.value.emailConfirmation;
        this.client.daysFromLastBill = this.clientForm.value.daysFromLastBill;
        this.client.email = this.clientForm.value.email;
        this.client.assignedCenter = this.clientForm.value.assignedCenter;
        this.client.countryNomenclature = this.clientForm.value.countryNomenclature;
        this.client.address = this.clientForm.value.address;
        this.client.contactPhone = this.clientForm.value.contactPhone;
        this.client.contact = this.clientForm.value.contact;
        this.client.paymentMethod = this.clientForm.value.paymentMethod;
        this.client.paymentDueIn = this.clientForm.value.paymentDueIn;
        this.client.paymentNotification = this.clientForm.value.paymentNotification;
        this.client.contractNr = this.clientForm.value.contractNr;
        this.client.contractExpirationDate = this.clientForm.value.contractExpirationDate;
        this.client.regCode = this.clientForm.value.regCode;
        this.client.isVatApplicable = this.clientForm.value.isVatApplicable;
        this.client.balance = this.clientForm.value.balance;
        this.client.debit = this.clientForm.value.debit;
        this.client.credit = this.clientForm.value.credit;
        this.client.creditApprovedLimit = this.clientForm.value.creditApprovedLimit;
        this.client.creditLimit = this.clientForm.value.creditLimit;
        this.client.legalNoticeReceivedOn = this.clientForm.value.legalNoticeReceivedOn;
        this.client.legalNoticeSentOn = this.clientForm.value.legalNoticeSentOn;
        this.client.legalNoticeOutcome = this.clientForm.value.legalNoticeOutcome;
        this.client.cipReceivedOn = this.clientForm.value.cipReceivedOn;
        this.client.cipSentOn = this.clientForm.value.cipSentOn;
        this.client.cipOutcome = this.clientForm.value.cipOutcome;
        this.client.finNoticeReceivedOn = this.clientForm.value.finNoticeReceivedOn;
        this.client.finNoticeSentOn = this.clientForm.value.finNoticeSentOn;
        this.client.finNoticeOutcome = this.clientForm.value.finNoticeOutcome;
        this.client.orgType = this.clientForm.value.orgType;
        this.client.salesAmount = this.clientForm.value.salesAmount;
        this.client.comment = this.clientForm.value.comment;
    }

    submit() {
        this.mapFormControlToObject();

        this.clientService.addClient(this.client).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    this.notificationService.success('Client created!', '', {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                } else {
                    this.notificationService.error('Client could not be added!', jsonResp.operationMessage, {
                        timeOut: 3000,
                        showProgressBar: true,
                        pauseOnHover: true,
                        clickToClose: false,
                        clickIconToClose: true
                    });
                }
            },
            err => {
                this.notificationService.error('Client could not be added!', err.toString(), {
                    timeOut: 3000,
                    showProgressBar: true,
                    pauseOnHover: true,
                    clickToClose: false,
                    clickIconToClose: true
                });
            });
    }

}
