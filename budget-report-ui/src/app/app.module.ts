import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RouterModule} from '@angular/router';
import {AuthGuard} from './app.guard';
import {HttpClientInMemoryWebApiModule} from 'angular-in-memory-web-api';

import {GaugeModule} from 'angular-gauge';

import 'hammerjs';

import {AppComponent} from './app.component';
import {routing} from './app.routing';

import {TestComponent} from './test/test.component';
import {ProjectComponent} from './project/project.component';
import {InMemoryDataService} from "./in-memory-data.service";

@NgModule({
    declarations: [
        AppComponent,
        TestComponent,
        ProjectComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        HttpClientInMemoryWebApiModule.forRoot(
             InMemoryDataService
        ),
        BrowserAnimationsModule,
        routing,
        GaugeModule.forRoot()
    ],
    providers: [
        RouterModule,
        AuthGuard
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}