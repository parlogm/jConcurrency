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
import {PriceGroupsComponent} from "./pages/nomenclatures/price_groups/price_groups.component";
import {AddPGComponent} from "./pages/nomenclatures/price_groups/add_pg/add_pg.component";
import {EditPGComponent} from "./pages/nomenclatures/price_groups/edit_pg/edit_pg.component";
import {AssignedCentersComponent} from "./pages/nomenclatures/assigned_centers/assigned_centers.component";
import {AddACComponent} from "./pages/nomenclatures/assigned_centers/add_ac/add_ac.component";
import {EditACComponent} from "./pages/nomenclatures/assigned_centers/edit_ac/edit_ac.component";
import {OrgTypesComponent} from "./pages/nomenclatures/org_types/org_types.component";
import {AddOTComponent} from "./pages/nomenclatures/org_types/add_ot/add_ot.component";
import {EditOTComponent} from "./pages/nomenclatures/org_types/edit_ot/edit_ot.component";
import {PaymentMethodsComponent} from "./pages/nomenclatures/payment_methods/payment_methods.component";
import {AddPMComponent} from "./pages/nomenclatures/payment_methods/add_pm/add_pm.component";
import {EditPMComponent} from "./pages/nomenclatures/payment_methods/edit_pm/edit_pm.component";

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
import {PriceGroupsService} from "./services/api/price-groups.service";
import {AssignedCenterService} from "./services/api/assigned-center.service";
import {OrgTypesService} from "./services/api/org-types.service";
import {PaymentMethodsService} from "./services/api/payment-methods.service";

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

        // Price groups
        PriceGroupsComponent,
        AddPGComponent,
        EditPGComponent,

        // Assigned centers
        AssignedCentersComponent,
        AddACComponent,
        EditACComponent,

        // Org types
        OrgTypesComponent,
        AddOTComponent,
        EditOTComponent,

        // Payment methods
        PaymentMethodsComponent,
        AddPMComponent,
        EditPMComponent,

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
        PriceGroupsService,
        AssignedCenterService,
        OrgTypesService,
        PaymentMethodsService,
    ],

    bootstrap: [AppComponent],

    entryComponents: [
        AddDialogComponent,
        EditDialogComponent,
        AddCountryComponent,
        EditCountryComponent,
        AddPGComponent,
        EditPGComponent,
        AddACComponent,
        EditACComponent,
        AddOTComponent,
        EditOTComponent,
        AddPMComponent,
        EditPMComponent
    ]
})

export class AppModule {
}
