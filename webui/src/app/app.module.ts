import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
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
    MatFormFieldModule, MatPaginatorModule, MatSpinner
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
import {ClientAddComponent} from "./pages/clients/client_add/client_add.component";
import {ClientComponent} from "./pages/clients/client.component";
import {ClientManageComponent} from "./pages/clients/client_manage/client_manage.component";
import {ClientsGenerationComponent} from "./pages/clients/clients_generation/clients_generation.component";
import {NomenclaturesComponent} from "./pages/nomenclatures/nomenclatures.component";
import {FidelityGroupsComponent} from "./pages/nomenclatures/fidelity_groups/fidelity_groups.component";
import {AddDialogComponent} from "./pages/nomenclatures/fidelity_groups/add_dialog/add_dialog.component";
import {EditDialogComponent} from "./pages/nomenclatures/fidelity_groups/edit_dialog/edit_dialog.component";
import {CountriesComponent} from "./pages/nomenclatures/countries/countries.component";
import {AddCountryComponent} from "./pages/nomenclatures/countries/add_country/add_country.component";
import {EditCountryComponent} from "./pages/nomenclatures/countries/edit_country/edit_country.component";

// Services
import {AppConfig} from './app-config';
import {UserInfoService} from './services/user-info.service';
import {AuthGuard} from './services/auth_guard.service';
import {ApiRequestService} from './services/api/api-request.service';
import {TranslateService} from './services/api/translate.service';
import {LoginService} from './services/api/login.service';
import {ClientService} from "./services/api/client.service";
import {FidelityGroupsService} from "./services/api/fidelity-groups.service";
import {CountryService} from "./services/api/country.service";
import {FlexLayoutModule} from "@angular/flex-layout";

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

        ClarityModule,

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
        MatPaginatorModule,

        NgHttpLoaderModule.forRoot(),

        // Local App Modules
        AppRoutingModule,
        FlexLayoutModule


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

        // Client pages
        ClientAddComponent,
        ClientManageComponent,
        ClientComponent,
        ClientsGenerationComponent,

        // Nomenclatures
        NomenclaturesComponent,

        // Fidelity groups
        FidelityGroupsComponent,
        AddDialogComponent,
        EditDialogComponent,

        // Countries
        CountriesComponent,
        AddCountryComponent,
        EditCountryComponent,

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
        ClientService,
        FidelityGroupsService,
        CountryService,
    ],

    bootstrap: [AppComponent],

    entryComponents: [
        AddDialogComponent,
        EditDialogComponent,
        AddCountryComponent,
        EditCountryComponent
    ]
})

export class AppModule {
}
