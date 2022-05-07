import { Component, OnInit } from '@angular/core';
import { AuthorizationService } from '../authorization.service';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-remove-sale',
  templateUrl: './remove-sale.component.html',
  styleUrls: ['./remove-sale.component.css']
})
export class RemoveSaleComponent implements OnInit {

  message:string
  colour:string
  email:string
  password:string
  constructor(private Auth:AuthorizationService, private data:LoginService) { }

  ngOnInit(): void {
    this.data.currentEmail.subscribe(data=>this.email=data)
    this.data.currentPassword.subscribe(data=>this.password=data)
  }


  removeSale(event: Event){
    event.preventDefault()
    if(this.checkInputs()) return
    var id=document.forms["remove"]["id"].value
    id=parseInt(id)
    this.Auth.removeSale(this.password,this.email,id).subscribe(data =>{
      this.message=data.message
      if(data.type=="message") this.colour="green"
      if(data.type=="error") this.colour="red"
      } ,
      (error)=>{
        this.colour="red"
        this.message="Error connecting the server"
    })
  }

  checkInputs(){
    var id=document.forms["remove"]["id"].value.trim()
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
}

