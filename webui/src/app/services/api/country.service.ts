import { Injectable, Inject } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { ApiRequestService } from './api-request.service';
import { HttpParams} from "@angular/common/http";

@Injectable()
export class CountryService {
    constructor(
        private apiRequest: ApiRequestService
    ) {}

    completed: boolean;

    getCountries(page?:number, size?:number): Observable<any> {
        //Create Request URL params
        let me = this;
        let params: HttpParams = new HttpParams();
        params = params.append('page', typeof page === "number"? page.toString():"0");
        params = params.append('size', typeof size === "number"? size.toString():"1000");

        return this.apiRequest.get('api/countries',params);
    }

    addCountry(country?:Object) {
        return this.apiRequest.post('api/countries/create', country);
    }

    updateCountry(country?:Object) {
        return this.apiRequest.post('api/countries/update', country);
        /*this.apiRequest.post('api/countries/update', country).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    console.log("success");
                }
            },
            err => {
                console.error("error");
            });*/
    }

    deleteCountry(id?:string) {
        return this.apiRequest.delete('api/countries/' + id);
    }

}
