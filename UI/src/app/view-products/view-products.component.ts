import { Component, OnInit } from '@angular/core';
import { Product } from '../add-product/Product';
import { AuthorizationService } from '../authorization.service';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-view-products',
  templateUrl: './view-products.component.html',
  styleUrls: ['./view-products.component.css']
})
export class ViewProductsComponent implements OnInit {
  message:string
  products:Product[]=[]
  constructor(private Auth:AuthorizationService) { }

  ngOnInit(): void {
    this.viewProducts()
  }

  viewProducts(){
    this.message=null
    this.Auth.getProducts().subscribe(result => {
     this.products=result
   }, 
    (error) => this.message="Error connecting the server")
 }

}
