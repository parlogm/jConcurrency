import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import 'rxjs/add/operator/mergeMap';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {DatePipe} from "@angular/common";
import {UserInfoService} from "../../../services/user-info.service";
import {ClientService} from "../../../services/api/client.service";

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

    maxDate = new Date();
    backDate = new Date();

    emailFormControl = new FormControl('', [
        Validators.required,
        Validators.email,
    ]);

    constructor(private router: Router, private clientService: ClientService, private _fb: FormBuilder, private datePipe: DatePipe,
                private userInfoService:UserInfoService) {
        var me = this;
        me.clientForm = this._fb.group({
            isCorporate : false,
            name: '',
            createdAt: new Date(),
            updatedAt: new Date(),
            clientPriceGroup: '',
            salesAgentId: '',
            fidelityCardId: '',
            fidelityGroup:'',
            lastBillingDate: new Date(),
            billingRateIndex: '',
            emailConfirmation: false,
            daysFromLastBill: '',
            email:'',
            assignedCenter:'',
            country:'',
            countryCode:'',
            address: '',
            contactPhone:'',
            contact:'',
            paymentMethod:'',
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
            orgType:'',
            salesAmount: '',
            comment:'',
        });
    }

    ngOnInit() {
        var me = this;
    }



    submit() {
        alert("save");
    }

}