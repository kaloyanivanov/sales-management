import { Component, OnInit } from '@angular/core';
import { AuthorizationService } from '../authorization.service';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-remove-product',
  templateUrl: './remove-product.component.html',
  styleUrls: ['./remove-product.component.css']
})
export class RemoveProductComponent implements OnInit {
  message:string
  colour:string
  email:string
  password:string
  constructor(private Auth:AuthorizationService, private data:LoginService) { }

  ngOnInit(): void {
    this.data.currentEmail.subscribe(data=>this.email=data)
    this.data.currentPassword.subscribe(data=>this.password=data)
  }

  removeProduct(event: Event){
    event.preventDefault()
    if(this.checkInputs()) return
    var id=document.forms["remove"]["id"].value
    this.Auth.removeProduct(id,this.email,this.password).subscribe(data => {  
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
    return false
  }
}
