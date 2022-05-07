import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddAgentComponent } from './add-agent/add-agent.component';
import { AddClientComponent } from './add-client/add-client.component';
import { AddProductComponent } from './add-product/add-product.component';
import { AddSaleComponent } from './add-sale/add-sale.component';
import { AdminComponent } from './admin/admin.component';
import { AnalyzeSalesComponent } from './analyze-sales/analyze-sales.component';
import { AgentAuthGuard } from './auth.guard';
import { AdminAuthGuard } from './auth.guard2';
import { CommercialComponent } from './commercial/commercial.component';
import { EditAgentComponent } from './edit-agent/edit-agent.component';
import { EditClientComponent } from './edit-client/edit-client.component';
import { EditProductComponent } from './edit-product/edit-product.component';
import { EditSaleComponent } from './edit-sale/edit-sale.component';
import { LoginComponent } from './login/login.component';
import { RemoveAgentComponent } from './remove-agent/remove-agent.component';
import { RemoveClientComponent } from './remove-client/remove-client.component';
import { RemoveProductComponent } from './remove-product/remove-product.component';
import { RemoveSaleComponent } from './remove-sale/remove-sale.component';
import { ViewAgentsComponent } from './view-agents/view-agents.component';
import { ViewClientsComponent } from './view-clients/view-clients.component';
import { ViewProductsComponent } from './view-products/view-products.component';
import { ViewSalesComponent } from './view-sales/view-sales.component';

const routes: Routes = [
  {path: 'admin',
  component: AdminComponent,
  canActivate: [AdminAuthGuard],
  canActivateChild: [AdminAuthGuard],
  children:[
    {
      path: 'view/agents',
      component: ViewAgentsComponent
    },
    {
      path: 'add/agent',
      component:AddAgentComponent
    },
    {
      path: 'edit/agent',
      component:EditAgentComponent
    },
    {
      path: 'remove/agent',
      component:RemoveAgentComponent
    },
    {
      path: 'view/products',
      component: ViewProductsComponent
    },
    {
      path: 'add/product',
      component:AddProductComponent
    },
    {
      path: 'remove/product',
      component:RemoveProductComponent
    },
    {
      path: 'sales',
      component:AnalyzeSalesComponent
    },
    {
      path: 'edit/product',
      component:EditProductComponent
    },
    
  ]
  },
  {path: '',
  component: LoginComponent
  },
  {path: 'commercial',
  component: CommercialComponent,
  canActivate: [AgentAuthGuard],
  canActivateChild: [AgentAuthGuard],
  children: [
    {
        path: 'remove',
        component: RemoveClientComponent
    },
    {
      path: 'add',
      component: AddClientComponent
  },
  {
    path: 'edit/client',
    component: EditClientComponent
  },
  {
    path: 'edit/sale',
    component: EditSaleComponent
  },
  {
    path: 'add/sale',
    component: AddSaleComponent
  },
  {
    path: 'sales',
    component:ViewSalesComponent
  },
  {
    path: 'remove/sale',
    component:RemoveSaleComponent
  },
  {
    path: 'view',
    component:ViewClientsComponent
  },
]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [AgentAuthGuard, AdminAuthGuard]
})
export class AppRoutingModule { }
