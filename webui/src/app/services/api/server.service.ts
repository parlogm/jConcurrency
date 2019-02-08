import { Injectable, Inject } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { ApiRequestService } from './api-request.service';
import { HttpParams} from "@angular/common/http";

@Injectable()
export class ServerService {
    constructor(
        private apiRequest: ApiRequestService
    ) {}

    completed: boolean;

    getServers(page?:number, size?:number): Observable<any> {
        //Create Request URL params
        let me = this;
        let params: HttpParams = new HttpParams();
        params = params.append('page', typeof page === "number"? page.toString():"0");
        params = params.append('size', typeof size === "number"? size.toString():"1000");

        return this.apiRequest.get('api/servers',params);
    }

    addServer(server?:Object) {
        return this.apiRequest.post('api/servers/create', server);
    }

    updateServer(server?:Object) {
        //this.apiRequest.put('api/servers/update', server);
        this.apiRequest.post('api/servers/update', server).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    console.log("success ba");
                }
            },
            err => {
                console.error("error ba");
            });
    }

    deleteServer(id?:string) {
        /*this.apiRequest.delete('api/servers/' + id).subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === "SUCCESS"){
                    console.log("success ba");
                }
            },
            err => {
                console.error("error ba");
            });*/
        return this.apiRequest.delete('api/servers/' + id);
    }

    getServerStats(field:string): Observable<any> {
        return this.apiRequest.get('api/server-stats/' + field );
    }

    getGraphiteStats(field:string): Observable<any> {
        return this.apiRequest.get('api/server-stats-graphite/' + field);
    }

}
