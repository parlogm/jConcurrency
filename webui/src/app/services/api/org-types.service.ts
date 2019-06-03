import { Injectable, Inject } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { ApiRequestService } from './api-request.service';
import { HttpParams} from "@angular/common/http";

@Injectable()
export class OrgTypesService {
    constructor(
        private apiRequest: ApiRequestService
    ) {}

    completed: boolean;

    getOrgTypes(page?:number, size?:number): Observable<any> {
        //Create Request URL params
        let me = this;
        let params: HttpParams = new HttpParams();
        params = params.append('page', typeof page === "number"? page.toString():"0");
        params = params.append('size', typeof size === "number"? size.toString():"1000");

        return this.apiRequest.get('api/orgTypes',params);
    }

    addOrgType(ot?:Object) {
        return this.apiRequest.post('api/orgTypes/create', ot);
    }

    updateOrgType(ot?:Object) {
        return this.apiRequest.post('api/orgTypes/update', ot);
    }

    deleteOrgType(id?:string) {
        return this.apiRequest.delete('api/orgTypes/' + id);
    }

}
