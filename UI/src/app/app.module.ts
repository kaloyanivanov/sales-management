import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import{HttpClientModule} from '@angular/common/http';
import { AdminComponent } from './admin/admin.component';
import { LoginComponent } from './login/login.component';
import { CommercialComponent } from './commercial/commercial.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material/material.module';
import { RemoveClientComponent } from './remove-client/remove-client.component';
import { AddClientComponent } from './add-client/add-client.component';
import { EditClientComponent } from './edit-client/edit-client.component';
import { EditSaleComponent } from './edit-sale/edit-sale.component';
import { ViewAgentsComponent } from './view-agents/view-agents.component';
import { AddAgentComponent } from './add-agent/add-agent.component';
import { RemoveAgentComponent } from './remove-agent/remove-agent.component';
import { AddProductComponent } from './add-product/add-product.component';
import { RemoveProductComponent } from './remove-product/remove-product.component';
import { ViewSalesComponent } from './view-sales/view-sales.component';
import { EditAgentComponent } from './edit-agent/edit-agent.component';
import { EditProductComponent } from './edit-product/edit-product.component';
import {MatMenuModule} from '@angular/material/menu';
import { AddSaleComponent } from './add-sale/add-sale.component';
import { RemoveSaleComponent } from './remove-sale/remove-sale.component';
import { ViewClientsComponent } from './view-clients/view-clients.component';
import { AnalyzeSalesComponent } from './analyze-sales/analyze-sales.component';
import { ViewProductsComponent } from './view-products/view-products.component';



@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    LoginComponent,
    CommercialComponent,
    RemoveClientComponent,
    AddClientComponent,
    EditClientComponent,
    EditSaleComponent,
    ViewAgentsComponent,
    AddAgentComponent,
    RemoveAgentComponent,
    AddProductComponent,
    RemoveProductComponent,
    ViewSalesComponent,
    EditAgentComponent,
    EditProductComponent,
    AddSaleComponent,
    RemoveSaleComponent,
    ViewClientsComponent,
    AnalyzeSalesComponent,
    ViewProductsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NoopAnimationsModule,
    MaterialModule,
    MatMenuModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
