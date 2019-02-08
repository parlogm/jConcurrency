import { Injectable, Inject } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { ApiRequestService } from './api-request.service';
import { HttpParams} from "@angular/common/http";

@Injectable()
export class GraphiteService {
    constructor(
        private apiRequest: ApiRequestService
    ) {}

    completed: boolean;

    getGraphiteAvailabilityMetrics(graphiteFilter?:Object): Observable<any> {
        return this.apiRequest.post('api/graphite-availability-metrics', graphiteFilter);
    }

    getGraphitecstpdMetrics(graphiteFilter?:Object): Observable<any> {
        return this.apiRequest.post('api/graphite-cstpd-metrics', graphiteFilter);
    }

    getGraphitelcsstdMetrics(graphiteFilter?:Object): Observable<any> {
        return this.apiRequest.post('api/graphite-lcsstd-metrics', graphiteFilter);
    }

    getGraphitecudMetrics(graphiteFilter?:Object): Observable<any> {
        return this.apiRequest.post('api/graphite-cud-metrics', graphiteFilter);
    }

    getGraphitetrMetrics(graphiteFilter?:Object): Observable<any> {
        return this.apiRequest.post('api/graphite-tr-metrics', graphiteFilter);
    }

    getGraphitetdrmdMetrics(graphiteFilter?:Object): Observable<any> {
        return this.apiRequest.post('api/graphite-tdrmd-metrics', graphiteFilter);
    }

    getGraphitetfrmdMetrics(graphiteFilter?:Object): Observable<any> {
        return this.apiRequest.post('api/graphite-tfrmd-metrics', graphiteFilter);
    }

}
