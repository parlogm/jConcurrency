import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent  }       from './home.component';

import { LoginComponent        }   from './pages/login/login.component';
import { LogoutComponent       }   from './pages/logout/logout.component';
import { DashboardComponent    }   from './pages/dashboard/dashboard.component';

import { AuthGuard } from './services/auth_guard.service';
import { PageNotFoundComponent }  from './pages/404/page-not-found.component';
import {ClientComponent} from "./pages/clients/client.component";
import {ClientManageComponent} from "./pages/clients/client_manage/client_manage.component";
import {ClientAddComponent} from "./pages/clients/client_add/client_add.component";
import {NomenclaturesComponent} from "./pages/nomenclatures/nomenclatures.component";
import {FidelityGroupsComponent} from "./pages/nomenclatures/fidelity_groups/fidelity_groups.component";

export const routes: Routes = [
  //Important: The sequence of path is important as the router go over then in sequential manner
  { path: '', redirectTo: '/home/dashboard', pathMatch: 'full' },
  {
    path: 'home',
    component: HomeComponent,
    canActivate:[AuthGuard],
    children:[  // Children paths are appended to the parent path
        { path: '', redirectTo: '/home/dashboard/client-stats', pathMatch: 'full', data:[{selectedHeaderItemIndex:1, selectedSubNavItemIndex:-1}] },  // Default path (if no deep path is specified for home component like webui/home then it will by default show ProductsComponent )
        {
            path     : 'dashboard',
            component: DashboardComponent,
            data     : [{selectedHeaderItemIndex:0, selectedSubNavItemIndex:-1}],
            children :[
                { path: ''        , redirectTo: '/home/dashboard/client-stats', pathMatch: 'full'},
                { path: 'client-stats'   , component: ClientManageComponent     , data:[{selectedHeaderItemIndex:0, selectedSubNavItemIndex:0}]  }
            ]
        },
        { path: '', redirectTo: '/home/clients/clients', pathMatch: 'full', data:[{selectedHeaderItemIndex:2, selectedSubNavItemIndex:-1}] },
        {
            path     : 'clients',
            component: ClientComponent,
            data     : [{selectedHeaderItemIndex:1, selectedSubNavItemIndex:-1}],
            children :[
                { path: ''        , redirectTo: '/home/clients/client_manage', pathMatch: 'full'},
                { path: 'client_manage'   , component: ClientManageComponent     , data:[{selectedHeaderItemIndex:1, selectedSubNavItemIndex:0}]  },
                { path: 'client_add'   , component: ClientAddComponent     , data:[{selectedHeaderItemIndex:1, selectedSubNavItemIndex:1}]  }
            ]
        },
        { path: '', redirectTo: '/home/nomenclatures/fidelity_groups', pathMatch: 'full', data:[{selectedHeaderItemIndex:3, selectedSubNavItemIndex:-1}] },
        {
            path     : 'nomenclatures',
            component: NomenclaturesComponent,
            data     : [{selectedHeaderItemIndex:2, selectedSubNavItemIndex:-1}],
            children :[
                { path: ''        , redirectTo: '/home/nomenclatures/fidelity_groups', pathMatch: 'full'},
                { path: 'fidelity_groups'   , component: FidelityGroupsComponent     , data:[{selectedHeaderItemIndex:2, selectedSubNavItemIndex:0}]  }
            ]
        },
    ]
  },
  { path: 'login' , component: LoginComponent       , data:[{selectedHeaderItemIndex:-1, selectedSubNavItemIndex:-1}] },
  { path: 'logout', component: LogoutComponent      , data:[{selectedHeaderItemIndex:-1, selectedSubNavItemIndex:-1}] },
  { path: '**'    , component: PageNotFoundComponent, data:[{selectedHeaderItemIndex:-1, selectedSubNavItemIndex:-1}] }

];
@NgModule({
  imports: [ RouterModule.forRoot(routes, {useHash:true} )],
  exports: [ RouterModule ],
  declarations:[PageNotFoundComponent]
})
export class AppRoutingModule {}
