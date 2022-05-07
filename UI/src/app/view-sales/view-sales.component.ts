import { Component, OnInit } from '@angular/core';
import { AuthorizationService } from '../authorization.service';
import { LoginService } from '../login.service';
import { Sale } from './Sales';

@Component({
  selector: 'app-view-sales',
  templateUrl: './view-sales.component.html',
  styleUrls: ['./view-sales.component.css']
})
export class ViewSalesComponent implements OnInit {
  message:string
  email:string
  sales:Sale[]=[]
  password:string
  constructor(private Auth:AuthorizationService, private data:LoginService) { }

  ngOnInit(): void {
    this.data.currentEmail.subscribe(data=>this.email=data)
    this.data.currentPassword.subscribe(data=>this.password=data)
    this.viewSales()
  }

  viewSales(){
    this.Auth.getAgentSales(this.email).subscribe(result => {
     this.sales=result
   }, 
    (error) => this.message="Error connecting the server")
 }
}
