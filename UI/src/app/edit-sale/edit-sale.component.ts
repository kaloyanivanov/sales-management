import { Component, OnInit } from '@angular/core';
import { Sale } from '../view-sales/Sales';
import { AuthorizationService } from '../authorization.service';
import { LoginService } from '../login.service';


@Component({
  selector: 'app-edit-sale',
  templateUrl: './edit-sale.component.html',
  styleUrls: ['./edit-sale.component.css']
})
export class EditSaleComponent implements OnInit {
  loaded:boolean=false
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

  getSale(event: Event){
    event.preventDefault()
    this.message=null
    if(this.checkEnterInputs()) return
    var id=document.forms["enter"]["id"].value
    id=parseInt(id)
    this.Auth.getSale(this.email,id,this.password).subscribe(data => {
      if(data!=null) {
        this.sale.id=data.id
        this.sale.clientName=data.clientName
        this.sale.date=data.date
        this.sale.productId=data.productId
        this.sale.quantitySold=data.quantitySold
        this.sale.sum=data.sum
        this.loaded=true}
     },
     (error)=>{
       this.colour="red"
       this.message="Error connecting the server"
   })
  }

  editSale(event: Event){
  event.preventDefault()
  this.message=null
  if(this.checkEditInputs())return
   var sale:Sale=new Sale()
   var saleId=document.forms["enter"]["id"].value
   saleId=parseInt(saleId)
   var Id=document.forms["update"]["sale_id"].value
   Id=parseInt(Id)
   sale.id=Id
   sale.agentEmail=this.email
   sale.clientName=document.forms["update"]["customer"].value
   sale.date=document.forms["update"]["date"].value
   var productId=document.forms["update"]["product_id"].value
   productId=parseInt(productId)
   sale.productId=productId
   sale.quantitySold=document.forms["update"]["quantity"].value
   sale.sum=document.forms["update"]["sum"].value
   this.Auth.editSale(this.email,saleId,sale,this.password).subscribe(data => {
    this.message=data.message
      if(data.type=="message") this.colour="green"
      if(data.type=="error") this.colour="red"
   },
   (error)=>{
     this.colour="red"
     this.message="Error connecting the server"
 })
  }


  checkEnterInputs(){
    var id=document.forms["enter"]["id"].value.trim()
    if(id==""){
      this.message="No empty fields allowed"
      this.colour="red"
      return true
    }
    if(!isFinite(id)){
      this.message="Id must be number"
      this.colour="red"
      return true
    }
    return false
  }

  checkEditInputs()
  {
    var saleId=document.forms["update"]["sale_id"].value
    var customer=document.forms["update"]["customer"].value
    var date=document.forms["update"]["date"].value
    var productId=document.forms["update"]["product_id"].value
    var quantitySold=document.forms["update"]["quantity"].value
    var sum=document.forms["update"]["sum"].value
    if(saleId==""||customer==""||date==""||productId==""||quantitySold==""||sum==""){
      this.message="No empty fields allowed"
      this.colour="red"
      return true
    }
    if(!isFinite(saleId)||!isFinite(productId)|| !isFinite(quantitySold)|| !isFinite(sum)){
      this.message="Id, product id, quantity sold and sum must be numbers"
      this.colour="red"
      return true
    }
    return false
  }

}
