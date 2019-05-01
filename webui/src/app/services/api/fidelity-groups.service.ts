import { Injectable, Inject } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { ApiRequestService } from './api-request.service';
import { HttpParams} from "@angular/common/http";

@Injectable()
export class FidelityGroupsService {
    constructor(
        private apiRequest: ApiRequestService
    ) {}

    completed: boolean;

    getFG(page?:number, size?:number): Observable<any> {
        //Create Request URL params
        let me = this;
        let params: HttpParams = new HttpParams();
        params = params.append('page', typeof page === "number"? page.toString():"0");
        params = params.append('size', typeof size === "number"? size.toString():"1000");

        return this.apiRequest.get('api/fgroups',params);
    }

    addFG(fn?:Object) {
        return this.apiRequest.post('api/fgroups/create', fn);
    }

    updateFG(fg?:Object) {
        //this.apiRequest.put('api/servers/update', server);
        this.apiRequest.post('api/fgroups/update', fg).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    console.log("success ba");
                }
            },
            err => {
                console.error("error ba");
            });
    }

    deleteFG(id?:string) {
        /*this.apiRequest.delete('api/fgroups/' + id).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    console.log("success ba");
                }
            },
            err => {
                console.error("error ba");
            });*/
        return this.apiRequest.delete('api/fgroups/' + id);
    }

}
