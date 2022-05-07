import { Component, OnInit } from '@angular/core';
import { AuthorizationService } from '../authorization.service';
import { LoginService } from '../login.service';
import { Sale } from '../view-sales/Sales';

@Component({
  selector: 'app-add-sale',
  templateUrl: './add-sale.component.html',
  styleUrls: ['./add-sale.component.css']
})
export class AddSaleComponent implements OnInit {

  sale:Sale=new Sale()
  message:string
  colour:string
  email:string
  password:string
  constructor(private Auth:AuthorizationService, private data:LoginService) { }

  ngOnInit(): void {
    this.data.currentEmail.subscribe(data=>this.email=data)
    this.data.currentPassword.subscribe(data=>this.password=data)
  }


  addSale(event: Event){
    event.preventDefault()
    this.message=null
    if(this.checkInputs())return
     var sale:Sale=new Sale()
     var saleId=document.forms["add"]["sale_id"].value
     saleId=parseInt(saleId)
     sale.id=saleId
     sale.agentEmail=this.email
     sale.clientName=document.forms["add"]["customer"].value
     sale.date=document.forms["add"]["date"].value
     var productId=document.forms["add"]["product_id"].value
     productId=parseInt(productId)
     sale.productId=productId
     sale.quantitySold=document.forms["add"]["quantity"].value
     sale.sum=document.forms["add"]["sum"].value
     this.Auth.addSale(this.email,this.password,sale).subscribe(data => {
      this.message=data.message
        if(data.type=="message") this.colour="green"
        if(data.type=="error") this.colour="red"
     },
     (error)=>{
       this.colour="red"
       this.message="Error connecting the server"
   })
    }


    checkInputs()
    {
      var saleId=document.forms["add"]["sale_id"].value
      var customer=document.forms["add"]["customer"].value
      var date=document.forms["add"]["date"].value
      var productId=document.forms["add"]["product_id"].value
      var quantitySold=document.forms["add"]["quantity"].value
      var sum=document.forms["add"]["sum"].value
      if(saleId==""||customer==""||date==""||productId==""||quantitySold==""||sum==""){
        this.message="No empty fields allowed"
        this.colour="red"
        return true
      }
      if(!isFinite(productId)|| !isFinite(quantitySold)|| !isFinite(sum)|| !isFinite(saleId)){
        this.message="Id, quantity sold and sum must be numbers"
        this.colour="red"
        return true
      }
      return false
    }

}


