import { Component, OnInit } from '@angular/core';
import { AuthorizationService } from '../authorization.service';
import { LoginService } from '../login.service';
import { Sale } from '../view-sales/Sales';

@Component({
  selector: 'app-analyze-sales',
  templateUrl: './analyze-sales.component.html',
  styleUrls: ['./analyze-sales.component.css']
})
export class AnalyzeSalesComponent implements OnInit {
  message:string
  email:string
  sales:Sale[]=[]
  adminEmail:string
  password:string
  constructor(private Auth:AuthorizationService, private data:LoginService) { }

  ngOnInit(): void {
    this.viewSales()
    this.data.currentEmail.subscribe(data=>this.adminEmail=data)
    this.data.currentPassword.subscribe(data=>this.password=data)
  }

  
  viewSales(){
    this.message=null
    this.Auth.getAllSales(this.adminEmail,this.password).subscribe(result => {
     this.sales=result
   }, 
    (error) => this.message="Error connecting the server")
 }

 viewSalesAgain(event:Event){
  event.preventDefault()
  this.viewSales()

 }

 viewAgentSales(event:Event){
   event.preventDefault()
   this.message=null
   if(this.checkInputs()) return
   var email=document.forms["view"]["email"].value
  this.Auth.getAgentSales(email).subscribe(result => {
   this.sales=result
 }, 
  (error) => this.message="Error connecting the server")
}

viewSalesForPriod(event:Event){
  event.preventDefault()
  this.message=null
  if(this.checkDates()) return
  var from=document.forms["date"]["from"].value
  var to=document.forms["date"]["to"].value
 this.Auth.getSalesForPeriod(from,to,this.adminEmail,this.password).subscribe(result => {
  this.sales=result
}, 
 (error) => this.message="Error connecting the server")
}

checkInputs(){
  var email=document.forms["view"]["email"].value.trim()
  if(email==""){
    this.message="No empty fields allowed"
    return true
  }
  return false
}

checkDates(){
  var from=document.forms["date"]["from"].value.trim()
  var to=document.forms["date"]["to"].value.trim()
  if(from==""||from==""){
    this.message="No empty fields allowed"
    return true
  }
  return false
}
}
