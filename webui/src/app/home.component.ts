import { Component, ViewEncapsulation, ViewChild, OnInit } from '@angular/core';
import { Router,ActivatedRoute, NavigationEnd } from '@angular/router';

import { LoginService   } from './services/api/login.service';
import { UserInfoService} from './services/user-info.service';

import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/operator/switchMap';

/* CLR Icons / Shapes */
import '@clr/icons';
import '@clr/icons/shapes/all-shapes';

@Component({
  selector   : 'home-comp',
  templateUrl: './home.component.html',
  styleUrls  : ['./home.scss'],
  encapsulation: ViewEncapsulation.None
})
export class HomeComponent   {

    public showAppAlert:boolean = false;
    public appHeaderItems=[
        {
            label   : 'Dashboard',
            href    : '/home/dashboard',
            subNav  : [
                { label:"Client Stats"  , href:"/home/dashboard/client_stats"  }
            ]
        },
        {
            label   : 'Clients',
            href    : '/home/clients',
            subNav  : [
                { label:"Manage"  , href:"/home/clients/client_manage"  },
                { label:"Add client", href:"/home/clients/client_add"},
                { label:"Generate clients", href:"/home/clients/clients_generation"},
                { label:"Statistics", href:"/home/clients/client_stats"}
            ]
        },
        {
            label   : 'Nomenclatures',
            href    : '/home/nomenclatures',
            subNav  : [
                { label:"Fidelity groups"  , href:"/home/nomenclatures/fidelity_groups"  },
                { label:"Countries"  , href:"/home/nomenclatures/countries"  },
                { label:"Payment methods"  , href:"/home/nomenclatures/payment_methods"  },
                { label:"Assigned centers"  , href:"/home/nomenclatures/assigned_centers"  },
                { label:"Org types"  , href:"/home/nomenclatures/org_types"  },
                { label:"Price groups"  , href:"/home/nomenclatures/price_groups"  }
            ]
        }
    ];

    public selectedHeaderItemIndex:number=0;
    public selectedSubNavItemIndex:number=1;
    public userName: string="";

    constructor(
        private router:Router,
        private activeRoute:ActivatedRoute,
        private loginService:LoginService,
        private userInfoService:UserInfoService
    ) {
        // This block is to retrieve the data from the routes (routes are defined in app-routing.module.ts)
        router.events
        .filter(event => event instanceof NavigationEnd)
        .map( _ => this.router.routerState.root)
        .map(route => {
            while (route.firstChild) route = route.firstChild;;
            return route;
        })
        .mergeMap( route => route.data)
        .subscribe(data => {
            console.log("Route data===: ", data[0]);
            this.selectedHeaderItemIndex = data[0]?data[0].selectedHeaderItemIndex:-1;
            this.selectedSubNavItemIndex = data[0]?data[0].selectedSubNavItemIndex:-1;
        });
        this.userName = this.userInfoService.getUserName();

    }

    navbarSelectionChange(val){
        // console.log(val);
    }

    closeAppAlert(){
        this.showAppAlert=false;
    }

}
