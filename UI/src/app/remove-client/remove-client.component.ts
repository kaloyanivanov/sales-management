import { Component, OnInit } from '@angular/core';
import { AuthorizationService } from '../authorization.service';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-remove-client',
  templateUrl: './remove-client.component.html',
  styleUrls: ['./remove-client.component.css']
})
export class RemoveClientComponent implements OnInit {

  message:string
  colour:string
  email:string
  password:string
  constructor(private Auth:AuthorizationService, private data:LoginService) { }

  ngOnInit(): void {
    this.data.currentEmail.subscribe(data=>this.email=data)
    this.data.currentPassword.subscribe(data=>this.password=data)
  }

  removeClient(event: Event){
    event.preventDefault()
    if(this.checkInputs()) return
    var email=document.forms["remove"]["email"].value
    this.Auth.removeClient(this.email,email,this.password).subscribe(data =>  {  
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
    var id=document.forms["remove"]["email"].value.trim()
    if(id==""){
      this.message="No empty fields allowed"
      this.colour="red"
      return true
    }
    return false
  }
}
