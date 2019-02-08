import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent  }       from './home.component';

import { LoginComponent        }   from './pages/login/login.component';
import { LogoutComponent       }   from './pages/logout/logout.component';
import { DashboardComponent    }   from './pages/dashboard/dashboard.component';
import { ServerManageComponent } from "./pages/servers/server_manage/server_manage.component";

import { AuthGuard } from './services/auth_guard.service';
import { PageNotFoundComponent }  from './pages/404/page-not-found.component';
import { ServerAddComponent } from "./pages/servers/server_add/server_add.component";
import {ServerComponent} from "./pages/servers/server.component";
import {ServerStatsComponent} from "./pages/servers/server_stats/server_stats.component";
import {GraphiteMetricsCompoment} from "./pages/graphite/graphite_metrics/graphite_metrics.component";

export const routes: Routes = [
  //Important: The sequence of path is important as the router go over then in sequential manner
  { path: '', redirectTo: '/home/dashboard/order', pathMatch: 'full' },
  {
    path: 'home',
    component: HomeComponent,
    canActivate:[AuthGuard],
    children:[  // Children paths are appended to the parent path
        { path: '', redirectTo: '/home/dashboard/server', pathMatch: 'full', data:[{selectedHeaderItemIndex:1, selectedSubNavItemIndex:-1}] },  // Default path (if no deep path is specified for home component like webui/home then it will by default show ProductsComponent )
        {
            path     : 'dashboard',
            component: DashboardComponent,
            data     : [{selectedHeaderItemIndex:0, selectedSubNavItemIndex:-1}],
            children :[
                { path: ''        , redirectTo: '/home/dashboard/graphite', pathMatch: 'full'},
                { path: 'graphite'   , component: GraphiteMetricsCompoment     , data:[{selectedHeaderItemIndex:0, selectedSubNavItemIndex:0}]  },
                { path: 'server'   , component: ServerStatsComponent     , data:[{selectedHeaderItemIndex:0, selectedSubNavItemIndex:1}]  }
            ]
        },
        { path: '', redirectTo: '/home/servers/servers', pathMatch: 'full', data:[{selectedHeaderItemIndex:2, selectedSubNavItemIndex:-1}] },
        {
            path     : 'servers',
            component: ServerComponent,
            data     : [{selectedHeaderItemIndex:1, selectedSubNavItemIndex:-1}],
            children :[
                { path: ''        , redirectTo: '/home/servers/server_manage', pathMatch: 'full'},
                { path: 'server_manage'   , component: ServerManageComponent     , data:[{selectedHeaderItemIndex:1, selectedSubNavItemIndex:0}]  },
                { path: 'server_add'   , component: ServerAddComponent     , data:[{selectedHeaderItemIndex:1, selectedSubNavItemIndex:1}]  }
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
