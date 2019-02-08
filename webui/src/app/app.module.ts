import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

//Third Party Modules
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {ClarityModule} from '@clr/angular';
import {SimpleNotificationsModule} from 'angular2-notifications';
import {MomentModule} from "ngx-moment";
import { NgHttpLoaderModule } from 'ng-http-loader';

import {
    MatAutocompleteModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatDatepickerModule,
    MatDialogModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatStepperModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    MatFormFieldModule
} from '@angular/material';

//Local App Modules
import {AppRoutingModule} from './app-routing.module';

// Directives
import {TrackScrollDirective} from './directives/track_scroll/track_scroll.directive';

// Components
import {BadgeComponent} from './components/badge/badge.component';
import {LegendComponent} from './components/legend/legend.component';
import {LogoComponent} from './components/logo/logo.component';

//Pages  -- Pages too are components, they contain other components
import {AppComponent} from './app.component';
import {HomeComponent} from './home.component';
import {LoginComponent} from './pages/login/login.component';
import {LogoutComponent} from './pages/logout/logout.component';
import {DashboardComponent} from './pages/dashboard/dashboard.component';
import {ServerManageComponent} from './pages/servers/server_manage/server_manage.component';
import {ServerAddComponent} from './pages/servers/server_add/server_add.component';
import {ServerComponent} from "./pages/servers/server.component";
import {ServerStatsComponent} from "./pages/servers/server_stats/server_stats.component";
import {GraphiteComponent} from "./pages/graphite/graphite.component";
import {GraphiteMetricsCompoment} from "./pages/graphite/graphite_metrics/graphite_metrics.component";

// Services
import {AppConfig} from './app-config';
import {UserInfoService} from './services/user-info.service';
import {AuthGuard} from './services/auth_guard.service';
import {ApiRequestService} from './services/api/api-request.service';
import {TranslateService} from './services/api/translate.service';
import {LoginService} from './services/api/login.service';
import {ServerService} from './services/api/server.service';
import {GraphiteService} from "./services/api/graphite.service";

@NgModule({

    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,

        // Thirdparty Module
        NgxDatatableModule,
        NgxChartsModule,

        MomentModule,

        ClarityModule.forChild(),

        BrowserAnimationsModule,
        SimpleNotificationsModule.forRoot(),

        // Material
        MatAutocompleteModule,
        MatButtonModule,
        MatButtonToggleModule,
        MatCardModule,
        MatCheckboxModule,
        MatChipsModule,
        MatDatepickerModule,
        MatDialogModule,
        MatExpansionModule,
        MatGridListModule,
        MatIconModule,
        MatListModule,
        MatMenuModule,
        MatProgressBarModule,
        MatProgressSpinnerModule,
        MatRadioModule,
        MatRippleModule,
        MatSelectModule,
        MatSidenavModule,
        MatSlideToggleModule,
        MatSliderModule,
        MatSnackBarModule,
        MatStepperModule,
        MatTableModule,
        MatTabsModule,
        MatToolbarModule,
        MatTooltipModule,
        MatNativeDateModule,
        MatFormFieldModule,
        MatInputModule,

        NgHttpLoaderModule,

        // Local App Modules
        AppRoutingModule


    ],

    declarations: [
        // Components
        BadgeComponent,
        LegendComponent,
        LogoComponent,

        //Pages -- Pages too are components, they contain other components
        AppComponent,
        HomeComponent,
        LoginComponent,
        LogoutComponent,
        DashboardComponent,

        // Server pages
        ServerManageComponent,
        ServerAddComponent,
        ServerStatsComponent,
        ServerComponent,

        // Graphite pages
        GraphiteComponent,
        GraphiteMetricsCompoment,

        //Directives
        TrackScrollDirective
    ],

    providers: [
        AuthGuard,
        UserInfoService,
        TranslateService,
        ApiRequestService,
        LoginService,
        AppConfig,
        ServerService,
        GraphiteService,
    ],

    bootstrap: [AppComponent]
})

export class AppModule {
}