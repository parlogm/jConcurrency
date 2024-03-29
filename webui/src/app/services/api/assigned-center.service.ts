import { Injectable, Inject } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { ApiRequestService } from './api-request.service';
import { HttpParams} from "@angular/common/http";

@Injectable()
export class AssignedCenterService {
    constructor(
        private apiRequest: ApiRequestService
    ) {}

    completed: boolean;

    getCenters(page?:number, size?:number): Observable<any> {
        //Create Request URL params
        let me = this;
        let params: HttpParams = new HttpParams();
        params = params.append('page', typeof page === "number"? page.toString():"0");
        params = params.append('size', typeof size === "number"? size.toString():"1000");

        return this.apiRequest.get('api/centers',params);
    }

    addCenter(center?:Object) {
        return this.apiRequest.post('api/centers/create', center);
    }

    updateCenter(center?:Object) {
        return this.apiRequest.post('api/centers/update', center);
    }

    deleteCenter(id?:string) {
        return this.apiRequest.delete('api/centers/' + id);
    }

}
