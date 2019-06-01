import { Injectable, Inject } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { ApiRequestService } from './api-request.service';
import { HttpParams} from "@angular/common/http";

@Injectable()
export class PaymentMethodsService {
    constructor(
        private apiRequest: ApiRequestService
    ) {}

    completed: boolean;

    getPaymentMethods(page?:number, size?:number): Observable<any> {
        //Create Request URL params
        let me = this;
        let params: HttpParams = new HttpParams();
        params = params.append('page', typeof page === "number"? page.toString():"0");
        params = params.append('size', typeof size === "number"? size.toString():"1000");

        return this.apiRequest.get('api/paymentMethods',params);
    }

    addPaymentMethod(pm?:Object) {
        return this.apiRequest.post('api/paymentMethods/create', pm);
    }

    updatePaymentMethod(pm?:Object) {
        return this.apiRequest.post('api/paymentMethods/update', pm);
    }

    deletePaymentMethod(id?:string) {
        return this.apiRequest.delete('api/paymentMethods/' + id);
    }

}
