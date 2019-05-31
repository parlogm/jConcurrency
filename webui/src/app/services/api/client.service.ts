import { Injectable, Inject } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { ApiRequestService } from './api-request.service';
import { HttpParams} from "@angular/common/http";

@Injectable()
export class ClientService {
    constructor(
        private apiRequest: ApiRequestService
    ) {}

    completed: boolean;

    getClients(page?:number, size?:number): Observable<any> {
        //Create Request URL params
        let me = this;
        let params: HttpParams = new HttpParams();
        params = params.append('page', typeof page === "number"? page.toString():"0");
        params = params.append('size', typeof size === "number"? size.toString():"1000");

        return this.apiRequest.get('api/clients',params);
    }

    addClient(client?:Object) {
        return this.apiRequest.post('api/clients/create', client);
    }

    updateClient(client?:Object) {
        //this.apiRequest.put('api/servers/update', server);
        this.apiRequest.post('api/clients/update', client).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    console.log("success ba");
                }
            },
            err => {
                console.error("error ba");
            });
    }

    deleteClient(id?:string) {
        /*this.apiRequest.delete('api/clients/' + id).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    console.log("success ba");
                }
            },
            err => {
                console.error("error ba");
            });*/
        return this.apiRequest.delete('api/clients/' + id);
    }

    getClientStats(field:string): Observable<any> {
        return this.apiRequest.get('api/client-stats/' + field );
    }

    iterativeGeneration(numberOfRecords?:number): Observable<any> {
        //Create Request URL params
        let params: HttpParams = new HttpParams();
        params = params.append('numberOfRecords', typeof numberOfRecords === "number" ? numberOfRecords.toString() : "0");

        return this.apiRequest.get('api/clients/iterativeGeneration', params);
    }

}
